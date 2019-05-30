package Hexxagon.Model;

import Support.TError;

import java.util.ArrayList;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.IGamePlay.StatusCode;

/**
 * Contains method specifications of Data Access Object of Hexxagon module.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IGameData {

    /**
     * Statuses of game.
     */
    public enum ProcessStatusCode {
        /**
         * The status is not determined yet.
         */
        NOTHING,
        /**
         * The game has been initialised already.
         */
        INITIALISED,
        /**
         * The game is prepared / ready to start the play.
         */
        PREPARED,
        /**
         * The playing has been paused.
         */
        PAUSED,
        /**
         * The game play has been stopPed permanently.
         */
        STOPPED,
        /**
         * The game is on!
         */
        RUNNING
    };

    /**
     * Sets the status of the game.
     *
     * @param status A {@code ProcessStatusCode} enum.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setGameProcessStatus(ProcessStatusCode status) throws TError;

    /**
     * Retrieves the status of the game.
     *
     * @return A {@code ProcessStatusCode} enum.
     */
    public ProcessStatusCode getGameProcessStatus();

    /**
     * Answers whether the cell exists specified by given coordinates.
     *
     * @param columnIndex Column index of cell.
     * @param rowIndex    Row index of cell.
     *
     * @return True if cell exists otherwise false.
     */
    public boolean isCellExists(int columnIndex, int rowIndex);

    /**
     * Retrieves the cell as {@code IField} specified by coordinates.
     * If cell not exists than {@code TError} is thrown.
     *
     * @param columnIndex Column index of cell.
     * @param rowIndex    Row index of cell.
     *
     * @return Returns an instance of class implementing {@code IField} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IField getCellAsField(int columnIndex, int rowIndex) throws TError;

   
    /**
     * Answers whether the specified player is defeated.
     *
     * @param playerIndex Unique index of specified player.
     *
     * @return True if player is defeated otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isPlayerDefeated(byte playerIndex) throws TError;

    /**
     * Retrieves the lowest player index.
     *
     * @return Returns a byte value higher than 0.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public byte lowestPlayerIndex() throws TError;

    /**
     * Answers whether the player exists at specified player index.
     *
     * @param playerIndex The unique index of player.
     *
     * @return True if player exists otherwise false.
     *
     */
    public boolean isPlayerExists(byte playerIndex);

    /**
     * Retrieves the player object of specified player.
     *
     * @param playerIndex Unique index of requested player.
     *
     * @return An instance of class implementing {@code IPlayer} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IPlayer getPlayer(byte playerIndex) throws TError;

    /**
     * Sets specified player to be defeated.
     *
     * @param playerIndex Unique index of player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setPlayerAsDefeated(byte playerIndex) throws TError;

    /**
     * Counts the cells occupied by specified player.
     *
     * @param playerIndex The unique index of specified player.
     *
     * @return A 0 or higher integer value.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public int getPlayerBallCount(byte playerIndex) throws TError;

    /**
     * Retrieves a two dimensional array containing all column and row indexes of occupied cells.
     *
     * @param playerIndex The unique index of specified player.
     *
     * @return An array.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public int[][] getPlayerPositions(byte playerIndex) throws TError;

    /**
     * Retrieves the list of players.
     *
     * @return An list of instances of classes implementing {@code IPlayer} interface.
     */
    public ArrayList<IPlayer> getPlayerList();

    /**
     * Retrieves the unique index of player who is permitted to do the next step.
     *
     * @return An byte value higher than 0.
     */
    public byte getAllowedPlayerIndex();

    /**
     * Sets the unique index of player who is permitted to do the next step.
     *
     * @param allowedPlayerIndex The unique index of player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setAllowedPlayerIndex(byte allowedPlayerIndex) throws TError;

    /**
     * Retrieves cells.
     *
     * @return A two dimensional array containing the cell state byte value.
     */
    public byte[][] getCells();

    /**
     * Sets the board shape of actual game play.
     *
     * @param boardShape A two dimensional byte array object containing cell state values
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setBoardShapeOfActualGamePlay(byte[][] boardShape) throws TError;

    /**
     * Tells which cell was clicked last.
     *
     * @return An instance of class implementing {@code IField} interface.
     */
    public IField getPreviousClickedField();

    /**
     * Sets the last clicked field.
     *
     * @param previousClickedField An instance of class implementing {@code IField} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setPreviousClickedField(IField previousClickedField) throws TError;

    /**
     * Deletes information about last clicked field.
     */
    public void removeLastClickedField();

    /**
     * Tells whether is the board full.
     * A board is full when no more unoccupied cell can be found.
     *
     * @return True if board is full otherwise false.
     */
    public boolean isBoardFull();

    /**
     * Counts the players are defeated.
     *
     * @return An integer value 0 or higher.
     */
    public int defeatedPlayerCount();

    /**
     * Counts of players participating in game.
     *
     * @return An {@code int} value 0 or higher.
     */
    public int playerCount();

    /**
     * Saves the complete game play into an external source.
     *
     * @param fileName The file path.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void saveGamePlay(String fileName) throws TError;

    /**
     * Loads a game play from an external source.
     *
     * @param fileName The file path.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void loadGamePlay(String fileName) throws TError;

    /**
     * Destroys the actual game play,
     */
    public void destroyActualGamePlay();

    /**
     * Sets the actual game play.
     *
     * @param actualGamePlay An instance of class implementing {@code IGamePlay} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setActualGamePlay(IGamePlay actualGamePlay) throws TError;

    /**
     * Tells whether the game is finished.
     *
     * @return True if game is finished otherwise false.
     */
    public boolean isGameFinished();

    /**
     * Sets whether the game is finished.
     *
     * @param isFinished True if game is finished otherwise false.
     */
    public void setIsGameFinished(boolean isFinished);

    /**
     * Retrieves a cell state byte value.
     *
     * @param columnIndex Column index of specified cell.
     * @param rowIndex    Row index of specified cell.
     *
     * @return An byte value -1 or higher.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public byte getCell(int columnIndex, int rowIndex) throws TError;

    /**
     * Retrieves the status of game.
     *
     * @return A {@code StatusCode} enum.
     */
    public StatusCode getStatus();

    /**
     * Tells whether the specified cell is occupied.
     *
     * @param columnIndex Column index of specified cell.
     * @param rowIndex    Row index of specified cell.
     *
     * @return True if cell is occupied otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isCellOccupied(int columnIndex, int rowIndex) throws TError;

    /**
     * Sets a cell being occupied by a given player.
     *
     * @param columnIndex Column index of specified cell.
     * @param rowIndex    Row index of specified cell.
     * @param playerIndex Unique index of player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setCellOccupiedByPlayer(int columnIndex, int rowIndex, byte playerIndex) throws TError;

    /**
     * Tells whether the specified cell is inactive.
     *
     * @param columnIndex Column index of specified cell.
     * @param rowIndex    Row index of specified cell.
     *
     * @return True if cell is inactive otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isCellInactive(int columnIndex, int rowIndex) throws TError;

    /**
     * Sets specified cell unoccupied.
     *
     * @param columnIndex Column index of specified cell.
     * @param rowIndex    Row index of specified cell.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setCellLiberated(int columnIndex, int rowIndex) throws TError;

    /**
     * Sets the status of actual game play.
     *
     * @param newStatus An {@code StatusCode} enum.
     */
    public void setGamePlayStatus(StatusCode newStatus);
}
