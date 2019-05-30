package Hexxagon.AI;

import Support.TError;

/**
 * Contains method specifications of state of board caused by alternate steps of players.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IBoardState {

    /**
     * Contains the value of a board cell when it is inactive.
     */
    public static final byte symbolOfInactiveCell = -1;
    /**
     * Contains the value of a board cell when it active and not occupied by any player.
     */
    public static final byte symbolOfEmptyCell = 0;
    /**
     * Contains the value of a board cell when it is occupied by first player.
     */
    public static final byte symbolOfFirstPlayer = 1;
    /**
     * Contains the value of a board cell when it is occupied by second player.
     */
    public static final byte symbolOfSecondPlayer = 2;

    /**
     * Answers whether the player has no ball left.
     *
     * @param playerIndex Unique index of player. It must not be in interval of 1-2 otherwise {@code  TError} is thrown.
     *
     * @return True if the specified player has no more ball left on the board otherwise false
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isThePlayerHasNoBall(byte playerIndex) throws TError;

    /**
     * Answers whether is a player who has no ball left.
     *
     * @return True if there is at least one player who has no ball left on the board otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isAnybodyHasNoBall() throws TError;

    /**
     * Answers whether no more unoccupied cell remained on the board.
     *
     * @return True if there no more unoccupied cell otherwise false.
     */
    public boolean isBoardFull();

    /**
     * Answers whether the specified cell is occupied.
     *
     * @param columnIndex The column index of the requested cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     * @param rowIndex    The row index of the requested cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     *
     * @return True if the requested cell exists otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isCellOccupied(int columnIndex, int rowIndex) throws TError;

    /**
     * Retrieves the value of the specified cell.
     *
     * @param columnIndex The column index of the requested cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     * @param rowIndex    The row index of the requested cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     *
     *
     * @return It is "-1" if cell is inactive. It is "0" if cell is active but not occupied. It is "1" if cell is occupied by first player. It is "2" if cell is occupied by second player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public byte getCellValue(int columnIndex, int rowIndex) throws TError;

    /**
     * Sets the value of the specified cell.
     *
     * @param columnIndex The column index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     * @param rowIndex    The row index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     *
     *
     * @param value       It can be "-1" if cell is inactive or "0" if cell is active but not occupied or "1" if cell is occupied by first player or "2" if cell is occupied by second player. In case of other values {@code  TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setCellValue(int columnIndex, int rowIndex, byte value) throws TError;

    /**
     * Retrieves the board representing two dimensional array.
     *
     * @return The reference to the array.
     */
    public byte[][] getBoard();

    /**
     * Retrieves the count of balls of specified player.
     *
     * @param playerIndex The index of the player. It must be 1 or 2 otherwise {@code TError is thrown}.
     *
     * @return 0 or higher int value.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public int getPlayerBallCount(byte playerIndex) throws TError;

    /**
     * Retrieves the count of active cells. Active cells can be occupied or unoccupied.
     *
     * @return 0 or higher int value.
     */
    public int getActiveCellCount();

    /**
     * Retrieves the count of unoccupied and active cells.
     *
     * @return 0 or higher int value.
     */
    public int getEmptyCellCount();

    /**
     * Retrieves the the unique index of active player. Active player who is allowed to do the ongoing step.
     *
     * @return 1 or 2 as value.
     */
    public byte getActualPlayerIndex();

    /**
     * Retrieves the the unique index of enemy player. Enemy player who is not allowed to do the ongoing step.
     *
     * @return 1 or 2 as value.
     */
    public byte getEnemyPlayerIndex();

    /**
     * Answers whether the requested cell exists or not.
     *
     * @param columnIndex The column index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     * @param rowIndex    The row index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     *
     * @return True if specified cell exists otherwise false.
     */
    public boolean isCellExists(int columnIndex, int rowIndex);

    /**
     * Answers whether the requested cell is an active and not occupied one.
     *
     * @param columnIndex The column index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     * @param rowIndex    The row index of the cell. It must not be less than 0 and higher than than the highest index of board representing array otherwise {@code  TError} is thrown.
     *
     *
     * @return True if specified cell is active and unoccupied otherwise false.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isCellEmptyAndActive(int columnIndex, int rowIndex) throws TError;

    /**
     * Answers whether the operator applied inside of the interval of the board.
     *
     * @param operator The object of the operator. Object must not be null otherwise {@code TError} is thrown.
     *
     * @return True if the operator is applied inside the interval of the board.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isOperatorInValidRange(IOperator operator) throws TError;

}
