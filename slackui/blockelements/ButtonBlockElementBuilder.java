package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.ButtonBlockElement;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import javax.annotation.Nullable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface ButtonBlockElementBuilder {
  ButtonBlockElement buildButton(
      String text, @Nullable String url, @Nullable SlackButtonStyle style);
}
