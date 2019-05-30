package Window;

import Hexxagon.Model.IGameData;
import Support.TError;
import Support.IItem;
import Support.TItem;
import Support.mvc.TModel;
import java.util.ArrayList;

/**
 *
 * Contains implementation of data component of application.
 * Instance attached to the controller responsible for storing:
 * - The list of optional board modes
 * - The list of optional difficulty levels
 * - The selected board mode by which the actual game settings are generated.
 * - The selected difficulty by which the artificial players strength is set.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class Storage extends TModel implements IStorage {

    /**
     * Stores the unique index of selected board model option. If nothing is selected than the value is "-1".
     */
    private int selectedBoardIndex = -1;
    /**
     * Stores the unique index of selected difficulty option. If nothing is selected than the value is "-1".
     */
    private int selectedDifficultyIndex = -1;

    /**
     * Stores the data component of the Hexxagon module.
     */
    private IGameData gameData;

    /**
     * List of all added board mode options.
     */
    private ArrayList<IBoardModeItem> boardModeList = new ArrayList<>();
    /**
     * List of all added difficulty options.
     */
    private ArrayList<TSelectItem> difficultyTypeList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameData getGameData() {
        return gameData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameData(IGameData gameData) throws TError {
        // Input checkings
        if(gameData == null) {
            throw new TError("The parameter sould not be null!");
        }
        this.gameData = gameData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedBoardIndex() {
        return selectedBoardIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedBoardIndex(int selectedBoardIndex) throws TError {

        // Input checkings
        if(!this.boardModeList.contains(new BoardModeItem(selectedBoardIndex))) {
            throw new TError("The item does not exist at specified index " + selectedBoardIndex);
        }

        // Store values
        this.selectedBoardIndex = selectedBoardIndex;
        this.firePropertyChange(MODIFICATIONCODE_GENERAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedDifficultyIndex() {
        return selectedDifficultyIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedDifficultyIndex(int selectedDifficultyIndex) throws TError {

        // Input checkings
        if(!this.difficultyTypeList.contains(new TSelectItem(selectedBoardIndex))) {
            throw new TError("The item does not exist at specified index " + selectedDifficultyIndex);
        }

        // Store values
        this.selectedDifficultyIndex = selectedDifficultyIndex;
        this.firePropertyChange(MODIFICATIONCODE_GENERAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBoardMode(IBoardModeItem boardMode) throws TError {
        // Input checking
        if(boardMode == null) {
            throw new TError("Parameter should not be null!");
        }

        // Store value
        if(!this.boardModeList.contains(boardMode)) {
            this.boardModeList.add(boardMode);
            this.firePropertyChange(MODIFICATIONCODE_GENERAL);
        }
        else {
            throw new TError("The item already is in the list!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDifficultyType(int index, int value, String caption) throws TError {
        // Input checking
        if(index < 0) {
            throw new TError("The difficulty index shoud not be less than 0!");
        }
        if(value < 1) {
            throw new TError("The difficulty value shoud not be less than 0!");
        }
        if(caption == null) {
            throw new TError("The caption shoud not be null!");
        }

        // Store value
        TSelectItem item = new TSelectItem(index, Integer.valueOf(value), caption);
        if(!this.difficultyTypeList.contains(item)) {
            this.difficultyTypeList.add(item);
            this.firePropertyChange(MODIFICATIONCODE_GENERAL);
        }
        else {
            throw new TError("The item already is in the list!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<TSelectItem> getDifficultyTypeList() {
        return difficultyTypeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<IBoardModeItem> getBoardModeList() {
        return boardModeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBoardModeExists(int boardModeIndex) {
        try {
            if(boardModeIndex >= 0) {
                IBoardModeItem item = new BoardModeItem(boardModeIndex);
                if(this.boardModeList.contains(item)) {
                    return true;
                }
            }
        }
        catch(TError err) {
            return false;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBoardModeItem getBoardMode(int boardModeIndex) throws TError {
        // Input checking
        if(boardModeIndex < 0) {
            throw new TError("Given index should not be less than 0!");
        }
        if(!this.isBoardModeExists(boardModeIndex)) {
            throw new TError("The requested board mode item at '" + boardModeIndex + "' index does not exist!");
        }

        IBoardModeItem item = new BoardModeItem(boardModeIndex);
        if(this.boardModeList.contains(item)) {
            int indx = this.boardModeList.indexOf(item);
            return this.boardModeList.get(indx);
        }

        throw new TError("Unexpected execution!");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDifficultyExists(int difficultyIndex) {

        if(difficultyIndex >= 0) {
            TSelectItem item = new TSelectItem(difficultyIndex);
            if(this.difficultyTypeList.contains(item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDifficultyValue(int difficultyIndex) throws TError {
        // Input checking
        if(difficultyIndex < 0) {
            throw new TError("Given index should not be less than 0! It was given:" + difficultyIndex);
        }
        if(!this.isDifficultyExists(difficultyIndex)) {
            throw new TError("The requested difficulty mode item at '" + difficultyIndex + "' index does not exist!");
        }

        TSelectItem item = new TSelectItem(difficultyIndex);
        if(this.difficultyTypeList.contains(item)) {
            int indx = this.difficultyTypeList.indexOf(item);
            return this.difficultyTypeList.get(indx).getValueAsInt();
        }

        throw new TError("Unexpected execution!");

    }

    /**
     * {@inheritDoc}
     */
    // @Override
    /* public ArrayList<IItem> getBoardModeItemListtt() {
        ArrayList<IItem> output = new ArrayList<>();
        for(IBoardModeItem item : boardModeList) {
            //output.add(item);
        }
        return output;
    }
     */
    /**
     * Implementation of {@code IBoardModeItem} sub-interface of {@code IStorage}
     * It extends the {@code TItem} general purpose abstract class.
     * Instances of this class represents an detailed settings of game.
     *
     * @author Sandor Kalmanchey
     * @version 1.0
     * @since 1.0
     */
    public static final class BoardModeItem implements IBoardModeItem {

        /**
         * Unique index of board mode option.
         */
        private int index;
        /**
         * Text displayed in select box graphical component.
         */
        private String caption = "";

        /**
         * Representing the board containing the pieces in positions.
         */
        private byte[][] board;

        /**
         * Containing all added player.
         */
        private ArrayList<IPlayerRow> playerList = new ArrayList<>();

        /**
         * Creates a {@code BoardModeItem} instance.
         *
         * @param index The unique index of board mode option. Value must not be less than 1 otherwise {@code TError} is thrown.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        public BoardModeItem(int index) throws TError {
            // Input checkings
            if(index < 0) {
                throw new TError("The given index sholud not be less than 0!");
            }

            // Store value
            this.index = index;
        }

        /**
         * Answers whether is any artificial player stored in the specified board mode option.
         *
         * @return True if there is at least one artificial player found otherwise false.
         */
        public boolean isAIPlayerAdded() {

            for(IPlayerRow player : playerList) {
                if(player.isAuto()) {
                    return true;
                }

            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIndex() {
            return index;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setIndex(int index) throws TError {
            // Input checkings
            if(index < 0) {
                throw new TError("The given index sholud not be less than 0!");
            }

            // Store value
            this.index = index;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCaption() {
            return caption;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setCaption(String caption) throws TError {
            // Input checkings
            if(caption == null) {
                throw new TError("The parameter sould not be null!");
            }
            // Store value
            this.caption = caption;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public byte[][] getBoard() {
            return board;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setBoard(byte[][] board) throws TError {
            // Input checkings
            if(board == null) {
                throw new TError("The parameter sould not be null!");
            }

            // Store value
            this.board = board;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getHumanPlayerCount() {
            int count = 0;
            for(IPlayerRow item : this.playerList) {
                if(!item.isAuto()) {
                    count++;
                }
            }
            return count;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getAutoPlayerCount() {
            int count = 0;
            for(IPlayerRow item : this.playerList) {
                if(item.isAuto()) {
                    count++;
                }
            }
            return count;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addPlayer(byte playerIndex, String color, boolean isAuto) throws TError {
            // Input checking
            if(playerIndex < 1) {
                throw new TError("The player index shoud not be less than 1!");
            }
            if(color == null) {
                throw new TError("The player color shoud not be null!");
            }

            // Store
            PlayerRow playerRow = new PlayerRow(playerIndex, color, isAuto);
            if(!this.playerList.contains(playerRow)) {
                this.playerList.add(playerRow);
            }
            else {
                throw new TError("The requested player index is in use already!");
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ArrayList<IPlayerRow> getPlayerList() {
            return playerList;
        }

        /**
         * {@inheritDoc}
         */
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
            final BoardModeItem other = (BoardModeItem) obj;
            if(this.index != other.index) {
                return false;
            }
            return true;
        }

        /**
         * Implementation of {@code IPlayerRow} sub-interface of {@code IStorage} .
         * Instances of this class represents a player of game.
         *
         * @author Sandor Kalmanchey
         * @version 1.0
         * @since 1.0
         */
        private static final class PlayerRow implements IPlayerRow {

            /**
             * The color code of player in html style like <code>#cc0000</code> .
             */
            private String color = "";

            /**
             * Unique index of the player.
             */
            private byte index = 0;

            /**
             * True if the player should be generated as artificial one.
             */
            private boolean isAuto = false;

            /**
             * Creates a {@code PlayerRow} instance.
             *
             * @param playerIndex Unique index of the player whether it be AI or human one
             * @param color       The color code of player in html style like <code>#cc0000</code>
             * @param isAuto      If true than AI player is generated otherwise a non-AI paler.
             *
             * @throws TError Thrown if an unrecoverable error was occurred.
             */
            public PlayerRow(byte playerIndex, String color, boolean isAuto) throws TError {
                // Input checking
                if(playerIndex < 1) {
                    throw new TError("The player index shoud not be less than 1!");
                }
                if(color == null) {
                    throw new TError("The player color shoud not be null!");
                }

                // Store values
                this.index = playerIndex;
                this.color = color;
                this.isAuto = isAuto;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getColor() {
                return color;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public byte getIndex() {
                return index;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isAuto() {
                return isAuto;
            }

            /**
             * Instances are equal only if they index have the same value.
             *
             * @param obj Instance to compare.
             *
             * @return True if equal otherwise false.
             */
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
                if(this.index != other.index) {
                    return false;
                }
                return true;
            }

        }

    }

}
