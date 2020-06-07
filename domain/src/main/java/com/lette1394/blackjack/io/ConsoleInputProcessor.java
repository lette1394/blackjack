package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.CommandListener;
import com.lette1394.blackjack.domain.player.PlayerRepository;
import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.event.EventAnnouncer;
import com.lette1394.blackjack.event.ListenersAware;

@RequiredArgsConstructor
public class ConsoleInputProcessor implements InputProcessor, ListenersAware<CommandListener> {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final EventAnnouncer<CommandListener> players = new EventAnnouncer<>(CommandListener.class);
    private final PlayerRepository playerRepository;

    @Override
    public void process(final String playerInput) {
        try {
            final ConsoleBlackjackProtocol protocol = new ConsoleBlackjackProtocol(playerInput);
            final Player player = playerRepository.find(protocol.getPlayerId());

            switch (protocol.getCommand()) {
                case COMMAND_JOIN:
                    players.announce().onJoin(player);
                    break;
                case COMMAND_STAY:
                    players.announce().onStay(player);
                    break;
                case COMMAND_HIT:
                    players.announce().onHit(player);
                    break;
                default:
                    throw new CannotParseBlackjackProtocolException(playerInput);
            }
        } catch (CannotParseBlackjackProtocolException e) {
            players.announce().onInvalidCommand(playerInput);
        }
    }

    @Override
    public void addListener(final CommandListener listener) {
        players.addListener(listener);
    }
}
