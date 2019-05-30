package Window;

import Hexxagon.Model.IGameData;
import Support.IItem;
import Support.TError;
import java.util.ArrayList;

/**
 * Contains method specifications of data component of application.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IStorage {

    /**
     * General code of property change delegated to the view component.
     */
    public static final String MODIFICATIONCODE_GENERAL = "General";

    /**
     * Stores the data component of the Hexxagon module.
     *
     * @param gameData An representation of Hexxagon data module. Value must not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setGameData(IGameData gameData) throws TError;

    /**
     * Retrieves the data component of the Hexxagon module.
     *
     * @return An object implementing {@code IGameData} interface.
     */
    public IGameData getGameData();

    /**
     * Retrieves the index of selected board option.
     *
     * @return An integer value not less than 0.
     */
    public int getSelectedBoardIndex();

    /**
     * Sets the value of the selected board index.
     *
     * @param selectedBoard An integer value not less than 0.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setSelectedBoardIndex(int selectedBoard) throws TError;

    /**
     * Retrieves the index of selected difficulty option.
     *
     * @return An int value not less than 0.
     */
    public int getSelectedDifficultyIndex();

    /**
     * Sets the value of the selected difficulty index.
     *
     * @param selectedFightMode An int value not less than 0.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void setSelectedDifficultyIndex(int selectedFightMode) throws TError;

    /**
     * Adds an board mode option containing details of game settings.
     *
     * @param boardMode Object of board mode. Object must not be null otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addBoardMode(IBoardModeItem boardMode) throws TError;

    /**
     * Adds a difficulty option.
     *
     * @param index   Unique item index of specified difficulty settings.
     * @param value   Value of difficulty used by AI player. Not less than 1 otherwise {@code TError} is thrown.
     * @param caption The text displayed in select box graphical component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void addDifficultyType(int index, int value, String caption) throws TError;

    /**
     * Retrieves a list of all added board mode options.
     *
     * @return Object of an array list.
     */
    public ArrayList<IBoardModeItem> getBoardModeList();

   
    /**
     * Retrieves the list of all added difficulty options.
     *
     * @return Object of an array list.
     */
    public ArrayList<TSelectItem> getDifficultyTypeList();

    /**
     * Retrieves a board mode option associated to {@code boardModeIndex}.
     * If no item is found at specified index than {@code TError} is thrown.
     *
     * @param boardModeIndex The unique index of board mode option.
     *
     * @return A board mode object
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public IBoardModeItem getBoardMode(int boardModeIndex) throws TError;

    /**
     * Answers whether the requested board mode option exists.
     *
     * @param boardModeIndex The unique index of requested board mode option.
     *
     * @return True if the requested board mode option exists otherwise false.
     */
    public boolean isBoardModeExists(int boardModeIndex);

    /**
     * Answers whether the requested difficulty option exists.
     *
     * @param difficultyIndex The unique index of difficulty option.
     *
     * @return True if the requested difficulty option exists otherwise false.
     */
    public boolean isDifficultyExists(int difficultyIndex);

    /**
     * Retrieves the value of the difficulty option associated to the {@code difficultyIndex} .
     * If no item is found at specified index than {@code TError} is thrown.
     *
     * @param difficultyIndex The unique index of difficulty option.
     *
     * @return The value of difficulty which is not less than 1.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public int getDifficultyValue(int difficultyIndex) throws TError;

    /**
     * This sub-interface of {@code IStorage} contains method specifications of settings of a board mode option.
     * It extends the {@code IItem} general purpose interface.
     *
     * @author Sandor Kalmanchey
     * @version 1.0
     * @since 1.0
     */
    public interface IBoardModeItem {

        /**
         * Answers whether is any AI player added.
         *
         * @return True if at least one AI player is added otherwise false.
         */
        public boolean isAIPlayerAdded();

        /**
         * Retrieves the unique index of board mode option.
         *
         * @return An int value not less than 0.
         */
        public int getIndex();

        /**
         * Sets the unique index of the specified board mode option.
         *
         * @param index An int value not less than 0.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        public void setIndex(int index) throws TError;

        /**
         * Retrieves the text displayed in select box component.
         *
         * @return A string value.
         */
        public String getCaption();

        /**
         * Sets the text displayed in select box component.
         *
         * @param caption The content which is wanted to be presented.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        public void setCaption(String caption) throws TError;

        /**
         * Retrieves the board containing the pieces in positions.
         *
         * @return A two dimensional byte object.
         */
        public byte[][] getBoard();

        /**
         * Stores the board containing the pieces in positions.
         *
         * @param board A two dimensional byte object.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        public void setBoard(byte[][] board) throws TError;

        /**
         * Tells the count of non AI players added.
         *
         * @return Value not less than 0
         */
        public int getHumanPlayerCount();

        /**
         * Tells the count of AI players added.
         *
         * @return Value not less than 0
         */
        public int getAutoPlayerCount();

        /**
         * Adds a player to the board mode option
         *
         * @param playerIndex Unique index of the player whether it be AI or human one
         * @param color       The color code of player in html style like <code>#cc0000</code>
         * @param isAuto      If true than AI player is generated otherwise a non-AI paler.
         *
         * @throws TError Thrown if an unrecoverable error was occurred.
         */
        public void addPlayer(byte playerIndex, String color, boolean isAuto) throws TError;

        /**
         * Retrieves the list of all generated and added players.
         *
         * @return An array list
         */
        public ArrayList<IPlayerRow> getPlayerList();

        /**
         * Instances are equal only if they index have the same value
         *
         * @param obj Instance to compare.
         *
         * @return True if equal otherwise false.
         */
        @Override
        public boolean equals(Object obj);

        /**
         * This sub-interface of {@code IStorage} contains method specifications of player data representation.
         *
         * @author Sandor Kalmanchey
         * @version 1.0
         * @since 1.0
         */
        public interface IPlayerRow {

            /**
             * Retrieves the colour code of specified player.
             *
             * @return The colour code of player in html style like <code>#cc0000</code>
             */
            public String getColor();

            /**
             * Retrieves the unique index of player.
             *
             * @return The index value of player which is not less than 1.
             */
            public byte getIndex();

            /**
             * Answers whether the player is an artificial one or not.
             *
             * @return True if it is an artificial player but otherwise false.
             */
            public boolean isAuto();
        }

    }

}
