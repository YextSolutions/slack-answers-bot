package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface HeaderBlock extends SlackBlock, WithHeaderBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.HEADER;
  }

  @Parameter
  TextObject text();

  static HeaderBlock of(TextObject text) {
    return ImmutableHeaderBlock.of(text);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableHeaderBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
