package com.yext.developerapps.slackanswersbotapp.models;

import com.google.gson.annotations.SerializedName;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum SlackBlockType {
  @SerializedName("actions")
  ACTIONS,

  @SerializedName("divider")
  DIVIDER,

  @SerializedName("header")
  HEADER,

  @SerializedName("image")
  IMAGE,

  @SerializedName("section")
  SECTION
}
