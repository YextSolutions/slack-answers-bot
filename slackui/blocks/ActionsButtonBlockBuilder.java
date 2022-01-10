package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.ActionsBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.ButtonBlockElementBuilder;
import java.util.List;
import javax.annotation.Nullable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class ActionsButtonBlockBuilder implements ActionsBlockBuilder {
  private final ButtonBlockElementBuilder buttonBuilder;

  ActionsButtonBlockBuilder(ButtonBlockElementBuilder buttonBuilder) {
    this.buttonBuilder = buttonBuilder;
  }

  @Override
  public ActionsBlock buildBlock(String text, String url, @Nullable SlackButtonStyle style) {
    return ActionsBlock.of(List.of(buttonBuilder.buildButton(text, url, style)));
  }
}
