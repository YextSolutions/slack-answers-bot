package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.List;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
@Immutable
@TypeAdapters
@YextStyle
public interface AnswersModule extends WithAnswersModule {
  String verticalConfigId();

  int resultsCount();

  List<AnswersResult> results();

  static Builder newBuilder() { return new Builder(); }

  class Builder extends ImmutableAnswersModule.Builder {}

  static AnswersModule fromJson(String module) {
    return gson().fromJson(module, AnswersModule.class);
  }
}
