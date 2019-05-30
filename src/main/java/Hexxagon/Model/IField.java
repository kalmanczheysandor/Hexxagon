package Hexxagon.Model;

import java.io.Serializable;


/**
 * Describes methods of object represents a cell on game board.
* @author Sandor Kalmanchey
 */
public interface IField extends Serializable {

    /**
     * Sets the index of the player who occupied the cell.
     * @param playerIndex Unique index of the player
     */
    public void setPlayerIndex(byte playerIndex);

    /**
     * Retrieves the unique index of the player.
     * @return Integer value.
     */
    public byte getPlayerIndex();

    /**
     * Retrieves the column index of cell.
     * @return Integer value higher than 0
     */
    public int getColumnIndex();

    /**
     * Retrieves the row index of cell.
     * @return Integer value higher than 0
     */
    public int getRowIndex();

    
    /**
     * Sets whether the cell is active or not.
     * If a cell is active than it can be occupied.
     * @param isInactive True if the cell is inactive otherwise false.
     */
    public void setIsInactive(boolean isInactive);
    /**
     * Retrieves whether the cell is active.
     * @return True if the cell is inactive otherwise false.
     */
    public boolean isInactive();

}
