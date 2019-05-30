package Window;

import Support.IItem;
import Support.TError;
import java.beans.PropertyChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBuilder;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object instance of this class is responsible for the initialisation and draw of graphical component displayed on settings page.
 * Contains the implementation of application page/view component specified by {@code ISettingsPage} interface.
 * The class extends the {@code JFXPage} javaFX specific abstract page class.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class SettingsPage extends JFXPage implements ISettingsPage {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(SettingsPage.class);
    /**
     * Object of back button graphical component.
     */
    private Button backButton;
    /**
     * Object of play button graphical component.
     */
    private Button playButton;
    /**
     * Object of board mode select box graphical component.
     */
    private ComboBox boardSelectInput;
    /**
     * Object of difficulty level select box graphical component.
     */
    private ComboBox difficultySelectInput;

    /**
     * Creates a {@code SettingsPage} instance.
     *
     * @param parentNode Outside graphical root node.
     */
    public SettingsPage(Object parentNode) {
        super(parentNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initComponent() {

        // Component initialisation
        this.backButton = new Button("Back");
        this.backButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showIntroPage();
            }
            catch(TError ex) {
                logger.error(ex.toString());
            }

        });

        // Component initialisation
        this.playButton = new Button("Play");
        this.playButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showPlayPage();
                this.getContainer().startGame();
            }
            catch(TError ex) {
                logger.error(ex.toString());
            }

        }
        );

        this.playButton.setDisable(
                true);

        // Component initialisation
        ObservableList boardList = FXCollections.observableArrayList();
        for(IStorage.IBoardModeItem item : this.getContainer().getStorage().getBoardModeList()) {
            boardList.add(item.getIndex(), item.getCaption());
        }

        this.boardSelectInput = ComboBoxBuilder.create()
                .id("uneditable-combobox")
                .promptText("Make a choice...")
                .items(boardList)
                .build();
        this.boardSelectInput.setMinWidth(300);
        this.boardSelectInput.setOnAction(
                (event) -> {
                    try {
                        int selectedKey = this.boardSelectInput.getSelectionModel().getSelectedIndex();

                        this.getContainer().selectBoardMode(selectedKey);
                    }
                    catch(TError ex) {
                        logger.error(ex.toString());
                    }
                }
        );

        // Component initialisation
        ObservableList difficultyList = FXCollections.observableArrayList();
        for(TSelectItem item
                : this.getContainer()
                        .getStorage().getDifficultyTypeList()) {
            difficultyList.add(item.getKey(), item.getCaption());
        }
        ObservableList fightTypeList = FXCollections.observableArrayList("Human player vs human player", "Human player vs AI player", "AI player vs AI player");

        this.difficultySelectInput = ComboBoxBuilder.create()
                .id("uneditable-combobox")
                .promptText("Make a choice...")
                .items(difficultyList)
                .build();

        this.difficultySelectInput.setMinWidth(
                300);

        this.difficultySelectInput.setOnAction(
                (event) -> {
                    try {
                        int selectedKey = this.difficultySelectInput.getSelectionModel().getSelectedIndex();

                        this.getContainer().selectDifficulty(selectedKey);
                    }
                    catch(TError ex) {
                        logger.error(ex.toString());
                    }
                }
        );

        this.difficultySelectInput.setDisable(
                true);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object drawContent() {

        StackPane menuPanel = this.createMenuPanel();

        AnchorPane root = new AnchorPane();
        root.getChildren().add(this.backButton);
        root.getChildren().add(this.playButton);
        root.getChildren().add(menuPanel);

        // Anchoring of menupanel
        AnchorPane.setLeftAnchor(menuPanel, Double.valueOf(0));
        AnchorPane.setRightAnchor(menuPanel, Double.valueOf(0));
        AnchorPane.setTopAnchor(menuPanel, Double.valueOf(0));
        AnchorPane.setBottomAnchor(menuPanel, Double.valueOf(50));

        // Anchoring of back button
        AnchorPane.setLeftAnchor(this.backButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.backButton, Double.valueOf(20));

        // Anchoring of play button
        AnchorPane.setRightAnchor(this.playButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.playButton, Double.valueOf(20));

        // Set css style
        root.setId("RootNode");
        // root.getStylesheets().add(SettingsPage.class.getResource(ISettingsPage.folderCSS+"/SettingsPage.css").toExternalForm());
        return root;
    }

    /**
     * Creates the node tree of select boxes.
     *
     * @return Reference of the top node of the tree.
     */
    private StackPane createMenuPanel() {

        //
        Text label01 = new Text("Select a board type");
        label01.setId("Label");

        //
        Text label02 = new Text("Select a fight type");
        label02.setId("Label");

        VBox centerBox = VBoxBuilder.create().alignment(Pos.CENTER_LEFT).spacing(15).build();
        centerBox.getChildren().add(label01);
        centerBox.getChildren().add(this.boardSelectInput);
        centerBox.getChildren().add(label02);
        centerBox.getChildren().add(this.difficultySelectInput);
        centerBox.setMaxWidth(300);

        StackPane root = new StackPane(centerBox);
        root.setAlignment(Pos.CENTER);

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
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {

        try {

            int boardKey = this.getContainer().getStorage().getSelectedBoardIndex();
            int fightKey = this.getContainer().getStorage().getSelectedDifficultyIndex();

            boolean isAutoPlayer = false;

            if(boardKey >= 0) {
                isAutoPlayer = this.getContainer().getStorage().getBoardMode(boardKey).isAIPlayerAdded();
                if(isAutoPlayer) {
                    this.difficultySelectInput.setDisable(false);
                }
                else {
                    this.difficultySelectInput.setDisable(true);
                }
            }

            if(isAutoPlayer) {
                if(boardKey >= 0 && fightKey >= 0) {
                    this.playButton.setDisable(false);
                }
                else {
                    this.playButton.setDisable(true);
                }
            }
            else {
                if(boardKey >= 0) {
                    this.playButton.setDisable(false);
                }
                else {
                    this.playButton.setDisable(true);
                }
            }
        }
        catch(TError err) {
            logger.error(err.toString());
        }

    }

}
