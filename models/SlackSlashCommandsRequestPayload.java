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
public interface SlackSlashCommandsRequestPayload
    extends SlackRequestPayload, WithSlackSlashCommandsRequestPayload {
  Optional<String> command();

  Optional<String> text();

  @SerializedName("trigger_id")
  String triggerId();

  @SerializedName("channel_id")
  String channelId();

  @SerializedName("user_id")
  String userId();

  @SerializedName("user_name")
  String userName();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableSlackSlashCommandsRequestPayload.Builder {}

  static SlackSlashCommandsRequestPayload fromJson(String json) {
    return gson().fromJson(json, SlackSlashCommandsRequestPayload.class);
  }
}
