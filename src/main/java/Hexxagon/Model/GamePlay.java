package Hexxagon.Model;

import Hexxagon.Model.IGamePlay.StatusCode;
import Support.TError;
import java.util.ArrayList;
import java.util.Objects;
import Hexxagon.Controller.IPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It stores the actual state of the game board
 *
 * @author Sandor Kalmanchey
 * @version 2.0 (1.0)
 * @since 2015-05-20 (1.0)
 * @since 2019-04-11 (2.0)
 */
public final class GamePlay implements IGamePlay {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(GamePlay.class);
    /**
     * List of the player instances participate in the game.
     */
    private ArrayList<IPlayer> playerList = new ArrayList<IPlayer>();

    /**
     * The unique index of the player permitted to do the next step.
     */
    private byte allowedPlayerIndex = Byte.MIN_VALUE;

    /**
     * Representation in the data layer of the board cells.
     */
    //private ArrayList<IGamePlayField> fieldList = new ArrayList<IGamePlayField>();
    /**
     * Stores the pattern of the game board.
     */
    private byte[][] stands;
    /**
     * Reference to the data field of cell previously clicked.
     */
    private IField previouslyClickedField;

    /**
     * The unique index of the player permitted to do touch the board.
     * ???????????
     */
    //private byte initialStepPlayerIndex = Byte.MIN_VALUE;
    /**
     * True if game is finished otherwise false
     */
    private boolean isFinished = false;

    /**
     * It stores the status code of the game
     */
    private StatusCode gamePlayStatus;

    /**
     * Creates a {@code GamePlay} instance.
     *
     * @param stands It is an byte[][] array which stores the states of the stands.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private GamePlay(byte[][] stands) throws TError {
        this.initialise();

        // Input checkings
        this.testAndThrowIfStandsInvalid(stands);

        // Store the values
        this.previouslyClickedField = null;
        this.stands = stands;
        
        logger.trace("GamePlay constructed!");

    }

    /**
     * Creates a {@code GamePlay} instance.
     */
    public GamePlay() {
        this.initialise();
        this.previouslyClickedField = null;
    }

    private void initialise() {
        for(byte playerIndex = IGamePlay.minimumExpectedPlayerIndex; playerIndex <= IGamePlay.maximumExpectedPlayerIndex; playerIndex++) {
            this.playerList.add(playerIndex - 1, null);
        }
    }

