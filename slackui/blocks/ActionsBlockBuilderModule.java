package com.yext.developerapps.slackanswersbotapp.slackui.blocks;

import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.EmailButtonBlockElementBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blockelements.SimpleButtonBlockElementBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Module
public interface ActionsBlockBuilderModule {
  @Provides
  @Named("emailActionsButtonBlockBuilder")
  static ActionsBlockBuilder provideEmailActionsButtonBlockBuilder(
      EmailButtonBlockElementBuilder builder) {
    return new ActionsButtonBlockBuilder(builder);
  }

  @Provides
  @Named("simpleActionsButtonBlockBuilder")
  static ActionsBlockBuilder provideSimpleActionsButtonBlockBuilder(
      SimpleButtonBlockElementBuilder builder) {
    return new ActionsButtonBlockBuilder(builder);
  }
}
