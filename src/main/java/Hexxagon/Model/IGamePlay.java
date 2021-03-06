package Hexxagon.Model;

import Support.TError;
import java.io.Serializable;
import java.util.ArrayList;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.IField;

/**
 * Contains method specifications of data component of Hexxagon module.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public interface IGamePlay extends Serializable {

    /**
     * It stores the highest index allowed of column.
     */
    public final static int maximumExpectedStandColumnIndex = 8;
    /**
     * It stores the highest index allowed of row.
     */
    public final static int maximumExpectedStandRowIndex = 8;

    /**
     * Stores maximum player index allowed.
     */
    public final static byte maximumExpectedPlayerIndex = 6;

    /**
     * Stores minimum player index allowed.
     */
    public final static byte minimumExpectedPlayerIndex = 1;

    /**
     * Stores statuses of actual game play.
     */
    public enum StatusCode {

        /**
         * When the game is finished because the board is full.
         */
        BoardFullFinish,
        /**
         * When the game is finished because no free cell can be reached by the next player.
         */
        EnemyBlockedFinish,
        /**
         * When the game is finished because all enemy player is defeated.
         */
        EnemyFallFinish,
        /**
         * When the game is not finished.
         */
        NotFinished
    };

    /**
     * An input testing method invocate from other methods.
     * It determines whether the input is correct or not. In case of invalid input {@code TError} is thrown.
     *
     * @param stands The board array to test.
     *
     * @throws TError Thrown if the board array is invalid.
     */
    public void testAndThrowIfStandsInvalid(byte[][] stands) throws TError;

    /**
     * Sets the status of the game.
     *
     * @param newStatus The status enum to be set.
     */
    public void setGamePlayStatus(StatusCode newStatus);

    /**
     * Retrieves the game status.
     *
     * @return A {@code StatusCode} enum.
     */
    public StatusCode getStatus();

    /**
     * Tells whether the specified player exists.
     *
     * @param playerIndex The unique player index of the player.
     *
     * @return True if the specified player exists, false otherwise.
     */
    public boolean isPlayerExists(byte playerIndex);

    /**
     * Adds a player to the game play.
     *
     * @param player      The player object.
     * @param playerIndex The unique index of the player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addPlayer(IPlayer player, byte playerIndex) throws TError;

    /**
     * Retrieves the list of the players.
     *
     * @return A list of {@code IPlayer} instances.
     */
    public ArrayList<IPlayer> getPlayerList();

    /**
     * Directly sets the list of players participating in the game.
     *
     * @param list The list to be stored.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setPlayerList(ArrayList<IPlayer> list) throws TError;

    /**
     * Retrieves the index of the player allowed to do the next step.
     *
     * @return A unique player index.
     */
    public byte getAllowedPlayerIndex();

    /**
     * Sets the index of the of the player allowed to do the next step.
     *
     * @param allowedPlayerIndex A unique player index,
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setAllowedPlayerIndex(byte allowedPlayerIndex) throws TError;

    /**
     * Retrieves the positions of players.
     * By other words: It shows the shape of the board.
     *
     * @return A two dimensional {@code byte } array.
     */
    public byte[][] getStands();

    /**
     * Sets the positions of the players.
     * By other words: It shows the shape of the board.
     *
     * @param stands A two dimensional {@code byte } array.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setBoard(byte[][] stands) throws TError;

    /**
     * Retrieves the cell previously clicked.
     *
     * @return An {@code IField} instance.
     */
    public IField getPreviouslyClickedField();

    /**
     * Sets the previously clicked field.
     *
     * @param previousClickedField An {@code IField} instance to represent the cell.
     */
    public void setPreviouslyClickedField(IField previousClickedField);

    /**
     * Tells whether the game is finished.
     *
     * @return True if game is finished, false otherwise.
     */
    public boolean isFinished();

    /**
     * Sets the finished state of the game.
     *
     * @param isFinished True if game is finished, false otherwise.
     */
    public void setIsFinished(boolean isFinished);

    /**
     * Tells whether a cell exists or not.
     *
     * @param columnIndex The column index of the cell.
     * @param rowIndex    The row index of the cell.
     *
     * @return True if the specified cell exists, false otherwise.
     */
    public boolean isStandExists(int columnIndex, int rowIndex);

    /**
     * Retrieves a cell as an {@code IField} instance.
     *
     * @param columnIndex The column index of the cell.
     * @param rowIndex    The row index of the cell.
     *
     * @return True if the specified cell exists, false otherwise.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     *
     */
    public IField getField(int columnIndex, int rowIndex) throws TError;

}
