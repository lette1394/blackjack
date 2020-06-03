package com.lette1394.blackjack;

public class BlackjackPlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final EventAnnouncer<BlackjackPlayerCommandListener> blackjackPlayer = new EventAnnouncer<>(BlackjackPlayerCommandListener.class);

    public void translate(final String playerInput) {
        switch (playerInput) {
            case COMMAND_JOIN:
                blackjackPlayer.announce().join();
                break;
            case COMMAND_STAY:
                blackjackPlayer.announce().stay();
                break;
            case COMMAND_HIT:
                blackjackPlayer.announce().hit();
                break;
            default:
                blackjackPlayer.announce().cannotHandle(playerInput);
                break;
        }
    }

    public void addListener(final BlackjackPlayerCommandListener listener) {
        blackjackPlayer.addListener(listener);
    }
}
