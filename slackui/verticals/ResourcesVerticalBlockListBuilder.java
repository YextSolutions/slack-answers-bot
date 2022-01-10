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
public class ResourcesVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String AMERICAN_FLAG_EMOJI = ":us:";
  private static final String FRENCH_FLAG_EMOJI = ":fr:";
  private static final String GERMAN_FLAG_EMOJI = ":de:";
  private static final String JAPANESE_FLAG_EMOJI = ":jp:";

  private static final SlackButtonStyle buttonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  ResourcesVerticalBlockListBuilder(
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
      if (data.name().isEmpty() || data.richTextDescription().isEmpty()) {
        throw new PermanentErrorException();
      }
      data.name().map(textBuilder::buildBoldMarkdownSection).ifPresent(blocks::add);
      data.richTextDescription().map(textBuilder::buildMarkdownSection).ifPresent(blocks::add);
      if ((data.englishCTA().isPresent()
              && data.englishCTA().get().label().isPresent()
              && data.englishCTA().get().link().isPresent())
          && data.englishCTA().get().link().get().startsWith("http")) {
        data.englishCTA()
            .map(cta -> buildButtonBlock(cta, AMERICAN_FLAG_EMOJI, buttonStyle))
            .ifPresent(blocks::add);
      }
      if ((data.frenchCTA().isPresent()
              && data.frenchCTA().get().label().isPresent()
              && data.frenchCTA().get().link().isPresent())
          && data.frenchCTA().get().link().get().startsWith("http")) {
        data.frenchCTA()
            .map(cta -> buildButtonBlock(cta, FRENCH_FLAG_EMOJI, buttonStyle))
            .ifPresent(blocks::add);
      }
      if ((data.germanCTA().isPresent()
              && data.germanCTA().get().label().isPresent()
              && data.germanCTA().get().link().isPresent())
          && data.germanCTA().get().link().get().startsWith("http")) {
        data.germanCTA()
            .map(cta -> buildButtonBlock(cta, GERMAN_FLAG_EMOJI, buttonStyle))
            .ifPresent(blocks::add);
      }
      if ((data.japaneseCTA().isPresent()
              && data.japaneseCTA().get().label().isPresent()
              && data.japaneseCTA().get().link().isPresent())
          && data.japaneseCTA().get().link().get().startsWith("http")) {
        data.japaneseCTA()
            .map(cta -> buildButtonBlock(cta, JAPANESE_FLAG_EMOJI, buttonStyle))
            .ifPresent(blocks::add);
      }
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private SlackBlock buildButtonBlock(
      CallToAction cta, String emoji, @Nullable SlackButtonStyle style) {
    return actionsBlockBuilder.buildBlock(
        emoji + "  " + cta.label().get(), cta.link().get(), style);
  }
}
