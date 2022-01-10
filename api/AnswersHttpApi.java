package com.yext.developerapps.slackanswersbotapp.api;

import com.yext.developerapps.slackanswersbotapp.models.AnswersConfig;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerappscommon.http.HttpExceptionHandler;
import com.yext.util.http.HttpException;
import com.yext.util.http.RequestBuilder;
import com.yext.util.http.RequestExecutor;
import java.io.IOException;
import java.util.function.Function;
import org.apache.http.client.methods.HttpGet;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public class AnswersHttpApi implements AnswersApi {
  private static final String BASE_URL = "https://liveapi.yext.com/v2/accounts/";
  private static final String UNIVERSAL_QUERY_ENDPOINT = "/answers/query";

  private static final String ANSWERS_V_PARAM_VALUE = "20211111";
  private static final String SLACK_ANSWERS_BOT_SOURCE = "slack";

  private static final HttpExceptionHandler httpExceptionHandler = HttpExceptionHandler.getDefault();

  private final Function<RequestBuilder, RequestExecutor> executor;

  AnswersHttpApi(
      Function<RequestBuilder, RequestExecutor> executor) {
    this.executor = executor;
  }

  public AnswersUniversalSearchResponse getUniversalSearchResults(String query, AnswersConfig config) {
    try {
      String url = BASE_URL + config.accountId() + UNIVERSAL_QUERY_ENDPOINT;
      RequestBuilder builder =
          RequestBuilder.forUrl(url)
              .setMethod(HttpGet.METHOD_NAME)
              .addParam("v", ANSWERS_V_PARAM_VALUE)
              .addParam("experienceKey", config.experienceKey())
              .addParam("locale", config.locale())
              .addParam("api_key", config.apiKey())
              .addParam("input", query)
              .addParam("source", SLACK_ANSWERS_BOT_SOURCE);
      return AnswersUniversalSearchResponse.fromJson(executor.apply(builder).execute());
    } catch (HttpException ex) {
      throw httpExceptionHandler.handleHttpException(ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
