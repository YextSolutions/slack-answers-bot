package controllers;

import static controllers.Messages.SLACK_APP_ERROR_MESSAGE;
import static controllers.Messages.TELESCOPE_ERROR_MESSAGE;
import static controllers.Messages.USER_ERROR_RESPONSE;

import com.google.common.base.Strings;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerapps.slackanswersbotapp.models.SlackResponse;
import com.yext.developerapps.slackanswersbotapp.models.SlackSlashCommandsRequestPayload;
import com.yext.developerapps.slackanswersbotapp.models.SlackViewType;
import com.yext.developerapps.slackanswersbotapp.models.ViewObject;
import com.yext.developerapps.slackanswersbotapp.models.ViewsOpenRequestBody;
import com.yext.developerapps.slackanswersbotapp.slackui.DaggerSlackUIBuilderComponent;
import com.yext.developerapps.slackanswersbotapp.util.JsonifyingSlackRequestPayloadParserModule;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestParser;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestPayloadParserModule;
import com.yext.developerapps.slackanswersbotapp.util.URLDecodingSlackRequestPayloadParserModule;
import com.yext.developerappscommon.exceptions.TransientErrorException;
import helpers.security.PublicAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@PublicAccess
public class TelescopeSlashCommandsController extends TelescopeController {
  private static final int MAX_VERTICALS_TO_BUILD = 6;
  private static final int MAX_RESULTS_PER_VERTICAL = 2;

  private static final Logger logger = LogManager.getLogger();
  private static final SlackRequestParser requestParser =
      URLDecodingSlackRequestPayloadParserModule.provideParser(
          JsonifyingSlackRequestPayloadParserModule.provideParser(
              SlackRequestPayloadParserModule.provideParser(
                  SlackSlashCommandsRequestPayload::fromJson)));

  public static void handleRequest() {
    logger.info("Handling slash command request");
    String body = readRequest();
    if (Strings.isNullOrEmpty(body)) {
      error();
    } else if (!isValid(validator, body)) {
      logger.info("Request could not be verified. Ignoring request.");
      forbidden("This request is not valid.");
    }
    try {
      SlackSlashCommandsRequestPayload requestPayload = parseRequest(body);
      String query = requestPayload.text().orElse("");
      if (query.equals("help")) {
        renderText(buildHelpMessage(requestPayload.userName()));
      } else if (!Strings.isNullOrEmpty(query)) {
        logger.info("Handling query: " + query);
        respond(query, requestPayload.channelId(), requestPayload.triggerId());
      } else {
        logger.warn("No user input found for querying the Answers API.");
        renderText(USER_ERROR_RESPONSE);
      }
      renderText(buildSuccessMessage(query));
    } catch (Exception ex) {
      logger.warn(ex);
      error();
    }
  }

  private static SlackSlashCommandsRequestPayload parseRequest(String payload) {
    return (SlackSlashCommandsRequestPayload) requestParser.parse(payload);
  }

  private static void respond(String query, String channelId, String triggerId) {
    try {
      ViewObject viewPayload = buildViewPayload(query, queryAnswersUniversalSearch(query, config));
      if (viewPayload.blocks().isEmpty()) {
        renderText(buildNoAnswersFoundMessage(query));
      }
      openView(buildViewsOpenRequestBody(triggerId, viewPayload), channelId);
    } catch (TransientErrorException ex) {
      logger.warn(ex);
      renderText(TELESCOPE_ERROR_MESSAGE);
    } catch (Exception ex) {
      logger.error(ex);
      renderText(TELESCOPE_ERROR_MESSAGE);
    }
  }

  /**
   * @return a ViewObject by building an instance of the ViewPayloadObjectBuilder and calling its
   *     buildViewPayload() method
   */
  private static ViewObject buildViewPayload(
      String query, AnswersUniversalSearchResponse answersResponse) {
    return DaggerSlackUIBuilderComponent.builder()
        .response(answersResponse)
        .viewType(SlackViewType.MODAL)
        .query(query)
        .appName(APP_NAME)
        .answersUrl(TELESCOPE_PROD_URL)
        .maxVerticalsToBuild(MAX_VERTICALS_TO_BUILD)
        .maxResultsPerVertical(MAX_RESULTS_PER_VERTICAL)
        .build()
        .viewPayloadBuilder()
        .buildViewPayload();
  }

  private static void openView(String payload, String channelId) {
    SlackResponse slackResponse = slackApi.openViews(payload, botToken);
    logger.info("Slack response: " + slackResponse);
    if (!slackResponse.ok()) {
      renderText(SLACK_APP_ERROR_MESSAGE);
    }
  }

  private static String buildViewsOpenRequestBody(String triggerId, ViewObject viewPayload) {
    return ViewsOpenRequestBody.of(triggerId, viewPayload).toJson();
  }
}
