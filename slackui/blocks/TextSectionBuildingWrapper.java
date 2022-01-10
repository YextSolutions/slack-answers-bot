package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.TextSectionBlock;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface TextSectionBuildingWrapper {
  TextSectionBlock buildPlainTextSection(String text);

  TextSectionBlock buildMarkdownSection(String text);

  TextSectionBlock buildBoldMarkdownSection(String text);

  TextSectionBlock buildFieldMarkdownSection(String field, String text);

  TextSectionBlock buildLinkMarkdownSection(String url, String text);

  TextSectionBlock buildPlainTextAccessorizingSection(String imageUrl, String altText, String text);

  TextSectionBlock buildLinkMarkdownAccessorizingSection(
      String url, String imageUrl, String altText, String text);
}
