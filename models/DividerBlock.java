package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface DividerBlock extends SlackBlock, WithDividerBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.DIVIDER;
  }

  static DividerBlock of() {
    return DividerBlock.newBuilder().build();
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableDividerBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
