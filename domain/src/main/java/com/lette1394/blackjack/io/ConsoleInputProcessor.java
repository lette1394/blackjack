package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.CommandListener;
import com.lette1394.blackjack.domain.player.PlayerRepository;
import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.event.EventAnnouncer;
import com.lette1394.blackjack.event.ListenersAware;

@RequiredArgsConstructor
public class ConsoleInputProcessor implements InputProcessor, ListenersAware<CommandListener> {
    private final EventAnnouncer<CommandListener> players = new EventAnnouncer<>(CommandListener.class);
    private final PlayerRepository playerRepository;

    @Override
    public void process(final String playerInput) {
        try {
            final ConsoleBlackjackProtocol protocol = new ConsoleBlackjackProtocol(playerInput);
            final Player player = playerRepository.find(protocol.getPlayerId());

            // TODO: command ENUM 만들기
            switch (protocol.getCommand()) {
                case "history":
                    players.announce().onHistory(player);
                    break;
                case "join":
                    players.announce().onJoin(player);
                    break;
                case "bet":
                    players.announce().onBet(player, protocol.getCoin());
                    break;
                case "stay":
                    players.announce().onStay(player);
                    break;
                case "hit":
                    players.announce().onHit(player);
                    break;
                case "rejoin":
                    players.announce().onRejoin(player);
                    break;
                case "leave":
                    players.announce().onLeave(player);
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
