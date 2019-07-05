/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Main.Main;
import Support.TError;
import java.beans.PropertyChangeEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ALX
 */
public class InfoPage extends JFXPage implements IInfoPage {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(IntroPage.class);

    /**
     * Object of towards button graphical component.
     */
    private Button backButton;

    private static final Image iconTitle = new Image(InfoPage.class.getResourceAsStream(Main.folderImage + "/title_left.gif"));
    private ImageView imageTitle;
    private Text authorText;
    private Text storyBlock;
    /**
     * Creates a {@code InfoPage} instance.
     *
     * @param parentNode Outside graphical root node.
     */
    public InfoPage(Object parentNode) {
        super(parentNode);
    }

    @Override
    protected void initComponent() throws TError {
        // Component initialisation
        this.backButton = new Button("Back");
        this.backButton.setOnMouseClicked((event) -> {

            try {
                this.getContainer().showIntroPage();
            }
            catch(TError ex) {
                logger.error(ex.toString());
            }
            catch(Exception ex) {
                logger.error(ex.toString());
            }

        });
        
        // Component initialisation
        imageTitle = new ImageView(iconTitle);
        imageTitle.setScaleX(1);
        imageTitle.setScaleY(0.9);
        
       // Component initialisation
        authorText = new Text(0, 0, ""
        + "Author: Sandor Kalmanchey, Pti Bsc. \n"
        + "E-mail: kalmanczheysandor@gmail.com\n"
        + "Neptun: Z2J914");
        authorText.setId("Text");
        authorText.setTextAlignment(TextAlignment.LEFT);
        
        // Component initialisation
        storyBlock = new Text(0, 0, ""
        + "This application was made as accomplishment for exam requirements in the following subjects:\n"
        + "- ILDV444 Mesterséges intelligencia alkalmazások \n"
        + "- ILDK311 Programozási környezetek\n"
        + "at University of Debrecen Faculty of Informatics.\n"
        + "After the first release in 2015 the application was redesigned in 2019.\n");
        storyBlock.setId("Text");
        
    }

    @Override
    protected Object drawContent() throws TError {
        AnchorPane root = new AnchorPane();
        root.setId("RootNode");

        // Attaching components
        root.getChildren().add(backButton);
        root.getChildren().add(imageTitle);
        root.getChildren().add(authorText);
        root.getChildren().add(storyBlock);
        

        // Anchoring of back button
        AnchorPane.setLeftAnchor(this.backButton, Double.valueOf(20));
        AnchorPane.setBottomAnchor(this.backButton, Double.valueOf(20));

        // Anchoring the title
        AnchorPane.setTopAnchor(imageTitle, Double.valueOf(-20));
        AnchorPane.setLeftAnchor(imageTitle, Double.valueOf(20));
        
        // Anchoring the author text
        AnchorPane.setRightAnchor(authorText, Double.valueOf(50));
        AnchorPane.setBottomAnchor(authorText, Double.valueOf(200));

        // Anchoring the story block text
        AnchorPane.setLeftAnchor(storyBlock, Double.valueOf(100));
        AnchorPane.setTopAnchor(storyBlock, Double.valueOf(200));
        return root;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {

    }

    @Override
    public void onAfterDraw() throws TError {

    }

}
