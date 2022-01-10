package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.List;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface FieldsSectionBlock extends SlackBlock, WithFieldsSectionBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.SECTION;
  }

  @Parameter
  List<TextObject> fields();

  static FieldsSectionBlock of(List<TextObject> fields) {
    return ImmutableFieldsSectionBlock.of(fields);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableFieldsSectionBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
