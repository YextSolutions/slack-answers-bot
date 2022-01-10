package com.yext.developerapps.slackanswersbotapp.models;

import com.google.gson.annotations.SerializedName;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum SlackTextFormatType {
  @SerializedName("plain_text")
  PLAIN_TEXT,

  @SerializedName("mrkdwn")
  MARKDOWN
}
