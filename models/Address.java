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
public interface Address extends WithAddress {
  Optional<String> line1();

  Optional<String> city();

  Optional<String> region();

  Optional<String> postalCode();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableAddress.Builder {}

  static Address fromJson(String address) {
    return gson().fromJson(address, Address.class);
  }
}
