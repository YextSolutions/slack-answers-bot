package com.yext.developerapps.slackanswersbotapp.api;

import com.yext.developerapps.slackanswersbotapp.models.SlackResponse;
import com.yext.util.secret.Secret;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackApi {
  SlackResponse postChatMessage(String body, Secret botToken);

  SlackResponse openViews(String body, Secret botToken);
}
