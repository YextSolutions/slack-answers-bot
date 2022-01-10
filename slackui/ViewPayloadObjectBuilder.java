package com.yext.developerapps.slackanswersbotapp.slackui;

import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackViewType;
import com.yext.developerapps.slackanswersbotapp.models.ViewObject;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.PlainTextObjectBuilder;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class ViewPayloadObjectBuilder implements ViewPayloadBuilder {
  private final SlackViewType viewType;

  private final String appName;

  private final PlainTextObjectBuilder plainTextBuilder;
  private final BlockListViewBuilder viewBuilder;

  @Inject
  ViewPayloadObjectBuilder(
      SlackViewType viewType,
      @Named("appName") String appName,
      PlainTextObjectBuilder plainTextBuilder,
      BlockListViewBuilder viewBuilder) {
    this.viewType = viewType;
    this.appName = appName;
    this.plainTextBuilder = plainTextBuilder;
    this.viewBuilder = viewBuilder;
  }

  @Override
  public ViewObject buildViewPayload() {
    List<SlackBlock> blocks = viewBuilder.buildBlockView();
    return ViewObject.newBuilder()
        .setType(viewType)
        .setTitle(plainTextBuilder.buildTextObject(appName))
        .setBlocks(blocks)
        .build();
  }
}
