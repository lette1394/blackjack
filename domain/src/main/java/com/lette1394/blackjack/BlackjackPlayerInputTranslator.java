package com.lette1394.blackjack;

public class BlackjackPlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final EventAnnouncer<BlackjackPlayerCommandListener> players = new EventAnnouncer<>(
            BlackjackPlayerCommandListener.class);

    public void translate(final String playerInput) {
        try {
            final BlackjackProtocol protocol = new BlackjackProtocol(playerInput);
            final String playerId = protocol.getPlayerId();

            switch (protocol.getCommand()) {
                case COMMAND_JOIN:
                    players.announce().join(playerId);
                    break;
                case COMMAND_STAY:
                    players.announce().stay(playerId);
                    break;
                case COMMAND_HIT:
                    players.announce().hit(playerId);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        } catch (CannotParseBlackjackProtocolException e) {
            players.announce().cannotHandle(playerInput);
        }
    }

    public void addListener(final BlackjackPlayerCommandListener listener) {
        players.addListener(listener);
    }
}
