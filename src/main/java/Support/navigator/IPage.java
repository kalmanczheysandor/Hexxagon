package Support.navigator;

import Support.TError;

/**
 * Contains generalised methods of page component.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IPage {

    /**
     * Initialisation event.
     * Typically used to initialise the components placed on the page.
     * The event is triggered by that navigator component to which the page is added.
     * Initialisation event is not occurred when the class is instantiated but after it is attached to a navigator component.
     */
    public void onInit();

    /**
     * Component draw event.
     * Typically used to draw the components placed on the page.
     * The event is triggered by that navigator component to which the page is added.
     */
    public void onDraw();

    /**
     * Component after draw event.
     *
     * The event is triggered by that navigator component to which the page is added.
     */
    public void onAfterDraw();

    /**
     * Attaches the navigator object to the page component.
     *
     * @param navigator The object instance of navigator component responsible.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setNavigator(INavigator navigator) throws TError;

    /**
     * Retrieves the object reference of navigator component.
     *
     * @return Object reference to the navigator component responsible.
     */
    public INavigator getNavigator();

    /**
     * Sets the unique page index of the page.
     * Determination of the index happens by the navigator component to which and after which the page is added.
     *
     * @param pageIndex The index of the page.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setPageIndex(int pageIndex) throws TError;

    /**
     * Retrieves the unique page index given by the navigator component responsible.
     *
     * @return Page index.
     */
    public int getPageIndex();

    /**
     * Retrieves the object reference to the inside graphical root node to which other graphical components are attached.
     *
     * @return Reference to the inside graphical root component.
     */
    public Object getPageContentNode();

    /**
     * Retrieves the object reference to the outside graphical root node to which the inside root node is attached when the page is displayed.
     *
     * @return Reference to the outside graphical root component.
     */
    public Object getParentNode();

    /**
     * Displays the page.
     * The page becomes visible.
     */
    public void display();

}
