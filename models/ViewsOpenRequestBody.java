package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ViewsOpenRequestBody extends WithViewsOpenRequestBody {
  @Parameter
  @SerializedName("trigger_id")
  String triggerId();

  @Parameter
  ViewObject view();

  static ViewsOpenRequestBody of(String triggerId, ViewObject view) {
    return ImmutableViewsOpenRequestBody.of(triggerId, view);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableViewsOpenRequestBody.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
