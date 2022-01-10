package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.google.common.annotations.VisibleForTesting;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResultData;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.FieldsSectionBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackButtonStyle;
import com.yext.developerapps.slackanswersbotapp.models.TextObject;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.FieldMarkdownTextObjectBuilder.FieldMarkdownTextObjectBuilderFactory;
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
public class GuruVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String LAST_UPDATED_TEXT = "Last Updated On";
  private static final String BUTTON_TEXT = "View Card";

  private static final SlackButtonStyle buttonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final FieldMarkdownTextObjectBuilderFactory fieldMarkdownObjectBuilderFactory;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  GuruVerticalBlockListBuilder(
      TextSectionBlockBuildingWrapper textBuilder,
      FieldMarkdownTextObjectBuilderFactory fieldMarkdownObjectBuilderFactory,
      @Named("simpleActionsButtonBlockBuilder") ActionsBlockBuilder actionsBlockBuilder) {
    this.textBuilder = textBuilder;
    this.fieldMarkdownObjectBuilderFactory = fieldMarkdownObjectBuilderFactory;
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
          || data.landingPageUrl().isEmpty()
          || (data.snippet().isEmpty() && data.body().isEmpty())) {
        throw new PermanentErrorException();
      }
      blocks.add(
          textBuilder.buildLinkMarkdownSection(data.landingPageUrl().get(), data.name().get()));
      if ((data.collection().isPresent() && !data.boards().isEmpty())
          || data.lastUpdateDate().isPresent()) {
        blocks.add(FieldsSectionBlock.of(getFieldsList(data)));
      }
      blocks.add(textBuilder.buildPlainTextSection(data.snippet().orElse(data.body().get())));
      data.landingPageUrl()
          .map(url -> actionsBlockBuilder.buildBlock(BUTTON_TEXT, url, buttonStyle))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  @VisibleForTesting
  List<TextObject> getFieldsList(AnswersResultData data) {
    if (data.collection().isPresent()
        && !data.boards().isEmpty()
        && data.lastUpdateDate().isPresent()) {
      return List.of(
          buildFieldText(data.collection().get(), data.boards().stream().findFirst().get()),
          buildFieldText(LAST_UPDATED_TEXT, data.lastUpdateDate().get()));
    } else if (data.collection().isPresent() && !data.boards().isEmpty()) {
      return List.of(
          buildFieldText(data.collection().get(), data.boards().stream().findFirst().get()));
    } else {
      return List.of(buildFieldText(LAST_UPDATED_TEXT, data.lastUpdateDate().get()));
    }
  }

  private TextObject buildFieldText(String field, String text) {
    return fieldMarkdownObjectBuilderFactory.create(field).buildTextObject(text);
  }
}
