package Support.navigator;

import Support.TError;

/**
 * Contains generalised methods of page navigator component.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface INavigator {

    /**
     * Adds a page to the navigator component.
     *
     * @param page The object instance of a page class implementing {@code IPage}.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addPage(IPage page) throws TError;

    /**
     * Displays the actual page.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showActualPage() throws TError;

    /**
     * Displays the page associated by {@code pageInndex}.
     *
     * @param pageIndex The unique index of the page.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showAPage(int pageIndex) throws TError;

    /**
     * Retrieves the page object associated by {@code pageIndex}.
     *
     * @param pageIndex The unique index of the page.
     *
     * @return The page object if found by {@code pageIndex} otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IPage getPage(int pageIndex) throws TError;

    /**
     * Tells whether the page associated by {@code pageIndex} exists or not.
     *
     * @param pageIndex The unique index of the page.
     *
     * @return True if page exists otherwise false.
     */
    public boolean isPageExists(int pageIndex);

}
