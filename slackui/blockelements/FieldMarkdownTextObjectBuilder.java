package com.yext.developerapps.slackanswersbotapp.slackui.blockelements;

import com.yext.developerapps.slackanswersbotapp.models.TextObject;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class FieldMarkdownTextObjectBuilder implements TextObjectBuilder {
  private final String field;
  private final MarkdownTextObjectBuilder delegate;

  @AssistedInject
  FieldMarkdownTextObjectBuilder(@Assisted String field, MarkdownTextObjectBuilder delegate) {
    this.field = field;
    this.delegate = delegate;
  }

  @Override
  public TextObject buildTextObject(String text) {
    return delegate.buildTextObject(buildMarkdown(field, text));
  }

  private String buildMarkdown(String field, String text) {
    return "*" + field + ":* " + text;
  }

  @AssistedFactory
  public interface FieldMarkdownTextObjectBuilderFactory {
    FieldMarkdownTextObjectBuilder create(String field);
  }
}
