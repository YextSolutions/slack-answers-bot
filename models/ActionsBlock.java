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
public interface ActionsBlock extends SlackBlock, WithActionsBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.ACTIONS;
  }

  @Parameter
  List<ButtonBlockElement> elements();

  static ActionsBlock of(List<ButtonBlockElement> elements) {
    return ImmutableActionsBlock.of(elements);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableActionsBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
