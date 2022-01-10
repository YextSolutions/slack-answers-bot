package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import java.util.HashMap;
import java.util.Map;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public enum Vertical {
  EVENTS("events", "Events"),
  GURU("guru", "Guru"),
  OFFICES("offices", "Offices"),
  QUICKLINKS("links", "Quicklinks"),
  RESOURCES("resources", "Resources"),
  SLACK("slack_channel", "#Slack Channels"),
  SPOTIFY("spotify_playlists", "Spotify Playlists"),
  TEAMS("teams", "Teams"),
  YEXTBOOK("yexters", "Yextbook"),
  YOUTUBE("videos", "YouTube Videos"),
  YGOALS("y_goals", "Y Goals");

  public static final Map<String, Vertical> verticalIdToVertical = new HashMap<>();

  static {
    for (Vertical vertical : Vertical.values()) {
      verticalIdToVertical.put(vertical.getId(), vertical);
    }
  }

  private final String id;
  private final String header;

  Vertical(String id, String header) {
    this.id = id;
    this.header = header;
  }

  public String getId() {
    return id;
  }

  public String getHeader() {
    return header;
  }
}
