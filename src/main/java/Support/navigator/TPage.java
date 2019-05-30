package Support.navigator;

import Support.TError;
import Support.mvc.TView;

/**
 * Contains generalised method implementations of page components specified by {@code IPage} interface.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public abstract class TPage extends TView implements IPage {

    /**
     * Unique index of the page given by the navigator component to which this page is attached.
     */
    private int pageIndex = -1;

    /**
     * Object of inside graphical root node to which all other components should be attached.
     */
    private Object insideRootNode;

    /**
     * Reference to the navigator component.
     */
    private INavigator navigator;

    /**
     * Tells whether the {@code onInit()} event has been triggered or not.
     */
    private boolean isInitialised = false;

    /**
     * Method to draw graphical components and invoked by the draw event.
     *
     * @return Reference to the top node of component tree.
     */
    protected abstract Object drawContent();

    /**
     * Method to initialise components and invoked by the init event.
     */
    protected abstract void initComponent();

    /**
     * Creates a {@code TPage} instance.
     *
     * @param parentNode Reference to the outside graphical node to which the inside components are attached when the page is displayed.
     */
    public TPage(Object parentNode) {
        super(parentNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onInit() {
        if(!this.isInitialised) {
            this.initComponent();
        }
        this.isInitialised = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDraw() {
        this.insideRootNode = this.drawContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPageIndex(int pageIndex) throws TError {
        // Input checkings
        if(pageIndex < 0) {
            throw new TError("Page index should not be lower than 0!");
        }

        // Store value
        this.pageIndex = pageIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setNavigator(INavigator navigator) throws TError {
        // Input checkings
        if(navigator == null) {
            throw new TError("The parameters should not be null!");
        }

        // Store value
        this.navigator = navigator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public INavigator getNavigator() {
        return this.navigator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPageIndex() {
        return this.pageIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getPageContentNode() {
        //this.onDraw();
        return this.insideRootNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParentNode() {
        return this.getGraphicalRoot();
    }

}
