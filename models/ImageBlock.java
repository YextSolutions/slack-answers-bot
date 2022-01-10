package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ImageBlock extends SlackBlock, WithImageBlock {
  @Derived
  default SlackBlockType type() {
    return SlackBlockType.IMAGE;
  }

  @Parameter
  @SerializedName("image_url")
  String imageUrl();

  @Parameter
  @SerializedName("alt_text")
  String altText();

  Optional<TextObject> title();

  static ImageBlock of(String imageUrl, String altText) {
    return ImmutableImageBlock.of(imageUrl, altText);
  }

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableImageBlock.Builder {}

  default String toJson() {
    return gson().toJson(this);
  }
}
