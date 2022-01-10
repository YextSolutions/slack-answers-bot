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
public class SpotifyPlaylistsVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String BUTTON_TEXT = "Listen Now";

  private static final SlackButtonStyle buttonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  SpotifyPlaylistsVerticalBlockListBuilder(
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
      if (data.name().isEmpty() || data.playlistUrl().isEmpty()) {
        throw new PermanentErrorException();
      }
      blocks.add(textBuilder.buildLinkMarkdownSection(data.playlistUrl().get(), data.name().get()));
      data.playlistUrl()
          .map(url -> actionsBlockBuilder.buildBlock(BUTTON_TEXT, url, buttonStyle))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }
}
