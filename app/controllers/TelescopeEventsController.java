package controllers;

import static controllers.Messages.TELESCOPE_ERROR_MESSAGE;
import static controllers.Messages.USER_ERROR_RESPONSE;

import com.google.common.base.Strings;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerapps.slackanswersbotapp.models.ChatPostMessageRequestBody;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackEvent;
import com.yext.developerapps.slackanswersbotapp.models.SlackEventsRequestPayload;
import com.yext.developerapps.slackanswersbotapp.models.SlackViewType;
import com.yext.developerapps.slackanswersbotapp.slackui.DaggerSlackUIBuilderComponent;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestParser;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestPayloadParserModule;
import com.yext.developerapps.slackanswersbotapp.util.URLDecodingSlackRequestPayloadParserModule;
import com.yext.developerappscommon.exceptions.TransientErrorException;
import helpers.security.PublicAccess;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@PublicAccess
public class TelescopeEventsController extends TelescopeController {
  private static final String NOTIFICATION_TEXT = "Telescope";
  private static final int MAX_VERTICALS_TO_BUILD = 3;
  private static final int MAX_RESULTS_PER_VERTICAL = 2;

  private static final Logger logger = LogManager.getLogger();
  private static final SlackRequestParser requestParser =
      URLDecodingSlackRequestPayloadParserModule.provideParser(
          SlackRequestPayloadParserModule.provideParser(SlackEventsRequestPayload::fromJson));

  public static void handleRequest() {
    logger.info("Handling Telescope event request.");
    String body = readRequest();
    if (Strings.isNullOrEmpty(body)) {
      error();
    } else if (!isValid(validator, body)) {
      logger.info("Request could not be verified. Ignoring request.");
      forbidden("This request is not valid.");
    }
    try {
      SlackEventsRequestPayload requestPayload = parseRequest(body);
      SlackEvent event = requestPayload.event();
      if (event.isValidEvent()) {
        String query = extractQuery(event.text());
        if (query.equals("help")) {
          postMessage(event.channel(), List.of(), buildHelpMessage(event.user()), event.threadId());
        } else if (!Strings.isNullOrEmpty(query)) {
          logger.info("Handling query: " + query);
          respond(query, event.channel(), event.threadId());
        } else {
          logger.warn("No user input found for querying the Answers API.");
          postMessage(event.channel(), List.of(), USER_ERROR_RESPONSE, event.threadId());
        }
      }
    } catch (Exception ex) {
      logger.warn(ex);
      error();
    }
  }

  private static SlackEventsRequestPayload parseRequest(String payload) {
    return (SlackEventsRequestPayload) requestParser.parse(payload);
  }

  /** This method removes all @-mentions from text to build query. */
  private static String extractQuery(String text) {
    return text.replaceAll("<@U.*>", "").trim();
  }

  /** This method queries the Answers API and then responds to Slack. */
  private static void respond(String query, String channelId, String threadId) {
    try {
      List<SlackBlock> blocks = buildBlockView(query, queryAnswersUniversalSearch(query, config));
      if (blocks.isEmpty()) {
        postMessage(channelId, blocks, buildNoAnswersFoundMessage(query), threadId);
      }
      postMessage(channelId, blocks, NOTIFICATION_TEXT, threadId);
    } catch (TransientErrorException ex) {
      logger.warn(ex);
      postTelescopeErrorMessage(channelId, threadId);
    } catch (Exception ex) {
      logger.error(ex);
      postTelescopeErrorMessage(channelId, threadId);
    }
  }

  private static void postTelescopeErrorMessage(String channelId, @Nullable String threadId) {
    postMessage(channelId, List.of(), TELESCOPE_ERROR_MESSAGE, threadId);
    error();
  }

  private static void postMessage(
      String channel, List<SlackBlock> blocks, String message, @Nullable String threadId) {
    slackApi.postChatMessage(
        buildChatPostMessageRequestBody(channel, blocks, message, threadId), botToken);
    ok();
  }

  private static String buildChatPostMessageRequestBody(
      String channel, List<SlackBlock> blocks, String message, @Nullable String threadId) {
    return ChatPostMessageRequestBody.newBuilder()
        .setChannel(channel)
        .setText(message)
        .setBlocks(blocks)
        .setThreadId(threadId)
        .build()
        .toJson();
  }

  /**
   * @return a List of SlackBlock by building an instance of the BlockListViewBuilder and calling
   *     its buildBlockView() method
   */
  private static List<SlackBlock> buildBlockView(
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
        .blockViewBuilder()
        .buildBlockView();
  }
}
