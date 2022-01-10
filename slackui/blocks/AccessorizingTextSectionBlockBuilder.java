package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.models.ImageBlockElement;
import com.yext.developerapps.slackanswersbotapp.models.TextSectionBlock;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class AccessorizingTextSectionBlockBuilder extends TextSectionBlockBuilder {
  private final String imageUrl;
  private final String altText;
  private final TextOnlyTextSectionBlockBuilder delegate;

  @AssistedInject
  AccessorizingTextSectionBlockBuilder(
      @Assisted("imageUrl") String imageUrl,
      @Assisted("altText") String altText,
      @Assisted TextOnlyTextSectionBlockBuilder delegate) {
    this.imageUrl = imageUrl;
    this.altText = altText;
    this.delegate = delegate;
  }

  @Override
  public TextSectionBlock buildSection(String text) {
    return delegate.buildSection(text).withAccessory(ImageBlockElement.of(imageUrl, altText));
  }

  @AssistedFactory
  interface AccessorizingTextSectionBlockBuilderFactory {
    AccessorizingTextSectionBlockBuilder create(
        @Assisted("imageUrl") String imageUrl,
        @Assisted("altText") String altText,
        @Assisted TextOnlyTextSectionBlockBuilder delegate);
  }
}
