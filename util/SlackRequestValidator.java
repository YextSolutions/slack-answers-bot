package com.yext.developerapps.slackanswersbotapp.util;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackRequestValidator {

  /**
   * This method validates that a request from Slack is indeed authentic. This validation is done as
   * per Slack's API spec, which can be found here:
   * https://api.slack.com/authentication/verifying-requests-from-slack
   */
  boolean isValid(String body, String timestamp, String signature);
}
