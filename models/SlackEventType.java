package com.yext.developerapps.slackanswersbotapp.models;

import com.google.gson.annotations.SerializedName;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum SlackEventType {
  @SerializedName("app_mention")
  AT_MENTION,

  @SerializedName("message")
  DIRECT_MESSAGE
}
