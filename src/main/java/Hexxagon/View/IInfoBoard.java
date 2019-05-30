package Hexxagon.View;

import Support.TError;

/**
 * Contains method specifications of info view component of game.
 * Instances of classes implementing this interface shows information about the actual game play.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public interface IInfoBoard {

    /**
     * Displays the component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void show() throws TError;

    /**
     * Stop component display.
     */
    public void hide();

    /**
     * Refreshes the component.
     * When component is refreshed than it rereads all data from data layer.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void refresh() throws TError;

    /**
     * Alias of refresh method.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void checkChanges() throws TError;
}
