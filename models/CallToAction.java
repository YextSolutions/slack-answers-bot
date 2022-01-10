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
public interface CallToAction extends WithCallToAction {
  Optional<String> label();

  Optional<String> link();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableCallToAction.Builder {}

  static CallToAction fromJson(String cta) {
    return gson().fromJson(cta, CallToAction.class);
  }
}
