package Hexxagon.AI;

import Support.TError;

/**
 * Contains method specifications of heuristic value determination.
 * Heuristic value tells how good or bad is a state represented by instance of a class implementing {@code IBoardState} interface.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IHeuristic {

    /**
     * Determines the goodness of the given board state.
     *
     * @return A value in the interval of minus and positive infinitive
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public double evaluateGoodness() throws TError;

}
