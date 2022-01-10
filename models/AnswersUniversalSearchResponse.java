package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
@Immutable
@TypeAdapters
@YextStyle
public interface AnswersUniversalSearchResponse extends WithAnswersUniversalSearchResponse {
  UniversalResponse response();

  static Builder newBuilder() { return new Builder(); }

  class Builder extends ImmutableAnswersUniversalSearchResponse.Builder {}

  static AnswersUniversalSearchResponse fromJson(String response) {
    return gson().fromJson(response, AnswersUniversalSearchResponse.class);
  }
}
