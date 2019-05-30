package Hexxagon.AI;

import Support.TCoordinate2D;
import Support.TError;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains method of fission step applied on board state.
 * A fission step never steps farer than a neighbour cell.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public final class FissionOperator extends Operator {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(FissionOperator.class);

    /**
     * Relative values of attack coordinates.
     */
    private static final TCoordinate2D[] relativeCoordinatesOfAttackZone = {
        /*C-1*/new TCoordinate2D(-1, 0), new TCoordinate2D(-1, +1),
        /*C0*/ new TCoordinate2D(0, -1), new TCoordinate2D(0, 1),
        /*C+1*/ new TCoordinate2D(1, -1), new TCoordinate2D(1, 0)
    };

    /**
     * Creates a {@code FissionOperator} instance.
     *
     * @param baseColumnIndex   Column index of base cell.
     * @param baseRowIndex      Row index of base cell.
     * @param targetColumnIndex Column index of destination cell.
     * @param targetRowIndex    Row index of destination cell.
     */
    public FissionOperator(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {
        super(baseColumnIndex, baseRowIndex, targetColumnIndex, targetRowIndex);
    }

    /**
     * Generates all available steps represented by this type of operator.
     *
     * @param boardState         The specified state on which all apliciable operator is generated.
     * @param offeredPlayerIndex Index of supported player.
     *
     * @return A list operator in all possible variation.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public static ArrayList<IOperator> generateAvailableOperatorsOnState(IBoardState boardState, byte offeredPlayerIndex) throws TError {
        // Input checkings
        if(boardState == null) {
            throw new TError("Input parameter should not be null!");
        }

        if(!isValidBoardState(boardState)) {
            throw new TError("Invalid board was given!");
        }
        if(boardState.getActualPlayerIndex() != offeredPlayerIndex && boardState.getEnemyPlayerIndex() != offeredPlayerIndex) {
            throw new TError("Invalid player index was given");
        }

        ArrayList<TCoordinate2D> fromCoordinates = new ArrayList<>();
        ArrayList<TCoordinate2D> targetCoordinates = new ArrayList<>();

        //
        byte[][] board = boardState.getBoard();
        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {
                byte value = board[columnIndex][rowIndex];

                if(value == offeredPlayerIndex) {
                    fromCoordinates.add(new TCoordinate2D(columnIndex, rowIndex));
                }
                else if(value == IBoardState.symbolOfEmptyCell) {
                    targetCoordinates.add(new TCoordinate2D(columnIndex, rowIndex));
                }
            }
        }

        //
        ArrayList<IOperator> output = new ArrayList<>();
        for(TCoordinate2D fromCoordinate : fromCoordinates) {
            for(TCoordinate2D targetCoordinate : targetCoordinates) {

                if(isAttemptedCellInPossibleAttackRange(fromCoordinate.getX(), fromCoordinate.getY(), targetCoordinate.getX(), targetCoordinate.getY())) {
                    IOperator opr = new FissionOperator(fromCoordinate.getX(), fromCoordinate.getY(), targetCoordinate.getX(), targetCoordinate.getY());
                    output.add(opr);
                }
            }
        }

        return output;
    }

    /**
     * Tells whether the distance is still belongs to a possibly valid range.
     * Does not test whether the base or destination cell exist or not.
     *
     * @param baseColumnIndex      Column index of base cell.
     * @param baseRowIndex         Row index of base cell.
     * @param attemptedColumnIndex Column index of destination cell.
     * @param attemptedRowIndex    Row index of destination cell.
     *
     * @return True if the step is in a apliciable range, true otherwise.
     */
    private static boolean isAttemptedCellInPossibleAttackRange(int baseColumnIndex, int baseRowIndex, int attemptedColumnIndex, int attemptedRowIndex) {

        for(TCoordinate2D relativeCoordinate : FissionOperator.relativeCoordinatesOfAttackZone) {

            // Generate possible attack coordinates based on base cell
            int possibleColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int possibleRowIndex = baseRowIndex + relativeCoordinate.getY();

            if(attemptedColumnIndex == possibleColumnIndex && attemptedRowIndex == possibleRowIndex) {
                return true;
            }
        }
        return false;

    }

    /**
     * Tells whether the destination cell is a neighbour cell of the base cell.
     *
     * @param baseColumnIndex      Column index of base cell.
     * @param baseRowIndex         Row index of base cell.
     * @param attemptedColumnIndex Column index of destination cell.
     * @param attemptedRowIndex    Row index of destination cell.
     *
     * @return True if the destination cell is a neighbour cell of the base cell, false otherwise.
     */
    private static boolean isTargetedCellIsAnAdjecentCell(int baseColumnIndex, int baseRowIndex, int attemptedColumnIndex, int attemptedRowIndex) {

        // Generate absolute adjecent coordinates based on base cell
        ArrayList<TCoordinate2D> adjecentCells = new ArrayList<>();
        for(TCoordinate2D relativeCoordinate : FissionOperator.relativeCoordinatesAdjacentCells) {

            int adjecentColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int adjecentRowIndex = baseRowIndex + relativeCoordinate.getY();

            // Store value
            adjecentCells.add(new TCoordinate2D(adjecentColumnIndex, adjecentRowIndex));
        }

        // Comparisation
        for(TCoordinate2D adjecentCoordinate : adjecentCells) {

            if(attemptedColumnIndex == adjecentCoordinate.getX() && attemptedRowIndex == adjecentCoordinate.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isApliciableOnState(IBoardState boardState) throws TError {

        // Input checkings
        if(boardState == null) {
            throw new TError("Property sholud not be null!");
        }

        if(!isValidBoardState(boardState)) {
            throw new TError("Invalid board was given!");
        }

        // If base cell does not exists
        if(!boardState.isCellExists(baseColumnIndex, baseRowIndex)) {
            logger.debug("Reject:001");
            return false;
        }

        // If target cell does not exists
        if(!boardState.isCellExists(targetColumnIndex, targetRowIndex)) {
            logger.debug("Reject:002");
            return false;
        }

        // If base and target cell are same
        if(baseColumnIndex == targetColumnIndex && baseRowIndex == targetRowIndex) {
            logger.debug("Reject:003");
            return false;
        }

        // The base cell is not occupied by the offered player
        if(boardState.getCellValue(baseColumnIndex, baseRowIndex) != boardState.getActualPlayerIndex()) {
            logger.debug("Reject:004->[" + baseColumnIndex + "," + baseRowIndex + "]!=" + boardState.getActualPlayerIndex());
            return false;
        }

        // If target cell is not empty because occupied or inactive
        if(boardState.getCellValue(targetColumnIndex, targetRowIndex) != IBoardState.symbolOfEmptyCell) {
            logger.debug("Reject:005");
            return false;
        }

        // Is the target cell in the attack zone
        if(!isAttemptedCellInPossibleAttackRange(baseColumnIndex, baseRowIndex, targetColumnIndex, targetRowIndex)) {
            logger.debug("Reject:006->[" + baseColumnIndex + "," + baseRowIndex + "]:[" + targetColumnIndex + "," + targetRowIndex + "]");
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IBoardState applyIt(IBoardState boardState) throws TError {

        // Input checkings
        if(boardState == null) {
            throw new TError("Property should not be null!");
        }

        if(!this.isApliciableOnState(boardState)) {
            throw new TError("Operator is not appliciable on given state!");
        }

        // Create new board and copy values from recent board
        byte[][] actualBoard = boardState.getBoard();
        byte[][] newBoard = null;
        for(int columnIndex = 0; columnIndex < actualBoard.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < actualBoard[columnIndex].length; rowIndex++) {
                // Instantiation of the new array
                if(newBoard == null) {
                    newBoard = new byte[actualBoard.length][actualBoard[columnIndex].length];
                }

                byte value = actualBoard[columnIndex][rowIndex];
                newBoard[columnIndex][rowIndex] = value;
            }
        }
        byte actualPlayerIndex = boardState.getActualPlayerIndex();
        byte nextPlayerIndex = boardState.getEnemyPlayerIndex();
        IBoardState newBoardState = new BoardState(newBoard, nextPlayerIndex);

        // Occupie the targeted cell
        if(newBoardState.getCellValue(this.targetColumnIndex, this.targetRowIndex) == IBoardState.symbolOfEmptyCell) {
            newBoardState.setCellValue(this.targetColumnIndex, this.targetRowIndex, actualPlayerIndex);
        }
        else {
            throw new TError("Invalid state was found!");
        }

        // Occupie the adjacent and occupied cells around the target cell
        for(TCoordinate2D relativeCoordinate : relativeCoordinatesAdjacentCells) {

            int adjacentCellColumnIndex = this.targetColumnIndex + relativeCoordinate.getX();
            int adjacentCellRowIndex = this.targetRowIndex + relativeCoordinate.getY();

            if(newBoardState.isCellExists(adjacentCellColumnIndex, adjacentCellRowIndex)) {

                if(newBoardState.isCellOccupied(adjacentCellColumnIndex, adjacentCellRowIndex)) {
                    newBoardState.setCellValue(adjacentCellColumnIndex, adjacentCellRowIndex, actualPlayerIndex);
                }
            }
        }

        return newBoardState;
    }

}
