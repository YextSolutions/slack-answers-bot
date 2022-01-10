package com.yext.developerapps.slackanswersbotapp.models;

import com.google.gson.annotations.SerializedName;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum SlackBlockElementType {
  @SerializedName("button")
  BUTTON,

  @SerializedName("image")
  IMAGE
}
