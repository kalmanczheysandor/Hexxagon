package Hexxagon.AI;

import Support.TError;
import java.util.ArrayList;

/**
 * Contains method of an operator which is never apliciable of any board state.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
final class ImpossibleOperator extends Operator {

    /**
     * Creates a {@code ImpossibleOperator} instance.
     *
     * @param baseColumnIndex   Column index of base cell.
     * @param baseRowIndex      Row index of base cell.
     * @param targetColumnIndex Column index of destination cell.
     * @param targetRowIndex    Row index of destination cell.
     */
    public ImpossibleOperator(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {
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

        // Adding a not appliciable
        ArrayList<IOperator> output = new ArrayList<>();
        IOperator opr = new ImpossibleOperator(0, 0, 0, 0);
        output.add(opr);

        return output;
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

        return false;
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

        return null;
    }

}
