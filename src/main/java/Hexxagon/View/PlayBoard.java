package Hexxagon.View;

import Hexxagon.Controller.IGameController;
import Hexxagon.Model.GameData;
import Hexxagon.Model.IGamePlay;
import Support.TError;
import Support.mvc.TView;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;


import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.StrokeTransition;
import javafx.animation.StrokeTransitionBuilder;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.IField;
import Hexxagon.Model.IGameData;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instance of this class realises a virtual game board as a JavaFX specific graphical node.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public class PlayBoard extends TView implements IPlayBoard {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlayBoard.class);
    /**
     * List of relative coordinates of attack zone.
     */
    private static final Coordinate[] relativeCoordinatesOfAttackZone = {
        /*C-2*/new Coordinate(-2, 0), new Coordinate(-2, 1), new Coordinate(-2, 2),
        /*C-1*/ new Coordinate(-1, -1), new Coordinate(-1, 0), new Coordinate(-1, +1), new Coordinate(-1, +2),
        /*C0*/ new Coordinate(0, -2), new Coordinate(0, -1), new Coordinate(0, 1), new Coordinate(0, 2),
        /*C+1*/ new Coordinate(1, -2), new Coordinate(1, -1), new Coordinate(1, 0), new Coordinate(1, 1),
        /*C+2*/ new Coordinate(2, -2), new Coordinate(2, -1), new Coordinate(2, 0)
    };

    /**
     * List of cells represented by graphical nodes so called pits.
     */
    private ArrayList<BoardPit> pitList = new ArrayList<BoardPit>();

    //String SoundDirectoryPath = "F:/Sound";
    // private AudioClip VoiceNotAllowedPlayerSelected = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound01.wav").toExternalForm());
    //private AudioClip VoiceOutside = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound01.wav").toExternalForm());
    //private AudioClip VoiceSpreadWithJump = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound02.wav").toExternalForm());
    //private AudioClip VoiceSpreadWithNoJump = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound03.wav").toExternalForm());
    //private AudioClip VoiceCapture = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound04.wav").toExternalForm());
    //private AudioClip VoiceShowSpreadArea = new AudioClip(PlayBoard.class.getResource(SoundDirectoryPath + "/Sound05.wav").toExternalForm());
    private Pane rootNode = new Pane();

    /**
     * Creates a {@code PlayBoard} instance.
     *
     * @param root JavaFX graphical node to where this graphical component would be attached.
     */
    public PlayBoard(Pane root) {
        super(root);
        logger.trace("PlayBoard constructed!");
        initialisation();
    }

    /**
     * Where sub component initialisation might happens.
     */
    private void initialisation() {

        // Adding to UI root node
        // this.getRoot().getChildren().add(button01);
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

        // Do animation on all pit when the board becomes visible
        byte[][] stands = this.getDataInstance().getCells();
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {
                if(stands[columnIndex][rowIndex] != -1) {
                    this.getPit(columnIndex, rowIndex).animateForStart();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        //this.RootNode.getChildren().remove(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() throws TError {
        //
        this.createBoard();

        //
        for(int columnIndex = 0; columnIndex <= IGamePlay.maximumExpectedStandColumnIndex; columnIndex++) {

            for(int rowIndex = 0; rowIndex <= IGamePlay.maximumExpectedStandRowIndex; rowIndex++) {

                BoardPit pit = this.getPit(columnIndex, rowIndex);
                pit.checkState();
            }
        }
    }

    /**
     * Creates the board.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private final void createBoard() throws TError {

        //
        byte[][] stands = this.getDataInstance().getCells();

        //
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) { //Columns

            // CreateAColumn
            int posX = 10 + ((columnIndex + 1) * 50);
            int posY = 250 - ((columnIndex + 1) * 22);

            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) { //Az adott oszlop sorai(minden sorban pontosan 1db cella)

                //
                BoardPit pit;
                if(this.isPitExists(columnIndex, rowIndex)) {
                    pit = this.getPit(columnIndex, rowIndex);
                }
                else {
                    pit = new BoardPit(this, posX + (rowIndex + 1) * 49, posY + (rowIndex + 1) * 23, columnIndex, rowIndex);
                    this.pitList.add(pit);
                }

                //
                if(stands[columnIndex][rowIndex] != -1) {
                    if(!this.rootNode.getChildren().contains(pit)) {
                        this.addToRootNode(pit);
                    }
                }
                else {
                    if(this.rootNode.getChildren().contains(pit)) {
                        this.rootNode.getChildren().remove(pit);

                    }
                }
                //
                pit.checkState();

            }

        }

    }

    /**
     * Occurs when a pit graphical component is clicked.
     *
     * @param columnIndex Column index of clicked pit.
     * @param rowIndex    Row index of clicked pit.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void pitClicked(int columnIndex, int rowIndex) throws TError {
        if(this.isControllerAttached()) {
            this.getControllerInstance().cellClicked(columnIndex, rowIndex);
        }
    }

    /**
     * Answers whether the pit exists at specified coordinates.
     *
     * @param columnIndex Column index of specified coordinate.
     * @param rowIndex    Row index of specified coordinate.
     *
     * @return True if pit exists at specified coordinates otherwise false.
     */
    private boolean isPitExists(int columnIndex, int rowIndex) {
        BoardPit pit = new BoardPit(this, 0, 0, columnIndex, rowIndex);
        if(this.pitList.contains(pit)) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves the pit object found at specified coordinates.
     *
     * @param columnIndex Column index of specified coordinate.
     * @param rowIndex    Row index of specified coordinate.
     *
     * @return An instance of {@code BoardPit} class.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private BoardPit getPit(int columnIndex, int rowIndex) throws TError {
        // Input checkings
        BoardPit pit = new BoardPit(this, 0, 0, columnIndex, rowIndex);
        if(!this.pitList.contains(pit)) {
            throw new TError("No data was found at given parameters [" + columnIndex + "," + rowIndex + "]!");
        }

        // Find data
        int indx = this.pitList.indexOf(pit);
        return this.pitList.get(indx);
    }

    /**
     * Adds a graphical node to the local root node.
     *
     * @param Node The graphical node to be added.
     */
    private final void addToRootNode(Node Node) {
        this.rootNode.getChildren().add(Node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(GameData.MODIFICATIONCODE_ACTUALGAMEPLAY)) {
            //String newStringValue = evt.getNewValue().toString();
            //xPositionTextField.setText(newStringValue);

        }
        else {
            logger.warn("Unexpected property change:" + evt.getPropertyName());
        }

    }

    /**
     * Gives back the general graphical root node as actual graph framework specific.
     *
     * @return A JavaFX specific graphical node object.
     */
    private Pane getUIRoot() {
        return (Pane) super.getGraphicalRoot();
    }

    /**
     * Retrieves the controller component as {@code IGameController} implementation.
     *
     * @return An instance of class implementing {@code IGameController} interface.
     */
    private IGameController getControllerInstance() {
        return (IGameController) super.getController();
    }

    /**
     * Retrieves the data component as {@code IGameData} implementation.
     *
     * @return An instance of class implementing {@code IGameData} interface.
     */
    private IGameData getDataInstance() {
        return (IGameData) super.getController().getModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animateWhenNotAllowedPlayerClickedOnAPit(int columnIndex, int rowIndex) {
        //VoiceNotAllowedPlayerSelected.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animateWhenClickedOutsideOfAttackZone(int columnIndex, int rowIndex) {
        //VoiceOutside.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animateAsAttackZone(int baseColumnIndex, int baseRowIndex) throws TError {
        // Input checkings
        if(!this.getDataInstance().isCellExists(baseColumnIndex, baseRowIndex)) {
            throw new TError("The requested coordinate[" + baseColumnIndex + "," + baseRowIndex + "] is out of range!");
        }
        if(!this.isPitExists(baseColumnIndex, baseRowIndex)) {
            throw new TError("The requested pit[" + baseColumnIndex + "," + baseRowIndex + "] is not found!");
        }

        //VoiceShowSpreadArea.play();
        // Remove possible recent animation
        this.removeAllAttackZone();

        // Animate base cell of attack zone
        this.animateAsAttackZoneOrigo(baseColumnIndex, baseRowIndex);

        // Animate cells of attack zone
        for(Coordinate relativeCoordinate : relativeCoordinatesOfAttackZone) {
            int zoneColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int zoneRowIndex = baseRowIndex + relativeCoordinate.getY();

            if(this.isPitExists(zoneColumnIndex, zoneRowIndex) && this.getDataInstance().isCellExists(zoneColumnIndex, zoneRowIndex)) {

                BoardPit pit = this.getPit(zoneColumnIndex, zoneRowIndex);
                IField field = this.getDataInstance().getCellAsField(zoneColumnIndex, zoneRowIndex);
                if((pit.isInactive() == false) && (field.getPlayerIndex() == 0)) {
                    pit.animateAsAttackZoneCell();
                }

            }
        }
    }

    /**
     * Displays animation on the centre pit of attack zone. Centre pit is the clicked cell which attack zone displayed around.
     *
     * @param columnIndex Column index of centre pit.
     * @param rowIndex    Row index of centre pit.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void animateAsAttackZoneOrigo(int columnIndex, int rowIndex) throws TError {
        BoardPit pit = this.getPit(columnIndex, rowIndex);
        IField field = this.getDataInstance().getCellAsField(columnIndex, rowIndex);

        if(pit != null && field != null) {
            if(!pit.isInactive() && field.getPlayerIndex() != 0) {
                pit.animateForSpreadAreaCenter();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeAllAttackZone() throws TError {

        for(BoardPit pit : this.pitList) {
            if(!pit.isInactive()) {
                pit.removeSpreadArea();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animatePitWhenCapturedByPlayer(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        BoardPit pit = this.getPit(columnIndex, rowIndex);
        if(pit != null) {
            pit.animateForCapture();
            //this.VoiceSpreadWithJump.play();

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animatePitWhenCapturedInLocalArea(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        BoardPit Pit = this.getPit(columnIndex, rowIndex);
        if(Pit != null) {
            Pit.animateForCapture();
            //this.VoiceCapture.play();

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void animatePitWhenAbandon(int columnIndex, int rowIndex) throws TError {
        BoardPit Pit = this.getPit(columnIndex, rowIndex);
        if(Pit != null) {
            Pit.animateForGiveUp();

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void showEndGameDialog(FinishCodeEnum caseCode, byte winnerIndex) throws TError {
        // Input checking
        if(caseCode == null) {
            throw new TError("The case code property should not be null!");
        }
        if(winnerIndex < 1) {
            throw new TError("The winner index property should not be less than 1!");
        }

        if(!this.getDataInstance().isPlayerExists(winnerIndex)) {
            throw new TError("The requested player index " + winnerIndex + " does not exist!");
        }

        // Get the color of the winner
        String winnerColor = this.getDataInstance().getPlayer(winnerIndex).getPlayerColor();

        // Generate a visula effect
        RadialGradient playerColorFill = new RadialGradient(0, 0, 0.3, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
            new Stop(0, Color.web(winnerColor)),
            new Stop(1, Color.BLACK)
        });

        // Generate a hexagon shape with the color of winner player
        int centreX = 0;
        int centreY = 0;
        Double[] hexaCoordinates = {centreX + 30.0, 0.0 + centreY, centreX + 90.0, 0.0 + centreY, centreX + 120.0, 40.0 + centreY, centreX + 90.0, 80.0 + centreY, centreX + 30.0, 80.0 + centreY, centreX + 0.0, 40.0 + centreY};

        Polygon hexaShape = new Polygon();
        hexaShape.getPoints().addAll(hexaCoordinates);
        hexaShape.setFill(playerColorFill);
        hexaShape.setId("PlayerShape");
        hexaShape.setLayoutX(110);
        hexaShape.setLayoutY(120);

        // Content
        Text winnerText = new Text();
        Group contentRoot = new Group();

        contentRoot.getChildren().add(hexaShape);
        contentRoot.getChildren().add(winnerText);

        // Generate dialog
        Alert dialog = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.TRANSPARENT);

        DialogPane dialogPane = dialog.getDialogPane();
        //dialogPane.getStylesheets().add(getClass().getResource(ViewLayerIfc.CssDirectoryPath+"/GameFinishDialog.css").toExternalForm());
        dialogPane.setHeader(contentRoot);

        dialog.show();

        /*String ColorString = "#000000";

        //---Note:
        if(winnerIndex > 0) {
            ColorString = this.getDataInstance().getPlayer(winnerIndex).getPlayerColor();
            if(ColorString == null) {
                ColorString = "#000000";
            }
        }
        //---

        //---Note: A szöveges értesítés
        String contentText = "";
        if(caseCode == FinishCodeEnum.BoardFull) {
            contentText = "Mert nincs több szabad hely";
        }
        else if(caseCode == FinishCodeEnum.EnemyBlocked) {
            contentText = "Mert az ellenség minde bábuja blokkolva van";
        }
        else if(caseCode == FinishCodeEnum.EnemyFall) {
            contentText = "Mert az ellenségnek nincs több bábuja";
        }
        //---

        //---Note:
        boolean IsWinner = true;
        if(winnerIndex == 0) {
            IsWinner = false;

        }

        // Do animation on all pit when the board becomes visible
        byte[][] stands = this.getDataInstance().getCells();
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {
                if(stands[columnIndex][rowIndex] > 0) {
                    this.getPit(columnIndex, rowIndex).animateForFinish();
                }
            }
        }

        //((ViewLayerIfc) this.getParentComponent().getParentComponent()).showGameFinishDialog(IsWinner, ColorString, ContentText);
        //---
         */
    }

    /**
     * ******************************************************************************************************
     * ******************************************************************************************************
     * ******************************************************************************************************
     */
    /**
     * Instances of this class implements a graphical component on board.
     *
     * @author Sandor Kalmanchey
     * @version 2.0
     * @since 1.0
     */
    private static class BoardPit extends Polygon {

        /**
         * Stores horizontal position of component measured in pixels.
         */
        private int posX = 0;
        /**
         * Stores vertical position of component measured in pixels.
         */
        private int posY = 0;
        /**
         * Stores column index of represented cell.
         */
        private int columnIndex = -1;
        /**
         * Stores row index of represented cell.
         */
        private int rowIndex = -1;
        /**
         *
         */
        //private int visualStatus = 0;

        /**
         * Stores the parent graphical component which is the board.
         */
        private PlayBoard parent = null;
        /**
         * Stores whether the pit is active or not.
         */
        private boolean isInactive = false;

        /**
         * Stores design information object.
         */
        private static LinearGradient emptyBackgroundFill;
        /**
         * Stores design information object.
         */
        private static RadialGradient spreadCenterFill;
        /**
         * Stores colour object of pit which is not under animation.
         */
        private static Color normalPitBorderColor;
        /**
         * Stores colour object of pit which is under animation.
         */
        private static Color spreadPitBorderColor;
        /**
         * Stores colour object of pit which is under animation.
         */
        private static Color animatePitBorderColor;

        static {

            // 
            BoardPit.emptyBackgroundFill = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.rgb(150, 150, 150)),
                new Stop(1, Color.rgb(15, 15, 15))
            });

            // 
            BoardPit.spreadCenterFill = new RadialGradient(0, 0, 0.3, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.YELLOW),
                new Stop(1, Color.BLACK)
            });

            BoardPit.normalPitBorderColor = Color.BLACK;
            BoardPit.spreadPitBorderColor = Color.WHITE;
            BoardPit.animatePitBorderColor = Color.ORANGE;

        }

        /**
         * Creates a {@code BoardPit} instance.
         *
         * @param parent Parent graphical object to which it is attached.
         */
        private BoardPit(PlayBoard parent) {
            this.parent = parent;

        }

        /**
         * Creates a {@code BoardPit} instance.
         *
         * @param parent      Parent graphical object to which it is attached.
         * @param posX        Stores horizontal position of component measured in pixels.
         * @param posY        Vertical position of component measured in pixels.
         * @param columnIndex Column index of represented cell.
         * @param rowIndex    Row index of represented cell.
         */
        BoardPit(PlayBoard parent, int posX, int posY, int columnIndex, int rowIndex) {
            this.parent = parent;
            this.posX = posX;
            this.posY = posY;
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
            this.init();
        }

        /**
         * Method where sub component initialisation happens.
         */
        private void init() {

            //---Note:A poligin pontjainak megadása relatív koordinátákkal
            this.getPoints().addAll(new Double[]{
                this.posX + 15.0, 0.0 + this.posY, //1
                this.posX + 45.0, 0.0 + this.posY, //2
                this.posX + 60.0, 20.0 + this.posY, //3
                this.posX + 45.0, 40.0 + this.posY, //4
                this.posX + 15.0, 40.0 + this.posY, //5
                this.posX + 0.0, 20.0 + this.posY //6
            });
            //---

            //---Note:Formázás
            this.setFill(BoardPit.emptyBackgroundFill);
            this.setStrokeType(StrokeType.CENTERED);
            this.setStroke(BoardPit.normalPitBorderColor);   ////Color.rgb(88, 0, 88)
            this.setStrokeWidth(3);
            this.setStrokeLineJoin(StrokeLineJoin.ROUND);
            this.setOpacity(1);
            //---

            //---Note:Események
            final BoardPit pit = this;
            this.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent Evt) {

                    try {
                        pit.pitClicked();
                    }
                    catch(TError ex) {
                        logger.error(ex.toString());
                    }
                }
            });

            this.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent Evt) {

                }
            });

            this.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent Evt) {

                }
            });
            //---
        }

        /**
         * Occurs when the pit component is clicked.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        private void pitClicked() throws TError {
            this.parent.pitClicked(this.getColumnIndex(), this.getRowIndex());

        }

        /**
         * ????????
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        private void checkState() throws TError {
            IField Field = this.parent.getDataInstance().getCellAsField(this.columnIndex, this.rowIndex);

            //---Note:
            if(Field.isInactive())//Note: Ha auz adatmező inaktív cellát jelöl
            {
                this.setFill(null);
                this.setStrokeWidth(1);
            }
            else {
                //---Note: Felhasználó színének állítása
                if(Field.getPlayerIndex() != 0)//Note: Ha az adott cella egy játékos által foglalva van
                {
                    IPlayer Player = this.parent.getDataInstance().getPlayer(Field.getPlayerIndex());

                    String ColorString = Player.getPlayerColor();

                    if(ColorString == null) {
                        ColorString = "#000000";
                    }

                    RadialGradient PlayerColorFill = new RadialGradient(0, 0, 0.3, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                        new Stop(0, Color.web(ColorString)),
                        new Stop(1, Color.BLACK)
                    });

                    this.setFill(PlayerColorFill);

                }
                else//Note: Ha az adott cellát nem foglalja játékos
                {
                    this.setFill(BoardPit.emptyBackgroundFill);
                }
                //---

            }
            //---

        }

        /**
         * Displays animation when game starts.
         */
        private void animateForStart() {
            //---Note:
            ScaleTransition Scale = ScaleTransitionBuilder.create()
                    .node(this)
                    .duration(Duration.seconds(2))
                    .fromX(0.0)
                    .fromY(0.0)
                    .toX(1)
                    .toY(1)
                    .cycleCount(1)
                    .autoReverse(false)
                    .build();
            //---

            //---Note:
            Scale.play();
            //---

        }

        /**
         * Displays animation when game finishes.
         */
        private void animateForFinish() {
            StrokeTransition Anim01;

            //---Note: Animáció
            Anim01 = StrokeTransitionBuilder.create()
                    .duration(Duration.seconds(0.3))
                    .shape(this)
                    .fromValue(BoardPit.normalPitBorderColor)
                    .toValue(BoardPit.animatePitBorderColor)
                    .cycleCount(6)
                    .autoReverse(true)
                    .build();

            Anim01.play();
            //---

        }

        /**
         * Displays animation on pit as attack zone.
         */
        private void animateAsAttackZoneCell() {
            this.setStroke(BoardPit.spreadPitBorderColor);
        }

        /**
         * Displays animation on pit as centre of attack zone.
         */
        private void animateForSpreadAreaCenter() {
            this.setFill(this.spreadCenterFill);
        }

        /**
         * Removes attack zone animation of pit.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        private void removeSpreadArea() throws TError {
            this.setStroke(BoardPit.normalPitBorderColor);
            this.checkState();
        }

        /**
         * Animates pit as captured.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        private void animateForCapture() throws TError {

            StrokeTransition Anim01;

            //---Note: Animáció
            Anim01 = StrokeTransitionBuilder.create()
                    .duration(Duration.seconds(0.3))
                    .shape(this)
                    .fromValue(BoardPit.normalPitBorderColor)
                    .toValue(BoardPit.animatePitBorderColor)
                    .cycleCount(6)
                    .autoReverse(true)
                    .build();

            Anim01.play();
            //---

            //---Note:
            this.checkState();
            //---

        }

        /**
         * Displays animation when pit becomes unoccupied.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        private void animateForGiveUp() throws TError {
            StrokeTransition Anim01;

            //---Note: Animáció
            Anim01 = StrokeTransitionBuilder.create()
                    .duration(Duration.seconds(0.3))
                    .shape(this)
                    .fromValue(BoardPit.normalPitBorderColor)
                    .toValue(BoardPit.animatePitBorderColor)
                    .cycleCount(6)
                    .autoReverse(true)
                    .build();

            Anim01.play();
            //---

            //---Note:
            this.checkState();
            //---

        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) {
                return false;
            }
            if(getClass() != obj.getClass()) {
                return false;
            }
            final BoardPit other = (BoardPit) obj;
            /*if(!Objects.equals(this.columnIndex, other.columnIndex)) {
                return false;
            }
            if(!Objects.equals(this.rowIndex, other.rowIndex)) {
                return false;
            }*/

            if(this.columnIndex != other.columnIndex) {
                return false;
            }

            if(this.rowIndex != other.rowIndex) {
                return false;
            }
            return true;
        }

        /**
         * Retrieves the column index of referenced cell.
         *
         * @return An integer value 0 or higher.
         */
        private int getColumnIndex() {
            return columnIndex;
        }

        /**
         * Retrieves the row index of referenced cell.
         *
         * @return An integer value 0 or higher.
         */
        private int getRowIndex() {
            return rowIndex;
        }

        /**
         * Makes inactivate the pit.
         */
        private void inactivate() {
            this.isInactive = true;
            this.setFill(null);
        }

        /**
         * Tells whether the pit is inactive.
         *
         * @return True if pit is inactive otherwise false.
         */
        private boolean isInactive() {
            return isInactive;
        }

    }

    /**
     * Instances of this class represents coordinates storing x and y values.
     *
     * @author Sandor Kalmanchey
     * @version 1.0
     * @since 1.0
     */
    private static class Coordinate {

        /**
         * Stores the x coordinate.
         */
        private int x;
        /**
         * Stores the y coordinate.
         */
        private int y;

        /**
         * Creates a {@code Coordinate} instance.
         *
         * @param x The x coordinate.
         * @param y The y coordinate.
         */
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Retrieves the x coordinate.
         *
         * @return An integer value.
         */
        public int getX() {
            return x;
        }

        /**
         * Retrieves the y coordinate.
         *
         * @return An integer value.
         */
        public int getY() {
            return y;
        }

    }
}
