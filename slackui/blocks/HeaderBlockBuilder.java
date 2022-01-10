package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.HeaderBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.PlainTextObjectBuilder;
import javax.inject.Inject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class HeaderBlockBuilder implements SlackBlockBuilder {
  private final PlainTextObjectBuilder textObjectBuilder;

  @Inject
  HeaderBlockBuilder(PlainTextObjectBuilder textObjectBuilder) {
    this.textObjectBuilder = textObjectBuilder;
  }

  @Override
  public SlackBlock buildBlock(String text) {
    return HeaderBlock.of(textObjectBuilder.buildTextObject(text));
  }
}
