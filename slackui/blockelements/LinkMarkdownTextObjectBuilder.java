package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.TextObject;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class LinkMarkdownTextObjectBuilder implements TextObjectBuilder {
  private final String url;
  private final MarkdownTextObjectBuilder delegate;

  @AssistedInject
  LinkMarkdownTextObjectBuilder(@Assisted String url, MarkdownTextObjectBuilder delegate) {
    this.url = url;
    this.delegate = delegate;
  }

  @Override
  public TextObject buildTextObject(String text) {
    return delegate.buildTextObject(buildMarkdown(text, url));
  }

  private String buildMarkdown(String text, String url) {
    return "*<" + url + "|" + text + ">*";
  }

  @AssistedFactory
  public interface LinkMarkdownTextObjectBuilderFactory {
    LinkMarkdownTextObjectBuilder create(String url);
  }
}
