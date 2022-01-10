package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.developerapps.slackanswersbotapp.models.SlackRequestPayload;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class JsonifyingSlackRequestPayloadParser implements SlackRequestParser {
  private final SlackRequestParser delegate;

  JsonifyingSlackRequestPayloadParser(SlackRequestParser delegate) {
    this.delegate = delegate;
  }

  @Override
  public SlackRequestPayload parse(String payload) {
    return delegate.parse(jsonify(payload));
  }

  String jsonify(String payload) {
    return "{\"" + payload.replaceAll("&", "\",\"").replaceAll("=", "\":\"") + "\"}";
  }
}
