package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import java.util.List;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface VerticalBlockListBuilder {
  List<SlackBlock> buildBlockList(AnswersResult result);
}
