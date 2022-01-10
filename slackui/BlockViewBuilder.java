package com.yext.developerapps.slackanswersbotapp.slackui;

import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import java.util.List;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface BlockViewBuilder {
  List<SlackBlock> buildBlockView();
}
