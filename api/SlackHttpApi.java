package com.yext.developerapps.slackanswersbotapp.api;

import com.google.common.annotations.VisibleForTesting;
import com.yext.developerapps.slackanswersbotapp.models.SlackResponse;
import com.yext.developerappscommon.http.HttpExceptionHandler;
import com.yext.util.http.HttpException;
import com.yext.util.http.RequestBuilder;
import com.yext.util.http.RequestExecutor;
import com.yext.util.secret.Secret;
import java.io.IOException;
import java.util.function.Function;
import org.apache.http.client.methods.HttpPost;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class SlackHttpApi implements SlackApi {
  private static final String BASE_URL = "https://slack.com/api";
  private static final String CHAT_POST_MESSAGE_ENDPOINT = "/chat.postMessage";
  private static final String VIEWS_OPEN_ENDPOINT = "/views.open";

  private static final String AUTHORIZATION_KEY = "Authorization";
  private static final String CONTENT_TYPE_KEY = "Content-type";
  private static final String JSON_CONTENT_TYPE = "application/json";
  private static final String CHARSET_KEY = "charset";
  private static final String UTF8_CHARSET = "utf-8";

  private static final HttpExceptionHandler httpExceptionHandler =
      HttpExceptionHandler.getDefault();

  private final Function<RequestBuilder, RequestExecutor> executor;

  SlackHttpApi(Function<RequestBuilder, RequestExecutor> executor) {
    this.executor = executor;
  }

  public SlackResponse postChatMessage(String body, Secret botToken) {
    return executeRequest(BASE_URL + CHAT_POST_MESSAGE_ENDPOINT, body, botToken);
  }

  public SlackResponse openViews(String body, Secret botToken) {
    return executeRequest(BASE_URL + VIEWS_OPEN_ENDPOINT, body, botToken);
  }

  @VisibleForTesting
  SlackResponse executeRequest(String url, String body, Secret botToken) {
    try {
      RequestBuilder request =
          RequestBuilder.forUrl(url)
              .setMethod(HttpPost.METHOD_NAME)
              .addHeader(AUTHORIZATION_KEY, "Bearer " + botToken.get())
              .addHeader(CONTENT_TYPE_KEY, JSON_CONTENT_TYPE)
              .addHeader(CHARSET_KEY, UTF8_CHARSET)
              .setBody(body);
      return SlackResponse.fromJson(executor.apply(request).execute());
    } catch (HttpException ex) {
      throw httpExceptionHandler.handleHttpException(ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
