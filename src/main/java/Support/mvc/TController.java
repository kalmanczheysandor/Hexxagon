package Support.mvc;

import Support.TError;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Generalised implementation of controller component specified by {@code IController} interface.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public abstract class TController implements IController, PropertyChangeListener {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(TController.class);
 
    /**
     * Contains the added and attached view components.
     */
    private ArrayList<IView> registeredViews;
    /**
     * Contains the attached model available.
     */
    private IModel model;

    /**
     * Creates a {@code TController} instance.
     */
    public TController() {
        this.registeredViews = new ArrayList<IView>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) throws TError {
        if(model == null) {
            throw new TError("Value sould not be null!");
        }

        // Deattach the previous model
        if(this.model != null) {
            model.removePropertyChangeListener(this);
        }

        // Attach the new model
        this.model = model;
        model.addPropertyChangeListener(this);  // Register the controller object
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel getModel() {
        return this.model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeModel() throws TError {
        if(this.model != null) {
            model.removePropertyChangeListener(this);
            this.model = null;
        }
        else {
            throw new TError("No model object was attached!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addView(IView view) throws TError {
        if(view == null) {
            throw new TError("Value sholud not be null!");
        }
        registeredViews.add(view);
        view.addController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeView(IView view) throws TError {
        if(view == null) {
            throw new TError("Value sholud not be null!");
        }

        if(registeredViews.indexOf(view) == -1) {
            throw new TError("The given view object was not attached!");
        }

        registeredViews.remove(view);
        view.removeController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<IView> getViewList() {
        return registeredViews;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        for(IView view : registeredViews) {
            try {
                view.modelPropertyChange(evt);
            }
            catch(TError err) {
                logger.error(err.toString());
            }
        }
    }

    /**
     * This is a convenience method that subclasses can call upon to fire
     * property changes back to the models. This method uses reflection to
     * inspect each of the model classes to determine whether it is the owner of
     * the property in question. If it isn't, a NoSuchMethodException is thrown,
     * which the method ignores.
     *
     * @param propertyName The name of the property.
     * @param newValue     An object that represents the new value of the property.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    protected void setModelProperty(String propertyName, Object newValue) throws TError {
        try {
            Method method = model.getClass().getMethod("set" + propertyName, new Class[]{
                newValue.getClass()
            });
            method.invoke(model, newValue);

        }
        catch(Exception ex) {
            throw new TError("The specified '" + propertyName + "' property does not exist on model object!");
        }
    }

}
