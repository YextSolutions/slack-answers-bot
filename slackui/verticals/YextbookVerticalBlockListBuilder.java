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
public class YextbookVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String LOCATION_EMOJI = ":round_pushpin:";
  private static final String TALK_TO_ME_TEXT = "Come Talk To Me About";
  private static final String EMAIL_BUTTON_TEXT = ":envelope:  Email ";
  private static final String SLACK_BUTTON_TEXT = ":slack:  Slack Direct Message";
  private static final String SLACK_BASE_URL = "slack://user?team=TUNK1C0C8&id=";
  private static final String ANSWERS_PROD_URL = "https://telescope.yext.com";

  private static final SlackButtonStyle emailButtonStyle = SlackButtonStyle.GREEN;
  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder simpleActionsBlockBuilder;
  private final ActionsBlockBuilder emailActionsBlockBuilder;

  @Inject
  YextbookVerticalBlockListBuilder(
      TextSectionBlockBuildingWrapper textBuilder,
      @Named("simpleActionsButtonBlockBuilder") ActionsBlockBuilder simplectionsBlockBuilder,
      @Named("emailActionsButtonBlockBuilder") ActionsBlockBuilder emailActionsBlockBuilder) {
    this.textBuilder = textBuilder;
    this.simpleActionsBlockBuilder = simplectionsBlockBuilder;
    this.emailActionsBlockBuilder = emailActionsBlockBuilder;
  }

  @Override
  public List<SlackBlock> buildBlockList(AnswersResult result) {
    return buildVerticalBlockList(result.data());
  }

  private List<SlackBlock> buildVerticalBlockList(AnswersResultData data) {
    try {
      List<SlackBlock> blocks = new ArrayList<>();
      blocks.add(DividerBlock.of());
      if (data.id().isEmpty() || data.name().isEmpty()) {
        throw new PermanentErrorException();
      }
      blocks.add(
          textBuilder.buildLinkMarkdownSection(
              buildYextbookUrl(data.id().get()), data.name().get()));
      data.jobTitle().map(textBuilder::buildPlainTextSection).ifPresent(blocks::add);
      data.office()
          .map(office -> textBuilder.buildPlainTextSection(LOCATION_EMOJI + "  " + office))
          .ifPresent(blocks::add);
      data.comeTalkToMeAbout()
          .map(text -> textBuilder.buildFieldMarkdownSection(TALK_TO_ME_TEXT, text))
          .ifPresent(blocks::add);
      data.emails().stream()
          .findFirst()
          .map(
              email ->
                  emailActionsBlockBuilder.buildBlock(
                      EMAIL_BUTTON_TEXT + data.name().get(), email, emailButtonStyle))
          .ifPresent(blocks::add);
      data.slackId()
          .map(
              id ->
                  simpleActionsBlockBuilder.buildBlock(
                      SLACK_BUTTON_TEXT, SLACK_BASE_URL + id, null))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private String buildYextbookUrl(String id) {
    return ANSWERS_PROD_URL + "/" + id;
  }
}
