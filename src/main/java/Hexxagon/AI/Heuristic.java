package Hexxagon.AI;

import Support.TCoordinate2D;
import Support.TError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The heuristic class to tell the goodness of a state node in a forsee tree.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public final class Heuristic implements IHeuristic {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Heuristic.class);

    /**
     * Stores the relative coordinates of attack zone.
     */
    private static final TCoordinate2D[] relativeCoordinatesOfAttackZone = {
        /*C-2*/new TCoordinate2D(-2, 0), new TCoordinate2D(-2, 1), new TCoordinate2D(-2, 2),
        /*C-1*/ new TCoordinate2D(-1, -1), new TCoordinate2D(-1, 0), new TCoordinate2D(-1, +1), new TCoordinate2D(-1, +2),
        /*C0*/ new TCoordinate2D(0, -2), new TCoordinate2D(0, -1), new TCoordinate2D(0, 1), new TCoordinate2D(0, 2),
        /*C+1*/ new TCoordinate2D(1, -2), new TCoordinate2D(1, -1), new TCoordinate2D(1, 0), new TCoordinate2D(1, 1),
        /*C+2*/ new TCoordinate2D(2, -2), new TCoordinate2D(2, -1), new TCoordinate2D(2, 0)
    };

    /**
     * Stores the board state on which the heuristic value is generated.
     */
    private IBoardState boardState;

    /**
     * Stores the unique index of supported player.
     */
    private byte offeredPlayerIndex;
    /**
     * Stores the unique index of enemy player.
     */
    private byte enemyPlayerIndex;

    /**
     * Creates a {@code Heuristic} instance.
     *
     * @param boardState         The object of the board state on which the heuristic value is generated.
     * @param offeredPlayerIndex The unique index of supported player.
     * @param enemyPlayerIndex   The unique index of enemy player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public Heuristic(IBoardState boardState, byte offeredPlayerIndex, byte enemyPlayerIndex) throws TError {
        // Input checkings
        if(boardState == null) {
            throw new TError("Parameter should not be null!");
        }
        if(offeredPlayerIndex == enemyPlayerIndex) {
            throw new TError("Illegal player index value!");
        }

        // Store values
        this.boardState = boardState;
        this.offeredPlayerIndex = offeredPlayerIndex;
        this.enemyPlayerIndex = enemyPlayerIndex;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    /*public double evaluateGoodness() throws TError {
        
        // Property checkings
        if(this.boardState == null) {
            throw new TError("Property value is not set");
        }

        int emptyCellCount = boardState.getEmptyCellCount();

        // Is end state or not?
        boolean isEndState = false;
        if(boardState.getEmptyCellCount() == 0) { // when no free cell is on board
            isEndState = true;
            logger.debug("EndState001");
        }
        else if(boardState.getPlayerBallCount(offeredPlayerIndex) == 0) {// When no ball remained of offered player
            isEndState = true;
            logger.debug("EndState002");
        }
        else if(this.getPlayerAttackableBallCount(offeredPlayerIndex) == 0) { // When all balls of offered player are blocked
            isEndState = true;
            logger.debug("EndState003");
        }
        else {
            logger.debug("NOTEndState");
        }

        int offeredPlayer_BallCount = boardState.getPlayerBallCount(offeredPlayerIndex);
        int offeredPlayer_attackableBallCount = this.getPlayerAttackableBallCount(offeredPlayerIndex);  // ennyivel tud lépni
        int offeredPlayer_blockedBallCount = offeredPlayer_BallCount - offeredPlayer_attackableBallCount;

        int enemyPlayer_BallCount = boardState.getPlayerBallCount(enemyPlayerIndex);
        int enemyPlayer_attackableBallCount = this.getPlayerAttackableBallCount(enemyPlayerIndex);
        int enemyPlayer_blockedBallCount = enemyPlayer_BallCount - enemyPlayer_attackableBallCount;

        if(isEndState) {
            if(offeredPlayer_BallCount < (enemyPlayer_BallCount + emptyCellCount)) { // When endstate and offered player will louse ir
                logger.debug(">>>>>>>>>>>>>>G01");
                return Double.NEGATIVE_INFINITY;
            }
            else if(offeredPlayer_BallCount == (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will louse and will not win because iti is a draw play
                logger.debug(">>>>>>>>>>>>>>G02");
                return Double.NEGATIVE_INFINITY + 1;
            }
            else if(offeredPlayer_BallCount > (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will win
                logger.debug(">>>>>>>>>>>>>>G03");
                return Double.POSITIVE_INFINITY;
            }
            else {
                logger.debug("!!!!!!!!!!!!!!!!!!UNEXPECTED!!!!!!!!!!!!!!!444");
            }
        }

        double diff = (offeredPlayer_BallCount - enemyPlayer_BallCount);
        double output = ((diff / emptyCellCount) * 1000);

        logger.debug("offeredPlayer_BallCount:" + offeredPlayer_BallCount);
        logger.debug("enemyPlayer_BallCount:" + enemyPlayer_BallCount);
        logger.debug("diff:" + diff);
        logger.debug("emptyCellCount:" + emptyCellCount);
        logger.debug("HEUR:" + output);
        return output;

    }
     */

    
     public double evaluateGoodness() throws TError {

        // Property checkings
        if(this.boardState == null) {
            throw new TError("Property value is not set");
        }

        // 
       

        int offeredPlayer_BallCount = boardState.getPlayerBallCount(offeredPlayerIndex);
        int offeredPlayer_attackableBallCount = this.getPlayerAttackableBallCount(offeredPlayerIndex);  // ennyivel tud lépni
        int offeredPlayer_blockedBallCount = offeredPlayer_BallCount - offeredPlayer_attackableBallCount;
        int offeredPlayer_protectedBallCount = getProtectedEmptyCellsCount(offeredPlayerIndex);
        
        
        int enemyPlayer_BallCount = boardState.getPlayerBallCount(enemyPlayerIndex);
        int enemyPlayer_attackableBallCount = this.getPlayerAttackableBallCount(enemyPlayerIndex);
        int enemyPlayer_blockedBallCount = enemyPlayer_BallCount - enemyPlayer_attackableBallCount;
        int enemyPlayer_protectedBallCount = getProtectedEmptyCellsCount(enemyPlayerIndex);
        
         int emptyCellCount = boardState.getEmptyCellCount();
         int allCellCount=emptyCellCount+offeredPlayer_BallCount+enemyPlayer_BallCount;
        
        
        
        
        if(isEndState()) {
            if(offeredPlayer_BallCount < (enemyPlayer_BallCount + emptyCellCount)) { // When endstate and offered player will louse it
                return Double.NEGATIVE_INFINITY;
            }
            else if(offeredPlayer_BallCount == (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will louse and will not win because iti is a draw play
                
                return Double.NEGATIVE_INFINITY + 1;
            }
            else if(offeredPlayer_BallCount > (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will win
                return Double.POSITIVE_INFINITY;
            }
            else {
                throw new TError("Unexpected execution!");
            }
        }
        else {
            
            double output = (offeredPlayer_BallCount+offeredPlayer_protectedBallCount) - (enemyPlayer_BallCount+enemyPlayer_protectedBallCount);
            //double output = (offeredPlayer_blockedBallCount - enemyPlayer_blockedBallCount);
            logger.debug("offeredPlayer_BallCount:" + offeredPlayer_BallCount);
            logger.debug("enemyPlayer_BallCount:" + enemyPlayer_BallCount);
            logger.debug("HEUR:" + output);
            return output;
        }

        

    }
    
    
//    public double evaluateGoodness2() throws TError {
//
//        // Property checkings
//        if(this.boardState == null) {
//            throw new TError("Property value is not set");
//        }
//
//        // 
//       
//
//        int offeredPlayer_BallCount = boardState.getPlayerBallCount(offeredPlayerIndex);
//        int offeredPlayer_attackableBallCount = this.getPlayerAttackableBallCount(offeredPlayerIndex);  // ennyivel tud lépni
//        int offeredPlayer_blockedBallCount = offeredPlayer_BallCount - offeredPlayer_attackableBallCount;
//
//        int enemyPlayer_BallCount = boardState.getPlayerBallCount(enemyPlayerIndex);
//        int enemyPlayer_attackableBallCount = this.getPlayerAttackableBallCount(enemyPlayerIndex);
//        int enemyPlayer_blockedBallCount = enemyPlayer_BallCount - enemyPlayer_attackableBallCount;
//
//         int emptyCellCount = boardState.getEmptyCellCount();
//         int allCellCount=emptyCellCount+offeredPlayer_BallCount+enemyPlayer_BallCount;
//        
//        
//        
//        
//        if(isEndState()) {
//            if(offeredPlayer_BallCount < (enemyPlayer_BallCount + emptyCellCount)) { // When endstate and offered player will louse ir
//                return Double.NEGATIVE_INFINITY;
//            }
//            else if(offeredPlayer_BallCount == (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will louse and will not win because iti is a draw play
//                
//                return Double.NEGATIVE_INFINITY + 1;
//            }
//            else if(offeredPlayer_BallCount > (enemyPlayer_BallCount + emptyCellCount)) {  // When endstate and offered player will win
//                return Double.POSITIVE_INFINITY;
//            }
//            else {
//                throw new TError("Unexpected execution!");
//            }
//        }
//        else {
//            
//            double diff = (offeredPlayer_BallCount - enemyPlayer_BallCount);
//            double output   = (diff/emptyCellCount)*1000;
//            logger.debug("offeredPlayer_BallCount:" + offeredPlayer_BallCount);
//            logger.debug("enemyPlayer_BallCount:" + enemyPlayer_BallCount);
//            logger.debug("HEUR:" + output);
//            return output;
//        }
//
//        
//
//    }

    private boolean isEndState() throws TError {

        if(boardState.getEmptyCellCount() == 0) { // when no free cell is on board
            return true;
        }
        else if(boardState.getPlayerBallCount(offeredPlayerIndex) == 0) {// When no ball remained of offered player
            return true;
        }
        else if(this.getPlayerAttackableBallCount(offeredPlayerIndex) == 0) { // When all balls of offered player are blocked
            return true;
        }

        return false;
    }

    /**
     * Tells the count of cell from where the specified player is able to attack.
     *
     * @param playerIndex The unique index of the specified player.
     *
     * @return An {@code int} value not lower than 0.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private int getPlayerAttackableBallCount(byte playerIndex) throws TError {
        // Input checkings
        if(playerIndex != this.offeredPlayerIndex && playerIndex != this.enemyPlayerIndex) {
            throw new TError("Ivalid player index was given! It was:" + playerIndex);
        }

        // Determine result
        byte[][] board = this.boardState.getBoard();
        int count = 0;
        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {

                if(board[columnIndex][rowIndex] == playerIndex) {
                    if(this.isEmptyCellFoundInAttackRange(columnIndex, rowIndex)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Tells is any empty cell in the attack range.
     *
     * @param centerColumnIndex The column index of the centre cell of the attack zone.
     * @param centerRowIndex    The row index of the centre cell of the attack zone.
     *
     * @return True if at least one empty cell is found in the attack zone, false otherwise.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isEmptyCellFoundInAttackRange(int centerColumnIndex, int centerRowIndex) throws TError {
        // Input checking 
        if(!this.boardState.isCellExists(centerColumnIndex, centerRowIndex)) {
            throw new TError("The coordinates [" + centerColumnIndex + "," + centerRowIndex + "] are out of range!");
        }

        // Check all possible attack point from source position
        for(TCoordinate2D relativeCoordinate : relativeCoordinatesOfAttackZone) {
            int attackColumnIndex = centerColumnIndex + relativeCoordinate.getX();
            int attackRowIndex = centerRowIndex + relativeCoordinate.getY();

            if(this.boardState.isCellExists(attackColumnIndex, attackRowIndex)) {
                if(this.boardState.isCellEmptyAndActive(attackColumnIndex, attackRowIndex)) {
                    return true;
                }
            }
        }

        return false;
    }

    
    private boolean isEnemyCellFoundInAttackRange(int centerColumnIndex, int centerRowIndex,byte enemyIndex) throws TError {
        // Input checking 
        if(!this.boardState.isCellExists(centerColumnIndex, centerRowIndex)) {
            throw new TError("The coordinates [" + centerColumnIndex + "," + centerRowIndex + "] are out of range!");
        }

        // Check all possible attack point from source position
        for(TCoordinate2D relativeCoordinate : relativeCoordinatesOfAttackZone) {
            int attackColumnIndex = centerColumnIndex + relativeCoordinate.getX();
            int attackRowIndex = centerRowIndex + relativeCoordinate.getY();

            if(this.boardState.isCellExists(attackColumnIndex, attackRowIndex)) {
                if(this.boardState.getCellValue(attackColumnIndex, attackRowIndex)==enemyIndex){
                    return true;
                }
            }
        }

        return false;
    }
    
    
    private int getProtectedEmptyCellsCount(byte playerIndex) throws TError {
        // Input checkings
        if(playerIndex != this.offeredPlayerIndex && playerIndex != this.enemyPlayerIndex) {
            throw new TError("Ivalid player index was given! It was:" + playerIndex);
        }

        // Determine result
        byte[][] board = this.boardState.getBoard();
        int count = 0;
        for(int columnIndex = 0; columnIndex < board.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < board[columnIndex].length; rowIndex++) {

                if(board[columnIndex][rowIndex] == 0) {
                    if(!this.isEmptyCellFoundInAttackRange(columnIndex, rowIndex)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    
    
    
    /**
     * The factory class of heuristic class.
     *
     * @author Sandor Kalmanchey
     * @version 1.0
     * @since 1.0
     */
    public static class HeuristicFactory implements IHeuristicFactory {

        /**
         * Creates a {@code HeuristicFactory} instance.
         */
        public HeuristicFactory() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IHeuristic fabricateInstance(IBoardState boardState, byte offeredPlayerIndex, byte enemyPlayerIndex) throws TError {
            return new Heuristic(boardState, offeredPlayerIndex, enemyPlayerIndex);
        }
    }

}
