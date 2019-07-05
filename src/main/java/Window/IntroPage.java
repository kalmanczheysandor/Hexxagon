package Window;

import Hexxagon.View.PlayBoard;
import Main.Main;
import Support.TError;
import java.beans.PropertyChangeEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object instance of this class is responsible for the initialisation and draw of graphical component displayed on intro page.
 * Contains the implementation of application page/view component specified by {@code ISettingsPage} interface.
 * The class extends the {@code JFXPage} javaFX specific abstract page class.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class IntroPage extends JFXPage implements IIntroPage {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(IntroPage.class);

    /**
     * Object of towards button graphical component.
     */
    private Button towardsButton;
    /**
     * Object of exit button graphical component.
     */
    private Button infoButton;

    /**
     * Object of text graphical component.
     */
    private Text headLine;

    private static final Image iconBulbBottom = new Image(IntroPage.class.getResourceAsStream(Main.folderImage + "/bigbulb_bottom.gif"));
    private static final Image iconBulbTop = new Image(IntroPage.class.getResourceAsStream(Main.folderImage + "/bigbulb_top.gif"));
    private static final Image iconTitle = new Image(IntroPage.class.getResourceAsStream(Main.folderImage + "/title.gif"));

    /**
     * Creates a {@code IntroPage} instance.
     *
     * @param parentNode Outside graphical root node.
     */
    public IntroPage(Object parentNode) {
        super(parentNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initComponent() throws TError {
       
       // Component initialisation
        this.infoButton = new Button("Info");
        this.infoButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showInfoPage();
            }
            catch(TError ex) {
                logger.error(ex.toString());
            }

        });

       // Component initialisation
        this.towardsButton = new Button("Next");
        this.towardsButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showSettingsPage();
            }
            catch(TError ex) {
                logger.error(ex.toString());
            }

        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object drawContent() throws TError {

        //
        AnchorPane root = new AnchorPane();
        root.setId("RootNode");

        // Attaching components
        root.getChildren().add(this.infoButton);
        root.getChildren().add(this.towardsButton);

        // Anchoring info button
        AnchorPane.setLeftAnchor(this.infoButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.infoButton, Double.valueOf(20));

        // Anchoring towards button
        AnchorPane.setRightAnchor(this.towardsButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.towardsButton, Double.valueOf(20));

        // TITLE
        ImageView imageTitle = new ImageView(iconTitle);
        imageTitle.setScaleX(0.85);
        imageTitle.setScaleY(0.85);
        root.getChildren().add(imageTitle);
        AnchorPane.setTopAnchor(imageTitle, Double.valueOf(200));
        AnchorPane.setLeftAnchor(imageTitle, Double.valueOf(-30));

        // VERSION NUMBER
        Text versionText = new Text(50, 50, "ARE YOU CLEVER ENOUGH?");
        versionText.setId("Text");
        root.getChildren().add(versionText);
        AnchorPane.setBottomAnchor(versionText, Double.valueOf(200));
        AnchorPane.setLeftAnchor(versionText, Double.valueOf(300));

        // BULB
        DropShadow boubleLight = new DropShadow();
        boubleLight.setRadius(5);
        boubleLight.setColor(Color.YELLOW);

        Pane bulbNode = new Pane();

        ImageView imageBulbTop = new ImageView(iconBulbTop);
        imageBulbTop.setEffect(boubleLight);
        imageBulbTop.setLayoutY(0);
        imageBulbTop.setLayoutX(0);

        ImageView imageBulbBottom = new ImageView(iconBulbBottom);
        imageBulbBottom.setLayoutY(128);
        imageBulbBottom.setLayoutX(29);

        bulbNode.getChildren().add(imageBulbTop);
        bulbNode.getChildren().add(imageBulbBottom);
        bulbNode.setScaleX(0.7);
        bulbNode.setScaleY(0.7);

        root.getChildren().add(bulbNode);
        root.setLeftAnchor(bulbNode, Double.valueOf(630));
        root.setTopAnchor(bulbNode, Double.valueOf(185));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);
        animation.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(boubleLight.radiusProperty(), 10)),
                new KeyFrame(Duration.millis(1000), new KeyValue(boubleLight.radiusProperty(), 70))
        );
        animation.play();

        // AUTHOR
        Text authorText = new Text(0, 0, ""
                + "Author: Sandor Kalmanchey, Pti Bsc. \n"
                + "E-mail: kalmanczheysandor@gmail.com\n"
                + "Neptun: Z2J914");
        authorText.setId("Text");
        authorText.setTextAlignment(TextAlignment.LEFT);
        //root.getChildren().add(authorText);
        AnchorPane.setRightAnchor(authorText, Double.valueOf(200));
        AnchorPane.setBottomAnchor(authorText, Double.valueOf(15));

        // NOTE
        Text storyBlock = new Text(0, 0, ""
                + "This application has been made as accomplishment for exam requirements in the following subjects:\n"
                + "- ILDV444 Mesterséges intelligencia alkalmazások \n"
                + "- ILDK311 Programozási környezetek\n"
                + "at University of Debrecen Faculty of Informatics.\n"
                + "After the first release in 2015 the application has been redesigned in 2019.\n");

        storyBlock.setId("Text");
        //root.getChildren().add(storyBlock);
        AnchorPane.setLeftAnchor(storyBlock, Double.valueOf(10));
        AnchorPane.setBottomAnchor(storyBlock, Double.valueOf(0));

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
        logger.trace("Property change triggered at IntroPage");
    }

}
