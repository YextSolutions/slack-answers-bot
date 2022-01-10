package com.yext.developerapps.slackanswersbotapp.models;

import com.google.gson.annotations.SerializedName;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum SlackViewType {
  @SerializedName("modal")
  MODAL,

  @SerializedName("home")
  HOME_TAB
}
