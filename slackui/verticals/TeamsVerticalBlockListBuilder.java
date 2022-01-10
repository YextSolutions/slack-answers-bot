package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResultData;
import com.yext.developerapps.slackanswersbotapp.models.CallToAction;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.ActionsBlockBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.TextSectionBlockBuildingWrapper;
import com.yext.developerappscommon.exceptions.PermanentErrorException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class TeamsVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String COME_FIND_US_TEXT = "Come Find Us";

  private static final SlackButtonStyle primaryCTAStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  TeamsVerticalBlockListBuilder(
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
      data.name().map(textBuilder::buildBoldMarkdownSection).ifPresent(blocks::add);
      data.building()
          .map(
              building ->
                  textBuilder.buildFieldMarkdownSection(
                      COME_FIND_US_TEXT, getTeamLocation(data.floor().orElse(null), building)))
          .ifPresent(blocks::add);
      data.teamOverview().map(textBuilder::buildPlainTextSection).ifPresent(blocks::add);
      if ((data.primaryCTA().isPresent()
              && data.primaryCTA().get().label().isPresent()
              && data.primaryCTA().get().link().isPresent())
          && data.primaryCTA().get().link().get().startsWith("http")) {
        data.primaryCTA().map(cta -> buildButtonBlock(cta, primaryCTAStyle)).ifPresent(blocks::add);
      }
      if ((data.secondaryCTA().isPresent()
              && data.secondaryCTA().get().label().isPresent()
              && data.secondaryCTA().get().link().isPresent())
          && data.secondaryCTA().get().link().get().startsWith("http")) {
        data.secondaryCTA().map(cta -> buildButtonBlock(cta, null)).ifPresent(blocks::add);
      }
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private String getTeamLocation(String floor, String building) {
    if (floor == null) {
      return building;
    } else {
      return floor + ", " + building;
    }
  }

  private SlackBlock buildButtonBlock(CallToAction cta, @Nullable SlackButtonStyle style) {
    return actionsBlockBuilder.buildBlock(cta.label().get(), cta.link().get(), style);
  }
}