    /**
     * Method to ease the input checking of board shape.
     *
     * @param stands the pattern array of board shape.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void testAndThrowIfStandsInvalid(byte[][] stands) throws TError {
        if(stands == null) {
            throw new TError("Parameter 'stands' should not be null!");
        }

        if(stands.length != GamePlay.maximumExpectedStandColumnIndex + 1) {
            throw new TError("The count of items allocated in first dimension of parameter 'stands' should be " + (GamePlay.maximumExpectedStandColumnIndex + 1) + "!");
        }

        for(int columnIndex = 0; columnIndex <= GamePlay.maximumExpectedStandColumnIndex; columnIndex++) {
            if(stands[columnIndex].length != GamePlay.maximumExpectedStandRowIndex + 1) {
                throw new TError("The count of items allocated in second dimension of parameter 'stands' should be " + (GamePlay.maximumExpectedStandRowIndex + 1) + " at the index of '" + columnIndex + "'!");
            }

            for(int rowIndex = 0; rowIndex <= GamePlay.maximumExpectedStandRowIndex; rowIndex++) {
                byte cellValue = stands[columnIndex][rowIndex];

                if(cellValue != -1 && cellValue != 0) {
                    if(cellValue < IGamePlay.minimumExpectedPlayerIndex || cellValue > IGamePlay.maximumExpectedPlayerIndex) {
                        throw new TError("Player index should be allocated in the allowed range which is from " + IGamePlay.minimumExpectedPlayerIndex + " to " + IGamePlay.minimumExpectedPlayerIndex + "!");
                    }

                    if(this.playerList.get(cellValue - 1) == null) {
                        throw new TError("No player exists at specified index" + cellValue + "!");
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ArrayList<IPlayer> getPlayerList() {
        return playerList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPlayerList(ArrayList<IPlayer> list) throws TError {
        // Input checkings
        if(list == null) {
            throw new TError("Parameter shold not be null!");
        }

        for(IPlayer aPlayer : list) {
            if(list.lastIndexOf(aPlayer) != list.indexOf(aPlayer)) {
                throw new TError("Each playerIndex sholuld be unique!");
            }

            if(aPlayer.getPlayerIndex() < IGamePlay.minimumExpectedPlayerIndex || aPlayer.getPlayerIndex() > IGamePlay.maximumExpectedPlayerIndex) {
                throw new TError("Player index should be allocated in the allowed range whicch is from " + IGamePlay.minimumExpectedPlayerIndex + " to " + IGamePlay.maximumExpectedPlayerIndex + "!");
            }
        }

        // Store values
        this.playerList = list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte getAllowedPlayerIndex() {
        return allowedPlayerIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAllowedPlayerIndex(byte allowedPlayerIndex) throws TError {

        // Input checkings
        if(allowedPlayerIndex < IGamePlay.minimumExpectedPlayerIndex || allowedPlayerIndex > IGamePlay.maximumExpectedPlayerIndex) {
            throw new TError("Player index should be allocated in the allowed range whicch is from " + IGamePlay.minimumExpectedPlayerIndex + " to " + IGamePlay.minimumExpectedPlayerIndex + "!");
        }

        if(this.playerList.get(allowedPlayerIndex - 1) == null) {
            throw new TError("No paleyer is stored at specified playerIndex!");
        }

        // Store the value
        this.allowedPlayerIndex = allowedPlayerIndex;
    }

    /**
     * {@inheritDoc}
     */
    /*@Override
    public final ArrayList<IGamePlayField> getFieldList() {
        return fieldList;
    }*/
    /**
     * {@inheritDoc}
     */
    @Override
    public final byte[][] getStands() {
        return stands;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setBoard(byte[][] stands) throws TError {
        // Input checkings
        this.testAndThrowIfStandsInvalid(stands);

        /*// Generating and storing of stands according to the stands
        this.fieldList.clear();

        for(int columnIndex = 0; columnIndex <= IGamePlay.maximumExpectedStandColumnIndex; columnIndex++) {

            for(int rowIndex = 0; rowIndex <= IGamePlay.maximumExpectedStandRowIndex; rowIndex++) {
                //     
                Field field = new Field(columnIndex, rowIndex);
                this.fieldList.add(field);

                // If board shape contains 0 than field should be inactivated
                if(stands[columnIndex][rowIndex] == 0) {
                    field.setIsInactive(true);
                }
            }
        }*/
        // Store stands pattern
        this.stands = stands;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IField getPreviouslyClickedField() {
        return previouslyClickedField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setPreviouslyClickedField(IField previouslyClickedField) {
        this.previouslyClickedField = previouslyClickedField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerExists(byte playerIndex) {
        if(playerIndex < IGamePlay.minimumExpectedPlayerIndex || playerIndex > IGamePlay.maximumExpectedPlayerIndex) {
            return false;
        }

        if(this.playerList.get(playerIndex - 1) == null) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(IPlayer player, byte playerIndex) throws TError {
        // Input checkings
        if(playerIndex < IGamePlay.minimumExpectedPlayerIndex || playerIndex > IGamePlay.maximumExpectedPlayerIndex) {
            throw new TError("Player index should be allocated in the allowed range whicch is from " + IGamePlay.minimumExpectedPlayerIndex + " to " + IGamePlay.maximumExpectedPlayerIndex + "! I was:" + playerIndex);
        }

        if(this.playerList.get(playerIndex - 1) != null) {
            throw new TError("The player index is in use already!");
        }

        // Store the player and his index
        player.setPlayerIndex(playerIndex);
        this.playerList.set(playerIndex - 1, player);

        // if allowedPlayerIndex is not determined
        this.allowedPlayerIndex = playerIndex;

        // Set the pieces onto the occupied positions
        //int rowIndex, columnIndex;
        /* for(int i = 0; i < occupiedPositions.length; i++) {
            // Input checking
            if(occupiedPositions[i].length != 2) {
                throw new TError("Invalid data format!");
            }

            columnIndex = occupiedPositions[i][0];
            rowIndex = occupiedPositions[i][1];

            IField field = this.getField(columnIndex, rowIndex);
            if(field != null) {
                field.setPlayerIndex(playerIndex);
            }
        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isStandExists(int columnIndex, int rowIndex) {
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            return false;
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final IField getField(int columnIndex, int rowIndex) throws TError {
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        byte standValue = this.stands[columnIndex][rowIndex];
        IField field;
        if(standValue == 0) { // If position is not occupied
            field = new Field(columnIndex, rowIndex);
            field.setIsInactive(false);
            field.setPlayerIndex((byte) 0);
        }
        else if(standValue == -1) { // If position is inactive
            field = new Field(columnIndex, rowIndex);
            field.setIsInactive(true);
            field.setPlayerIndex((byte) 0);
        }
        else {  // Occupied by a player
            field = new Field(columnIndex, rowIndex);
            field.setIsInactive(false);
            field.setPlayerIndex(standValue);
        }

        return field;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGamePlayStatus(StatusCode newStatus) {
        this.gamePlayStatus = newStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusCode getStatus() {
        return this.gamePlayStatus;
    }

    /**
     * A game play consists of stands and other data. This class implements the
     * data part of the so called stands or pits represented on the board.
     *
     * @author Sándor Kálmánchey
     * @version 2.0
     * @since 2015-05-20 (1.0)
     * @since 2019-04-11 (2.0)
     */
    public final static class Field implements IField {

        /**
         * It stores the index of player occupied the cell.
         */
        private byte playerIndex = 0;
        /**
         * The column index of the cell.
         */
        private int columnIndex = 0;
        /**
         * the row index of the cell.
         */
        private int rowIndex = 0;

        /**
         * It stores wether the cell is occupieable.
         *
         */
        private boolean isInactive = false;

        /**
         *
         * @param columnIndex Column index of the cell
         * @param rowIndex    Row index of the cell
         *
         * @throws TError If error occurs
         */
        public Field(int columnIndex, int rowIndex) throws TError {
            // Input checkings
            if((columnIndex < 0) || (columnIndex > IGamePlay.maximumExpectedStandColumnIndex)) {
                throw new TError("Value of parameter 'columnIndex' should be between 0 and " + IGamePlay.maximumExpectedStandColumnIndex + " but it was " + columnIndex);
            }
            if((rowIndex < 0) || (rowIndex > IGamePlay.maximumExpectedStandRowIndex)) {
                throw new TError("Value of parameter 'rowIndex' should be between 0 and " + IGamePlay.maximumExpectedStandRowIndex + " but it was " + rowIndex);
            }

            // Store values
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setPlayerIndex(byte playerIndex) {
            this.playerIndex = playerIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final byte getPlayerIndex() {
            return playerIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final int getColumnIndex() {
            return columnIndex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final int getRowIndex() {
            return rowIndex;
        }

        @Override
        public boolean isInactive() {
            return isInactive;
        }

        @Override
        public void setIsInactive(boolean isInactive) {
            this.isInactive = isInactive;
        }

        /*@Override
        public boolean equals(Object obj) {

            if(obj == null) {
                return false;
            }
            if(getClass() != obj.getClass()) {
                //return false;
            }
            final Field other = (Field) obj;

            if(!Objects.equals(this.columnIndex, other.columnIndex)) {
                return false;
            }
            if(!Objects.equals(this.rowIndex, other.rowIndex)) {
                return false;
            }
            return true;
        }*/
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
            final Field other = (Field) obj;
            if(this.playerIndex != other.playerIndex) {
                return false;
            }
            if(this.columnIndex != other.columnIndex) {
                return false;
            }
            if(this.rowIndex != other.rowIndex) {
                return false;
            }
            if(this.isInactive != other.isInactive) {
                return false;
            }
            return true;
        }

    }
}
