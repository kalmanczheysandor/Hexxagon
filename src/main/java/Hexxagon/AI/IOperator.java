package Hexxagon.AI;

import Support.TError;

/**
 * Contains specifications of common methods of different operators applied in game.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IOperator {

    /**
     * Answers whether the operator is apply able on the specified board state.
     * @param boardState The object of state on which the operator is tested. Object must not be null otherwise {@code TError} is thrown.
     *
     * @return True if operator is apliciable on the given state.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public boolean isApliciableOnState(IBoardState boardState) throws TError;

    /**
     * Applies the operator on the given state, and by it generates a new state which is result of the operator.
     * @param boardState The object of state on which the operator is applied. Object must not be null otherwise {@code TError} is thrown.
     *
     * @return The result state.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IBoardState applyIt(IBoardState boardState) throws TError;

    /**
     * Retrieves the column index of cell from where the operator moves the piece.
     * @return An "0" or higher integer value.
     */

    public int getBaseColumnIndex();

    /**
     * Retrieves the row index of cell from where the operator moves the piece.
     * @return An "0" or higher integer value.
     */
    public int getBaseRowIndex();

    /**
     * Retrieves the column index of cell to where the operator moves the piece.
     * @return An "0" or higher integer value.
     */
    public int getTargetColumnIndex();

    /**
     * Retrieves the row index of cell to where the operator moves the piece.
     * @return An "0" or higher integer value.
     */
    public int getTargetRowIndex();

}
