package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackBlockBuilder {
  SlackBlock buildBlock(String text);
}
