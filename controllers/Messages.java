package controllers;

/**
 * This class contains various messages that are displayed to the end-user.
 *
 * @author Vaibhav Gadodia (vgadodia@yext.com)
 */
public final class Messages {
  private static final String TELESCOPE_BOT_ID = "[REDACTED]";

  static final String TELESCOPE_ERROR_MESSAGE =
      """
      Oh no! Looks like Telescope needs a moment to recalibrate. :eyes: Please try your question again. If the issue persists, reach out to us at #help-telescope.
      """;

  static final String BASE_SUCCESS_MESSAGE =
      "Here is the link to Telescope with the answers to your question: ";

  static final String HELP_MESSAGE =
      """
      Are you looking for the perfect answer on how to use *Telescope*? :face_with_monocle:
      You've come to the right place! :partying_face:

      *Get started by searching in any channel or DM with*
      >/telescope {your question}
      which will create a pop-up with your answer. :likeit:
      (this result is only visible to you)

      *Or, in any channel you can try*
      ><@%s> {your question}
      which will answer your question in a thread directly in your channel! :fb-wow:
      (this result will be visible to everyone in the channel)

      *OR, you can even add Telescope to your Apps in Slack and message it directly.* :dana_yay:

      Here are a few common questions you could try to begin your Telescope exploration. :telescope:

      >/telescope what's the zoom link for manifesto?
      >/telescope address of the nyc office?
      ><@%s> corporate pitch deck
      ><@%s> what is the win site?

      For now, Telescope works for *asking questions in English*. :sunglasses:
      """
          .formatted(TELESCOPE_BOT_ID, TELESCOPE_BOT_ID, TELESCOPE_BOT_ID);

  static final String NO_ANSWERS_FOUND_MESSAGE =
      "Oops, Telescope didn't find any results for your query: ";

  static final String SLACK_APP_ERROR_MESSAGE =
      """
      Oh no! Something is afoot with the Telescope Slack app! :flushed: Please try again. If the issue persists, reach out to us at #help-telescope.
      """;

  static final String TRY_AGAIN_MESSAGE = ":sob: Try another one! :telescope:";

  static final String USER_ERROR_RESPONSE =
      """
      Oh no! Looks like something went wrong there. :eyes: Please confirm that your query is correct. If the issue persists, reach us at #help-telescope.
      """;
}
