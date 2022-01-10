package com.yext.developerapps.slackanswersbotapp.api;

import com.yext.util.http.RequestExecutor;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackHttpApiModule {
  static SlackHttpApi provideApi() {
    return new SlackHttpApi(RequestExecutor::of);
  }
}
