package com.yext.developerapps.slackanswersbotapp.util;

import com.yext.lmp.helpers.Signer;
import com.yext.util.secret.Secret;
import java.time.Instant;

/** @author Vaibhav Gadodia (vgadodia@yext.com) */
public interface SlackRequestHashValidatorModule {
  static SlackRequestValidator provideValidator(Secret secret) {
    return new SlackRequestHashValidator(new Signer(secret.get()), Instant::now);
  }
}
