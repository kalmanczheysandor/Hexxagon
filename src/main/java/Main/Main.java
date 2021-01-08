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
import Window.IInfoPage;
import Window.IIntroPage;
import Window.IPlayPage;
import Window.ISettingsPage;
import Window.IStorage;
import Window.PlayPage;
import Window.Storage;
import Window.Storage.BoardModeItem;
import Window.IStorage.IBoardModeItem;
import Window.InfoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class of the whole application.
 * ide a netbeans jon
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
        IInfoPage infoPage = new InfoPage(root);
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
        container.setInfoPage(infoPage);

        container.showIntroPage();

        // Create stage
        Scene scene = new Scene(root, 750, 600);

        primaryStage.setTitle("Hexxagon 2.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
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
        IBoardModeItem mode101_HumanAndAI = new BoardModeItem(0);
        mode101_HumanAndAI.setCaption("Board 1 - Human agains AI ");
        mode101_HumanAndAI.setBoard(board01For2Player());
        mode101_HumanAndAI.addPlayer((byte) 1, "#008000", false);
        mode101_HumanAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode102_HumanAndAI = new BoardModeItem(1);
        mode102_HumanAndAI.setCaption("Board 2 - Human agains AI ");
        mode102_HumanAndAI.setBoard(board02For2Player());
        mode102_HumanAndAI.addPlayer((byte) 1, "#008000", false);
        mode102_HumanAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode103_HumanAndAI = new BoardModeItem(2);
        mode103_HumanAndAI.setCaption("Board 3 - Human agains AI ");
        mode103_HumanAndAI.setBoard(board03For2Player());
        mode103_HumanAndAI.addPlayer((byte) 1, "#008000", false);
        mode103_HumanAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode101_AIAndAI = new BoardModeItem(3);
        mode101_AIAndAI.setCaption("Board 1 - AI against AI");
        mode101_AIAndAI.setBoard(board01For2Player());
        mode101_AIAndAI.addPlayer((byte) 1, "#008000", true);
        mode101_AIAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode102_AIAndAI = new BoardModeItem(4);
        mode102_AIAndAI.setCaption("Board 2 - AI against AI");
        mode102_AIAndAI.setBoard(board02For2Player());
        mode102_AIAndAI.addPlayer((byte) 1, "#008000", true);
        mode102_AIAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode103_AIAndAI = new BoardModeItem(5);
        mode103_AIAndAI.setCaption("Board 3 - AI against AI");
        mode103_AIAndAI.setBoard(board03For2Player());
        mode103_AIAndAI.addPlayer((byte) 1, "#008000", true);
        mode103_AIAndAI.addPlayer((byte) 2, "#FF0000", true);

        IBoardModeItem mode101_HumanAndHuman = new BoardModeItem(6);
        mode101_HumanAndHuman.setCaption("Board 1 - Human against Human");
        mode101_HumanAndHuman.setBoard(board01For2Player());
        mode101_HumanAndHuman.addPlayer((byte) 1, "#008000", false);
        mode101_HumanAndHuman.addPlayer((byte) 2, "#FF0000", false);

        IBoardModeItem mode102_HumanAndHuman = new BoardModeItem(7);
        mode102_HumanAndHuman.setCaption("Board 2 - Human against Human");
        mode102_HumanAndHuman.setBoard(board02For2Player());
        mode102_HumanAndHuman.addPlayer((byte) 1, "#008000", false);
        mode102_HumanAndHuman.addPlayer((byte) 2, "#FF0000", false);

        IBoardModeItem mode103_HumanAndHuman = new BoardModeItem(8);
        mode103_HumanAndHuman.setCaption("Board 3 - Human against Human");
        mode103_HumanAndHuman.setBoard(board03For2Player());
        mode103_HumanAndHuman.addPlayer((byte) 1, "#008000", false);
        mode103_HumanAndHuman.addPlayer((byte) 2, "#FF0000", false);

        /*
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
         */
        IStorage storage = new Storage();
        storage.addBoardMode(mode101_HumanAndAI);
        storage.addBoardMode(mode102_HumanAndAI);
        storage.addBoardMode(mode103_HumanAndAI);
        storage.addBoardMode(mode101_AIAndAI);
        storage.addBoardMode(mode102_AIAndAI);
        storage.addBoardMode(mode103_AIAndAI);
        storage.addBoardMode(mode101_HumanAndHuman);
        storage.addBoardMode(mode102_HumanAndHuman);
        storage.addBoardMode(mode103_HumanAndHuman);
        storage.addDifficultyType(0, 1, "Sandbox");
        storage.addDifficultyType(1, 2, "Beginner");
        storage.addDifficultyType(2, 3, "Moderate");
        storage.addDifficultyType(3, 4, "Hard");
        storage.addDifficultyType(4, 5, "Expert");

        return storage;
    }

    /**
     * Generates a game board shape for 2 players.
     *
     * @return A two dimensional byte array telling whether the cell is inactive in game or not
     */
    /*private byte[][] generateFieldFor2Player() {
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
    }*/

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
    private byte[][] board01For2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 1, 0, 0, 0, 2},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, -1, 0, 0, 0},
            {2, 0, 0, -1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, -1, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {1, 0, 0, 0, 2, -1, -1, -1, -1}
        };
    }

    private byte[][] board02For2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 1, 0, 0, 0, 2},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, -1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {1, 0, 0, 0, 2, -1, -1, -1, -1}
        };
    }

    private byte[][] board03For2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 1, 0, 0, 0, 2},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, -1, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, -1, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, -1, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, -1, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {1, 0, 0, 0, 2, -1, -1, -1, -1}
        };
    }

    /*private byte[][] generateFieldFor2Player() {
        return new byte[][]{
            {-1, -1, -1, -1, 1, 0, 0, 0, 2},
            {-1, -1, -1, 0, 0, 0, 0, 0, 0},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1},
            {1, 0, 0, 0, 2, -1, -1, -1, -1}
        };
    }*/
 /*
    private byte[][] generateFieldFor2Player() {
        return new byte[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 0}
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
