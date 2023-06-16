package mdc.components.players;

import mdc.components.piles.OwnBank;
import mdc.components.piles.OwnPlayerPile;
import mdc.components.piles.OwnProperty;

/**
 * Class used to act as a player
 */
public class Player {
    private OwnBank ownBank;
    private OwnProperty ownProperty;
    private OwnPlayerPile ownPlayerPile;

    public Player(OwnBank ownBank, OwnProperty ownProperty, OwnPlayerPile ownPlayerPile) {
        this.ownBank = ownBank;
        this.ownProperty = ownProperty;
        this.ownPlayerPile = ownPlayerPile;
    }


    public OwnBank getOwnBank() {
        return ownBank;
    }

    public OwnProperty getOwnProperty() {
        return ownProperty;
    }

    public void clear(Player player) {
        player.ownBank.clear();
    }

    public OwnPlayerPile getOwnPlayerPile() {
        return ownPlayerPile;
    }

}
