package com.yext.developerapps.slackanswersbotapp.slackui;

import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.EVENTS;
import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.QUICKLINKS;
import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.SPOTIFY;
import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.YEXTBOOK;
import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.YOUTUBE;
import static com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical.verticalIdToVertical;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.google.common.annotations.VisibleForTesting;
import com.yext.developerapps.slackanswersbotapp.models.AnswersResult;
import com.yext.developerapps.slackanswersbotapp.models.AnswersUniversalSearchResponse;
import com.yext.developerapps.slackanswersbotapp.models.CallToAction;
import com.yext.developerapps.slackanswersbotapp.models.DirectAnswer;
import com.yext.developerapps.slackanswersbotapp.models.DirectAnswerRelatedItem;
import com.yext.developerapps.slackanswersbotapp.models.DirectAnswerSnippet;
import com.yext.developerapps.slackanswersbotapp.models.DirectAnswerType;
import com.yext.developerapps.slackanswersbotapp.models.DividerBlock;
import com.yext.developerapps.slackanswersbotapp.models.SlackBlock;
import com.yext.developerapps.slackanswersbotapp.models.UniversalResponse;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.ActionsBlockBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.HeaderBlockBuilder;
import com.yext.developerapps.slackanswersbotapp.slackui.blocks.TextSectionBlockBuildingWrapper;
import com.yext.developerapps.slackanswersbotapp.slackui.verticals.Vertical;
import com.yext.developerapps.slackanswersbotapp.slackui.verticals.VerticalBlockListBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class builds a List of SlackBlocks, which, are the UI portion of the request sent to Slack.
 * If an Answers response has a Direct Answer, additional blocks are built to include that at the
 * top of the UI. Furthermore, the implementation uses various VerticalBlockListBuilders to
 * construct different UI elements for each vertical in the Answers response.
 *
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public class BlockListViewBuilder implements BlockViewBuilder {
  private static final String DIRECT_ANSWER_HEADER = "Direct Answer";
  private static final String MORE_RESULTS_BUTTON_TEXT = ":link:  See more in Telescope!";

  private static final Set<Vertical> verticalsWithDirectAnswerLinks =
      Set.of(EVENTS, QUICKLINKS, SPOTIFY, YEXTBOOK, YOUTUBE);

  private final AnswersUniversalSearchResponse response;
  private final int maxVerticalsToBuild;
  private final int maxResultsPerVertical;
  private final String query;
  private final String answersUrl;

  private final HeaderBlockBuilder headerBlockBuilder;
  private final TextSectionBlockBuildingWrapper textBuildingWrapper;
  private final ActionsBlockBuilder actionsBlockBuilder;
  private final Map<Vertical, VerticalBlockListBuilder> verticalToBlockListBuilder;

  @Inject
  BlockListViewBuilder(
      AnswersUniversalSearchResponse response,
      @Named("maxVerticalsToBuild") int maxVerticalsToBuild,
      @Named("maxResultsPerVertical") int maxResultsPerVertical,
      @Named("query") String query,
      @Named("answersUrl") String answersUrl,
      HeaderBlockBuilder headerBlockBuilder,
      TextSectionBlockBuildingWrapper textBuildingWrapper,
      @Named("simpleActionsButtonBlockBuilder") ActionsBlockBuilder actionsBlockBuilder,
      Map<Vertical, VerticalBlockListBuilder> verticalToBlockListBuilder) {
    this.response = response;
    this.maxVerticalsToBuild = maxVerticalsToBuild;
    this.maxResultsPerVertical = maxResultsPerVertical;
    this.query = query;
    this.answersUrl = answersUrl;
    this.headerBlockBuilder = headerBlockBuilder;
    this.textBuildingWrapper = textBuildingWrapper;
    this.actionsBlockBuilder = actionsBlockBuilder;
    this.verticalToBlockListBuilder = verticalToBlockListBuilder;
  }

  @Override
  public List<SlackBlock> buildBlockView() {
    List<SlackBlock> blocks = new ArrayList<>();
    response.response().directAnswer().map(this::buildDirectAnswerBlocks).ifPresent(blocks::addAll);
    blocks.addAll(buildVerticalBlocks(response.response()));
    if (!blocks.isEmpty()) {
      blocks.add(DividerBlock.of());
      blocks.add(buildMoreResultsButton(query, answersUrl));
    }
    return blocks;
  }

  private List<SlackBlock> buildDirectAnswerBlocks(DirectAnswer answer) {
    List<SlackBlock> blocks = new ArrayList<>();
    blocks.add(headerBlockBuilder.buildBlock(DIRECT_ANSWER_HEADER));
    blocks.add(DividerBlock.of());
    buildValueBlock(answer).ifPresent(blocks::add);
    if (answer.type().equals(DirectAnswerType.FEATURED_SNIPPET)
        && answer.answer().snippet().isPresent()) {
      answer
          .answer()
          .snippet()
          .map(snippet -> buildSnippetBlock(snippet, answer.answer().fieldType()))
          .ifPresent(blocks::add);
    }
    return blocks;
  }

  /**
   * This method builds a SlackBlock based on the direct answer value, as follows: i) If the value
   * is a valid url, then the value itself is set as the link. ii) Else, if there is a valid related
   * item with a link, that link is extracted and used in the output. iii) In case no link is found,
   * the value is output as non-hyperlinked bold markdown.
   */
  @VisibleForTesting
  Optional<SlackBlock> buildValueBlock(DirectAnswer answer) {
    if (answer.answer().value().isPresent()) {
      String value = answer.answer().value().get();
      if (value.startsWith("http")) {
        return Optional.of(textBuildingWrapper.buildLinkMarkdownSection(value, value));
      } else if (answer.relatedItem().map(this::validateRelatedItem).orElse(false)) {
        Optional<String> link = getVerticalDirectAnswerLink(answer.relatedItem().get());
        if (link.isPresent()) {
          return Optional.of(textBuildingWrapper.buildLinkMarkdownSection(link.get(), value));
        }
      }
      return Optional.of(textBuildingWrapper.buildBoldMarkdownSection(value));
    } else {
      return Optional.empty();
    }
  }

  private boolean validateRelatedItem(DirectAnswerRelatedItem item) {
    return verticalIdToVertical.containsKey(item.verticalConfigId())
        && verticalsWithDirectAnswerLinks.contains(
            verticalIdToVertical.get(item.verticalConfigId()));
  }

  private Optional<String> getVerticalDirectAnswerLink(DirectAnswerRelatedItem item) {
    Vertical vertical = verticalIdToVertical.get(item.verticalConfigId());
    return switch (vertical) {
      case EVENTS, YOUTUBE -> item.data().fieldValues().landingPageUrl();
      case QUICKLINKS -> item.data().fieldValues().primaryCTA().flatMap(CallToAction::link);
      case SPOTIFY -> item.data().fieldValues().playlistUrl();
      case YEXTBOOK -> item.data().fieldValues().pagesUrl();
      default -> Optional.empty();
    };
  }

  @VisibleForTesting
  SlackBlock buildSnippetBlock(DirectAnswerSnippet snippet, String fieldType) {
    // TODO: Ask Answers about the field types and update this conditional to make it more robust
    if (fieldType.contains("rich_text")) {
      return textBuildingWrapper.buildMarkdownSection(snippet.value());
    } else {
      return textBuildingWrapper.buildPlainTextSection(snippet.value());
    }
  }

  private List<SlackBlock> buildVerticalBlocks(UniversalResponse response) {
    return response.modules().stream()
        .limit(maxVerticalsToBuild)
        .filter(module -> verticalIdToVertical.containsKey(module.verticalConfigId()))
        .map(
            module ->
                buildVerticalBlockView(
                    module.results(), verticalIdToVertical.get(module.verticalConfigId())))
        .flatMap(Collection::stream)
        .collect(toList());
  }

  @VisibleForTesting
  List<SlackBlock> buildVerticalBlockView(List<AnswersResult> results, Vertical vertical) {
    List<SlackBlock> blocks = new ArrayList<>();
    blocks.add(headerBlockBuilder.buildBlock(vertical.getHeader()));
    blocks.addAll(
        results.stream()
            .limit(maxResultsPerVertical)
            .map(result -> verticalToBlockListBuilder.get(vertical).buildBlockList(result))
            .flatMap(Collection::stream)
            .collect(toUnmodifiableList()));
    // If all vertical results were malformed, do not return header
    if (blocks.size() == 1) {
      return List.of();
    }
    return blocks;
  }

  private SlackBlock buildMoreResultsButton(String query, String answersUrl) {
    return actionsBlockBuilder.buildBlock(
        MORE_RESULTS_BUTTON_TEXT, answersUrl + "?" + formatQueryParam(query), null);
  }

  private String formatQueryParam(String query) {
    return "query=" + query.replaceAll(" ", "+");
  }
}
