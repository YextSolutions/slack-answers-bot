package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
@Immutable
@TypeAdapters
@YextStyle
public interface DirectAnswer extends WithDirectAnswer {
  DirectAnswerType type();

  DirectAnswerAnswer answer();

  Optional<DirectAnswerRelatedItem> relatedItem();

  static Builder newBuilder() { return new Builder(); }

  class Builder extends ImmutableDirectAnswer.Builder {}

  static DirectAnswer fromJson(String answer) {
    return gson().fromJson(answer, DirectAnswer.class);
  }
}
