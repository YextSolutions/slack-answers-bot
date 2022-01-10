package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.yext.util.immutables.YextStyle;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface EventTime extends WithEventTime {
  String start();

  String end();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableEventTime.Builder {}

  static EventTime fromJson(String event) {
    return gson().fromJson(event, EventTime.class);
  }
}
