package com.yext.developerapps.slackanswersbotapp.api;

import com.yext.developerapps.slackanswersbotapp.models.AnswersConfig;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public interface AnswersApi {
  AnswersUniversalSearchResponse getUniversalSearchResults(String query, AnswersConfig config);
}
