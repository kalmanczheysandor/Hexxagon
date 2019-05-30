package Support.mvc;

import Support.TError;

/**
 * Generalised implementation of view component specified by {@code IView} interface.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public abstract class TView implements IView {

    /**
     * Contains the reference of the graphical node component of the displayed window in order to provide attachment to it.
     */
    Object graphicalRoot;

    /**
     * Contains reference of the attached controller object.
     */
    IController controller = null;

    /**
     * Creates a {@code TView} instance.
     *
     * @param graphicalRoot Object of the graphical node.
     */
    protected TView(Object graphicalRoot) {
        this.graphicalRoot = graphicalRoot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addController(IController controller) throws TError {
        // Input checkings
        if(controller == null) {
            throw new TError("Property sholud not be null!");
        }
        // Store value
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeController() {
        this.controller = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IController getController() {
        return controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isControllerAttached() {
        if(this.controller == null) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the reference of the outside graphical node.
     *
     * @return Reference of the outside graphical node if it was give otherwise null.
     */
    protected Object getGraphicalRoot() {
        return graphicalRoot;
    }

    /**
     * Sets the outside graphical node.
     *
     * @param graphicalRoot The object instance of the outside graphical node.
     */
    protected void setGraphicalRoot(Object graphicalRoot) {
        this.graphicalRoot = graphicalRoot;
    }

}
