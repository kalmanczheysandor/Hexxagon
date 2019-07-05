package Hexxagon.AI;

import Support.TError;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of the MinMax forsee algorithm.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public final class MinMax implements IMinMax {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(MinMax.class);

    /**
     * Stores the expected value of cell when it is invalid.
     */
    private static byte symbolOfInactiveCell = -1;
    /**
     * Stores the expected value of cell when it is empty.
     */
    private static byte symbolOfEmptyCell = 0;
    /**
     * Stores the expected value of cell when it is occupied by first player.
     */
    private static byte symbolOfFirstPlayer = 1;
    /**
     * Stores the expected value of cell when it is occupied by second player.
     */
    private static byte symbolOfSecondPlayer = 2;
    /**
     * Stores whether to use tree cut mechanism.
     */
    private static boolean isTreeCut = true;

    public static int count = 0;

    /**
     * Creates a {@code MinMax} instance.
     */
    public MinMax() {
        logger.trace("MinMax constructed!");
    }

    /**
     * Offers the possible best step on specified state.
     *
     * @param initialState       The initial game state on which the next step have to be offered.
     * @param heuristicFactory   The factory object to create heuristic instances.
     * @param deepness           The deepness of forsee levels.
     * @param offeredPlayerIndex The unique index of offered player.
     * @param enemyPlayerIndex   The unique index of the enemy player.
     *
     * @return An {@code IOerator} instance representing the offered step.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public static IOperator takeOffer(IBoardState initialState, IHeuristicFactory heuristicFactory, int deepness, byte offeredPlayerIndex, byte enemyPlayerIndex) throws TError {
        // Input checkings
        if(deepness < 1) {
            throw new TError("The deepness value should be at least 1!");
        }
        if(deepness > 5) {
            throw new TError("The deepness value should be maximum 5!");
        }
        if(initialState == null) {
            throw new TError("Property 'initialState' should not be null!");
        }
        if(heuristicFactory == null) {
            throw new TError("Property 'heuristics' should not be null!");
        }
        if(offeredPlayerIndex == enemyPlayerIndex) {
            throw new TError("Offered and enemy player indexes should not be equal!");
        }

        if(offeredPlayerIndex != symbolOfFirstPlayer && offeredPlayerIndex != symbolOfSecondPlayer) {
            throw new TError("Invalid offered  player index was given!");
        }

        if(enemyPlayerIndex != symbolOfFirstPlayer && enemyPlayerIndex != symbolOfSecondPlayer) {
            throw new TError("Invalid enemy  player index was given!");
        }

        BranchResult result = getGoodness(initialState, heuristicFactory, deepness, offeredPlayerIndex, enemyPlayerIndex, 0, "*", true);
        //double returnValue = result.getReturnValue();
        IOperator creatorOperator = result.getCauserOperator();

        return creatorOperator;
    }

    /**
     * Retrieves the best step at a node and the top node of the forsee tree. Recursively invocate.
     *
     * @param initialState       The previous game state on which the next step have to be offered.
     * @param heuristicFactory   The factory object to create heuristic instances.
     * @param deepness           The deepness of forsee levels.
     * @param offeredPlayerIndex The unique index of offered player.
     * @param enemyPlayerIndex   The unique index of the enemy player.
     * @param parentLevelValue   The value of the parent level to keep track of deepness.
     *
     * @return A {@code BranchResult} instance representing the best step possible at a node of the forsee tree, and some other details.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private static BranchResult getGoodness(IBoardState initialState, IHeuristicFactory heuristicFactory, int deepness, byte offeredPlayerIndex, byte enemyPlayerIndex, double parentLevelValue, String path, boolean isTopNode) throws TError {
        byte levelPlayerIndex = initialState.getActualPlayerIndex();       // The paler who can do a step on this level

        count++;

        if(levelPlayerIndex == enemyPlayerIndex) {
            return getGoodnessWhenEnemyPlayer(initialState, heuristicFactory, deepness, offeredPlayerIndex, enemyPlayerIndex, parentLevelValue, path, isTopNode);
        }
        else if(levelPlayerIndex == offeredPlayerIndex) {
            return getGoodnessWhenOfferedPlayer(initialState, heuristicFactory, deepness, offeredPlayerIndex, enemyPlayerIndex, parentLevelValue, path, isTopNode);
        }

        throw new TError("Unexpected execution!");
    }

    private static BranchResult getGoodnessWhenOfferedPlayer(IBoardState initialState, IHeuristicFactory heuristicFactory, int deepness, byte offeredPlayerIndex, byte enemyPlayerIndex, double parentLevelValue, String path, boolean isTopNode) throws TError {

        // Input checkings
        if(initialState.getActualPlayerIndex() != offeredPlayerIndex) {
            throw new TError("Not equal supported player index is found!");
        }

        // The initial value should be the lowest possible
        double valueMax = Double.NEGATIVE_INFINITY;

        IOperator bestOperator = null;

        if(deepness > 0) {

            int nodeIndex = 1;

            // Test operator offering
            for(IOperator operator : aggregatedOperatorOfferings(initialState, offeredPlayerIndex)) {

                if(operator.isApliciableOnState(initialState) && initialState.isOperatorInValidRange(operator)) {

                    // Generate the new state 
                    IBoardState newBoardState = operator.applyIt(initialState);

                    // Determine the goodness of the new state
                    BranchResult result = getGoodness(newBoardState, heuristicFactory, deepness - 1, offeredPlayerIndex, enemyPlayerIndex, valueMax, path + ">" + nodeIndex, false);

                    if(result != null) {
                        double childHeur = result.getReturnValue();

                        // If the new value is better than the recent one then override the previous one and store the applied operator
                        if(childHeur >= valueMax) {
                            valueMax = childHeur;
                            bestOperator = operator;
                        }

                        // Cut
                        if((!isTopNode) && (parentLevelValue < valueMax)) {
                            return null;
                        }
                    }

                    nodeIndex++;
                }
            }

            // Dermining wether it is an enstate or not
            if(bestOperator == null) {            // If it is an endstate
                IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);
                return new BranchResult(null, heur.evaluateGoodness());
            }
            else {                              // If it is not an endstate
                IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);
                return new BranchResult(bestOperator, valueMax);
            }
        }
        else if(deepness == 0) {
            IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);
            return new BranchResult(null, heur.evaluateGoodness());
        }

        throw new TError("Unexpecded execution!");
    }

    private static BranchResult getGoodnessWhenEnemyPlayer(IBoardState initialState, IHeuristicFactory heuristicFactory, int deepness, byte offeredPlayerIndex, byte enemyPlayerIndex, double parentLevelValue, String path, boolean isTopNode) throws TError {

        // Input checkings
        if(initialState.getActualPlayerIndex() != enemyPlayerIndex) {
            throw new TError("Not equal enemy player index is found!");
        }

        // The initial value should be the highest possible
        double valueMin = Double.POSITIVE_INFINITY;
        IOperator bestOperator = null;

        if(deepness > 0) {

            int nodeIndex = 1;
            for(IOperator operator : aggregatedOperatorOfferings(initialState, enemyPlayerIndex)) {

                if(operator.isApliciableOnState(initialState) && initialState.isOperatorInValidRange(operator)) {

                    // Generate the new state 
                    IBoardState newBoardState = operator.applyIt(initialState);

                    // Determine the goodness of the new state
                    BranchResult result = getGoodness(newBoardState, heuristicFactory, deepness - 1, offeredPlayerIndex, enemyPlayerIndex, valueMin, path + ">" + nodeIndex, false);

                    if(result != null) {
                        double childHeur = result.getReturnValue();

                        // If the new value is worse than the recent one
                        if(childHeur < valueMin) {
                            valueMin = childHeur;
                            bestOperator = operator;
                        }

                        // Cut
                        if((!isTopNode) && (parentLevelValue > valueMin)) {
                            return null;
                        }
                    }

                    nodeIndex++;
                }
            }

            // Dermining wether it is an enstate or not
            if(bestOperator == null) {            // If it is an endstate
                IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);

                return new BranchResult(null, heur.evaluateGoodness());
            }
            else {                              // If it is not an endstate
                IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);
                return new BranchResult(bestOperator, valueMin);
            }
        }
        else if(deepness == 0) {
            IHeuristic heur = heuristicFactory.fabricateInstance(initialState, offeredPlayerIndex, enemyPlayerIndex);
            return new BranchResult(null, heur.evaluateGoodness());
        }

        throw new TError("Unexpecded execution!");
    }

    /**
     * Generates all operators which are allowed to use on a state.
     * It is aggregated because includes all different type of operators.
     * Impossible operator is included to provide at least one loop.
     *
     * @param boardState         The state of board on which the offered operators will be applied.
     * @param offeredPlayerIndex The index of supported player.
     *
     * @return A list of operators as {@code IOerator} instances.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public static ArrayList<IOperator> aggregatedOperatorOfferings(IBoardState boardState, byte offeredPlayerIndex) throws TError {
        // Input checkings
        if(boardState == null) {
            throw new TError("Parameter should not be null!");
        }
        if(symbolOfFirstPlayer != offeredPlayerIndex && symbolOfSecondPlayer != offeredPlayerIndex) {
            throw new TError("Invalid player index was given");
        }

        // Generate return 
        ArrayList<IOperator> operatorOffering = new ArrayList<>();
        operatorOffering.addAll(FissionOperator.generateAvailableOperatorsOnState(boardState, offeredPlayerIndex));
        operatorOffering.addAll(JumpOperator.generateAvailableOperatorsOnState(boardState, offeredPlayerIndex));
        operatorOffering.addAll(ImpossibleOperator.generateAvailableOperatorsOnState(boardState, offeredPlayerIndex));

        return operatorOffering;

    }

    /**
     * It is a helper class to represent a node in the forsee tree.
     */
    private static class BranchResult {

        /**
         * The operator which created the state.
         */
        private IOperator causerOperator;
        /**
         * The heuristic value of generated state.
         */
        private double returnValue;

        /**
         * Creates a {@code BranchResult} instance.
         *
         * @param causerOperator The operator which created the state.
         * @param returnValue    The heuristic value of generated state.
         */
        public BranchResult(IOperator causerOperator, double returnValue) {
            this.causerOperator = causerOperator;
            this.returnValue = returnValue;
        }

        /**
         * Retrieves the operator which created the state.
         *
         * @return An {@code IOerator} instance.
         */
        public IOperator getCauserOperator() {
            return causerOperator;
        }

        /**
         * Retrieves the heuristic value of generated state.
         *
         * @return An {@code double} value in range of minus and plus infinitive
         */
        public double getReturnValue() {
            return returnValue;
        }
    }
}
