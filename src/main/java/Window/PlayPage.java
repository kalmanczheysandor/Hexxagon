package Window;

import Hexxagon.View.FaceBoard;
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
import Hexxagon.View.IFaceBoard;

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
     * Object of faceBoard graphical component of Hexxagon module.
     */
    private IFaceBoard faceBoard;
    
    /**
     * Graphical node on which placed the graphical components of Hexxahon module.
     */
    private AnchorPane gameNode;
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
    protected void initComponent() throws TError{

        // Inatialise a component
        this.backButton = new Button("Back");
        this.backButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().stopGame();
            }
            catch(TError ex) {
                 logger.error(ex.toString());
            }
            catch(Exception ex) {
                 logger.error(ex.toString());
            }
        });

        // The graphical root for hexagon boards
        this.gameNode = new AnchorPane();
        
        Pane playBoardNode= new Pane();
        Pane infoBoardNode= new Pane();
        Pane faceBoardNode= new Pane();

        // Inatialise a component
        playBoard = new PlayBoard(playBoardNode);

        // Inatialise a component
        infoBoard = new InfoBoard(infoBoardNode);
        
        // Inatialise a component
        faceBoard = new FaceBoard(faceBoardNode);
        
        
        
        this.gameNode.getChildren().add(faceBoardNode);
        this.gameNode.setRightAnchor(faceBoardNode, Double.valueOf(-20));
        this.gameNode.setTopAnchor(faceBoardNode, Double.valueOf(-20));
      
        this.gameNode.getChildren().add(playBoardNode);
        this.gameNode.setTopAnchor(playBoardNode, Double.valueOf(0));
        this.gameNode.setTopAnchor(playBoardNode, Double.valueOf(0));
        this.gameNode.getChildren().add(infoBoardNode);
            
            
       
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object drawContent() throws TError{

        AnchorPane root = new AnchorPane();
       
        root.getChildren().add(this.backButton);
        root.getChildren().add(this.gameNode);
        
        AnchorPane.setLeftAnchor(this.gameNode, Double.valueOf(0));
        AnchorPane.setRightAnchor(this.gameNode, Double.valueOf(0));
        AnchorPane.setTopAnchor(this.gameNode, Double.valueOf(0));
        AnchorPane.setBottomAnchor(this.gameNode, Double.valueOf(40));
        
        AnchorPane.setLeftAnchor(this.backButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.backButton, Double.valueOf(20));

        // Set css style
        root.setId("RootNode");
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
    public IFaceBoard getFaceBoard() {
        return faceBoard;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {
        logger.trace("Property change triggered at PlayPage");
    }

}
