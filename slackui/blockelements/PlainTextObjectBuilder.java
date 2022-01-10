package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.SlackTextFormatType;
import com.yext.developerapps.slackanswersbotapp.models.TextObject;
import javax.inject.Inject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class PlainTextObjectBuilder implements TextObjectBuilder {
  @Inject
  PlainTextObjectBuilder() {}

  @Override
  public TextObject buildTextObject(String text) {
    return TextObject.of(SlackTextFormatType.PLAIN_TEXT, text);
  }
}
