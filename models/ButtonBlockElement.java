package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ButtonBlockElement extends WithButtonBlockElement {
  @Derived
  default SlackBlockElementType type() {
    return SlackBlockElementType.BUTTON;
  }

  TextObject text();

  @SerializedName("action_id")
  String actionId();

  Optional<String> url();

  Optional<SlackButtonStyle> style();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableButtonBlockElement.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
