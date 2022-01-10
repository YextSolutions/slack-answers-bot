package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.TextObject;
import javax.inject.Inject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class BoldMarkdownTextObjectBuilder implements TextObjectBuilder {
  private final MarkdownTextObjectBuilder delegate;

  @Inject
  BoldMarkdownTextObjectBuilder(MarkdownTextObjectBuilder delegate) {
    this.delegate = delegate;
  }

  @Override
  public TextObject buildTextObject(String text) {
    return delegate.buildTextObject(buildMarkdown(text));
  }

  private String buildMarkdown(String text) {
    return "*" + text + "*";
  }
}
