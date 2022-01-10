package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface UniversalResponse extends WithUniversalResponse {
  List<AnswersModule> modules();

  Optional<DirectAnswer> directAnswer();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableUniversalResponse.Builder {}

  static UniversalResponse fromJson(String response) {
    return gson().fromJson(response, UniversalResponse.class);
  }
}
