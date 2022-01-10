package controllers;

import com.yext.developerapps.slackanswersbotapp.api.AnswersApi;
import com.yext.developerapps.slackanswersbotapp.api.AnswersHttpApiModule;
import com.yext.developerapps.slackanswersbotapp.models.AnswersConfig;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestValidator;
import controllers.common.YextController;
import helpers.security.PermitUnsafeCsrfPost;
import helpers.security.PublicAccess;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Controller for slackanswersbot */
@PublicAccess
@PermitUnsafeCsrfPost
public abstract class SlackAnswersBotController extends YextController {
  protected static final String SLACK_REQUEST_HEADER_TIMESTAMP_KEY = "x-slack-request-timestamp";
  protected static final String SLACK_REQUEST_HEADER_SIG_KEY = "x-slack-signature";

  protected static final AnswersApi answersApi = AnswersHttpApiModule.provideApi();

  /** @return request body as a String or an empty String, in case of an IOException */
  protected static String readRequest() {
    try {
      return new String(request.body.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException ex) {
      return "";
    }
  }

  protected static boolean isValid(SlackRequestValidator validator, String body) {
    return validator.isValid(
        body,
        request.headers.get(SLACK_REQUEST_HEADER_TIMESTAMP_KEY).toString(),
        request.headers.get(SLACK_REQUEST_HEADER_SIG_KEY).toString());
  }

  protected static AnswersUniversalSearchResponse queryAnswersUniversalSearch(
      String query, AnswersConfig config) {
    return answersApi.getUniversalSearchResults(query, config);
  }

  /** Method to test if the server is available */
  public static void overview() {
    Soy.render();
  }
}
