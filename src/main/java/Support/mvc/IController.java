package Support.mvc;

import Support.TError;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * Contains generalised methods of controller layer of MVC design pattern.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public interface IController {

    /**
     * Attaches the model component to the class.
     * Only one component allowed.
     *
     * @param model Object of an {@code IModel} instance. Parameter must not be null.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setModel(IModel model) throws TError;

    /**
     * Retrieves the attached model instance reference.
     *
     * @return Model object reference.
     */
    public IModel getModel();

    /**
     * Detaches the previously added model component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void removeModel() throws TError;

    /**
     * Attaches a view component to the class.
     *
     * @param view The view component to add.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addView(IView view) throws TError;

    /**
     * Returns a list of added view components.
     *
     * @return the list.
     */
    public ArrayList<IView> getViewList();

    /**
     * It removes and detaches a view component from the controller.
     *
     * @param view The view component to remove.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void removeView(IView view) throws TError;

    /**
     * Delegates property change event to the view components from the model component.
     *
     * @param evt Describes the change.
     */
    public void propertyChange(PropertyChangeEvent evt);

}
