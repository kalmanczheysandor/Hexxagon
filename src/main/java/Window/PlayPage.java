package Window;

import Hexxagon.View.IInfoBoard;
import Hexxagon.View.IPlayBoard;
import Hexxagon.View.InfoBoard;
import Hexxagon.View.PlayBoard;
import Support.TError;
import java.beans.PropertyChangeEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object instance of this class is responsible for the initialisation and draw of graphical component displayed on play page.
 * Contains the implementation of application page/view component specified by {@code ISettingsPage} interface.
 * The class extends the {@code JFXPage} javaFX specific abstract page class.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class PlayPage extends JFXPage implements IPlayPage {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlayPage.class);

    /**
     * Object of play board graphical component of Hexxagon module.
     */
    private IPlayBoard playBoard;
    /**
     * Object of info board graphical component of Hexxagon module.
     */
    private IInfoBoard infoBoard;

    /**
     * Graphical node on which placed the graphical components of Hexxahon module.
     */
    private Pane gameNode;
    /**
     * Object of back button graphical component.
     */
    private Button backButton;

    /**
     * Creates a {@code PlayPage} instance.
     *
     * @param parentNode Outside graphical root node.
     */
    public PlayPage(Object parentNode) {
        super(parentNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initComponent() {

        // Inatialise a component
        this.backButton = new Button("Back");
        this.backButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showSettingsPage();
            }
            catch(TError ex) {
                 logger.error(ex.toString());
            }

        });

        // The graphical root for hexagon boards
        this.gameNode = new Pane();

        // Inatialise a component
        playBoard = new PlayBoard(gameNode);

        // Inatialise a component
        infoBoard = new InfoBoard(gameNode);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object drawContent() {

        AnchorPane root = new AnchorPane();
        root.getChildren().add(this.backButton);
        root.getChildren().add(this.gameNode);
        AnchorPane.setLeftAnchor(this.backButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.backButton, Double.valueOf(20));

        // Set css style
        root.setId("RootNode");
        // root.getStylesheets().add(PlayPage.class.getResource(IPlayPage.folderCSS+"/PlayPage.css").toExternalForm());
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAfterDraw() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlayBoard getPlayBoard() {
        return playBoard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IInfoBoard getInfoBoard() {
        return infoBoard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {
        logger.trace("Property change triggered at PlayPage");
    }

}
