package com.yext.developerapps.slackanswersbotapp.slackui;

import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerapps.slackanswersbotapp.models.SlackViewType;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.ActionsBlockBuilderModule;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.TextOnlyTextSectionBlockBuilderModule;
import com.yext.developerapps.slackanswersbotapp.slackui.verticals.VerticalBlockListBuilderRegistry;
import dagger.BindsInstance;
import dagger.Component;
import javax.inject.Named;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Component(
    modules = {
      ActionsBlockBuilderModule.class,
      TextOnlyTextSectionBlockBuilderModule.class,
      VerticalBlockListBuilderRegistry.class
    })
public interface SlackUIBuilderComponent {
  ViewPayloadObjectBuilder viewPayloadBuilder();

  BlockListViewBuilder blockViewBuilder();

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder response(AnswersUniversalSearchResponse response);

    @BindsInstance
    Builder viewType(SlackViewType viewType);

    @BindsInstance
    Builder query(@Named("query") String query);

    @BindsInstance
    Builder appName(@Named("appName") String appName);

    @BindsInstance
    Builder answersUrl(@Named("answersUrl") String answersUrl);

    @BindsInstance
    Builder maxVerticalsToBuild(@Named("maxVerticalsToBuild") int maxVerticalsToBuild);

    @BindsInstance
    Builder maxResultsPerVertical(@Named("maxResultsPerVertical") int maxResultsPerVertical);

    SlackUIBuilderComponent build();
  }
}
