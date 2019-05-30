package Support.mvc;

import Support.TError;
import java.beans.PropertyChangeEvent;

/**
 * Contains generalised methods of view layer of MVC design pattern.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IView {

    /**
     * Tells whether a controller component is attached.
     *
     * @return True if controller component attached to the view component and false otherwise.
     */
    public boolean isControllerAttached();

    /**
     * Receives the property change event occurred in the model component. The event is delegated by the controller component.
     *
     * @param evt Describes the details of property change occurred in the model component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void modelPropertyChange(final PropertyChangeEvent evt) throws TError;

    /**
     * Attaches a controller object to the view component. Only one is available at same time.
     *
     * @param controller The controller object to attach.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addController(IController controller) throws TError;

    /**
     * Detaches the previously added controller component.
     */
    public void removeController();

    /**
     * Retrieves the reference of the previously added controller component.
     *
     * @return Reference to the added controller component. Null if no component is added yet.
     */
    public IController getController();
}
