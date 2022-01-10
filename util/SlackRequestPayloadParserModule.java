package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.developerapps.slackanswersbotapp.models.SlackRequestPayload;
import java.util.function.Function;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackRequestPayloadParserModule {
  static SlackRequestParser provideParser(Function<String, SlackRequestPayload> payloadBuilder) {
    return new SlackRequestPayloadParser(payloadBuilder);
  }
}
