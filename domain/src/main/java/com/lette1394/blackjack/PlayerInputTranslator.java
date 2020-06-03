package com.lette1394.blackjack;

public class PlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final EventAnnouncer<PlayerCommandListener> announcer = new EventAnnouncer<>(PlayerCommandListener.class);

    public void translate(final String playerInput) {
        switch (playerInput) {
            case COMMAND_JOIN:
                announcer.announce().join();
                break;
            case COMMAND_STAY:
                announcer.announce().stay();
                break;
            case COMMAND_HIT:
                announcer.announce().hit();
                break;
            default:
                announcer.announce().cannotHandle(playerInput);
                break;
        }
    }

    public void addListener(final PlayerCommandListener listener) {
        announcer.addListener(listener);
    }
}
