package Main;

import Window.IntroPage;
import Window.Container;
import Window.SettingsPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Support.TError;
import Window.IContainer;
import Window.IIntroPage;
import Window.IPlayPage;
import Window.ISettingsPage;
import Window.IStorage;
import Window.PlayPage;
import Window.Storage;
import Window.Storage.BoardModeItem;
import Window.IStorage.IBoardModeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The main class of the whole application.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public class Main extends Application {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * Relative path of folder of css files.
     */
    public static final String folderCSS = "/css";
    /**
     * Relative path of folder of image files.
     */
    public static final String folderImage = "/image";
    /**
     * Relative path of folder of audio files.
     */
    public static final String folderAudio = "/audio";

    /**
     * Starts the application launched by JavaFX framework.
     *
     * @param primaryStage JavaFX graphical node instance.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    @Override
    public void start(Stage primaryStage) throws TError {

        logger.info("Application is launched.");
        
        // Main graphical node
        StackPane root = new StackPane();
        root.setId("scene");
        root.getStylesheets().add(Main.class.getResource(Main.folderCSS + "/general.css").toExternalForm());

        // Pages
        IIntroPage introPage = new IntroPage(root);
        ISettingsPage settingsPage = new SettingsPage(root);
        IPlayPage playPage = new PlayPage(root);

        // Data
        IStorage storage = this.buildStorage();

        // Container
        IContainer container = new Container(root);
        container.setStorage(storage);
        container.setIntroPage(introPage);
        container.setSettingsPage(settingsPage);
        container.setPlayPage(playPage);

        container.showIntroPage();

        // Create stage
        Scene scene = new Scene(root, 900, 600);

        primaryStage.setTitle("Hexxagon 2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Builds the all necessary data of application data component.
     *
     * @return An instance of class implementing {@code IStorage} interface.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private IStorage buildStorage() throws TError {

        // Mode type
        IBoardModeItem mode2AI = new BoardModeItem(0);
        mode2AI.setCaption("2 AI player agains each other");
        mode2AI.setBoard(generateFieldFor2Player());
        mode2AI.addPlayer((byte) 1, "#008000", true);
        mode2AI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode1AI1Human = new BoardModeItem(1);
        mode1AI1Human.setCaption("1 Human player agains 1 AI player");
        mode1AI1Human.setBoard(generateFieldFor2Player());
        mode1AI1Human.addPlayer((byte) 1, "#008000", false);
        mode1AI1Human.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode2Human = new BoardModeItem(2);
        mode2Human.setCaption("2 Human player agains each other");
        mode2Human.setBoard(generateFieldFor2Player());
        mode2Human.addPlayer((byte) 1, "#008000", false);
        mode2Human.addPlayer((byte) 2, "#FF0000", false);

        IBoardModeItem mode3Human = new BoardModeItem(3);
        mode3Human.setCaption("3 Human player agains each other");
        mode3Human.setBoard(generateFieldFor3Player());
        mode3Human.addPlayer((byte) 1, "#008000", false);
        mode3Human.addPlayer((byte) 2, "#FF0000", false);
        mode3Human.addPlayer((byte) 3, "#ff6600", false);

        IBoardModeItem mode4Human = new BoardModeItem(4);
        mode4Human.setCaption("4 Human player agains each other");
        mode4Human.setBoard(generateFieldFor4Player());
        mode4Human.addPlayer((byte) 1, "#008000", false);
        mode4Human.addPlayer((byte) 2, "#FF0000", false);
        mode4Human.addPlayer((byte) 3, "#ff6600", false);
        mode4Human.addPlayer((byte) 4, "#aa6600", false);

        IStorage storage = new Storage();
        storage.addBoardMode(mode2AI);
        storage.addBoardMode(mode1AI1Human);
        storage.addBoardMode(mode2Human);
        storage.addBoardMode(mode3Human);
        storage.addBoardMode(mode4Human);
        storage.addDifficultyType(0, 1, "Level 1");
        storage.addDifficultyType(1, 2, "Level 2");
        storage.addDifficultyType(2, 3, "Level 3");
        storage.addDifficultyType(3, 4, "Level 4");
        return storage;
    }

    /**
     * Generates a game board shape for 2 players.
     *
     * @return A two dimensional byte array telling whether the cell is inactive in game or not
     */
    private byte[][] generateFieldFor2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 0, 0, 0, 0, 1},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 2, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {2, 0, 0, 0, 0, -1, -1, -1, -1}
        };
    }

    /**
     * Generates a game board shape for 2 players
     *
     * @return A two dimensional byte array telling whether the cell is inactive in game or not
     */
    /*private byte[][] generateFieldFor2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 1, 1, 1, 1, 1},
            {-1, -1, -1, 1, 1, 1, 1, 1, 1},
            {-1, -1, 1, 1, 1, 1, 0, 2, 1},
            {-1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, -1},
            {1, 1, 1, 1, 1, 1, 1, -1, -1},
            {1, 1, 1, 1, 1, 1, -1, -1, -1},
            {1, 1, 1, 1, 1, -1, -1, -1, -1}
        };
    }*/
    /**
     * Generates a game board shape for 3 players.
     *
     * @return A two dimensional byte array telling whether the cell is inactive in game or not
     */
    private byte[][] generateFieldFor3Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 0, 0, 0, 0, 1},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {2, 0, 0, 0, 0, -1, -1, -1, -1}
        };
    }

    /**
     * Generates a game board shape for 4 players.
     *
     * @return A two dimensional byte array telling whether the cell is inactive in game or not
     */
    private byte[][] generateFieldFor4Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 0, 0, 0, 0, 1},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 4, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {2, 0, 0, 0, 0, -1, -1, -1, -1}
        };
    }

    /**
     * The launch method.
     *
     * @param args input arguments.
     */
    public static void main(String[] args) {
        launch(args);

    }

}
