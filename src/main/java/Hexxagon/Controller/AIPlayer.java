package Hexxagon.Controller;

import Hexxagon.AI.IOperator;
import Hexxagon.AI.MinMax;
import Hexxagon.AI.BoardState;
import Hexxagon.AI.Heuristic.HeuristicFactory;
import Support.TError;
import Hexxagon.AI.IBoardState;
import Hexxagon.AI.IHeuristicFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains methods of an artificial player.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
final public class AIPlayer extends Player implements IAIPlayer {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AIPlayer.class);
    private Thread StepThrd;
    private int deepness = 1;
    private byte symbolOfInactiveCell = -1;
    private byte symbolOfEmptyCell = 0;
    private byte symbolOfFirstPlayer = 1;
    private byte symbolOfSecondPlayer = 2;
    private IHeuristicFactory heuristicFactory = new HeuristicFactory();

    /**
     * Creates a {@code AIPlayer} instance.
     *
     * @param colorString The colour code of the player.
     */
    public AIPlayer(String colorString) {
        super(colorString);
        this.isAutoPlayer = true;
        
        logger.trace("AIPlayer constructed!");
    }

    /**
     * Creates a {@code AIPlayer} instance.
     *
     * @param colorString The colour code of the player.
     * @param playerIndex The unique index of the player.
     */
    public AIPlayer(String colorString, byte playerIndex) {
        super(colorString, playerIndex);
        this.isAutoPlayer = true;
        
        logger.trace("AIPlayer constructed!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IOperator takeYourStep(byte[][] theBoard) throws TError {
        // Input checkings
        if(!this.isValidBoard(theBoard)) {
            throw new TError("Invalid board was given!");
        }
        if(this.symbolOfFirstPlayer != this.getPlayerIndex() && this.symbolOfSecondPlayer != this.getPlayerIndex()) {
            throw new TError("Invalid player index!");
        }
        if((this.symbolOfInactiveCell == this.symbolOfEmptyCell) || (this.symbolOfInactiveCell == this.symbolOfFirstPlayer) || (this.symbolOfInactiveCell == this.symbolOfSecondPlayer) || (this.symbolOfEmptyCell == this.symbolOfFirstPlayer) || (this.symbolOfEmptyCell == this.symbolOfSecondPlayer) || (this.symbolOfFirstPlayer == this.symbolOfSecondPlayer)) {
            throw new TError("Invalid property value!");
        }

        // Convert and store value
        byte[][] board = this.boardConvertter(theBoard);

        // Determine offered and enemy plaer indexes
        byte offeredPlayerIndex = this.symbolOfFirstPlayer;
        byte enemyPlayerIndex = this.symbolOfSecondPlayer;
        if(this.symbolOfSecondPlayer == this.getPlayerIndex()) {
            offeredPlayerIndex = this.symbolOfSecondPlayer;
            enemyPlayerIndex = this.symbolOfFirstPlayer;
        }
        logger.debug("Offered:" + offeredPlayerIndex + "; enemy:" + enemyPlayerIndex);
        // Create board sate
        IBoardState boardState = new BoardState(board, offeredPlayerIndex);

        logger.debug(boardState.toString());
        // Calculate next step
        return MinMax.takeOffer(boardState, this.heuristicFactory, this.deepness, offeredPlayerIndex, enemyPlayerIndex);

    }

    /**
     * Tells whether the given board is valid or not.
     *
     * @param board The board to be tested.
     *
     * @return True if the board is a valid one, false otherwise.
     */
    private boolean isValidBoard(byte[][] board) {
        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {
                byte value = board[columnIndex][rowIndex];

                if((value != this.symbolOfInactiveCell) && (value != this.symbolOfEmptyCell) && (value != this.symbolOfFirstPlayer) && (value != this.symbolOfSecondPlayer)) {
                    return false;
                }
            }
        }
        return true;
    }

    private byte[][] boardConvertter(byte[][] board) throws TError {
        byte[][] output = null;

        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {
                // Instantiation of the new array
                if(output == null) {
                    output = new byte[board.length][board[columnIndex].length];
                }

                // Check and convert values
                byte value = board[columnIndex][rowIndex];
                if(value == this.symbolOfInactiveCell) {
                    output[columnIndex][rowIndex] = IBoardState.symbolOfInactiveCell;
                }
                else if(value == this.symbolOfEmptyCell) {
                    output[columnIndex][rowIndex] = IBoardState.symbolOfEmptyCell;
                }
                else if(value == this.symbolOfFirstPlayer) {
                    output[columnIndex][rowIndex] = IBoardState.symbolOfFirstPlayer;
                }
                else if(value == this.symbolOfSecondPlayer) {
                    output[columnIndex][rowIndex] = IBoardState.symbolOfSecondPlayer;
                }
                else {
                    throw new TError("Unexpected board value was found at [" + columnIndex + "][" + rowIndex + "]!");
                }
            }
        }

        if(output == null) {
            throw new TError("Unexpected null value was found!");
        }

        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeepness(int deepness) throws TError {
        this.deepness = deepness;
    }
}
