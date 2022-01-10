package com.yext.developerapps.slackanswersbotapp.slackui.verticals;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Module
public interface VerticalBlockListBuilderRegistry {
  @Binds
  @IntoMap
  @VerticalKey(Vertical.EVENTS)
  VerticalBlockListBuilder bindEvents(EventsVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.GURU)
  VerticalBlockListBuilder bindGuru(GuruVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.OFFICES)
  VerticalBlockListBuilder bindOffices(OfficesVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.QUICKLINKS)
  VerticalBlockListBuilder bindQuicklinks(QuicklinksVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.RESOURCES)
  VerticalBlockListBuilder bindResources(ResourcesVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.SLACK)
  VerticalBlockListBuilder bindSlack(SlackChannelsVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.SPOTIFY)
  VerticalBlockListBuilder bindSpotify(SpotifyPlaylistsVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.TEAMS)
  VerticalBlockListBuilder bindTeams(TeamsVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.YEXTBOOK)
  VerticalBlockListBuilder bindYextbook(YextbookVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.YGOALS)
  VerticalBlockListBuilder bindYGoals(YGoalsVerticalBlockListBuilder builder);

  @Binds
  @IntoMap
  @VerticalKey(Vertical.YOUTUBE)
  VerticalBlockListBuilder bindYoutube(YouTubeVideosVerticalBlockListBuilder builder);
}
