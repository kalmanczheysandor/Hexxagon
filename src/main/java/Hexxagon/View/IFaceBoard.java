package Hexxagon.View;

import Support.TError;




/**
 * Contains method specifications of face view component of game.
 * Instances of classes implementing this interface shows a bulb face symbolising the AI player.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IFaceBoard {

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
     * Displays the "inactive" animation.
     */
    public void animateInactive();

    /**
     * Displays the "think" animation.
     */
    public void animateThink();

    /**
     * Displays the "crazy" animation.
     */
    public void animateCrazy();

    /**
     * Displays the "surprise" animation.
     */
    public void animateSurprise();

    /**
     * Displays the "wait" animation.
     */
    public void animateWait();
}
