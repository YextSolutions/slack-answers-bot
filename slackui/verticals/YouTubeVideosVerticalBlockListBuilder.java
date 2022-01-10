package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResultData;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.ThumbnailPhoto;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.TextSectionBlockBuildingWrapper;
import com.yext.developerappscommon.exceptions.PermanentErrorException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class YouTubeVideosVerticalBlockListBuilder implements VerticalBlockListBuilder {
  private static final String THUMBNAIL_ALT_TEXT = "video";

  private static final Logger logger = LogManager.getLogger();

  private final TextSectionBlockBuildingWrapper textBuilder;

  @Inject
  YouTubeVideosVerticalBlockListBuilder(TextSectionBlockBuildingWrapper textBuilder) {
    this.textBuilder = textBuilder;
  }

  @Override
  public List<SlackBlock> buildBlockList(AnswersResult result) {
    return buildVerticalBlockList(result.data());
  }

  private List<SlackBlock> buildVerticalBlockList(AnswersResultData data) {
    try {
      List<SlackBlock> blocks = new ArrayList<>();
      blocks.add(DividerBlock.of());
      if (data.name().isEmpty() || data.landingPageUrl().isEmpty()) {
        throw new PermanentErrorException();
      }
      if (data.description().isPresent()) {
        blocks.add(
            textBuilder.buildLinkMarkdownSection(data.landingPageUrl().get(), data.name().get()));
        blocks.add(
            buildVideoDescriptionBlock(
                data.description().get(), data.videoThumbnail().orElse(null)));
      } else {
        blocks.add(
            buildVideoTitleThumbnailBlock(
                data.name().get(), data.landingPageUrl().get(), data.videoThumbnail().orElse(null)));
      }
      return blocks;
    } catch (PermanentErrorException ex) {
      logger.warn(ex);
      return List.of();
    }
  }

  private SlackBlock buildVideoTitleThumbnailBlock(
      String title, String url, @Nullable ThumbnailPhoto photo) {
    if (photo == null) {
      return textBuilder.buildLinkMarkdownSection(url, title);
    } else {
      return textBuilder.buildLinkMarkdownAccessorizingSection(
          url, photo.url(), THUMBNAIL_ALT_TEXT, title);
    }
  }

  private SlackBlock buildVideoDescriptionBlock(String desc, @Nullable ThumbnailPhoto photo) {
    if (photo == null) {
      return textBuilder.buildPlainTextSection(desc);
    } else {
      return textBuilder.buildPlainTextAccessorizingSection(photo.url(), THUMBNAIL_ALT_TEXT, desc);
    }
  }
}
