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
public class QuicklinksVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final SlackButtonStyle buttonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  QuicklinksVerticalBlockListBuilder(
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
      // TODO: For all CTAs, clean the filtering logic to remove startsWith http checks and instead
      // figure out how to gracefully handle emails and phones as well.
      if ((data.primaryCTA().isPresent()
              && data.primaryCTA().get().label().isPresent()
              && data.primaryCTA().get().link().isPresent())
          && data.primaryCTA().get().link().get().startsWith("http")) {
        data.primaryCTA()
            .map(
                cta ->
                    actionsBlockBuilder.buildBlock(
                        cta.label().get(), cta.link().get(), buttonStyle))
            .ifPresent(blocks::add);
      }
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }
}
