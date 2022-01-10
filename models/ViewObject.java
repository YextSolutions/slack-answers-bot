package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ViewObject {
  SlackViewType type();

  TextObject title();

  List<? extends SlackBlock> blocks();

  Optional<TextObject> close();

  Optional<TextObject> submit();

  @SerializedName("clear_on_close")
  Optional<Boolean> clearOnClose();

  @SerializedName("submit_disabled")
  Optional<Boolean> submitDisabled();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableViewObject.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
