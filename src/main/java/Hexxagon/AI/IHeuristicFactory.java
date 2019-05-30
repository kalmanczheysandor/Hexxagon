package Hexxagon.AI;

import Support.TError;

/**
 * Contains method specifications factory class of heuristic object.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IHeuristicFactory {

    /**
     * Creates a heuristic object.
     *
     * @param boardState         The object of state about the heuristic tells the goodness. Object must not be null otherwise {@code TError} is thrown.
     * @param offeredPlayerIndex The index of player for who the best step is attempted to given. It must be 1 or 2 otherwise {@code TError is thrown}.
     * @param enemyPlayerIndex   The opposite player of the offered player. It must be 1 or 2 otherwise {@code TError is thrown}.
     *
     * @return An instance of class implementing the {@code IHeuristic} interface and containing the input parameters.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IHeuristic fabricateInstance(IBoardState boardState, byte offeredPlayerIndex, byte enemyPlayerIndex) throws TError;
}
