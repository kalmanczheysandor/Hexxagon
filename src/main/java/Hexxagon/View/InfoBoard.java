package Hexxagon.View;

import Hexxagon.Model.GameData;
import Support.TError;
import Support.mvc.TView;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.IGameData;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instance of this class realises an info panel of game as a JavaFX specific graphical node.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public final class InfoBoard extends TView implements IInfoBoard {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(InfoBoard.class);
    /**
     * Stores local graphical root node.
     */
    private VBox rootNode = new VBox(1);

    /**
     * Stores a list of payer rows which are represents the colour and ball count of the specified player.
     */
    private ArrayList<PlayerRow> itemList = new ArrayList<>();

    /**
     * Creates an instance of {@code InfoBoard}.
     *
     * @param root The JavFX graphical node to to where graphical nodes instantiated inside in this class are attached
     */
    public InfoBoard(Pane root) {
        super(root);
        logger.trace("InfoBoard constructed!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) throws TError {
      logger.debug("***********property change**************GAMEINFO");
        this.maintain();
    }

    /**
     * Creates the board.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private final void createBoard() throws TError {

        // Clean the list
        this.removeNotExsistingPlayers();

        // Adding players
        if(this.rootNode.getChildren().size() == 0) {

            ArrayList<IPlayer> playerList = this.getDataInstance().getPlayerList();
            for(IPlayer player : playerList) {
                if(player != null) {
                    byte playerIndex = player.getPlayerIndex();
                    if(!this.isPlayerIndexInList(playerIndex)) {    // if it is not in the list
                        String playerColor = player.getPlayerColor();
                        boolean isAIPlayer = player.isAutoPlayer();

                        // Create an instance and store
                        PlayerRow row = new PlayerRow(playerIndex, playerColor, isAIPlayer);
                        if(this.itemList.add(row)) {
                            this.addToRootNode(row);
                        }
                    }
                }
            }
        }
        this.maintain();

        //
        // this.checkActivePlayers();
        // this.checkAllowedPlayer();
        // this.checkPlayersScore();
        // this.checkDefeat();
    }

    /**
     * Refreshes data.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void maintain() throws TError {
        for(PlayerRow item : this.itemList) {
            byte playerIndex = item.getPlayerIndex();
            if(this.getDataInstance().isPlayerExists(playerIndex)) {
                int score = this.getDataInstance().getPlayerBallCount(playerIndex);

                item.setScore(score);

                if(this.getDataInstance().getAllowedPlayerIndex() == playerIndex) {
                    item.animateAsActualPlayer();
                }
                else {
                    item.animateAsNotActualPlayer();
                }
            }
            else {  // If player not found in data layer
                this.rootNode.getChildren().remove(item);   // remove the row of player
            }
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
     * Tells whether the index exists or not.
     *
     * @param playerIndex The index to be found in the list.
     *
     * @return True if player index is in the list, false otherwise.
     */
    private boolean isPlayerIndexInList(byte playerIndex) {
        PlayerRow instance = new PlayerRow(playerIndex);
        if(this.itemList.contains(instance)) {
            return true;
        }
        return false;
    }

    /**
     * Removes all player representations who are not exist anymore.
     */
    private void removeNotExsistingPlayers() {

        for(PlayerRow row : this.itemList) {
            byte playerIndex = row.getPlayerIndex();
            if(!this.getDataInstance().isPlayerExists(playerIndex)) {
                int indx = this.itemList.indexOf(row);
                this.itemList.remove(indx);
            }
        }
    }

    /**
     * Adds a JavaFx specific graphical node to the root node.
     *
     * @param Node The JavaFx specific node to add.
     */
    private final void addToRootNode(Node Node) {
        this.rootNode.getChildren().add(Node);
    }

    /**
     * Removes a JavaFx specific node from the root node.
     *
     * @param Node The JavaFx specific node to remove.
     */
    private final void removeToRootNode(Node Node) {
        this.rootNode.getChildren().remove(Node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkChanges() throws TError {
        this.refresh();

    }

    /**
     * It returns the object as instance of the implementation class and not like {@code IModel}.
     *
     * @return An {@code IGameData} instance.
     */
    private IGameData getDataInstance() {
        return (IGameData) super.getController().getModel();
    }

    /**
     * Gives back the general graphical root node as actual graph framework specific.
     *
     * @return An {@code Pane} instance.
     */
    private Pane getUIRoot() {
        return (Pane) super.getGraphicalRoot();
    }

    /**
     * Visual row class.
     * Any instance of this class represents an player participating in the game
     */
    private static class PlayerRow extends Pane {

        /**
         * Stores the width of the avatar node.
         */
        private static final int avatarNodeWidth = 30;
        /**
         * Stores the height of the avatar node.
         */
        private static final int avatarNodeHeight = 20;
        /**
         * Stores the width of the glass row node.
         */
        private static final int glassRowWidth = 120;
        /**
         * Stores the height of the glass row node.
         */
        private static final int glassRowHeight = 30;
        /**
         * Stores the arc of the avatar node.
         */
        private static final int arc = 30;
        /**
         * Stores normal gradient style. Used when player is not to step.
         */
        private static final LinearGradient normalBackground;
        /**
         * Stores light gradient style. Used when player is to step.
         */
        private static final LinearGradient lightBackground;
        /**
         * Stores defeated gradient style. Used when player is defeated.
         */
        private static final LinearGradient defeatedPlayerColor;

        static {
            //
            normalBackground = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.rgb(0, 0, 0)),
                new Stop(1, Color.rgb(0, 0, 0))
            });

            //
            lightBackground = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.rgb(250, 250, 250)),
                new Stop(1, Color.rgb(0, 0, 0))
            });

            // 
            defeatedPlayerColor = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.rgb(50, 50, 50)),
                new Stop(1, Color.rgb(0, 0, 0))
            });

        }

        /**
         * Stores index of player represented in the graphical row.
         */
        private byte playerIndex;
        /**
         * Stores text node of player score represented in the graphical row.
         */
        private Text scoreNode;
        /**
         * Stores avatar node of player represented in the graphical row.
         */
        private Circle avatarNode;
        /**
         * Stores glass row graphical node.
         */
        private Rectangle glassNode;
        /**
         * Stores actual score of player represented in the graphical row.
         */
        private int score = 0;
        /**
         * Stores colour code of player.
         */
        private String playerColor;
        /**
         * Stores whether the player is artificial or not.
         */
        private boolean isAIPlayer;

        /**
         * Creates an {@code PlayerRow} instance.
         *
         * @param playerIndex Unique index of a player.
         */
        private PlayerRow(byte playerIndex) {
            this.playerIndex = playerIndex;
        }

        /**
         * Creates an {@code PlayerRow} instance.
         *
         * @param playerIndex Unique index of a player.
         * @param playerColor Colour code of the player.
         * @param isAIPlayer  True if the player is artificial, false otherwise.
         */
        private PlayerRow(byte playerIndex, String playerColor, boolean isAIPlayer) {

            // Create glass node
            this.glassNode = new Rectangle(PlayerRow.glassRowWidth, PlayerRow.glassRowHeight);
            this.glassNode.setFill(normalBackground);
            this.glassNode.setOpacity(0.5);
            this.glassNode.setLayoutX(15);
            this.glassNode.setLayoutY(0);
            this.glassNode.setArcHeight(arc);
            this.glassNode.setArcWidth(arc);
            this.glassNode.setStrokeType(StrokeType.CENTERED);
            this.glassNode.setStroke(Color.TRANSPARENT);
            this.glassNode.setStrokeWidth(3);

            //
            RadialGradient avatar = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(1, Color.BLACK),
                new Stop(0, Color.web(playerColor))

            });

            // Create avatar node
            this.avatarNode = new Circle(0, 0, 13, avatar);

            // Create score node
            this.scoreNode = new Text();
            this.scoreNode.setFont(Font.font("Bauhaus 93", 14));
            this.scoreNode.setFill(Color.YELLOW);
            this.scoreNode.setText("??");
            this.scoreNode.setTextAlignment(TextAlignment.CENTER);

            StackPane ppp = new StackPane();
            ppp.setLayoutX(17);
            ppp.setLayoutY(2);
            ppp.getChildren().add(this.avatarNode);
            ppp.getChildren().add(this.scoreNode);

            Text name = new Text();
            name.setFont(Font.font("Bauhaus 93", 13));
            name.setFill(Color.WHITE);
            name.setTextAlignment(TextAlignment.CENTER);
            name.setLayoutX(50);
            name.setLayoutY(20);
            if(isAIPlayer) {
                name.setText("Player " + playerIndex + " (AI)");
            }
            else {
                name.setText("Player " + playerIndex);
            }

            // Attaching
            this.getChildren().add(this.glassNode);
            this.getChildren().add(ppp);
            this.getChildren().add(name);

            // Store values
            this.playerIndex = playerIndex;
            this.playerColor = playerColor;
            this.isAIPlayer = isAIPlayer;
        }

        /**
         * Animates the graphical node as it shows the actual player.
         */
        public void animateAsActualPlayer() {
            this.glassNode.setStroke(Color.YELLOW);
        }

        /**
         * Animates the graphical node as it shows not the actual player.
         */
        public void animateAsNotActualPlayer() {
            this.glassNode.setStroke(Color.TRANSPARENT);
        }

        /**
         * Sets the score value.
         *
         * @param score An int value 0 or higher.
         */
        public void setScore(int score) {
            this.score = score;
            this.scoreNode.setText(String.valueOf(score));

        }

        /**
         * Retrieves the player index.
         *
         * @return An int value 1 or higher.
         */
        public byte getPlayerIndex() {
            return playerIndex;
        }

        /**
         * Sets the player index.
         *
         * @param playerIndex An int value 1 or higher.
         */
        public void setPlayerIndex(byte playerIndex) {
            this.playerIndex = playerIndex;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            if(obj == null) {
                return false;
            }
            if(getClass() != obj.getClass()) {
                return false;
            }
            final PlayerRow other = (PlayerRow) obj;
            if(this.playerIndex != other.playerIndex) {
                return false;
            }
            return true;
        }

    }
}
