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
public interface SlackResponse extends WithSlackResponse {
  boolean ok();

  Optional<String> error();

  Optional<String> warning();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableSlackResponse.Builder {}

  static SlackResponse fromJson(String response) {
    return gson().fromJson(response, SlackResponse.class);
  }
}
