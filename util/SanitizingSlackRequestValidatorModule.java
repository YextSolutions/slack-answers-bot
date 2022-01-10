package com.yext.developerapps.slackanswersbotapp.util;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SanitizingSlackRequestValidatorModule {
  static SanitizingSlackRequestValidator provideValidator(SlackRequestValidator delegate) {
    return new SanitizingSlackRequestValidator(delegate);
  }
}
