package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResultData;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.ActionsBlockBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.TextSectionBlockBuildingWrapper;
import com.yext.developerappscommon.exceptions.PermanentErrorException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class EventsVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String ZOOM_BUTTON_TEXT = "Join Zoom";
  private static final String EVENT_BUTTON_TEXT = "View Event";

  private static final SlackButtonStyle zoomButtonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  EventsVerticalBlockListBuilder(
      TextSectionBlockBuildingWrapper textBuilder,
      @Named("simpleActionsButtonBlockBuilder") ActionsBlockBuilder actionsBlockBuilder) {
    this.textBuilder = textBuilder;
    this.actionsBlockBuilder = actionsBlockBuilder;
  }

  @Override
  public List<SlackBlock> buildBlockList(AnswersResult result) {
    return buildVerticalBlockList(result.data());
  }

  private List<SlackBlock> buildVerticalBlockList(AnswersResultData data) {
    try {
      List<SlackBlock> blocks = new ArrayList<>();
      blocks.add(DividerBlock.of());
      if (data.name().isEmpty()) {
        throw new PermanentErrorException();
      }
      blocks.add(textBuilder.buildBoldMarkdownSection(data.name().get()));
      data.time()
          .map(time -> buildEventTimeString(time.start(), time.end()))
          .map(textBuilder::buildPlainTextSection)
          .ifPresent(blocks::add);
      data.meetingLink()
          .map(link -> actionsBlockBuilder.buildBlock(ZOOM_BUTTON_TEXT, link, zoomButtonStyle))
          .ifPresent(blocks::add);
      data.landingPageUrl()
          .map(url -> actionsBlockBuilder.buildBlock(EVENT_BUTTON_TEXT, url, null))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private String buildEventTimeString(String start, String end) {
    return convertTimestampToString(start) + " - " + convertTimestampToString(end);
  }

  private String convertTimestampToString(String timestamp) {
    return timestamp.split("T")[0] + ", " + timestamp.split("T")[1];
  }
}
