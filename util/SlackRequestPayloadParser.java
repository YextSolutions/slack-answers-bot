package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.developerapps.slackanswersbotapp.models.SlackRequestPayload;
import com.yext.developerappscommon.exceptions.TransientErrorException;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class SlackRequestPayloadParser implements SlackRequestParser {
  private static final Logger logger = LogManager.getLogger();

  private final Function<String, SlackRequestPayload> payloadBuilder;

  SlackRequestPayloadParser(Function<String, SlackRequestPayload> payloadBuilder) {
    this.payloadBuilder = payloadBuilder;
  }

  @Override
  public SlackRequestPayload parse(String jsonPayload) {
    try {
      return payloadBuilder.apply(jsonPayload);
    } catch (Exception ex) {
      logger.info("slack payload: " + jsonPayload);
      throw new TransientErrorException(ex);
    }
  }
}
