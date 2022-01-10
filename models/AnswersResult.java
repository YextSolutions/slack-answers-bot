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
public interface AnswersResult extends WithAnswersResult {
  AnswersResultData data();

  static Builder newBuilder() { return new Builder(); }

  class Builder extends ImmutableAnswersResult.Builder {}

  static AnswersResult fromJson(String result) {
    return gson().fromJson(result, AnswersResult.class);
  }
}
