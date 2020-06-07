package com.lette1394.blackjack.domain.trump;

import java.util.LinkedList;
import java.util.List;

public class DrawReadyTrumps extends Trumps {
    public DrawReadyTrumps(final List<Trump> trumps) {
        super(new LinkedList<>(trumps));
    }
}
