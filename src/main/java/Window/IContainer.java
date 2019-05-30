package Window;

import Support.TError;

/**
 * Contains method specifications of controller component of application.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IContainer {

    /**
     * Sets the object of the data component.
     *
     * @param storage Parameter must not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setStorage(IStorage storage) throws TError;

    /**
     * Retrieves the data component of application.
     *
     * @return Reference to the data component implementing the {@code IStorage} interface.
     */
    public IStorage getStorage();

    /**
     * Attaches the intro page view component to the controller.
     *
     * @param page An instance of class implementing the {@code IIntroPage} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setIntroPage(IIntroPage page) throws TError;

    /**
     * Attaches the settings page view component to the controller.
     *
     * @param page An instance of class implementing the {@code ISettingsPage} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setSettingsPage(ISettingsPage page) throws TError;

    /**
     * Attaches the intro page view component to the controller.
     *
     * @param page An instance of class implementing the {@code IPlayPage} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setPlayPage(IPlayPage page) throws TError;

    /**
     * Retrieves the attached object of intro page component.
     *
     * @return Reference to the page component implementing the {@code IIntroPage} interface.
     */
    public IIntroPage getIntroPage();

    /**
     * Retrieves the attached object of settings page component.
     *
     * @return Reference to the page component implementing the {@code ISettingsPage} interface.
     */
    public ISettingsPage getSettingsPage();

    /**
     * Retrieves the attached object of paly page component.
     *
     * @return Reference to the page component implementing the {@code IPlayPage} interface.
     */
    public IPlayPage getPlayPage();

    /**
     * Appoints the intro page to be the active page and displays it.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showIntroPage() throws TError;

    /**
     * Appoints the settings page to be the active page and displays it.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showSettingsPage() throws TError;

    /**
     * Appoints the play page to be the active page and displays it.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showPlayPage() throws TError;

    /**
     * Delegates to the data component the difficulty index chosen at view component.
     *
     * @param difficultyIndex The unique difficulty index to delegate. Value must not be less than 0 otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void selectDifficulty(int difficultyIndex) throws TError;

    /**
     * Delegates to the data component the board mode option index chosen at view component.
     *
     * @param boardModeIndex The unique board mode option index to delegate. Value must not be less than 0 otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void selectBoardMode(int boardModeIndex) throws TError;

    /**
     * Starts the Hexxagon module.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void startGame() throws TError;

    /**
     * Stops the Hexxagon module.
     */
    public void stopGame();

}
