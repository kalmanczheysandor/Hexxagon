package Hexxagon.Controller;

import java.util.Objects;

/**
 * Contains generalised methods of all possible type of players in controller layer.
 * Artificial player and human player should be an extension of this class
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public class Player implements IPlayer {

    /**
     * Contains the colour of the player.
     */
    protected String playerColor;

    /**
     * Contains whether the player is an artificial player.
     */
    protected boolean isAutoPlayer = false;

    /**
     * Contains the unique index of the player.
     */
    protected byte playerIndex;

    /**
     * Contains whether the player is defeated.
     */
    protected boolean isDefeated = false;

    /**
     * Creates a {@code Player} instance.
     *
     * @param colorString The colour code of player.
     */
    public Player(String colorString) {
        this.playerColor = colorString;
    }

    /**
     * Creates a {@code Player} instance.
     *
     * @param colorString Colour code of player.
     * @param playerIndex Unique index of player
     */
    public Player(String colorString, byte playerIndex) {
        this.playerColor = colorString;
        this.playerIndex = playerIndex;
    }

    /**
     * Creates a {@code Player} instance.
     *
     * @param playerIndex unique index of player
     */
    public Player(byte playerIndex) {
        this.playerIndex = playerIndex;
    }

    /*public final boolean getIsAutoPlayer() {
        return this.isAutoPlayer;
    }*/
    /**
     * {@inheritDoc}
     */
    @Override
    public final byte getPlayerIndex() {
        return playerIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPlayerIndex(byte playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isDefeated() {
        return isDefeated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setIsDefeated(Boolean isDefeated) {
        this.isDefeated = isDefeated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        final Player other = (Player) obj;
        if(!Objects.equals(this.playerIndex, other.playerIndex)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAutoPlayer() {
        //return this.getIsAutoPlayer();
        return this.isAutoPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerColor() {
        return this.playerColor;
    }

}
