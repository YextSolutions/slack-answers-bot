package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.Address;
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
public class OfficesVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String HANDBOOK_BUTTON_TEXT = "Get Directions";

  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;
  private final ActionsBlockBuilder actionsBlockBuilder;

  @Inject
  OfficesVerticalBlockListBuilder(
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
      data.address()
          .map(this::buildAddressString)
          .map(textBuilder::buildPlainTextSection)
          .ifPresent(blocks::add);
      data.officeHandbook()
          .map(handbook -> actionsBlockBuilder.buildBlock(HANDBOOK_BUTTON_TEXT, handbook, null))
          .ifPresent(blocks::add);
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private String buildAddressString(Address address) {
    String addressStr = "";
    if (address.line1().isPresent()) {
      addressStr += address.line1().get();
    }
    if (address.city().isPresent()) {
      addressStr += ", " + address.city().get();
    }
    if (address.region().isPresent()) {
      addressStr += " " + address.city().get();
    }
    if (address.postalCode().isPresent()) {
      addressStr += ", " + address.postalCode().get();
    }
    return addressStr;
  }
}
