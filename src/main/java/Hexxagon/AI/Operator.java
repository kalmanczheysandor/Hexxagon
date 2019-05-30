package Hexxagon.AI;

import Support.TCoordinate2D;

/**
 * Contains shared methods of different type of operators.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
abstract class Operator implements IOperator {

    /**
     * Relative values of adjacent coordinates.
     */
    protected static final TCoordinate2D[] relativeCoordinatesAdjacentCells = {
        /*C-1*/new TCoordinate2D(-1, 0), new TCoordinate2D(-1, +1),
        /*C0*/ new TCoordinate2D(0, -1), new TCoordinate2D(0, 1),
        /*C+1*/ new TCoordinate2D(1, -1), new TCoordinate2D(1, 0)
    };

    /**
     * Stores column index from where the step will be taken.
     */
    protected final int baseColumnIndex;
    /**
     * Stores row index from where the step will be taken.
     */
    protected final int baseRowIndex;

    /**
     * Stores column index to where the step will be taken.
     */
    protected final int targetColumnIndex;
    /**
     * Stores row index to where the step will be taken.
     */
    protected final int targetRowIndex;

    /**
     * Creates a {@code Operator} instance.
     *
     * @param baseColumnIndex   Column index of base cell.
     * @param baseRowIndex      Row index of base cell.
     * @param targetColumnIndex Column index of destination cell.
     * @param targetRowIndex    Row index of destination cell.
     */
    public Operator(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {
        // Store values
        this.baseColumnIndex = baseColumnIndex;
        this.baseRowIndex = baseRowIndex;
        this.targetColumnIndex = targetColumnIndex;
        this.targetRowIndex = targetRowIndex;
    }

    /**
     * Tells whether the given {@code boardState} is a valid one.
     *
     * @param boardState The state to test.
     *
     * @return True if the state is a valid one, false otherwise.
     */
    protected static boolean isValidBoardState(IBoardState boardState) {
        if(boardState == null) {
            return false;
        }

        byte[][] board = boardState.getBoard();

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
     * {@inheritDoc}
     */
    @Override
    public int getBaseColumnIndex() {
        return baseColumnIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBaseRowIndex() {
        return baseRowIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTargetColumnIndex() {
        return targetColumnIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTargetRowIndex() {
        return targetRowIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" + "baseColumnIndex=" + baseColumnIndex + ", baseRowIndex=" + baseRowIndex + ", targetColumnIndex=" + targetColumnIndex + ", targetRowIndex=" + targetRowIndex + '}';
    }

}
