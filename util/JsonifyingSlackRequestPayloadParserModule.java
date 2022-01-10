package com.yext.developerapps.slackanswersbotapp.util;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface JsonifyingSlackRequestPayloadParserModule {
  static SlackRequestParser provideParser(SlackRequestParser delegate) {
    return new JsonifyingSlackRequestPayloadParser(delegate);
  }
}
