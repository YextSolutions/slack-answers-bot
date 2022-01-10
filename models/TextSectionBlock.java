package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface TextSectionBlock extends SlackBlock, WithTextSectionBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.SECTION;
  }

  @Parameter
  TextObject text();

  Optional<ImageBlockElement> accessory();

  static TextSectionBlock of(TextObject text) {
    return ImmutableTextSectionBlock.of(text);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableTextSectionBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
