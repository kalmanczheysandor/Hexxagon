package Hexxagon.Controller;

import Hexxagon.AI.IOperator;
import Hexxagon.Model.GamePlay;
import Hexxagon.Model.IGameData;
import Hexxagon.Model.IGameData.ProcessStatusCode;
import Hexxagon.Model.IGamePlay;
import Hexxagon.Model.IGamePlay.StatusCode;
import Support.TError;
import Support.TCoordinate2D;
import Support.TFailed;
import Hexxagon.View.IPlayBoard.FinishCodeEnum;
import Support.mvc.IView;
import Support.mvc.TController;
import java.util.ArrayList;
import Hexxagon.Model.IField;
import Hexxagon.View.IInfoBoard;
import Hexxagon.View.IPlayBoard;
import Support.mvc.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It is the controller object of the Hexxagon module.
 *
 * @author Sandor Kalmanchey
 * @version 2.0
 * @since 1.0
 */
public class GameController extends TController implements IGameController {

    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    /**
     * Contains relative coordinates of fission typed step.
     */
    private static final TCoordinate2D[] relativeCoordinatesOfFissionAttackZone = {
        /*C-1*/new TCoordinate2D(-1, 0), new TCoordinate2D(-1, +1),
        /*C0*/ new TCoordinate2D(0, -1), new TCoordinate2D(0, 1),
        /*C+1*/ new TCoordinate2D(1, -1), new TCoordinate2D(1, 0)
    };

    /**
     * Contains relative coordinates of jump typed step.
     */
    private static final TCoordinate2D[] relativeCoordinatesOfJumpAttackZone = {
        /*C-2*/new TCoordinate2D(-2, 0), new TCoordinate2D(-2, 1), new TCoordinate2D(-2, 2),
        /*C-1*/ new TCoordinate2D(-1, -1), new TCoordinate2D(-1, +2),
        /*C0*/ new TCoordinate2D(0, -2), new TCoordinate2D(0, 2),
        /*C+1*/ new TCoordinate2D(1, -2), new TCoordinate2D(1, 1),
        /*C+2*/ new TCoordinate2D(2, -2), new TCoordinate2D(2, -1), new TCoordinate2D(2, 0)
    };

    /**
     * Contains relative coordinates of adjacent cells.
     */
    private static final TCoordinate2D[] relativeCoordinatesOfAdjacentCells = {
        /*C-1*/new TCoordinate2D(-1, 0), new TCoordinate2D(-1, +1),
        /*C0*/ new TCoordinate2D(0, -1), new TCoordinate2D(0, 1),
        /*C+1*/ new TCoordinate2D(1, -1), new TCoordinate2D(1, 0)
    };

