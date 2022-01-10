package com.yext.developerapps.slackanswersbotapp.util;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public interface URLDecodingSlackRequestPayloadParserModule {
  static URLDecodingSlackRequestPayloadParser provideParser(SlackRequestParser delegate) {
    return new URLDecodingSlackRequestPayloadParser(delegate);
  }
}
