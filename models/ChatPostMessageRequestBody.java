package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ChatPostMessageRequestBody extends WithChatPostMessageRequestBody {
  @Parameter
  String channel();

  @Parameter
  String text();

  @Parameter
  List<? extends SlackBlock> blocks();

  @Parameter
  @SerializedName("thread_ts")
  Optional<String> threadId();

  @Derived
  @SerializedName("unfurl_links")
  default boolean unfurlLinks() {
    return false;
  }

  @Derived
  @SerializedName("unfurl_media")
  default boolean unfurlMedia() {
    return false;
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableChatPostMessageRequestBody.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