    /**
     * Forces all info board visual components to refresh themselves.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void refreshOnAllGameInfo() throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IInfoBoard) {
                ((IInfoBoard) view).refresh();
            }
        }
    }

    /**
     * Forces all info board visual components to display themselves.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showOnAllGameInfo() throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IInfoBoard) {
                ((IInfoBoard) view).show();
            }
        }
    }

    /**
     * Forces all play board visual components to display the end game dialog.
     *
     * @param caseCode    The case code of how was the game finished.
     * @param winnerIndex The unique player index of winner player.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showEndGameDialogOnAllGameBoard(FinishCodeEnum caseCode, byte winnerIndex) throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).showEndGameDialog(caseCode, winnerIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to refresh themselves.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void refreshOnAllGameBoard() throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).refresh();
            }
        }
    }

    /**
     * Forces all play board visual components to display themselves.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showOnAllGameBoard() throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).show();
            }
        }
    }

    /**
     * Forces all play board visual components to remove possible attack zones animation.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void removeAttackZoneOnAllGameBoard() throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).removeAllAttackZone();
            }
        }
    }

    /**
     * Forces all play board visual components to display possible attack zones animation.
     *
     * @param columnIndex Column index of base cell of the attack zone. Index should valid and existing otherwise {@code TError} is thrown.
     * @param rowIndex    Row index of base cell of the attack zone. Index should valid and existing otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showAttackZoneOnAllGameBoard(int columnIndex, int rowIndex) throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animateAsAttackZone(columnIndex, rowIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to display animation when a not allowed player was clicked any of the added play boards.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     */
    private void informNotAllowedPlayerClickedOnAPitOnAllGameBoard(int columnIndex, int rowIndex) {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animateWhenNotAllowedPlayerClickedOnAPit(columnIndex, rowIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to display animation when a cell is occupied.
     *
     * @param columnIndex Column index of occupied cell. Index should valid and existing otherwise {@code TError} is thrown.
     * @param rowIndex    Row index of occupied cell. Index should valid and existing otherwise {@code TError} is thrown.
     * @param playerIndex Unique index of player by whom the cell is occupied.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showPitAsOccupiedByOnAllGameBoard(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animatePitWhenCapturedByPlayer(columnIndex, rowIndex, playerIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to display animation when a the player is clicked outside of attack zone.
     *
     * @param columnIndex Column index of clicked cell.
     * @param rowIndex    Row index of clicked cell.
     */
    private void showTheAllowedPlayerClickedOutsideOnAllGameBoard(int columnIndex, int rowIndex) {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animateWhenClickedOutsideOfAttackZone(columnIndex, rowIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to display animation when a cell is abandoned.
     * Cell is abandoned after jump.
     *
     * @param columnIndex Column index of abandoned cell. Index should valid and existing otherwise {@code TError} is thrown.
     * @param rowIndex    Row index of abandoned cell. Index should valid and existing otherwise {@code TError} is thrown.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showPitAsAbandonedAllGameBoard(int columnIndex, int rowIndex) throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animatePitWhenAbandon(columnIndex, rowIndex);
            }
        }
    }

    /**
     * Forces all play board visual components to display animation when neighbour cells are captured/assimilated.
     *
     * @param columnIndex Column index of assimilated cell.
     * @param rowIndex    Row index of assimilated cell.
     * @param playerIndex Player index of player to who the cell is assimilated.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void showPitAsCapturedInLocalAreaOnAllGameBoard(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        ArrayList<IView> list = this.getViewList();
        for(IView view : list) {
            if(view instanceof IPlayBoard) {
                ((IPlayBoard) view).animatePitWhenCapturedInLocalArea(columnIndex, rowIndex, playerIndex);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void cellClicked(int clickedColumnIndex, int clickedRowIndex) throws TError {

        // Input checkings
        if(!this.getGameData().isCellExists(clickedColumnIndex, clickedRowIndex)) {
            throw new TError("The requested coordinate[" + clickedColumnIndex + "," + clickedRowIndex + "] is out of range!");
        }

        //byte    clickedStandValue = this.getGameData().getCell(columnIndex, rowIndex);
        //
        //IField clickedField = this.getGameData().getCellAsField(columnIndex, rowIndex);
        IField previousClickedField = this.getGameData().getPreviousClickedField();
        byte humanPlayerIndex = this.getGameData().getAllowedPlayerIndex();

        // Value checkings
        //if(clickedField == null) {
        //   throw new TError("Null value was found!");
        // }
        //
        if(this.getGameData().isCellOccupied(clickedColumnIndex, clickedRowIndex)) {                              // When the cell is not empty in other words when it is occupied 
            this.chooseAttackerCell(clickedColumnIndex, clickedRowIndex, humanPlayerIndex);               // Select which piece will be used for attack/for occupie another cell
        }
        else if(!this.getGameData().isCellOccupied(clickedColumnIndex, clickedRowIndex) && !this.getGameData().isCellInactive(clickedColumnIndex, clickedRowIndex)) { // When the clicked cell is not occupied jet

            //
            int fromColumnIndex = -1;
            int fromRowIndex = -1;
            if(previousClickedField != null) {
                fromColumnIndex = previousClickedField.getColumnIndex();
                fromRowIndex = previousClickedField.getRowIndex();
            }

            //
            int targetColumnIndex = clickedColumnIndex;
            int targetRowIndex = clickedRowIndex;

            //
            if((fromColumnIndex >= 0) && (fromRowIndex >= 0)) { //Note: Akkor, ha van kiválasztva támadó cella
                if(this.tryToCaptureTheTarget(fromColumnIndex, fromRowIndex, targetColumnIndex, targetRowIndex, humanPlayerIndex)) {
                    this.nextRound();
                }
            }
        }

    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public final void autoPlayerCellStep(int fromColumnIndex, int fromRowIndex, int targetColumnIndex, int targetRowIndex, byte playerIndex) throws TError {
        logger.debug("autoPlayerCellStep");
        boolean isSucces = this.tryToCaptureTheTarget(fromColumnIndex, fromRowIndex, targetColumnIndex, targetRowIndex, playerIndex);

        if(isSucces) {
            logger.debug("Succes");
            this.nextRound();

        }
        else {
            logger.debug("Not Succes");

        }

    }

    /**
     * Annak a cellának a kijelölése, ahonnan a természetes játékos lépni szándékozik.
     *
     * @param clickedCellColumnIndex Annak a cellának az oszlopindexe, ahonnan a lépés történik.
     * @param clickedCellRowIndex    Annak a cellának a sorindexe, ahonnan a lépés történik.
     * @param playerIndex            A cellát kiválasztani szándékozó természetes játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void chooseAttackerCell(int clickedCellColumnIndex, int clickedCellRowIndex, byte playerIndex) throws TError {

        // Input checking
        if(!this.getGameData().isCellExists(clickedCellColumnIndex, clickedCellRowIndex)) {
            throw new TError("Requested coordinate[" + clickedCellColumnIndex + "," + clickedCellRowIndex + "] is out of range!");
        }

        try {

            IField clickedField = this.getGameData().getCellAsField(clickedCellColumnIndex, clickedCellRowIndex);
            IField previousClickedField = this.getGameData().getPreviousClickedField();

            int previousClickedColumnIndex = -1;        // It is out of coordinate range
            int previousClickedRowIndex = -1;           // It is out of coordinate range
            int actualPlayerIndex = this.getGameData().getAllowedPlayerIndex();

            if(previousClickedField != null) {
                previousClickedColumnIndex = previousClickedField.getColumnIndex();
                previousClickedRowIndex = previousClickedField.getRowIndex();
            }

            if(clickedField == null) {
                throw new TError("Unexpected null value was found!");
            }

            // If inactive cell was selected
            if(clickedField.isInactive()) {
                throw new TFailed();
            }

            // The player not allowed to do the step
            if(actualPlayerIndex != playerIndex) {
                throw new TFailed();
            }
            // The selected player is not a natural player
            if(this.getGameData().getPlayer(playerIndex).isAutoPlayer()) {
                throw new TFailed();
                //TheLogger.trace("A lépni szándékozó játékos nem természetes játékos!");
            }
            //
            if(clickedField.getPlayerIndex() != playerIndex) {
                throw new TFailed();
                //TheLogger.trace("A cella más játékost tartalmaz(" + ClickedField.getPlayerIndex() + ") mint: " + PlayerIndex);
            }

            // There is no unoccupied cell around
            if(!this.isPossibilityToSpreadFromThisCellByPlayer(clickedCellColumnIndex, clickedCellRowIndex, playerIndex)) {
                throw new TFailed();
                //TheLogger.trace("A cellából nem tud elmenni");
            }

            // When the actual click is happened on the same cell on which the previous happened. In this case the spread area will be deactivated
            if((clickedField.getColumnIndex() == previousClickedColumnIndex) && (clickedField.getRowIndex() == previousClickedRowIndex)) {

                this.removeAttackZoneOnAllGameBoard();
                this.getGameData().removeLastClickedField();
            }
            else { // When the targeted/clicked actual and previous cell is not the same. In this case the the spread area should be reallocated 
                this.showAttackZoneOnAllGameBoard(clickedCellColumnIndex, clickedCellRowIndex);
                this.getGameData().setPreviousClickedField(clickedField);
            }

        }
        catch(TFailed exp) {
            this.informNotAllowedPlayerClickedOnAPitOnAllGameBoard(clickedCellColumnIndex, clickedCellRowIndex);
        }
    }

    /**
     * Annak a cellának a lefoglalása, ahova a természetes vagy gépi játékos
     * lépett. A lefoglalás hatására a környező cellák is lefoglalódhatnak.
     *
     * @param fromColumnIndex     Annak a cellának az oszlopindexe, ahonnan a lépés
     *                            történik.
     * @param fromRowIndex        Annak a cellának a sorindexe, ahonnan a lépés
     *                            történik.
     * @param targetColumnIndex   Annak a cellának az oszlopindexe, ahova a lépés
     *                            történik.
     * @param targetRowIndex      Annak a cellának a sorindexe, ahova a lépés
     *                            történik.
     * @param attackerPlayerIndex XXXX
     *
     * @return A végrehalytás sikerességének logikai értéke.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean tryToCaptureTheTarget(int fromColumnIndex, int fromRowIndex, int targetColumnIndex, int targetRowIndex, byte attackerPlayerIndex) throws TError {

        try {

            // The source cell does not exists
            if(!this.getGameData().isCellExists(fromColumnIndex, fromRowIndex)) {
                throw new TError("Source cell is not exists!");
            }

            // The target cell does not exists
            if(!this.getGameData().isCellExists(targetColumnIndex, targetRowIndex)) {
                throw new TError("Destination cell is not exists!");
            }

            // It tried to attack from inactive cell
            if(this.getGameData().isCellInactive(fromColumnIndex, fromRowIndex)) {
                logger.debug("E03");
                throw new TFailed();
            }

            // It tried to attack against inactive cell
            if(this.getGameData().isCellInactive(targetColumnIndex, targetRowIndex)) {
                logger.debug("E04");
                throw new TFailed();
            }

            // The targeted cell is not empty but occupied
            if(this.getGameData().isCellOccupied(targetColumnIndex, targetRowIndex)) {
                logger.debug("E05");
                throw new TFailed();
            }

            // The source cell is occupied by anotehr palyer
            if(this.getGameData().getCell(fromColumnIndex, fromRowIndex) != attackerPlayerIndex) {
                logger.debug("E06");
                throw new TFailed();
            }

            // If it is out of the legal attack range
            if(!isInTheAttackRange(fromColumnIndex, fromRowIndex, targetColumnIndex, targetRowIndex)) {
                logger.debug("E07");
                throw new TFailed();
            }

            if(this.isInTheFissionAttackZone(fromColumnIndex, fromRowIndex, targetColumnIndex, targetRowIndex)) {// It was clicked in the Area01

                this.getGameData().setCellOccupiedByPlayer(targetColumnIndex, targetRowIndex, attackerPlayerIndex);
                this.showPitAsOccupiedByOnAllGameBoard(targetColumnIndex, targetRowIndex, attackerPlayerIndex);

                // Occupie the neighbourhood
                this.occupieAdjacentCells(targetColumnIndex, targetRowIndex, attackerPlayerIndex);

                //
                this.removeAttackZoneOnAllGameBoard();
                this.getGameData().removeLastClickedField();

            }
            else if(this.isInTheJumpAttackZone(fromColumnIndex, fromRowIndex, targetColumnIndex, targetRowIndex)) {// It was clicked in the Area02

                this.getGameData().setCellOccupiedByPlayer(targetColumnIndex, targetRowIndex, attackerPlayerIndex);
                this.showPitAsOccupiedByOnAllGameBoard(targetColumnIndex, targetRowIndex, attackerPlayerIndex);

                // Release the source cell because it is a jump
                this.giveUpCell(fromColumnIndex, fromRowIndex);

                // Occupie the neighbourhood
                this.occupieAdjacentCells(targetColumnIndex, targetRowIndex, attackerPlayerIndex);

                //
                this.removeAttackZoneOnAllGameBoard();
                this.getGameData().removeLastClickedField();
            }

        }
        catch(TFailed exp) {
            this.showTheAllowedPlayerClickedOutsideOnAllGameBoard(targetColumnIndex, targetRowIndex);
            return false;
        }

        return true;
    }

    /**
     * Adott cella felszabadításának adminisztrálása.
     *
     * @param targetColumnIndex A cella oszlopindexe.
     * @param targetRowIndex    A cella sorindexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void giveUpCell(int targetColumnIndex, int targetRowIndex) throws TError {
        // Input checkings
        if(!this.getGameData().isCellExists(targetColumnIndex, targetRowIndex)) {
            throw new TError("The given coordinate[targetColumnIndex, targetRowIndex] is out of range!");
        }

        // Store value
        this.getGameData().setCellLiberated(targetColumnIndex, targetRowIndex);

        // Force view component 
        this.showPitAsAbandonedAllGameBoard(targetColumnIndex, targetRowIndex);

    }

    /**
     * Occupies all neighbour cells of base cell.
     *
     * @param baseColumnIndex    The column index of base cell.
     * @param baseRowIndex       The row index of base cell.
     * @param atackerPlayerIndex The player index of the occupier.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void occupieAdjacentCells(int baseColumnIndex, int baseRowIndex, byte atackerPlayerIndex) throws TError {

        for(TCoordinate2D relativeCoordinate : relativeCoordinatesOfAdjacentCells) {
            int adjacentColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int adjacentRowIndex = baseRowIndex + relativeCoordinate.getY();

            if(this.getGameData().isCellExists(adjacentColumnIndex, adjacentRowIndex)) {
                if(this.getGameData().isCellOccupied(adjacentColumnIndex, adjacentRowIndex)) {
                    this.getGameData().setCellOccupiedByPlayer(adjacentColumnIndex, adjacentRowIndex, atackerPlayerIndex);
                }
            }
        }
    }

    /**
     * Adott cella elfoglalásának megkisérlése.
     *
     * @param columnIndex Az elfoglalandó cella oszlopindexe.
     * @param rowIndex    Az elfoglalandó cella sorindexe.
     * @param playerIndex A támadó játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void tryToCaptureACell(int columnIndex, int rowIndex, byte playerIndex) throws TError {
        // Input checkings
        if(columnIndex < 0 || columnIndex > GamePlay.maximumExpectedStandColumnIndex) {
            throw new TError("The value of specified property 'columnIndex' is out of the allowed range. It was " + columnIndex + "!");
        }

        if(rowIndex < 0 || rowIndex > GamePlay.maximumExpectedStandRowIndex) {
            throw new TError("The value of specified property 'rowIndex' is out of the allowed range. It was " + rowIndex + "!");
        }

        if(!this.getGameData().isPlayerExists(playerIndex)) {
            throw new TError("Player was not found");
        }

        //
        if(this.getGameData().isCellOccupied(columnIndex, rowIndex)) {

            byte value = this.getGameData().getCell(columnIndex, rowIndex);
            if(value != playerIndex) {
                this.getGameData().setCellOccupiedByPlayer(columnIndex, rowIndex, playerIndex);
            }
        }
    }

    /**
     * A metődus elfoglalja az összes még szabad cellát. Ez a metódus akkor
     * szabad meghívni, amikor a játék szabályainak megfelelőean az adott
     * játékos ellenfele nem tud lépni, amikor pedig ő következne. A játék
     * szabályainak értelmében, ilyenkor ujra és ujra az aktuális játékos
     * léphet. Pontosabban mondva, minden szabad cella átadódik neki és a játék
     * végetér
     *
     * @param playerIndex A játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void captureAllEmptyCellByThePlayer(byte playerIndex) throws TError {

        // Input checkings
        if(!this.getGameData().isPlayerExists(playerIndex)) {
            throw new TError("No player waas found at index " + playerIndex + "!");
        }

        byte[][] stands = this.getGameData().getCells();

        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {

                if(stands[columnIndex][rowIndex] == 0) {
                    stands[columnIndex][rowIndex] = playerIndex;  //???????
                    this.showPitAsCapturedInLocalAreaOnAllGameBoard(columnIndex, rowIndex, playerIndex);
                }

            }
        }

        //
        //this.refreshOnAllGameInfo();
        this.refreshOnAllGameBoard();

    }

    /**
     * Tells whether the targeted cell is in the attack range counted from the base cell.
     *
     * @param baseColumnIndex   The column index of the base cell.
     * @param baseRowIndex      The row index of the base cell.
     * @param targetColumnIndex The column index of the target cell.
     * @param targetRowIndex    The row index of the target cell.
     *
     * @return True if the target cell is in the attack range, false otherwise.
     */
    private boolean isInTheAttackRange(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {

        if(this.isInTheFissionAttackZone(baseColumnIndex, baseRowIndex, targetColumnIndex, targetRowIndex)) {
            return true;
        }
        if(this.isInTheJumpAttackZone(baseColumnIndex, baseRowIndex, targetColumnIndex, targetRowIndex)) {
            return true;
        }

        return false;
    }

    /**
     * A metódus arra ad választ, hogy a két cella közötti távolság az osztódási
     * távolságban van-e.
     *
     * @param baseColumnIndex   A kiindulási cella oszlopindexe.
     * @param baseRowIndex      A kiindulási cella sorindexe.
     * @param targetColumnIndex A cél cella oszlopindexe.
     * @param targetRowIndex    A cél cella sorindexe.
     *
     * @return Igaz, ha a távolságon belül van, Hamis, ha a távolságon kivül
     *         van.
     */
    private boolean isInTheFissionAttackZone(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {

        for(TCoordinate2D relativeCoordinate : relativeCoordinatesOfFissionAttackZone) {
            int attackColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int attackRowIndex = baseRowIndex + relativeCoordinate.getY();
            if(attackColumnIndex == targetColumnIndex && attackRowIndex == targetRowIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * A metódus arra ad választ, hogy a két cella közötti távolság az ugrási
     * távolságban van-e.
     *
     * @param baseColumnIndex   A kiindulási cella oszlopindexe.
     * @param baseRowIndex      A kiindulási cella sorindexe.
     * @param targetColumnIndex A cél cella oszlopindexe.
     * @param targetRowIndex    A cél cella sorindexe.
     *
     * @return Igaz, ha a távolságon belül van, Hamis, ha a távolságon kivül
     *         van.
     */
    private boolean isInTheJumpAttackZone(int baseColumnIndex, int baseRowIndex, int targetColumnIndex, int targetRowIndex) {

        for(TCoordinate2D relativeCoordinate : relativeCoordinatesOfJumpAttackZone) {
            int attackColumnIndex = baseColumnIndex + relativeCoordinate.getX();
            int attackRowIndex = baseRowIndex + relativeCoordinate.getY();
            if(attackColumnIndex == targetColumnIndex && attackRowIndex == targetRowIndex) {
                return true;
            }
        }
        return false;

    }

    /**
     * Pushes the game into the next stage.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void nextRound() throws TError {
        boolean isFinish = false;
        byte previousPlayerIndex = this.getGameData().getAllowedPlayerIndex();

        // Animation
        //this.refreshOnAllGameInfo();
        // Calculate the effect of the last step
        this.calculateStepEffect();
        this.determineGamePlayStatus(previousPlayerIndex);

        //
        byte lastPlayerIndex = previousPlayerIndex;
        FinishCodeEnum caseCode = null;
        StatusCode currentStatus = this.getGameData().getStatus();

        if(currentStatus == StatusCode.BoardFullFinish) {
            logger.debug("Case001");
            isFinish = true;
            caseCode = IPlayBoard.FinishCodeEnum.BoardFull;
        }
        else if(currentStatus == StatusCode.EnemyFallFinish) {
            logger.debug("Case002");
            isFinish = true;
            caseCode = IPlayBoard.FinishCodeEnum.EnemyFall;

            this.captureAllEmptyCellByThePlayer(lastPlayerIndex);
        }
        else if(currentStatus == StatusCode.EnemyBlockedFinish) {
           logger.debug("Case003");
            isFinish = true;
            caseCode = IPlayBoard.FinishCodeEnum.EnemyBlocked;

            this.captureAllEmptyCellByThePlayer(lastPlayerIndex);
        }
        //---

        //---Note:
        if(isFinish) {//Note: Ha a játék végetért
            logger.debug("FINISH");
            byte winnerIndex = this.determineTheWinnerIndex();
            this.getGameData().setIsGameFinished(true);
            this.getGameData().setGameProcessStatus(ProcessStatusCode.STOPPED);
            this.showEndGameDialogOnAllGameBoard(caseCode, winnerIndex);
        }
        else {
            byte nextPlayerIndex = this.getNextAllowedPlayerIndex(previousPlayerIndex);

            // A tábla csak a céljátékos számára való engedélyezése
            this.getGameData().setAllowedPlayerIndex(nextPlayerIndex);

            //
            //this.refreshOnAllGameInfo();
            // Check wether if the palyer is artificial and invoke it
            final IPlayer player = this.getGameData().getPlayer(nextPlayerIndex);

            if(player.isAutoPlayer()) {
                logger.debug("AutoPlayer");
                byte[][] board = this.getGameData().getCells();
                IAIPlayer artificialPlayer = (IAIPlayer) player;

                final GameController me = this;
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            IOperator opr = artificialPlayer.takeYourStep(board);
                            Thread.sleep(2000);
                            if(opr != null) {
                                me.autoPlayerCellStep(opr.getBaseColumnIndex(), opr.getBaseRowIndex(), opr.getTargetColumnIndex(), opr.getTargetRowIndex(), nextPlayerIndex);
                            }
                            else {
                                logger.debug("AUTOPLAYER >>>>>>>>> NO STEP");
                            }

                        }
                        catch(TError ex) {
                            logger.debug("HHHHHHHHHHHHHHHHHHH");
                        }
                        catch(InterruptedException ex) {
                            logger.debug("Time");
                        }

                    }
                }.start();
            }
        }
    }

    /**
     * A győztes játékos indexének meghatározása.
     *
     * @return "0", ha döntetlen, más esetben a játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private byte determineTheWinnerIndex() throws TError {
        byte winnerIndex = 0;
        byte player01 = 1;
        byte player02 = 2;

        //---Note:
        int player01BallCount = this.getGameData().getPlayerBallCount(player01);
        int player02BallCount = this.getGameData().getPlayerBallCount(player02);
        //---

        //---Note:
        if(player01BallCount > player02BallCount) {
            winnerIndex = player01;
        }
        else if(player01BallCount < player02BallCount) {
            winnerIndex = player02;
        }
        //---    

        return winnerIndex;
    }

    /**
     * A játékmenet vizsgálata és állapotának beállítása.
     *
     * @param lastPlayerIndex Az utolsónak lépő játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void determineGamePlayStatus(byte lastPlayerIndex) throws TError {
        //
        StatusCode newStatus = StatusCode.NotFinished;

        //
        if(this.getGameData().isBoardFull()) {//Note: Ha a táblán már nics több szabad hely.
            logger.debug("Determine>>>>>>001");
            newStatus = StatusCode.BoardFullFinish;
        }
        else if((this.getGameData().playerCount() - 1) == this.getGameData().defeatedPlayerCount()) {//Note: Ha már csak 1db játékos maradt
            logger.debug("Determine>>>>>>002");
            newStatus = StatusCode.EnemyFallFinish;
        }
        else if(this.isAllEnemyOfThisPlayerBlocked(lastPlayerIndex)) {//Note: Ha a legutóljára lépett játékos lépésének a hatására minden ellenfél blokkolva van.

            logger.debug("Determine[" + lastPlayerIndex + "]>>>>>>003");
            newStatus = StatusCode.EnemyBlockedFinish;
        }

        this.getGameData().setGamePlayStatus(newStatus);

    }

    /**
     * Az utoljára megtett lépés hatásának kiszámítása.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private void calculateStepEffect() throws TError {

        // Check whether a player lost its last ball
        for(IPlayer player : this.getGameData().getPlayerList()) {

            if(player != null) {

                byte playerIndex = (byte) (this.getGameData().getPlayerList().indexOf(player) + 1);

                if(!this.getGameData().isPlayerDefeated(playerIndex)) {

                    int ballCount = this.getGameData().getPlayerBallCount(playerIndex);

                    if(ballCount == 0) {//Note: A játékos labdái elfogytak ezért kiesik

                        this.getGameData().setPlayerAsDefeated(playerIndex);
                    }
                }
            }
        }

    }

    /**
     * A játékmenet állapotától függöen a bemenetként megadott játékos indexhez
     * képest megadja annak a játékosnak az indexét, aki léphet.
     *
     * @param previousPlayerIndex Az előző játékos indexe.
     *
     * @return A következő lépésre engedélyezett játékos indexe.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private byte getNextAllowedPlayerIndex(byte previousPlayerIndex) throws TError {
        byte lastAttemptedIndex = previousPlayerIndex;
        byte nextPlayerIndex;

        do {

            if((lastAttemptedIndex + 1) < this.getGameData().getPlayerList().size()) {
                nextPlayerIndex = (byte) (lastAttemptedIndex + 1);
            }
            else {
                nextPlayerIndex = IGamePlay.minimumExpectedPlayerIndex;
            }

            // If player does not exists
            if(!this.getGameData().isPlayerExists(nextPlayerIndex)) {
                lastAttemptedIndex = nextPlayerIndex;
                continue;
            }

            //
            if(this.getGameData().isPlayerDefeated(nextPlayerIndex)) {
                lastAttemptedIndex = nextPlayerIndex;
                continue;
            }

            if(!this.isPossibilityToSpreadSomewhereByPlayer(nextPlayerIndex)) {
                lastAttemptedIndex = nextPlayerIndex;
                continue;
            }

            return nextPlayerIndex;
        }
        while(1 == 1);
    }

    /**
     * Arra ad választ, hogya a bemenetként megadott játékos, minden ellenfele
     * blokkolva van-e.
     *
     * @param playerIndex Annak a játékosnak az indexe, akinek az ellenfeleit
     *                    vizsgálja a metódus.
     *
     * @return Igaz, ha a játékos minden ellenfele blokkolva van. Hamis, az
     *         ellenkező esetben.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isAllEnemyOfThisPlayerBlocked(byte playerIndex) throws TError {
        // Input checkings
        if(!this.getGameData().isPlayerExists(playerIndex)) {
            throw new TError("No player exists at playerindex '" + playerIndex + "' ");
        }

        //
        for(IPlayer player : this.getGameData().getPlayerList()) {
            if(player != null) {

                byte enemyPlayerIndex = (byte) (this.getGameData().getPlayerList().indexOf(player) + 1);

                if(!this.getGameData().isPlayerDefeated(enemyPlayerIndex) && (enemyPlayerIndex != playerIndex)) {
                   logger.debug("Ball[" + playerIndex + "]: compared to " + enemyPlayerIndex);
                    if(this.isPossibilityToSpreadSomewhereByPlayer(enemyPlayerIndex)) { //---Note: Ennek a játékosnak még van lépési lehetősége
                        return false;
                    }
                }

            }
        }

        return true;
    }

    /**
     * Arra ad választ, hogya a bemenetként megadott játékosnak van-e lépési
     * lehetősége még a táblán.
     *
     * @param playerIndex A vizsgálandó játékos indexe.
     *
     * @return Igaz, ha van lehetősége lépni. Hamis, az ellenkező esetben.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isPossibilityToSpreadSomewhereByPlayer(byte playerIndex) throws TError {
        // Input checkings
        if(!this.getGameData().isPlayerExists(playerIndex)) {
            throw new TError("No player waas found at index " + playerIndex + "!");
        }

        byte[][] stands = this.getGameData().getCells();

        for(int columnIndex = 0; columnIndex < stands.length; columnIndex++) {
            for(int rowIndex = 0; rowIndex < stands[columnIndex].length; rowIndex++) {

                if(stands[columnIndex][rowIndex] == playerIndex) {

                    // Check app steps available of the cell
                    if(this.isPossibilityToSpreadFromThisCellByPlayer(columnIndex, rowIndex, playerIndex)) {
                        return true;
                    }
                    //---
                }

            }
        }
        return false;
    }

    /**
     * Annak meghatározása, hogy az adott játékosnak van-e lehetősége az adott
     * cellából valamerre lépni.
     *
     * @param columnIndex A kiindulási cella oszlopindexe.
     * @param rowIndex    A kiindulási cella sorindexe.
     * @param playerIndex A lépni szándékozó játékos indexe.
     *
     * @return Igaz, ha van legalább 1 lépési lehetőség.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isPossibilityToSpreadFromThisCellByPlayer(int columnIndex, int rowIndex, int playerIndex) throws TError {
        // If the cell is inactive
        if(this.getGameData().isCellInactive(columnIndex, rowIndex)) {
            return false;
        }

        // If the cell is not occupied at all
        if(!this.getGameData().isCellOccupied(columnIndex, rowIndex)) {
            return false;
        }

        // The specified cell is occupied but by an another player
        if(this.getGameData().getCell(columnIndex, rowIndex) != playerIndex) {
            return false;
        }

        // if there is no unoccupied cell in the attack area
        if(!this.isAnEmptyCellInAttackRange(columnIndex, rowIndex)) {
            return false;
        }

        return true;
    }

    /**
     * Annak ellenörzése, hogy a kiindulási cellához képest van-e olyan üres
     * cella, ami még lépési távolságban van.
     *
     * @param columnIndex A kiindulási cella oszlopindexe.
     * @param rowIndex    A kiindulási cella sorindexe.
     *
     * @return Igaz, ha van legalább 1 üres cella.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isAnEmptyCellInAttackRange(int columnIndex, int rowIndex) throws TError {
        //C-2
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 2, rowIndex + 0)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 2, rowIndex + 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 2, rowIndex + 2)) {
            return true;
        }

        //C-1
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 1, rowIndex - 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 1, rowIndex + 0)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 1, rowIndex + 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex - 1, rowIndex + 2)) {
            return true;
        }

        //C0
        if(this.isThisCellActiveAndNotOccupied(columnIndex, rowIndex - 2)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex, rowIndex - 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex, rowIndex + 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex, rowIndex + 2)) {
            return true;
        }

        //C+1
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 1, rowIndex - 2)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 1, rowIndex - 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 1, rowIndex + 0)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 1, rowIndex + 1)) {
            return true;
        }

        //C+2
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 2, rowIndex - 2)) {
            return true;
        }

        if(this.isThisCellActiveAndNotOccupied(columnIndex + 2, rowIndex - 1)) {
            return true;
        }
        if(this.isThisCellActiveAndNotOccupied(columnIndex + 2, rowIndex + 0)) {
            return true;
        }
        return false;
    }

    /**
     * Azt vizsgálja a metódus, hogy a kooridnátákkal hivatkozott cella, aktiv-e
     * és üres-e.
     *
     * @param columnIndex A viszgált cella oszlopindexe.
     * @param rowIndex    A viszgált cella sorindexe.
     *
     * @return Igaz, ha a cella üres és aktív. Hamis, az ellenkező esetben.
     *
     * @throws TError Thrown if an unrecoverable error was occurred.
     */
    private boolean isThisCellActiveAndNotOccupied(int columnIndex, int rowIndex) throws TError {
        if(this.getGameData().isCellExists(columnIndex, rowIndex)) {
            if(this.getGameData().getCell(columnIndex, rowIndex) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public final void startGame() throws TError {
        // Status checking
        if(this.getGameData().getGameProcessStatus() != ProcessStatusCode.PREPARED) {
            throw new TError("Wrong status found");
        }

        // Modify the status
        this.getGameData().setGameProcessStatus(IGameData.ProcessStatusCode.RUNNING);

        // Animation
        this.showOnAllGameBoard();      // Animation
        this.showOnAllGameInfo();       // Animation

        //
        this.nextRound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void pauseGame() throws TError {
        //---Note:
        boolean IsError = false;
        ProcessStatusCode CurrentStatus = this.getGameData().getGameProcessStatus();
        //---

        //---Note:
        if((CurrentStatus != ProcessStatusCode.RUNNING) && (CurrentStatus != ProcessStatusCode.PAUSED) && (CurrentStatus != ProcessStatusCode.STOPPED)) {
            IsError = true;
        }
        //---

        //---Note:
        if(!IsError) {
            //---Note:
            this.getGameData().setGameProcessStatus(IGameData.ProcessStatusCode.PAUSED);
            //---
        }
        else {

            throw new TError();
        }
        //---

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void continueGame() throws TError {
        //---Note:
        boolean IsError = false;
        ProcessStatusCode CurrentStatus = this.getGameData().getGameProcessStatus();
        //---

        //---Note:
        if((CurrentStatus != ProcessStatusCode.PAUSED) && (CurrentStatus != ProcessStatusCode.RUNNING)) {
            IsError = true;
        }
        //---

        //---Note:
        if(!IsError) {
            //---Note:
            this.getGameData().setGameProcessStatus(IGameData.ProcessStatusCode.RUNNING);
            //---
        }
        else {
            throw new TError();
        }
        //---

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stopGame() throws TError {
        //
        ProcessStatusCode currentStatus = this.getGameData().getGameProcessStatus();

        //
        if((currentStatus != ProcessStatusCode.PAUSED) && (currentStatus == ProcessStatusCode.RUNNING) && (currentStatus != ProcessStatusCode.STOPPED)) {
            this.getGameData().setGameProcessStatus(IGameData.ProcessStatusCode.STOPPED);
        }
        else {
            throw new TError("At this stage the request is rejected!");

        }

    }

    /**
     * Creates a player.
     *
     * @param playerColor The colour code of player.
     *
     * @return An {@code IPlayer} instance.
     */
    public final static IPlayer fabricateGamePlayer(String playerColor) {
        Player Output = new Player(playerColor);
        return Output;
    }

    /**
     * Creates a player.
     *
     * @param playerColor The colour code of player.
     * @param playerIndex The unique index of player.
     *
     * @return An {@code IPlayer} instance.
     */
    public final static IPlayer fabricateGamePlayer(String playerColor, byte playerIndex) {
        Player Output = new Player(playerColor, playerIndex);

        return Output;
    }

    /**
     * Creates a human player.
     *
     * @param playerColor The colour code of player.
     *
     * @return An {@code IPlayer} instance.
     */
    public static IPlayer fabricateHumanPlayer(String playerColor) {
        return new HumanPlayer(playerColor);
    }

    /**
     * Creates an artificial player.
     *
     * @param playerColor The colour code of player.
     *
     * @return An {@code IPlayer} instance.
     */
    public static IPlayer fabricateAutoPlayer(String playerColor) {
        return new AIPlayer(playerColor);
    }

    /**
     * {@inheritDoc}
     */
    public IGameData getGameData() {
        return (IGameData) super.getModel();
    }

    /**
     * {@inheritDoc}
     */
    public void setGameData(IGameData gameData) throws TError {
        // Input checkings
        if(gameData == null) {
            throw new TError("Property sould not be null");
        }
        super.setModel((IModel) gameData);
    }

    /**
     * {@inheritDoc}
     */
    public void addPlayBoard(IPlayBoard playBoard) throws TError {
        // Input checkings
        if(playBoard == null) {
            throw new TError("Property sould not be null");
        }
        // Store value
        super.addView((IView) playBoard);
    }

    /**
     * {@inheritDoc}
     */
    public void addInfoBoard(IInfoBoard infoBoard) throws TError {
        // Input checkings
        if(infoBoard == null) {
            throw new TError("Property sould not be null");
        }
        // Store value
        super.addView((IView) infoBoard);
    }
}
