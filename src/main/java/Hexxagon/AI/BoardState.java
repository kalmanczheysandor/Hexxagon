package Hexxagon.AI;

import Support.TError;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the implementation of sate component specified by {@code IBoardState} interface.
 * The instance of class represents a state of the board caused by operator apply.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public final class BoardState implements IBoardState {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(BoardState.class);
    /**
     * Array representation of board.
     */
    private byte[][] board;
    /**
     * The index of player for who is allowed to do the ongoing step.
     */
    private byte actualPlayerIndex;

    /**
     * Creates a {@code BoardState} instance.
     *
     * @param board             A two dimensional array representing the board table. Value must not be null and each cell must contain one of the allowed values (-1,0,1,2) otherwise {@code TError} is thrown.
     * @param actualPlayerIndex The unique index of player who is allowed to do the ongoing step. Value must be 1 or 2 otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public BoardState(byte[][] board, byte actualPlayerIndex) throws TError {
        // Input checkings
        if(!BoardState.isValidBoard(board)) {
            throw new TError("Invalid board was given!");
        }
        if(IBoardState.symbolOfFirstPlayer != actualPlayerIndex && IBoardState.symbolOfSecondPlayer != actualPlayerIndex) {
            throw new TError("Invalid player index!");
        }
        if((IBoardState.symbolOfInactiveCell == IBoardState.symbolOfEmptyCell) || (IBoardState.symbolOfInactiveCell == IBoardState.symbolOfFirstPlayer) || (IBoardState.symbolOfInactiveCell == IBoardState.symbolOfSecondPlayer) || (IBoardState.symbolOfEmptyCell == IBoardState.symbolOfFirstPlayer) || (IBoardState.symbolOfEmptyCell == IBoardState.symbolOfSecondPlayer) || (IBoardState.symbolOfFirstPlayer == IBoardState.symbolOfSecondPlayer)) {
            throw new TError("Invalid set of property values!");
        }

        // Store values
        this.board = board;
        this.actualPlayerIndex = actualPlayerIndex;
        
        logger.trace("BoardState constructed!");
    }

    /**
     * Answers whether the board is a valid board.
     * A board is valid when:
     * - each cells contains one of the allowed values (-1,0,1,2)
     * - The length and with of array is equal.
     *
     * @param board The board which have to be tested.
     *
     * @return True if the board object is a valid one otherwise false.
     */
    private static boolean isValidBoard(byte[][] board) {
        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {
                byte value = board[columnIndex][rowIndex];
                if((value != IBoardState.symbolOfInactiveCell) && (value != IBoardState.symbolOfEmptyCell) && (value != IBoardState.symbolOfFirstPlayer) && (value != IBoardState.symbolOfSecondPlayer)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tells whether the player index is a valid one.
     *
     * @param playerIndex The player index to validate.
     *
     * @return True if the player index is valid false, otherwise.
     */
    private static boolean isValidPlayerIndex(byte playerIndex) {
        if(playerIndex == IBoardState.symbolOfFirstPlayer || playerIndex == IBoardState.symbolOfSecondPlayer) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isThePlayerHasNoBall(byte playerIndex) throws TError {
        // Input checkings
        if(!isValidPlayerIndex(playerIndex)) {
            throw new TError("Invalid player index value was given!");
        }

        // Determination
        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {

                if(this.board[columnIndex][rowIndex] == playerIndex) {
                    return false;
                }

            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isAnybodyHasNoBall() throws TError {

        if(this.isThePlayerHasNoBall(IBoardState.symbolOfFirstPlayer) || this.isThePlayerHasNoBall(IBoardState.symbolOfSecondPlayer)) {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isBoardFull() {

        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {
                if(this.board[columnIndex][rowIndex] == IBoardState.symbolOfEmptyCell) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEmptyAndActive(int columnIndex, int rowIndex) throws TError {
        // Input checking 
        if(!this.isCellExists(columnIndex, rowIndex)) {
            throw new TError("The coordinates [" + columnIndex + "," + rowIndex + "] are out of range!");
        }

        // Determine value
        if(this.getCellValue(columnIndex, rowIndex) == IBoardState.symbolOfEmptyCell) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellExists(int columnIndex, int rowIndex) {
        if(columnIndex < 0 || columnIndex > (this.board.length - 1)) {
            return false;
        }

        if(rowIndex < 0 || rowIndex > (this.board[columnIndex].length - 1)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellOccupied(int columnIndex, int rowIndex) throws TError {
        // Input checking 
        if(!this.isCellExists(columnIndex, rowIndex)) {
            throw new TError("The coordinates [" + columnIndex + "," + rowIndex + "] are out of range!");
        }

        // Get value
        if(this.board[columnIndex][rowIndex] == IBoardState.symbolOfFirstPlayer || this.board[columnIndex][rowIndex] == IBoardState.symbolOfSecondPlayer) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte getCellValue(int columnIndex, int rowIndex) throws TError {
        // Input checking 
        if(!this.isCellExists(columnIndex, rowIndex)) {
            throw new TError("The coordinates [" + columnIndex + "," + rowIndex + "] are out of range!");
        }

        // Get value
        return this.board[columnIndex][rowIndex];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCellValue(int columnIndex, int rowIndex, byte fieldValue) throws TError {

        // Input checking 
        if(!this.isCellExists(columnIndex, rowIndex)) {
            throw new TError("The coordinates [" + columnIndex + "," + rowIndex + "] are out of range!");
        }
        if((fieldValue != IBoardState.symbolOfInactiveCell) && (fieldValue != IBoardState.symbolOfEmptyCell) && (fieldValue != IBoardState.symbolOfFirstPlayer) && (fieldValue != IBoardState.symbolOfSecondPlayer)) {
            throw new TError("Invalid field value was given!");
        }

        // Set value
        this.board[columnIndex][rowIndex] = fieldValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEmptyCellCount() {

        int count = 0;
        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {
                if(this.board[columnIndex][rowIndex] == IBoardState.symbolOfEmptyCell) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerBallCount(byte playerIndex) throws TError {
        // Input checking
        if(!isValidPlayerIndex(playerIndex)) {
            throw new TError("Invalid player index value was given!");
        }

        // Determine value
        int count = 0;
        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {
                if(this.board[columnIndex][rowIndex] == playerIndex) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActiveCellCount() {
        int count = 0;

        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {
                if(this.board[columnIndex][rowIndex] == IBoardState.symbolOfEmptyCell || this.board[columnIndex][rowIndex] == IBoardState.symbolOfFirstPlayer || this.board[columnIndex][rowIndex] == IBoardState.symbolOfSecondPlayer) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        String rowsStr = "";
        for(int columnIndex = 0; columnIndex < this.board.length; columnIndex++) {
            String SwapStr = "";
            for(int k = 1; k < columnIndex; k++) {
                SwapStr += "_.";
            }

            String AColumnStr = SwapStr;

            for(int rowIndex = 0; rowIndex < this.board[columnIndex].length; rowIndex++) {

                switch (this.board[columnIndex][rowIndex]) {
                    case IBoardState.symbolOfInactiveCell:
                        AColumnStr += " |";
                        break;
                    case IBoardState.symbolOfEmptyCell:
                        AColumnStr += "_|";
                        break;
                    case IBoardState.symbolOfFirstPlayer:
                        AColumnStr += "X|";
                        break;
                    case IBoardState.symbolOfSecondPlayer:
                        AColumnStr += "O|";
                        break;

                }

            }
            rowsStr = AColumnStr + "\n" + rowsStr;
        }
        return rowsStr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[][] getBoard() {
        return board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte getActualPlayerIndex() {
        return actualPlayerIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte getEnemyPlayerIndex() {

        if(this.actualPlayerIndex == IBoardState.symbolOfFirstPlayer) {
            return IBoardState.symbolOfSecondPlayer;
        }
        return IBoardState.symbolOfFirstPlayer;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isOperatorInValidRange(IOperator operator) throws TError {

        // Input checkings
        if(operator == null) {
            throw new TError("Property sholud not be null!");
        }

        // If base cell does not exists
        if(!this.isCellExists(operator.getBaseColumnIndex(), operator.getBaseRowIndex())) {
            return false;
        }

        // If target cell does not exists
        if(!this.isCellExists(operator.getTargetColumnIndex(), operator.getTargetRowIndex())) {
            return false;
        }

        return true;

    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final BoardState other = (BoardState) obj;
        if(this.actualPlayerIndex != other.actualPlayerIndex) {
            return false;
        }
        if(!Arrays.deepEquals(this.board, other.board)) {
            return false;
        }
        return true;
    }

}
