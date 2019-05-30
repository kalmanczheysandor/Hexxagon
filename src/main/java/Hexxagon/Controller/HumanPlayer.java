package Hexxagon.Controller;

/**
 * Contains methods of a non-artificial player.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public final class HumanPlayer extends Player {

    /**
     * Creates a {@code HumanPlayer} instance.
     *
     * @param colorString The colour code of the player. 
     */
    public HumanPlayer(String colorString) {
        super(colorString);
        this.isAutoPlayer = false;
    }

    /**
     * Creates a {@code HumanPlayer} instance.
     * @param colorString The colour code of the player.
     * @param playerIndex The unique index of the player.
     */
    public HumanPlayer(String colorString, byte playerIndex) {
        super(colorString, playerIndex);
        this.isAutoPlayer = false;
    }
}
