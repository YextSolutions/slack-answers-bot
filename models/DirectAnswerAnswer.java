package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface DirectAnswerAnswer extends WithDirectAnswerAnswer {
  Optional<String> value();

  Optional<DirectAnswerSnippet> snippet();

  String fieldType();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableDirectAnswerAnswer.Builder {}

  static DirectAnswerAnswer fromJson(String json) {
    return gson().fromJson(json, DirectAnswerAnswer.class);
  }
}
