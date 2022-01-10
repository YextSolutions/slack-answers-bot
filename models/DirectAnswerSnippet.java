package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface DirectAnswerSnippet extends WithDirectAnswerSnippet {
  String value();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableDirectAnswerSnippet.Builder {}

  static DirectAnswerSnippet fromJson(String json) {
    return gson().fromJson(json, DirectAnswerSnippet.class);
  }
}
