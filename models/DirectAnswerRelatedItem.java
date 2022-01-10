package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface DirectAnswerRelatedItem extends WithDirectAnswerRelatedItem {
  String verticalConfigId();

  DirectAnswerData data();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableDirectAnswerRelatedItem.Builder {}

  static DirectAnswerRelatedItem fromJson(String json) {
    return gson().fromJson(json, DirectAnswerRelatedItem.class);
  }
}
