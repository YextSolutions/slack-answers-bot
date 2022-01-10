package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResultData;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
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
public class YGoalsVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  YGoalsVerticalBlockListBuilder(
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
      if (data.name().isEmpty()
          || data.topLevelDepartment().isEmpty()
          || data.department().isEmpty()) {
        throw new PermanentErrorException();
      }
      data.name().map(textBuilder::buildBoldMarkdownSection).ifPresent(blocks::add);
      blocks.add(
          textBuilder.buildPlainTextSection(
              buildDepartmentName(data.topLevelDepartment().get(), data.department().get())));
      data.goalStatus()
          .map(status -> actionsBlockBuilder.buildBlock(status, null, null))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private String buildDepartmentName(String topLevelDepartment, String department) {
    return topLevelDepartment + " - " + department;
  }
}
