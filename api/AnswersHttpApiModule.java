package com.yext.developerapps.slackanswersbotapp.api;


import com.yext.util.http.RequestExecutor;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public interface AnswersHttpApiModule {
  static AnswersApi provideApi() {
    return new AnswersHttpApi(RequestExecutor::of);
  }
}
