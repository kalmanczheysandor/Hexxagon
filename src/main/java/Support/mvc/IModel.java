package Support.mvc;

import Support.TError;
import java.beans.PropertyChangeListener;

/**
 * Contains generalised methods of model layer of MVC design pattern.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public interface IModel {

    /**
     * Attaches a property change listener.
     *
     * @param listener The property change listener object.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) throws TError;

    /**
     * Detaches the specified property change listener.
     *
     * @param listener The property change listener to remove.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) throws TError;
}
