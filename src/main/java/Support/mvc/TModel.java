package Support.mvc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Generalised implementation of model component specified by {@code IModel} interface.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public abstract class TModel implements IModel {

    /**
     * Object to contain the attached property change listeners.
     */
    protected PropertyChangeSupport propertyChangeSupport;

    /**
     * Creates a {@code TModel} instance.
     */
    public TModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Triggers a property change event.
     *
     * @param propertyName Describes the name of the changed property.
     * @param oldValue     The value before the change was occurred.
     * @param newValue     The new value after the change was occurred.
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Shortened version.
     *
     * @param propertyName Describes the name of the changed property.
     */
    protected void firePropertyChange(String propertyName) {
        propertyChangeSupport.firePropertyChange(propertyName, null, null);
    }

}
