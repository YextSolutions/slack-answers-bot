package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.TextSectionBlock;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.TextObjectBuilder;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class TextOnlyTextSectionBlockBuilder extends TextSectionBlockBuilder {
  private final TextObjectBuilder textBuilder;

  TextOnlyTextSectionBlockBuilder(TextObjectBuilder textBuilder) {
    this.textBuilder = textBuilder;
  }

  @Override
  public TextSectionBlock buildSection(String text) {
    return TextSectionBlock.newBuilder().setText(textBuilder.buildTextObject(text)).build();
  }
}
