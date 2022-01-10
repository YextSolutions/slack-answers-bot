package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface ThumbnailPhoto extends WithThumbnailPhoto {
  String url();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableThumbnailPhoto.Builder {}

  static ThumbnailPhoto fromJson(String json) {
    return gson().fromJson(json, ThumbnailPhoto.class);
  }
}
