package Support.navigator;

import Support.TError;
import Support.mvc.IView;
import Support.mvc.TController;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Contains generalised method implementations of navigator components specified by {@code INavigator} interface.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public abstract class TNavigator extends TController implements INavigator {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(TNavigator.class);
 
    /**
     * Contains the attached page components.
     */
    private ArrayList<IPage> pageList = new ArrayList<>();

    /**
     * Contains the unique page index of page displayed.
     */
    private int actulaPageIndex = -1;

    /**
     * Reference to the outside graphical root node.
     */
    private Object rootNode;

    /**
     * Creates a {@code TNavigator} instance.
     * @param rootNode The graphical node to where other nodes instantiated here will be attached.
     */
    public TNavigator(Pane rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPage(IPage page) throws TError {
        // Input checkings
        if(page == null) {
            throw new TError("Property should not be null!");
        }

        // Determine page index
        if(this.pageList.add(page)) {
            int uniquePageIndex = this.pageList.size() - 1;
            page.setPageIndex(uniquePageIndex);
            page.setNavigator(this);

            this.actulaPageIndex = uniquePageIndex;

            this.addView((IView) page);          // Because of TView
            page.onInit();
        }
        else {
            throw new TError("Page adding was not successfull!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showActualPage() throws TError {
        // Input checkings
        if(this.actulaPageIndex == -1 || this.pageList.isEmpty()) {
            throw new TError("There is no page added yet!");
        }
        logger.debug("showActualPage:::" + this.actulaPageIndex);
        //
        this.showAPage(this.actulaPageIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showAPage(int pageIndex) throws TError {
        // Input checkings
        if(pageIndex < 0 || pageIndex > (this.pageList.size() - 1)) {
            throw new TError("The requested page index[" + pageIndex + "] is out of the actual range");
        }
        if(!this.isPageExists(pageIndex)) {
            throw new TError("The requested page at index[" + pageIndex + "] was not found!");
        }

        // Remove previous one
        for(IPage item : this.getPageList()) {
            Node childNode = (Node) item.getPageContentNode();
            Pane parentNode = (Pane) item.getParentNode();

            if(parentNode.getChildren().contains(childNode)) {
                parentNode.getChildren().remove(childNode);
            }
        }

        // Find page
        IPage page = this.getPage(pageIndex);

        // Remove previous one and add the new one
        page.onDraw();
        page.display();

        this.setActulaPageIndex(pageIndex);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPage getPage(int pageIndex) throws TError {
        // Input checkings
        if(pageIndex < 0 || pageIndex > (this.pageList.size() - 1)) {
            throw new TError("The requested page index[" + pageIndex + "] is out of the actual range");
        }
        if(!this.isPageExists(pageIndex)) {
            throw new TError("The requested page at index[" + pageIndex + "] was not found!");
        }

        return this.pageList.get(pageIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPageExists(int pageIndex) {
        if(pageIndex < this.pageList.size()) {
            if(this.pageList.get(pageIndex) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the index of page selected to be displayed.
     *
     * @param actulaPageIndex The unique index of page.
     */
    private void setActulaPageIndex(int actulaPageIndex) {
        this.actulaPageIndex = actulaPageIndex;
    }

    /**
     * Retrieves the list of added page components.
     *
     * @return The array list of added page components.
     */
    protected final ArrayList<IPage> getPageList() {
        return pageList;
    }

    /**
     * Retrieves the outside graphical root node.
     *
     * @return Reference to the outside node object.
     */
    protected final Object getRootNode() {
        return rootNode;
    }

}
