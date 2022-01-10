package com.yext.developerapps.slackanswersbotapp.util;

import com.google.common.annotations.VisibleForTesting;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class SanitizingSlackRequestValidator implements SlackRequestValidator {
  private SlackRequestValidator delegate;

  SanitizingSlackRequestValidator(SlackRequestValidator delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean isValid(String body, String timestamp, String signature) {
    return delegate.isValid(body, sanitize(timestamp), sanitize(signature));
  }

  /** @return a sanitized String, without the surrounding square brackets */
  @VisibleForTesting
  String sanitize(String str) {
    return str.substring(1, str.length() - 1);
  }
}
