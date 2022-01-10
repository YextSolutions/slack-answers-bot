package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.TextSectionBlock;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.FieldMarkdownTextObjectBuilder.FieldMarkdownTextObjectBuilderFactory;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.LinkMarkdownTextObjectBuilder.LinkMarkdownTextObjectBuilderFactory;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.AccessorizingTextSectionBlockBuilder.AccessorizingTextSectionBlockBuilderFactory;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This wrapper class hides the boilerplate for constructing various types of TextSectionBlocks.
 * Along with the advantage of not having to repeat the boilerplate in the various
 * BlockListBuilders, this class also allows those builders to reduce their dependencies as just a
 * single injection of this class covers multiple individual injections of different TextObject and
 * TextSection builders.
 *
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public class TextSectionBlockBuildingWrapper implements TextSectionBuildingWrapper {
  private final FieldMarkdownTextObjectBuilderFactory fieldMarkdownObjectBuilderFactory;
  private final LinkMarkdownTextObjectBuilderFactory linkMarkdownObjectBuilderFactory;
  private final AccessorizingTextSectionBlockBuilderFactory accessorizingSectionBuilderFactory;

  private final TextOnlyTextSectionBlockBuilder plainTextSectionBuilder;
  private final TextOnlyTextSectionBlockBuilder markdownSectionBuilder;
  private final TextOnlyTextSectionBlockBuilder boldMarkdownSectionBuilder;

  @Inject
  public TextSectionBlockBuildingWrapper(
      FieldMarkdownTextObjectBuilderFactory fieldMarkdownObjectBuilderFactory,
      LinkMarkdownTextObjectBuilderFactory linkMarkdownObjectBuilderFactory,
      AccessorizingTextSectionBlockBuilderFactory accessorizingSectionBuilderFactory,
      @Named("plainTextOnlyTextSectionBlockBuilder")
          TextOnlyTextSectionBlockBuilder plainTextSectionBuilder,
      @Named("markdownTextOnlyTextSectionBlockBuilder")
          TextOnlyTextSectionBlockBuilder markdownSectionBuilder,
      @Named("boldMarkdownTextOnlyTextSectionBlockBuilder")
          TextOnlyTextSectionBlockBuilder boldMarkdownSectionBuilder) {
    this.fieldMarkdownObjectBuilderFactory = fieldMarkdownObjectBuilderFactory;
    this.linkMarkdownObjectBuilderFactory = linkMarkdownObjectBuilderFactory;
    this.accessorizingSectionBuilderFactory = accessorizingSectionBuilderFactory;
    this.plainTextSectionBuilder = plainTextSectionBuilder;
    this.markdownSectionBuilder = markdownSectionBuilder;
    this.boldMarkdownSectionBuilder = boldMarkdownSectionBuilder;
  }

  @Override
  public TextSectionBlock buildPlainTextSection(String text) {
    return plainTextSectionBuilder.buildSection(text);
  }

  @Override
  public TextSectionBlock buildMarkdownSection(String text) {
    return markdownSectionBuilder.buildSection(text);
  }

  @Override
  public TextSectionBlock buildBoldMarkdownSection(String text) {
    return boldMarkdownSectionBuilder.buildSection(text);
  }

  @Override
  public TextSectionBlock buildFieldMarkdownSection(String field, String text) {
    return new TextOnlyTextSectionBlockBuilder(fieldMarkdownObjectBuilderFactory.create(field))
        .buildSection(text);
  }

  @Override
  public TextSectionBlock buildLinkMarkdownSection(String url, String text) {
    return new TextOnlyTextSectionBlockBuilder(linkMarkdownObjectBuilderFactory.create(url))
        .buildSection(text);
  }

  @Override
  public TextSectionBlock buildPlainTextAccessorizingSection(
      String imageUrl, String altText, String text) {
    return accessorizingSectionBuilderFactory
        .create(imageUrl, altText, plainTextSectionBuilder)
        .buildSection(text);
  }

  @Override
  public TextSectionBlock buildLinkMarkdownAccessorizingSection(
      String url, String imageUrl, String altText, String text) {
    return accessorizingSectionBuilderFactory
        .create(
            imageUrl,
            altText,
            new TextOnlyTextSectionBlockBuilder(linkMarkdownObjectBuilderFactory.create(url)))
        .buildSection(text);
  }
}
