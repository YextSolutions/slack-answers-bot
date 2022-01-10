package com.yext.developerapps.slackanswersbotapp.models;

import static com.yext.util.immutables.GsonWithAdaptors.gson;

import com.google.gson.annotations.SerializedName;
import com.yext.util.immutables.YextStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
@Immutable
@TypeAdapters
@YextStyle
public interface AnswersResultData extends WithAnswersResultData {
  Optional<String> id();

  Optional<String> name();

  Optional<String> landingPageUrl();

  Optional<String> body();

  @SerializedName("c_collectionName")
  Optional<String> collection();

  @SerializedName("c_boardsTitle")
  List<String> boards();

  @SerializedName("externalArticleUpdateDate")
  Optional<String> lastUpdateDate();

  Optional<String> description();

  @SerializedName("youtube_thumbnailPhoto")
  Optional<ThumbnailPhoto> videoThumbnail();

  @SerializedName("c_channelUrl")
  Optional<String> slackChannelUrl();

  @SerializedName("c_numberOfMembers")
  Optional<String> slackChannelMemberCount();

  @SerializedName("c_purpose")
  Optional<String> slackChannelDescription();

  @SerializedName("c_floorWhereToFindUs")
  Optional<String> floor();

  @SerializedName("c_buildingWhereToFindUs")
  Optional<String> building();

  @SerializedName("c_teamOverview")
  Optional<String> teamOverview();

  @SerializedName("c_primaryCTA")
  Optional<CallToAction> primaryCTA();

  @SerializedName("c_secondaryCTA")
  Optional<CallToAction> secondaryCTA();

  Optional<EventTime> time();

  @SerializedName("googleCalendar_meetingLink")
  Optional<String> meetingLink();

  @SerializedName("c_jobTitle")
  Optional<String> jobTitle();

  @SerializedName("c_office")
  Optional<String> office();

  @SerializedName("c_comeTalkToMeAbout")
  Optional<String> comeTalkToMeAbout();

  List<String> emails();

  @SerializedName("c_slackID")
  Optional<String> slackId();

  Optional<String> richTextDescription();

  Optional<Address> address();

  Optional<String> mainPhone();

  @SerializedName("c_officeHandbook")
  Optional<String> officeHandbook();

  @SerializedName("c_englishCTA")
  Optional<CallToAction> englishCTA();

  @SerializedName("c_frenchCTA")
  Optional<CallToAction> frenchCTA();

  @SerializedName("c_germanCTA")
  Optional<CallToAction> germanCTA();

  @SerializedName("c_japaneseCTA")
  Optional<CallToAction> japaneseCTA();

  @SerializedName("s_snippet")
  Optional<String> snippet();

  @SerializedName("c_topLevelDepartment")
  Optional<String> topLevelDepartment();

  @SerializedName("c_department")
  Optional<String> department();

  @SerializedName("c_goalStatus")
  Optional<String> goalStatus();

  @SerializedName("c_playlistURL")
  Optional<String> playlistUrl();

  @SerializedName("c_pagesURL")
  Optional<String> pagesUrl();

  static Builder newBuilder() {
    return new Builder();
  }

  class Builder extends ImmutableAnswersResultData.Builder {}

  static AnswersResultData fromJson(String data) {
    return gson().fromJson(data, AnswersResultData.class);
  }
}
