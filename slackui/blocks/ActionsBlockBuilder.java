package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.ActionsBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import javax.annotation.Nullable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface ActionsBlockBuilder {
  ActionsBlock buildBlock(String text, String url, @Nullable SlackButtonStyle style);
}
