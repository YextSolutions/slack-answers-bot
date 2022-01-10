package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.ButtonBlockElement;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import javax.annotation.Nullable;
import javax.inject.Inject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class EmailButtonBlockElementBuilder implements ButtonBlockElementBuilder {
  private static final String MAIL_TO_PREFIX = "mailto:";

  private final SimpleButtonBlockElementBuilder delegate;

  @Inject
  EmailButtonBlockElementBuilder(SimpleButtonBlockElementBuilder delegate) {
    this.delegate = delegate;
  }

  @Override
  public ButtonBlockElement buildButton(String text, String url, @Nullable SlackButtonStyle style) {
    return delegate.buildButton(text, buildEmailLink(url), style);
  }

  private String buildEmailLink(String url) {
    return MAIL_TO_PREFIX + url;
  }
}
