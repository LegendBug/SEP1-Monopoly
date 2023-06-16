package mdc.states.game;

import java.util.LinkedList;

public class GameInfoBar extends LinkedList<String> {
    private final int limit;

    public GameInfoBar(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(String str) {
        if (str == null) return false;
        super.addFirst(str);
        while (size() > limit) {
            super.removeLast();
        }
        return true;
    }
}
