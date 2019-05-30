package Hexxagon.Model;

import Hexxagon.Controller.Player;
import Hexxagon.Model.IGameData.ProcessStatusCode;
import Hexxagon.Model.IGamePlay.StatusCode;
import Support.TError;
import Support.mvc.TModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import Hexxagon.Controller.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It is the Data Access Object of the Hexxagon module.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public class GameData extends TModel implements IGameData, Serializable {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(GameData.class);
    /**
     * Stores modification code of change in player list.
     */
    public static final String MODIFICATIONCODE_PLAYERLIST = "PlayerList";
    /**
     * Stores modification code of change in allowed player index.
     */
    public static final String MODIFICATIONCODE_ALLOWEDPLAYERINDEX = "AllowedPlayerIndex";
    /**
     * * Stores modification code of change in field list.
     */
    public static final String MODIFICATIONCODE_FIELDLIST = "FieldList";
    /**
     * Stores modification code of change in board shape.
     */
    public static final String MODIFICATIONCODE_BOARDSHAPE = "BoardShape";
    /**
     * Stores modification code of change in previously clicked field.
     */
    public static final String MODIFICATIONCODE_PREVIOUSLYCLICKEDFIELD = "PreviousClickedField";
    /**
     * Stores modification code of change in next allowed player.
     */
    public static final String MODIFICATIONCODE_FREEFORPLAYERINDEX = "FreeForPlayerIndex";
    /**
     * Stores modification code of change in is game finished.
     */
    public static final String MODIFICATIONCODE_ISFINISHED = "IsFinished";
    /**
     * Stores modification code of change in game play status.
     */
    public static final String MODIFICATIONCODE_GAMEPLAYSTATUS = "GamePlayStatus";
    /**
     * Stores modification code of change in game process status.
     */
    public static final String MODIFICATIONCODE_GAMEPROCESSSTATUS = "GameProcessStatus";
    /**
     * Stores modification code of change in actual game play.
     */
    public static final String MODIFICATIONCODE_ACTUALGAMEPLAY = "ActualGamePlay";
    /**
     * Stores modification code of change in defeated player.
     */
    public static final String MODIFICATIONCODE_PLAYERASDEFEATED = "PlayerAsDefeated";
    /**
     * Stores game save path.
     */
    public static final String GameSaveDirectoryPath = "Saved";

    /**
     * Stores the actual game play. This object can be write and read from file.
     */
    private IGamePlay actualGamePlay;

    /**
     * The state of the game
     */
    private ProcessStatusCode gameProcessStatus = ProcessStatusCode.NOTHING;

    /**
     * Creates a {@code GameData} instance.
     */
    public GameData() {
        logger.trace("GameData constructed!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellExists(int columnIndex, int rowIndex) {
        return this.actualGamePlay.isStandExists(columnIndex, rowIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IField getCellAsField(int columnIndex, int rowIndex) throws TError {
        return this.actualGamePlay.getField(columnIndex, rowIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte lowestPlayerIndex() throws TError {

        // Input checkings
        if(this.actualGamePlay == null) {
            throw new TError("No game play is set yet!");
        }
        ArrayList<IPlayer> list = this.actualGamePlay.getPlayerList();
        if(list == null) {
            throw new TError("No player list is set in actual game play!");
        }

        // Try to find the player
        for(IPlayer player : list) {
            if(player != null) {
                return (byte) (list.indexOf(player) + 1);
            }
        }
        throw new TError("No player in the list!");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isPlayerExists(byte playerIndex) {

        // Input checkings
        if(this.actualGamePlay == null) {
            return false;
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            return false;
        }

        // Find the player
        IPlayer player = new Player(playerIndex);
        if(this.actualGamePlay.getPlayerList().contains(player)) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IPlayer getPlayer(byte playerIndex) throws TError {
        // Input checkings
        if(this.actualGamePlay == null) {
            throw new TError("No game play is set yet!");
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            throw new TError("No player list is set in actual game play!");
        }

        IPlayer player = new Player(playerIndex);
        if(!this.actualGamePlay.getPlayerList().contains(player)) {
            throw new TError("Plyer not found by playerIndex:" + playerIndex);
        }

        // Find value
        int indx = this.actualGamePlay.getPlayerList().indexOf(player);
        return this.actualGamePlay.getPlayerList().get(indx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerAsDefeated(byte playerIndex) throws TError {
        // Input checkings
        if(this.actualGamePlay == null) {
            throw new TError("No game play is set yet!");
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            throw new TError("No player list is set in actual game play!");
        }
        if(!this.actualGamePlay.isPlayerExists(playerIndex)) {
            throw new TError("No player exists at playerindex '" + playerIndex + "' ");
        }

        // Store value
        IPlayer player = this.actualGamePlay.getPlayerList().get(playerIndex - 1);
        player.setIsDefeated(true);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_PLAYERASDEFEATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerDefeated(byte playerIndex) throws TError {
        // Input checkings
        if(this.actualGamePlay == null) {
            throw new TError("No game play is set yet!");
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            throw new TError("No player list is set in actual game play!");
        }
        if(!this.actualGamePlay.isPlayerExists(playerIndex)) {
            throw new TError("No player exists at playerindex '" + playerIndex + "' ");
        }
        // Store value
        IPlayer player = this.actualGamePlay.getPlayerList().get(playerIndex - 1);
        return player.isDefeated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPlayerBallCount(byte playerIndex) throws TError {
        // Input checkings
        if(this.actualGamePlay == null) {
            throw new TError("No game play is set yet!");
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            throw new TError("No player list is set in actual game play!");
        }
        if(!this.actualGamePlay.isPlayerExists(playerIndex)) {
            throw new TError("No player exists at playerindex '" + playerIndex + "' ");
        }

        // Determine count
        int count = 0;
        byte[][] stands = this.actualGamePlay.getStands();
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {
                if(stands[columnIndex][rowIndex] == playerIndex) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ArrayList<IPlayer> getPlayerList() {
        if(this.actualGamePlay == null) {
            return null;
        }
        if(this.actualGamePlay.getPlayerList() == null) {
            return null;
        }

        // Get list
        return this.actualGamePlay.getPlayerList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte getAllowedPlayerIndex() {
        return this.actualGamePlay.getAllowedPlayerIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAllowedPlayerIndex(byte allowedPlayerIndex) throws TError {
        // Input checkings
        IPlayer Player = new Player(allowedPlayerIndex);
        if(!this.actualGamePlay.getPlayerList().contains(Player)) {
            throw new TError("Player not found");
        }

        // Store value
        this.actualGamePlay.setAllowedPlayerIndex(allowedPlayerIndex);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_ALLOWEDPLAYERINDEX);
    }

    /**
     * {@inheritDoc}
     */
    /* @Override
    public final ArrayList<IField> getFieldList() {
        return this.actualGamePlay.getFieldList();
    }*/
    /**
     * {@inheritDoc}
     */
    @Override
    public final byte[][] getCells() {
        return this.actualGamePlay.getStands();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte getCell(int columnIndex, int rowIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        byte[][] stands = this.actualGamePlay.getStands();

        return stands[columnIndex][rowIndex];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isCellInactive(int columnIndex, int rowIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        byte[][] stands = this.actualGamePlay.getStands();

        if(stands[columnIndex][rowIndex] == -1) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isCellOccupied(int columnIndex, int rowIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        byte[][] stands = this.actualGamePlay.getStands();

        if(stands[columnIndex][rowIndex] != -1 && stands[columnIndex][rowIndex] != 0) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCellOccupiedByPlayer(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        if(!this.actualGamePlay.isPlayerExists(playerIndex)) {
            throw new TError("Player was not found");
        }

        if(this.isCellInactive(columnIndex, rowIndex)) {
            throw new TError("The cell is inactive");
        }

        // Store value
        byte[][] stands = this.actualGamePlay.getStands();
        stands[columnIndex][rowIndex] = playerIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setCellLiberated(int columnIndex, int rowIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        if(this.isCellInactive(columnIndex, rowIndex)) {
            throw new TError("The cell is inactive");
        }

        // Store value
        byte[][] stands = this.actualGamePlay.getStands();
        stands[columnIndex][rowIndex] = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setBoardShapeOfActualGamePlay(byte[][] boardShape) throws TError {
        // Input checkings
        this.actualGamePlay.testAndThrowIfStandsInvalid(boardShape);

        // Store value
        this.actualGamePlay.setBoard(boardShape);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_BOARDSHAPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IField getPreviousClickedField() {
        return this.actualGamePlay.getPreviouslyClickedField();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPreviousClickedField(IField previousClickedField) throws TError {
        // Input checkings
        if(previousClickedField == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        this.actualGamePlay.setPreviouslyClickedField(previousClickedField);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_PREVIOUSLYCLICKEDFIELD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void removeLastClickedField() {
        // Store value
        this.actualGamePlay.setPreviouslyClickedField(null);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_PREVIOUSLYCLICKEDFIELD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isBoardFull() {
        byte[][] stands = this.actualGamePlay.getStands();
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {
                if(stands[columnIndex][rowIndex] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int defeatedPlayerCount() {
        int count = 0;

        for(IPlayer player : this.actualGamePlay.getPlayerList()) {
            if(player != null) {
                if(player.isDefeated()) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int playerCount() {
        return this.actualGamePlay.getPlayerList().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[][] getPlayerPositions(byte playerIndex) throws TError {

        // Input checkings
        if(!this.actualGamePlay.isPlayerExists(playerIndex)) {
            throw new TError("Player not found");
        }

        // Determine the size and create the return array
        int indexCount = (IGamePlay.maximumExpectedStandRowIndex + 1) * (IGamePlay.maximumExpectedStandColumnIndex + 1);
        int[][] output = new int[indexCount][2];

        // Set default values
        for(int i = 0; i <= (indexCount - 1); i++) {
            output[i][0] = 0;    // The column index
            output[i][1] = 0;    // The row index
        }

        // Overwrite where the specified player occupied the field
        int i = 0;
        byte[][] stands = this.actualGamePlay.getStands();
        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {
                if(stands[columnIndex][rowIndex] == playerIndex) {
                    output[i][0] = columnIndex;
                    output[i][1] = rowIndex;
                }
                i++;
            }
        }
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyActualGamePlay() {

        // Store value
        this.actualGamePlay = null;

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_ACTUALGAMEPLAY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGamePlay(String fileName) throws TError {
        String fullPath = GameSaveDirectoryPath + "/" + fileName;
        File loadDir = new File(fullPath);

        // If the load invocked when the playing is on
        if((this.gameProcessStatus != ProcessStatusCode.INITIALISED)
                && (this.gameProcessStatus != ProcessStatusCode.PREPARED)
                && (this.gameProcessStatus != ProcessStatusCode.STOPPED)) {
            throw new TError("Gameplay loading is not allowed when the game is on!");
        }

        // If file is not exists
        if(!loadDir.exists()) {
            throw new TError();

        }

        // Open and read the serialise file
        try(FileInputStream fileStream = new FileInputStream(fullPath)) {

            try(ObjectInputStream objectStream = new ObjectInputStream(fileStream)) {

                // read the object from file
                this.actualGamePlay = (IGamePlay) objectStream.readObject();

                // Determine the status 
                StatusCode playStatus = this.actualGamePlay.getStatus();
                this.gameProcessStatus = ProcessStatusCode.STOPPED;  // The default 
                if(playStatus == StatusCode.NotFinished) {          // Overwrite
                    this.gameProcessStatus = ProcessStatusCode.PAUSED;
                }

                // Close the stream
                objectStream.close();
            }
            catch(IOException | ClassNotFoundException exp) {
                throw new TError("Error occured when tried to read serialise file!");
            }

            // Close steram
            fileStream.close();

            //???????
            //this.GameInfoBoard.refresh();
            //this.GamePlayBoard.refresh();
        }
        catch(FileNotFoundException ex) {
            throw new TError("Requested file was not found!");
        }
        catch(IOException ex) {
            throw new TError("I/O error occured!");
        }

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_ACTUALGAMEPLAY);

        // ???????
        this.gameProcessStatus = ProcessStatusCode.PREPARED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void saveGamePlay(String fileName) throws TError {
        // If the load invocked when the playing is on
        if((this.gameProcessStatus != ProcessStatusCode.STOPPED) && (this.gameProcessStatus != ProcessStatusCode.PREPARED) && (this.gameProcessStatus != ProcessStatusCode.PAUSED)) {
            throw new TError("Gameplay saving is not allowed when the game is on!");
        }

        // Attempt to create the the folder of save if not exists
        File saveDir = new File(GameSaveDirectoryPath);
        if(!saveDir.exists()) {
            saveDir.mkdirs();
        }

        // If folder does not exists
        if(!saveDir.exists()) {
            throw new TError("Destination folder is not found!");
        }

        // Generate file name    
        fileName = GameSaveDirectoryPath + "/" + fileName + ".dmp";

        // Open and write content into file 
        try(FileOutputStream fileStream = new FileOutputStream(fileName)) {
            try(ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
                objectStream.writeObject(this.actualGamePlay);
                objectStream.flush();
                objectStream.close();
            }

            // Close stream
            fileStream.close();
        }
        catch(FileNotFoundException ex) {
            throw new TError("Destination file was not found!");
        }
        catch(IOException ex) {
            throw new TError("I/O error occured!");
        }
    }

    /**
     * Retrieves the actual game play object.
     *
     * @return An {@code IGamePlay} instance.
     */
    private final IGamePlay getActualGamePlay() {
        return actualGamePlay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setActualGamePlay(IGamePlay ActualGamePlay) throws TError {

        // If it is invocked when the playing is on
        if((this.gameProcessStatus != ProcessStatusCode.INITIALISED) && (this.gameProcessStatus != ProcessStatusCode.PREPARED) && (this.gameProcessStatus != ProcessStatusCode.STOPPED)) {
            //????? throw new TError("The setting of gameplay is not allowed when the game is on!");
        }

        // Save values
        this.actualGamePlay = ActualGamePlay;
        this.gameProcessStatus = ProcessStatusCode.PREPARED;

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_ACTUALGAMEPLAY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameFinished() {
        return this.actualGamePlay.isFinished();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsGameFinished(boolean isFinished) {

        // Store value
        this.actualGamePlay.setIsFinished(isFinished);

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_ISFINISHED);
    }

    /**
     * Egy {@code IGamePlay} interfacenak megfelelő objektumot ad vissza.
     * <p>
     * A metódus arra szolgál, hogy az adott rétegbe nem tartozó osztályok és
     * objektumok is kaphassanak ilyen interfacet implementáló objektumot.
     * Függetlenül attól, hogy ismernék az adott rétegben az adott interfacet
     * implementáló osztályt.
     * </p>
     *
     * @param boardShape A játéktábala formáját rprezentáló kétdimenziós int
     *                   tömb. A tömb csak 0-kat és 1-ket tartalmazhat!
     *
     * @return Egy {@code IGamePlay} interfacenak megfelelő objektum.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private static final IGamePlay fabricateGamePlay(byte[][] boardShape) throws TError {
        // Get an instance
        //return new GamePlay(boardShape);
        return null;
    }

    /**
     * Egy @link{IGamePlay} interfacenak megfelelő objektumot ad vissza.
     * <p>
     * A metódus arra szolgál, hogy az adott rétegbe nem tartozó osztályok és
     * objektumok is kaphassanak ilyen interfacet implementáló objektumot.
     * Függetlenül attól, hogy ismernék az adott rétegben az adott interfacet
     * implementáló osztályt.
     * </p>
     *
     * @return Egy IGamePlay interfacenak megfelelő objektum.
     */
    public static final IGamePlay fabricateIGamePlay() {
        return new GamePlay();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameProcessStatus(ProcessStatusCode status) throws TError {
        // Input checkings
        if(status == null) {
            throw new TError("The parameter should not be null!");
        }

        // Store value
        this.gameProcessStatus = status;

        // Announce property modification
        this.firePropertyChange(MODIFICATIONCODE_GAMEPROCESSSTATUS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessStatusCode getGameProcessStatus() {
        return this.gameProcessStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusCode getStatus() {
        return this.actualGamePlay.getStatus();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGamePlayStatus(StatusCode newStatus) {
        this.actualGamePlay.setGamePlayStatus(newStatus);
    }

}
