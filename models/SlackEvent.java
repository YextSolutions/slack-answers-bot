package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface SlackEvent extends WithSlackEvent {
  SlackEventType type();

  String text();

  String user();

  @SerializedName("ts")
  String threadId();

  String channel();

  @SerializedName("bot_id")
  Optional<String> botId();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableSlackEvent.Builder {}

  static SlackEvent fromJson(String json) {
    return gson().fromJson(json, SlackEvent.class);
  }

  /** @return true if the event was initiated by a non-bot user */
  default boolean isValidEvent() {
    return botId().isEmpty();
  }
}
