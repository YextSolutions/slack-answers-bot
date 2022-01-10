package controllers;

import static controllers.Messages.BASE_SUCCESS_MESSAGE;
import static controllers.Messages.HELP_MESSAGE;
import static controllers.Messages.NO_ANSWERS_FOUND_MESSAGE;
import static controllers.Messages.TRY_AGAIN_MESSAGE;

import com.yext.developerapps.slackanswersbotapp.api.SlackApi;
import com.yext.developerapps.slackanswersbotapp.api.SlackHttpApiModule;
import com.yext.developerapps.slackanswersbotapp.models.AnswersConfig;
import com.yext.developerapps.slackanswersbotapp.util.SanitizingSlackRequestValidatorModule;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestHashValidatorModule;
import com.yext.developerapps.slackanswersbotapp.util.SlackRequestValidator;
import com.yext.util.secret.Secret;
import helpers.security.PublicAccess;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@PublicAccess
public abstract class TelescopeController extends SlackAnswersBotController {
  protected static final String APP_NAME = "Telescope";

  private static final String ANSWERS_ACCOUNT_ID = "[REDACTED]";
  private static final String ANSWERS_EXP_KEY = "[REDACTED]";
  private static final String ANSWERS_LOCALE = "en";
  protected static final String TELESCOPE_PROD_URL = "https://telescope.yext.com/search";

  private static final Secret answersApiKey = Secret.env("SLACK_ANSWERS_BOT_TELESCOPE_ANSWERS_KEY");
  protected static final Secret botToken =
      Secret.env("SLACK_ANSWERS_BOT_TELESCOPE_GLOBAL_BOT_TOKEN_V2");
  private static final Secret signingSecret =
      Secret.env("SLACK_ANSWERS_BOT_TELESCOPE_GLOBAL_SIGNING_SECRET_V1");

  protected static final SlackRequestValidator validator =
      SanitizingSlackRequestValidatorModule.provideValidator(
          SlackRequestHashValidatorModule.provideValidator(signingSecret));

  protected static final AnswersConfig config =
      AnswersConfig.newBuilder()
          .setEnterpriseId("")
          .setWorkspaceId("")
          .setApiKey(answersApiKey.get())
          .setAccountId(ANSWERS_ACCOUNT_ID)
          .setExperienceKey(ANSWERS_EXP_KEY)
          .setLocale(ANSWERS_LOCALE)
          .setProdUrl(TELESCOPE_PROD_URL)
          .build();

  protected static final SlackApi slackApi = SlackHttpApiModule.provideApi();

  protected static String buildHelpMessage(String name) {
    return "Hello, <@" + name + ">! " + HELP_MESSAGE;
  }

  protected static String buildSuccessMessage(String query) {
    return BASE_SUCCESS_MESSAGE + buildTelescopeUrl(query);
  }

  protected static String buildNoAnswersFoundMessage(String query) {
    return NO_ANSWERS_FOUND_MESSAGE + "*" + query + "* " + TRY_AGAIN_MESSAGE;
  }

  private static String buildTelescopeUrl(String query) {
    return TELESCOPE_PROD_URL + "?" + formatQueryParam(query);
  }

  private static String formatQueryParam(String query) {
    return "query=" + query.replaceAll(" ", "+");
  }
}
