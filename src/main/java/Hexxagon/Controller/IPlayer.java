package Hexxagon.Controller;

import java.io.Serializable;

/**
 * Describes generalised methods of all possible type of players in controller layer.
 * Artificial player and human player should implement these methods.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public interface IPlayer extends Serializable {

    /**
     * Retrieves the colour of player.
     *
     * @return The colour code of player in html style like <code>#cc0000</code>
     */
    public String getPlayerColor();

    /**
     * Retrieves the unique index of the player.
     *
     * @return An higher than 0 integer value.
     */
    public byte getPlayerIndex();

    /**
     * Sets the unique index of the player.
     *
     * @param playerIndex Value should be higher than 0.
     */
    public void setPlayerIndex(byte playerIndex);

    /**
     * Tells whether the player is defeated.
     *
     * @return True if player is defeated otherwise false.
     */
    public boolean isDefeated();

    /**
     * Set the defeated status of player.
     *
     * @param isDefeated True if player is defeated otherwise false.
     */
    public void setIsDefeated(Boolean isDefeated);

    /**
     * Tells whether the player is an artificial player.
     *
     * @return True if the player is artificial otherwise false.
     */
    public boolean isAutoPlayer();
}
