package Hexxagon.View;

import Support.TError;

/**
 * Contains method specifications of board view component of game.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 2.0
 */
public interface IPlayBoard {

    /**
     * Relative path of folder of css files.
     */
    public static final String folderCSS = "css";
    /**
     * Relative path of folder of image files.
     */
    public static final String folderImage = "image";
    /**
     * Relative path of folder of audio files.
     */
    public static final String folderAudio = "audio";

    /**
     * Contains different reasons why the game was finished.
     */
    public static enum FinishCodeEnum {
        /**
         * When the game was finished due to having no more unoccupied cell.
         */
        BoardFull,
        /**
         * When the game was finished due to the enemy having no more unoccupied cell to reach.
         */
        EnemyBlocked,
        /**
         * When the game was finished due to the enemy lousing all its balls.
         */
        EnemyFall
    };

    /**
     * Displays the view component.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void show() throws TError;

    /**
     * Sops to display the component.
     */
    public void hide();

    /**
     * Refreshes the component.
     * When component is refreshed than it rereads all data from data layer.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void refresh() throws TError;

    /**
     *
     */
    //public void refreshInfoPanels();
    /**
     * Displays animation when not the allowed player clicks on board.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     */
    public void animateWhenNotAllowedPlayerClickedOnAPit(int columnIndex, int rowIndex);

    /**
     * Displays animation when the click was occurred outside the attack zone.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     */
    public void animateWhenClickedOutsideOfAttackZone(int columnIndex, int rowIndex);

    /**
     * Displays animation of attack zone when a cell is selected.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void animateAsAttackZone(int columnIndex, int rowIndex) throws TError;

    /**
     * Stops animation of showing attack zone.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void removeAllAttackZone() throws TError;

    /**
     * Displays animation when a cell is occupied by a player.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     * @param playerIndex The unique index of player who occupies the cell.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void animatePitWhenCapturedByPlayer(int columnIndex, int rowIndex, byte playerIndex) throws TError;

    /**
     * Displays animation when cells are assimilitated to the occupied cells.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     * @param playerIndex The unique index of player who occupies the cell.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void animatePitWhenCapturedInLocalArea(int columnIndex, int rowIndex, byte playerIndex) throws TError;

    /**
     * Displays animation when a cell becomes unoccupied.
     *
     * @param columnIndex Column index of cell.
     * @param rowIndex    Row index of cell.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void animatePitWhenAbandon(int columnIndex, int rowIndex) throws TError;

    /**
     * Displays a dialog which shows the winer player.
     *
     * @param caseCode    An {@code FinishCodeEnum} enum which represents the reason
     * @param winnerIndex Unique index of player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    public void showEndGameDialog(FinishCodeEnum caseCode, byte winnerIndex) throws TError;
}
