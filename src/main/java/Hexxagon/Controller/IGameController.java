package Hexxagon.Controller;

import Hexxagon.Model.IGameData;
import Hexxagon.View.IFaceBoard;
import Hexxagon.View.IInfoBoard;
import Hexxagon.View.IPlayBoard;
import Support.TError;

/**
 * Contains generalised methods of controller layer of MVC design pattern.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public interface IGameController {

    /**
     * Starts the game play.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void startGame() throws TError;

    /**
     * Interrupts the game.
     * If invoked in inappropriate game state than {@code TError } is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void pauseGame() throws TError;

    /**
     * Allows to continue the game.
     * If invoked in inappropriate game state than {@code TError } is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void continueGame() throws TError;

    /**
     * Stops the game.
     * If invoked in inappropriate game state than {@code TError } is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void stopGame() throws TError;

    /**
     * Tells whether the game is active.
     * If a game is not active than no human or artificial player can invoke events.
     * @return True if the game is running otherwise false.
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    
    public boolean isGameRunning() throws TError;
    
    
    
    /**
     * Informs the controller module about a step from a position to another one.
     * This method is used only by artificial player.
     *
     * @param fromColumnIndex   Column index of the cell from where the step is taken. Index should be a valid one otherwise {@code TError} is thrown.
     * @param fromRowIndex      Row index of the cell from where the step is taken. Index should be a valid one otherwise {@code TError} is thrown.
     * @param targetColumnIndex Column index of the cell to where the step is taken. Index should be a valid one otherwise {@code TError} is thrown.
     * @param targetRowIndex    Row index of the cell to where the step is taken. Index should be a valid one otherwise {@code TError} is thrown.
     * @param playerIndex       The unique index of player. Index should be a valid one otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void autoPlayerCellStep(int fromColumnIndex, int fromRowIndex, int targetColumnIndex, int targetRowIndex, byte playerIndex) throws TError;

    /**
     * Informs the controller module about that a cell was clicked by a human player.
     * This method is used only by the human player.
     *
     * @param columnIndex Column index of the cell clicked. Index should be a valid one otherwise {@code TError} is thrown.
     * @param rowIndex    Row index of the cell clicked. Index should be a valid one otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void cellClicked(int columnIndex, int rowIndex) throws TError;

    /**
     * Sets the data component of the game.
     * Only one data component is allowed to being attached to controller.
     *
     * @param gameData An object instance of class implementing {@code IGameData} interface. Parameter should not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setGameData(IGameData gameData) throws TError;

    /**
     * Adds a play board visual component to the controller.
     * Play board is the visual interface on which the balls are placed.
     * More than one play board component is allowed to being attached to controller.
     *
     * @param playBoard An object instance of class implementing {@code IPlayBoard} interface. Parameter should not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addPlayBoard(IPlayBoard playBoard) throws TError;

    /**
     * Adds a info board visual component to the controller.
     * Info board is the visual interface on which the actual points of players are presented.
     * More than one info board component is allowed to being attached to controller.
     *
     * @param infoBoard An object instance of class implementing {@code IInfoBoard} interface. Parameter should not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addInfoBoard(IInfoBoard infoBoard) throws TError;
    
    
    /**
     * Adds a face board visual component to the controller.
     * Face board is the visual interface on which the artificial player is presented.
     * More than one info board component is allowed to being attached to controller.
     *
     * @param faceBoard An object instance of class implementing {@code IFaceBoard} interface. Parameter should not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addFaceBoard(IFaceBoard faceBoard) throws TError;
}
