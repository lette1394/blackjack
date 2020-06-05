package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlackjackPlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final EventAnnouncer<BlackjackPlayerCommandListener> players = new EventAnnouncer<>(BlackjackPlayerCommandListener.class);
    private final PlayerRepository playerRepository;

    public void translate(final String playerInput) {
        try {
            final BlackjackProtocol protocol = new BlackjackProtocol(playerInput);
            final Player player = playerRepository.find(protocol.getPlayerId());

            switch (protocol.getCommand()) {
                case COMMAND_JOIN:
                    players.announce().join(player);
                    break;
                case COMMAND_STAY:
                    players.announce().stay(player);
                    break;
                case COMMAND_HIT:
                    players.announce().hit(player);
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
