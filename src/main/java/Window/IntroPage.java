package Window;

import Support.TError;
import java.beans.PropertyChangeEvent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Button exitButton;

    /**
     * Object of text graphical component.
     */
    private Text headLine;

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
    protected void initComponent() {
        IntroPage page = this;

        //
        this.exitButton = new Button("Exit");

        //
        this.towardsButton = new Button("Next");
        this.towardsButton.setOnMouseClicked((event) -> {

            try {
                page.getContainer().showSettingsPage();
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
    protected Object drawContent() {

        //
        AnchorPane root = new AnchorPane();
        root.setId("RootNode");

        //
        root.getChildren().add(this.towardsButton);
        AnchorPane.setRightAnchor(this.towardsButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.towardsButton, Double.valueOf(20));

        Text authorText = new Text(50, 10, ""
                + "Author: Sandor Kalmanchey, Pti Bsc. \n"
                + "E-mail: kalmanczheysandor@gmail.com\n"
                + "Neptun: Z2J914");
        authorText.setId("Text");
        root.getChildren().add(authorText);
        AnchorPane.setRightAnchor(authorText, Double.valueOf(100));
        AnchorPane.setBottomAnchor(authorText, Double.valueOf(20));

        //
        this.headLine = new Text(20, 120, "HEXXAGON");
        headLine.setId("HeadLine");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.WHITE);
        headLine.setEffect(dropShadow);
        root.getChildren().add(headLine);
        AnchorPane.setTopAnchor(headLine, Double.valueOf(20));
        AnchorPane.setLeftAnchor(headLine, Double.valueOf(50));

        Text versionText = new Text(50, 50, "2.0");
        versionText.setId("Text");
        root.getChildren().add(versionText);
        AnchorPane.setTopAnchor(versionText, Double.valueOf(200));
        AnchorPane.setLeftAnchor(versionText, Double.valueOf(720));

        //
        Text storyBlock = new Text(300, 200, ""
                + "This application has been made as accomplishment for exam requirements in the following subjects:\n"
                + "- ILDV444 Mesterséges intelligencia alkalmazások \n"
                + "- ILDK311 Programozási környezetek\n"
                + "at University of Debrecen Faculty of Informatics.\n"
                + "After the first release in 2015 the application has been redesigned in 2019.\n");

        storyBlock.setId("Text");
        root.getChildren().add(storyBlock);
        AnchorPane.setTopAnchor(storyBlock, Double.valueOf(300));
        AnchorPane.setLeftAnchor(storyBlock, Double.valueOf(300));

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
