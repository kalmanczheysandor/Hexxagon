package Window;

import Hexxagon.Controller.GameController;
import Hexxagon.Controller.IAIPlayer;
import Hexxagon.Controller.IGameController;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.GameData;
import Hexxagon.Model.GamePlay;
import Hexxagon.Model.IGameData;
import Hexxagon.Model.IGamePlay;
import Hexxagon.View.IFaceBoard;
import Hexxagon.View.IInfoBoard;
import Hexxagon.View.IPlayBoard;
import Support.TArray;
import Support.TError;
import Support.mvc.IModel;
import Support.navigator.IPage;
import Support.navigator.TNavigator;
import Window.IStorage.IBoardModeItem;
import Window.IStorage.IBoardModeItem.IPlayerRow;
import java.util.Arrays;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the implementation of application controller component specified by {@code IContainer} interface.
 * The class extends the {@code TNavigator} general purpose abstract navigator class.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class Container extends TNavigator implements IContainer {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Container.class);
    /**
     * Stores unique index value for intro page.
     */
    public static final int PAGE_INTRO = 0;
    /**
     * Stores unique index value for settings page.
     */
    public static final int PAGE_SETTINGS = 1;
    /**
     * Stores unique index value for play page.
     */
    public static final int PAGE_PLAY = 2;
    /**
     * Stores unique index value for intro page.
     */
    public static final int PAGE_INFO = 3;
    /**
     * Object reference to the info page.
     */
    private IInfoPage infoPage;
    /**
     * Object reference to the intro page.
     */
    private IIntroPage introPage;
    /**
     * Object reference to the settings page.
     */
    private ISettingsPage settingsPage;
    /**
     * Object reference to the play page.
     */
    private IPlayPage playPage;

    /**
     * Object reference to the application data component.
     */
    private IStorage storage;
    /**
     * Object reference to the controller component of the Hexxagon module.
     */
    private IGameController gameController;

    /**
     * Creates a {@code Container} instance.
     *
     * @param rootNode Unknown
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public Container(Pane rootNode) throws TError {
        super(rootNode);

        // Input checking
        if(rootNode == null) {
            throw new TError("Parameter must not be null!");
        }

        //
        this.gameController = new GameController();

        logger.trace("Container constructed!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStorage(IStorage storage) throws TError {
        // Input checking
        if(storage == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.storage = storage;
        this.setModel((IModel) storage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStorage getStorage() {
        return storage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInfoPage(IInfoPage page) throws TError {
        // Input checking
        if(page == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.infoPage = page;
        this.addPage((IPage) page);                 // Add to the navigator
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntroPage(IIntroPage page) throws TError {
        // Input checking
        if(page == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.introPage = page;
        this.addPage((IPage) page);                 // Add to the navigator
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettingsPage(ISettingsPage page) throws TError {
        // Input checking
        if(page == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.settingsPage = page;
        this.addPage((IPage) page);                 // Add to the navigator

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayPage(IPlayPage page) throws TError {
        // Input checking
        if(page == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.playPage = page;

        this.addPage((IPage) page);                 // Add to the navigator

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IInfoPage getInfoPage() {
        return infoPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IIntroPage getIntroPage() {
        return introPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISettingsPage getSettingsPage() {
        return settingsPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlayPage getPlayPage() {
        return playPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInfoPage() throws TError {
        this.showAPage(PAGE_INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showIntroPage() throws TError {
        this.showAPage(PAGE_INTRO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showSettingsPage() throws TError {
        this.showAPage(PAGE_SETTINGS);
    }

    @Override
    public void showPlayPage() throws TError {
        this.showAPage(PAGE_PLAY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectDifficulty(int fightId) throws TError {
        this.getStorage().setSelectedDifficultyIndex(fightId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectBoardMode(int boardId) throws TError {
        this.getStorage().setSelectedBoardIndex(boardId);
    }

    /**
     * Generates a game play component.
     *
     * @return Generated component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private IGamePlay buildGamePlay() throws TError {

        IGamePlay gamePlay = new GamePlay();
        int boardKey = this.getStorage().getSelectedBoardIndex();

        // Adding players
        IBoardModeItem boardMode = this.getStorage().getBoardMode(boardKey);
        for(IPlayerRow playerItem : boardMode.getPlayerList()) {
            IPlayer playerObj;
            if(!playerItem.isAuto()) {
                playerObj = GameController.fabricateHumanPlayer(playerItem.getColor());
            }
            else {
                int difficultyKey = this.getStorage().getSelectedDifficultyIndex();
                int difficultyValue = this.getStorage().getDifficultyValue(difficultyKey);
                playerObj = GameController.fabricateAutoPlayer(playerItem.getColor());
                ((IAIPlayer) playerObj).setDeepness(difficultyValue);

            }
            gamePlay.addPlayer(playerObj, playerItem.getIndex());
        }

        // Set game field
        byte[][] board = TArray.copy2D(boardMode.getBoard());
        gamePlay.setBoard(board);

        return gamePlay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() throws TError {
        IPlayBoard playBoard;
        IInfoBoard infoBoard;
        IFaceBoard faceBoard;
        IPlayPage playPage;
        IGameData gameData;

        // Test whether the play page is set
        if(this.getPlayPage() != null) {
            playPage = this.getPlayPage();
        }
        else {
            throw new TError("The play page is not set yet!");
        }

        // Gaining references
        playBoard = playPage.getPlayBoard();
        infoBoard = playPage.getInfoBoard();
        faceBoard = playPage.getFaceBoard();
        gameData = this.getStorage().getGameData();

        // Initialisation  of the main components of game
        gameData.setActualGamePlay(buildGamePlay());

        //
        this.gameController.setGameData(gameData);
        this.gameController.addInfoBoard(infoBoard);
        this.gameController.addPlayBoard(playBoard);
        this.gameController.addFaceBoard(faceBoard);

        this.gameController.startGame();
        this.showPlayPage();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopGame() throws TError {
        this.gameController.stopGame();
        this.showSettingsPage();
    }
}
