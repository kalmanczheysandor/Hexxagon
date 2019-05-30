package Hexxagon.Controller;

import Hexxagon.AI.IOperator;
import Support.TError;

/**
 * Contains method specifications of artificial player.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IAIPlayer extends IPlayer {

//    /**
//     * Az automata játékos által betartandó minimális lépésmegtételi késleltetés mikrosecundumban.
//     */
//    //public static final long minimalStepRealisationDelayr = 1000;
//    /**
//     * Az automata játékos által betartandó minimális általános szál altatási idő.
//     */
//    //public static final long loopDelay = 100;
//    /**
//     * Az automata játékos által betartandó minimális lépésmegtételi animációs idő.
//     */
//    //public static final long cellSelectedDelay = 1000;
//    
//    
    
    
    
    
    /**
     * Retrieves the step taken by the artificial player.
     * Here have to be invocate the minmax algorithm.
     *
     * @param theBoard The board on which the next step have to be taken.
     *
     * @return An {@code IOperator} instance representing the next step.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IOperator takeYourStep(byte[][] theBoard) throws TError;

    /**
     * Sets the deepness of forsee mechanism.
     *
     * @param deepness A value between 1 and 4
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setDeepness(int deepness) throws TError;
}
