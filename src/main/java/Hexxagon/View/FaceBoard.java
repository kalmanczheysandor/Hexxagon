package Hexxagon.View;

import Support.TCoordinate2D;
import Support.TError;
import Support.mvc.TView;
import java.beans.PropertyChangeEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instance of this class realises an bulb face of game as a JavaFX specific graphical node.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class FaceBoard extends TView implements IFaceBoard {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlayBoard.class);

    /**
     * Relative path of folder of css files.
     */
    private static final String folderCSS = "/css";
    /**
     * Relative path of folder of image files.
     */
    private static final String folderImage = "/image";
    /**
     * Relative path of folder of audio files.
     */
    private static final String folderAudio = "/audio";

    /**
     * Image file.
     */
    private static final Image iconBulbBottom = new Image(FaceBoard.class.getResourceAsStream(folderImage + "/bulb_bottom.gif"));
    /**
     * Image file.
     */
    private static final Image iconBulbTop = new Image(FaceBoard.class.getResourceAsStream(folderImage + "/bulb_top.gif"));
    /**
     * Image file.
     */
    private static final Image iconGlasses = new Image(FaceBoard.class.getResourceAsStream(folderImage + "/glasses.gif"));
    /**
     * Image file.
     */
    private static final Image iconMustageLeft = new Image(FaceBoard.class.getResourceAsStream(folderImage + "/mustage_left.gif"));
    /**
     * Image file.
     */
    private static final Image iconMustageRight = new Image(FaceBoard.class.getResourceAsStream(folderImage + "/mustage_right.gif"));

    /**
     * Visual component.
     */
    private ImageView imageBulbBottom;
    /**
     * Visual component.
     */
    private ImageView imageBulbTop;
    /**
     * Visual component.
     */
    private ImageView imageGlasses;
    /**
     * Visual component.
     */
    private ImageView imageMustageLeft;
    /**
     * Visual component.
     */
    private ImageView imageMustageRight;
    /**
     * Animation component.
     */
    Timeline animationThink;
    /**
     * Animation component.
     */
    Timeline animationSeeTarget;
    /**
     * Animation component.
     */
    Timeline animationSeeTargetOff;
    /**
     * Animation component.
     */
    Timeline animationCrazy;
    /**
     * Animation component.
     */
    Timeline animationSurprise;

    private TCoordinate2D leftEyeNormal = new TCoordinate2D(23, 47);
    private TCoordinate2D rightEyeNormal = new TCoordinate2D(82, 47);
    private TCoordinate2D leftEyeTarget = new TCoordinate2D(17, 55);
    private TCoordinate2D rightEyeTarget = new TCoordinate2D(77, 55);
    private TCoordinate2D leftEyeCrazy = new TCoordinate2D(33, 47);
    private TCoordinate2D rightEyeCrazy = new TCoordinate2D(72, 47);

    /**
     * Stores local graphical root node.
     */
    private AnchorPane rootNode = new AnchorPane();
    private Pane faceNode;

    /**
     * Creates a {@code PlayBoard} instance.
     *
     * @param root JavaFX graphical node to where this graphical component would be attached.
     */
    public FaceBoard(Pane root) throws TError {
        super(root);
        createBoard();
        animateWait();
    }

    public void animateInactive() {
        faceNode.setOpacity(0.9);
        this.animationThink.stop();
        this.animationSeeTarget.stop();
    }

    public void animateThink() {
        faceNode.setOpacity(1);
        this.animationThink.playFromStart();
        this.animationSeeTarget.playFromStart();
    }

    public void animateCrazy() {
        this.animationCrazy.playFromStart();
    }

    public void animateSurprise() {
        this.animationSurprise.playFromStart();
    }

    public void animateWait() {
        faceNode.setOpacity(0.5);
        this.animationThink.stop();
        this.animationSeeTargetOff.playFromStart();
    }

    /**
     * Creates the board.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private final void createBoard() throws TError {

        if(this.rootNode.getChildren().size() == 0) {

            faceNode = new Pane();

            DropShadow boubleLight = new DropShadow();
            boubleLight.setRadius(5);
            boubleLight.setColor(Color.YELLOW);

            imageBulbBottom = new ImageView(iconBulbBottom);
            imageBulbBottom.setLayoutY(126);
            imageBulbBottom.setLayoutX(29);

            imageBulbTop = new ImageView(iconBulbTop);
            imageBulbTop.setEffect(boubleLight);

            imageGlasses = new ImageView(iconGlasses);
            imageGlasses.setLayoutY(20);
            imageGlasses.setLayoutX(-10);

            imageMustageLeft = new ImageView(iconMustageLeft);
            imageMustageLeft.setLayoutY(80);
            imageMustageLeft.setLayoutX(-6);

            imageMustageRight = new ImageView(iconMustageRight);
            imageMustageRight.setLayoutY(80);
            imageMustageRight.setLayoutX(49);

            Circle eyeLeft = new Circle(2, Color.YELLOW);
            eyeLeft.setStrokeType(StrokeType.OUTSIDE);
            eyeLeft.setStroke(Color.BLACK);
            eyeLeft.setStrokeWidth(5);
            eyeLeft.setLayoutY(leftEyeNormal.getY());
            eyeLeft.setLayoutX(leftEyeNormal.getX());

            Circle eyeRight = new Circle(2, Color.YELLOW);
            eyeRight.setStrokeType(StrokeType.OUTSIDE);
            eyeRight.setStroke(Color.BLACK);
            eyeRight.setStrokeWidth(5);
            eyeRight.setLayoutY(rightEyeNormal.getY());
            eyeRight.setLayoutX(rightEyeNormal.getX());

            faceNode.getChildren().add(imageBulbTop);
            faceNode.getChildren().add(imageBulbBottom);
            faceNode.getChildren().add(imageGlasses);
            faceNode.getChildren().add(imageMustageLeft);
            faceNode.getChildren().add(imageMustageRight);
            faceNode.getChildren().add(eyeLeft);
            faceNode.getChildren().add(eyeRight);
            faceNode.setScaleX(0.5);
            faceNode.setScaleY(0.5);
            faceNode.setLayoutX(0);
            faceNode.setLayoutY(-50);

            this.rootNode.getChildren().add(faceNode);

            this.rootNode.setRightAnchor(faceNode, Double.valueOf(0));
            this.rootNode.setTopAnchor(faceNode, Double.valueOf(0));
            this.rootNode.setMaxSize(50, 50);

            animationThink = new Timeline();
            animationThink.setCycleCount(Timeline.INDEFINITE);
            animationThink.setAutoReverse(true);
            animationThink.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(boubleLight.radiusProperty(), 10),
                            new KeyValue(eyeRight.layoutXProperty(), leftEyeNormal.getY())
                    ),
                    new KeyFrame(Duration.millis(500), new KeyValue(boubleLight.radiusProperty(), 70))
            );

            animationSeeTarget = new Timeline();
            animationSeeTarget.setCycleCount(1);
            animationSeeTarget.setAutoReverse(false);
            animationSeeTarget.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO,
                        new KeyValue(eyeLeft.layoutXProperty(), leftEyeNormal.getX()),
                        new KeyValue(eyeLeft.layoutYProperty(), leftEyeNormal.getY()),
                        new KeyValue(eyeRight.layoutXProperty(), rightEyeNormal.getX()),
                        new KeyValue(eyeRight.layoutYProperty(), rightEyeNormal.getY())
                    ),
                    new KeyFrame(Duration.millis(200),
                        new KeyValue(eyeLeft.layoutXProperty(), leftEyeTarget.getX()),
                        new KeyValue(eyeLeft.layoutYProperty(), leftEyeTarget.getY()),
                        new KeyValue(eyeRight.layoutXProperty(), rightEyeTarget.getX()),
                        new KeyValue(eyeRight.layoutYProperty(), rightEyeTarget.getY())
                    )
            );

            animationSeeTargetOff = new Timeline();
            animationSeeTargetOff.setCycleCount(1);
            animationSeeTargetOff.setAutoReverse(false);
            animationSeeTargetOff.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeTarget.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeTarget.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeTarget.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeTarget.getY())
                    ),
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeNormal.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeNormal.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeNormal.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeNormal.getY())
                    )
            );

            animationCrazy = new Timeline();
            animationCrazy.setCycleCount(1);
            animationCrazy.setAutoReverse(false);
            animationCrazy.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(faceNode.opacityProperty(), 0.5),
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeNormal.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeNormal.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeNormal.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeNormal.getY())
                    ),
                    new KeyFrame(Duration.millis(50),
                            new KeyValue(faceNode.opacityProperty(), 1),
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeCrazy.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeCrazy.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeCrazy.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeCrazy.getY())
                    ),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(faceNode.opacityProperty(), 1),
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeCrazy.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeCrazy.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeCrazy.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeCrazy.getY())
                    ),
                    new KeyFrame(Duration.millis(500),
                            new KeyValue(faceNode.opacityProperty(), 0.5),
                            new KeyValue(eyeLeft.layoutXProperty(), leftEyeNormal.getX()),
                            new KeyValue(eyeLeft.layoutYProperty(), leftEyeNormal.getY()),
                            new KeyValue(eyeRight.layoutXProperty(), rightEyeNormal.getX()),
                            new KeyValue(eyeRight.layoutYProperty(), rightEyeNormal.getY())
                    )
            );

            animationSurprise = new Timeline();
            animationSurprise.setCycleCount(1);
            animationSurprise.setAutoReverse(false);
            animationSurprise.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(faceNode.opacityProperty(), 0.5),
                            new KeyValue(eyeLeft.strokeWidthProperty(), 5),
                            new KeyValue(eyeRight.strokeWidthProperty(), 5),
                            new KeyValue(eyeLeft.radiusProperty(), 2),
                            new KeyValue(eyeRight.radiusProperty(), 2)
                    ),
                    new KeyFrame(Duration.millis(50),
                            new KeyValue(faceNode.opacityProperty(), 1),
                            new KeyValue(eyeLeft.strokeWidthProperty(), 11),
                            new KeyValue(eyeRight.strokeWidthProperty(), 11),
                            new KeyValue(eyeLeft.radiusProperty(), 5),
                            new KeyValue(eyeRight.radiusProperty(), 5)
                    ),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(faceNode.opacityProperty(), 1),
                            new KeyValue(eyeLeft.strokeWidthProperty(), 11),
                            new KeyValue(eyeRight.strokeWidthProperty(), 11),
                            new KeyValue(eyeLeft.radiusProperty(), 5),
                            new KeyValue(eyeRight.radiusProperty(), 5)
                    ),
                    new KeyFrame(Duration.millis(500),
                            new KeyValue(faceNode.opacityProperty(), 0.5),
                            new KeyValue(eyeLeft.strokeWidthProperty(), 5),
                            new KeyValue(eyeRight.strokeWidthProperty(), 5),
                            new KeyValue(eyeLeft.radiusProperty(), 2),
                            new KeyValue(eyeRight.radiusProperty(), 2)
                    )
            );

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() throws TError {
        // If info board not created and not attached to the outside nodes
        if(!this.getUIRoot().getChildren().contains(this.rootNode)) {
            this.createBoard();
            this.getUIRoot().getChildren().add(this.rootNode);
        }

        //this.checkAllowedPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        this.getUIRoot().getChildren().remove(this.rootNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() throws TError {
        //this.createBoard();
        //this.checkActivePlayers();
        //this.checkAllowedPlayer();
        //this.checkPlayersScore();
        // this.checkDefeat();

    }

    /**
     * Gives back the general graphical root node as actual graph framework specific.
     *
     * @return An {@code Pane} instance.
     */
    private Pane getUIRoot() {
        return (Pane) super.getGraphicalRoot();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {

    }

}
