package com.yext.developerapps.slackanswersbotapp.models;

import com.yext.util.immutables.YextStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

/**
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
@Immutable
@YextStyle
public interface AnswersConfig extends WithAnswersConfig {
  String enterpriseId();

  String workspaceId();

  String apiKey();

  String accountId();

  String experienceKey();

  Optional<String> verticalKey();

  String locale();

  String prodUrl();

  static Builder newBuilder() { return new Builder(); }

  class Builder extends ImmutableAnswersConfig.Builder {}
}
