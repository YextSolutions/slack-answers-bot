package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.developerapps.slackanswersbotapp.models.SlackRequestPayload;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackRequestParser {
  SlackRequestPayload parse(String payload);
}
