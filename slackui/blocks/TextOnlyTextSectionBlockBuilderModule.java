package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.BoldMarkdownTextObjectBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.MarkdownTextObjectBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.PlainTextObjectBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Module
public interface TextOnlyTextSectionBlockBuilderModule {
  @Provides
  @Named("plainTextOnlyTextSectionBlockBuilder")
  static TextOnlyTextSectionBlockBuilder providePlainTextOnlyTextSectionBlockBuilder(
      PlainTextObjectBuilder textBuilder) {
    return new TextOnlyTextSectionBlockBuilder(textBuilder);
  }

  @Provides
  @Named("markdownTextOnlyTextSectionBlockBuilder")
  static TextOnlyTextSectionBlockBuilder provideMarkdownTextOnlyTextSectionBlockBuilder(
      MarkdownTextObjectBuilder textBuilder) {
    return new TextOnlyTextSectionBlockBuilder(textBuilder);
  }

  @Provides
  @Named("boldMarkdownTextOnlyTextSectionBlockBuilder")
  static TextOnlyTextSectionBlockBuilder provideBoldMarkdownTextOnlyTextSectionBlockBuilder(
      BoldMarkdownTextObjectBuilder textBuilder) {
    return new TextOnlyTextSectionBlockBuilder(textBuilder);
  }
}
