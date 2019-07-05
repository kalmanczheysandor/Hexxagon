package Window;

import Support.TError;
import Support.navigator.TPage;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains JavaFX specific implementations of {@code TPage} abstract class.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public abstract class JFXPage extends TPage {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(JFXPage.class);

    /**
     * Creates a {@code JFXPage} instance.
     *
     * @param parentNode Reference to the outside graphical node to which the inside components are attached when the page is displayed.
     */
    public JFXPage(Object parentNode) {
        super(parentNode);
        logger.trace("Page initialised!");
    }

    /**
     * Retrieves reference to the navigator component.
     *
     * @return Reference of the navigator component.
     */
    protected IContainer getContainer() {
        return (IContainer) this.getController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {

        Node childNode = (Node) this.getPageContentNode();
        Pane parentNode = (Pane) this.getParentNode();

        // Attach the actula page
        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .node(childNode)
                .fromValue(0)
                .toValue(1)
                .cycleCount(1)
                .autoReverse(false)
                .build();

        ScaleTransition scaleTransition = ScaleTransitionBuilder.create()
                .node(childNode)
                .duration(Duration.seconds(0.5))
                .fromX(0.5)
                .fromY(0.5)
                .toX(1)
                .toY(1)
                .cycleCount(1)
                .autoReverse(false)
                .build();

        parentNode.getChildren().add(childNode);

        fadeTransition.play();
        scaleTransition.play();

    }

   
}
