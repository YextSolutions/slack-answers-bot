package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.lmp.helpers.Signer;
import java.time.Instant;
import java.util.function.Supplier;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public class SlackRequestHashValidator implements SlackRequestValidator {
  private static final String VERSION_NUMBER = "v0";

  private final Signer signer;
  private final Supplier<Instant> instantSupplier;

  SlackRequestHashValidator(Signer signer, Supplier<Instant> instantSupplier) {
    this.signer = signer;
    this.instantSupplier = instantSupplier;
  }

  public boolean isValid(String body, String timestamp, String signature) {
    if (!isRecent(Long.parseLong(timestamp))) {
      return false;
    }

    String hashSignature = computeSignature(timestamp, body);

    // TODO: Replace with an hmac compare method.
    return hashSignature.equals(signature);
  }

  private boolean isRecent(long timestamp) {
    return Math.abs(instantSupplier.get().getEpochSecond() - timestamp) < (60 * 5);
  }

  private String computeSignature(String timestamp, String body) {
    return VERSION_NUMBER + "=" + signer.signature(buildSignatureBase(timestamp, body));
  }

  private String buildSignatureBase(String timestamp, String body) {
    return VERSION_NUMBER + ":" + timestamp + ":" + body;
  }
}
