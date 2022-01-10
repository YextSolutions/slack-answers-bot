package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface TextObject extends WithTextObject {
  @Parameter
  SlackTextFormatType type();

  @Parameter
  String text();

  static TextObject of(SlackTextFormatType type, String text) {
    return ImmutableTextObject.of(type, text);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableTextObject.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
