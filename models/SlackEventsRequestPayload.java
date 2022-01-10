package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface SlackEventsRequestPayload
    extends SlackRequestPayload, WithSlackEventsRequestPayload {
  SlackEvent event();

  @SerializedName("event_id")
  String eventId();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableSlackEventsRequestPayload.Builder {}

  static SlackEventsRequestPayload fromJson(String json) {
    return gson().fromJson(json, SlackEventsRequestPayload.class);
  }
}
