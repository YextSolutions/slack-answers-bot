package com.yext.developerapps.slackanswersbotapp.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.yext.developerapps.slackanswersbotapp.models.SlackRequestPayload;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public class URLDecodingSlackRequestPayloadParser implements SlackRequestParser {
  private final SlackRequestParser delegate;

  URLDecodingSlackRequestPayloadParser(SlackRequestParser delegate) {
    this.delegate = delegate;
  }

  @Override
  public SlackRequestPayload parse(String payload) {
    return delegate.parse(decode(payload));
  }

  String decode(String payload) {
    return URLDecoder.decode(payload, StandardCharsets.UTF_8);
  }
}
