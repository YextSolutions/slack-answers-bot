package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.TextSectionBlock;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public abstract class TextSectionBlockBuilder implements SlackBlockBuilder {
  public SlackBlock buildBlock(String text) {
    return buildSection(text);
  }

  abstract TextSectionBlock buildSection(String text);
}
