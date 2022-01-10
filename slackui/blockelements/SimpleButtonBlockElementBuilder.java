package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.ButtonBlockElement;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import javax.annotation.Nullable;
import javax.inject.Inject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class SimpleButtonBlockElementBuilder implements ButtonBlockElementBuilder {
  private final PlainTextObjectBuilder textBuilder;

  @Inject
  SimpleButtonBlockElementBuilder(PlainTextObjectBuilder textBuilder) {
    this.textBuilder = textBuilder;
  }

  @Override
  public ButtonBlockElement buildButton(
      String text, @Nullable String url, @Nullable SlackButtonStyle style) {
    ButtonBlockElement button =
        ButtonBlockElement.newBuilder()
            .setText(textBuilder.buildTextObject(text))
            .setUrl(url)
            .setStyle(style)
            .setActionId(text)
            .build();
    return button;
  }
}
