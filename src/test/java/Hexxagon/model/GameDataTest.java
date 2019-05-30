/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexxagon.model;

import Hexxagon.Controller.IPlayer;
import Hexxagon.Controller.Player;
import Support.TError;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Hexxagon.Model.GamePlay;
import Hexxagon.Model.IGameData;
import Hexxagon.Model.GameData;
import Hexxagon.Model.GamePlay.Field;
import Hexxagon.Model.IGamePlay;
import Hexxagon.Model.IField;

/**
 *
 * @author ALX
 */
public class GameDataTest {

    private IGamePlay gamePlay001;

    public GameDataTest() {

        
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws TError {
        
        // Initialisation of game play
        gamePlay001 = new GamePlay();
        ArrayList<IPlayer> list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        gamePlay001.setPlayerList(list);
        gamePlay001.setBoard(new byte[][]{{1, 2, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}});

    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_isCellExists() throws TError {
        IGameData gameData;

        // Test: when lowest exists
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertTrue(gameData.isCellExists(0, 0));

        // Test: when highest exists
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertTrue(gameData.isCellExists(IGamePlay.maximumExpectedStandColumnIndex, IGamePlay.maximumExpectedStandRowIndex));

        // Test: when column index is out of range (to low)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertFalse(gameData.isCellExists(-1, IGamePlay.maximumExpectedStandRowIndex));

        // Test: when column index is out of range (to high)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertFalse(gameData.isCellExists(IGamePlay.maximumExpectedStandColumnIndex + 1, IGamePlay.maximumExpectedStandRowIndex));

        // Test: when row index is out of range (to low)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertFalse(gameData.isCellExists(IGamePlay.maximumExpectedStandColumnIndex, -1));

        // Test: when row index is out of range (to high)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertFalse(gameData.isCellExists(IGamePlay.maximumExpectedStandColumnIndex, IGamePlay.maximumExpectedStandRowIndex + 1));

    }

    @Test
    public void test_getCellAsField() throws TError {
        IGameData gameData;
        IField field;

        // Test: when it is equal
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        field = new Field(0, 0);
        field.setPlayerIndex((byte) 1);
        field.setIsInactive(false);
        assertEquals(field, gameData.getCellAsField(0, 0));

        // Test: when it is not equal
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        field = new Field(0, 0);
        field.setPlayerIndex((byte) 2);
        field.setIsInactive(false);
        assertNotEquals(field, gameData.getCellAsField(0, 0));

        // Test for TError: when column index is out of range (to low)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.getCellAsField(-1, 0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when column index is out of range (to high)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.getCellAsField(IGamePlay.maximumExpectedStandColumnIndex + 1, 0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when row index is out of range (to low)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.getCellAsField(0, -1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when row index is out of range (to high)
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.getCellAsField(0, IGamePlay.maximumExpectedStandRowIndex + 1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_lowestPlayerIndex() throws TError {
        IGameData gameData;

        // Test: when everithing is proper
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertEquals(1, gameData.lowestPlayerIndex());

        // Test for TError: when no game play is set
        gameData = new GameData();
        try {
            gameData.lowestPlayerIndex();
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_isPlayerExists() throws TError {
        IGameData gameData;

        // Test: when player exists
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertTrue(gameData.isPlayerExists((byte) 1));

        // Test: when player exists
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertTrue(gameData.isPlayerExists((byte) 2));

        // Test: when player does not exist
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertFalse(gameData.isPlayerExists((byte) 3));

        // Test: when no game play is set
        gameData = new GameData();
        assertFalse(gameData.isPlayerExists((byte) 1));

    }

    @Test
    public void test_getPlayer() throws TError {

        IGameData gameData;
        IPlayer player;

        // Test: when player exists
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        player = new Player((byte) 1);
        assertEquals(player, gameData.getPlayer((byte) 1));

        // Test: when player does not equal
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        player = new Player((byte) 2);
        assertNotEquals(player, gameData.getPlayer((byte) 1));

        // Test for TError: when no game play is set
        gameData = new GameData();
        try {
            gameData.getPlayer((byte) 1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

    }

    @Test
    public void test_setPlayerAsDefeated() throws TError {
        IGameData gameData;
        IPlayer player;

        // Test: when player exists and defeated
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        gameData.setPlayerAsDefeated((byte)1);
        assertTrue(gameData.isPlayerDefeated((byte)1));
        
        // Test: when player exists and defeated
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        gameData.setPlayerAsDefeated((byte)1);
        assertFalse(gameData.isPlayerDefeated((byte)2));
        
        
        // Test for TError: when no game play is set
        gameData = new GameData();
        try {
            gameData.setPlayerAsDefeated((byte)1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
        
         // Test for TError: when player does not exist
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.setPlayerAsDefeated((byte)9);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_isPlayerDefeated() throws TError {
        IGameData gameData;
        IPlayer player;

        // Test: when player exists and defeated
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        gameData.setPlayerAsDefeated((byte)1);
        assertTrue(gameData.isPlayerDefeated((byte)1));
        
        // Test: when player exists and defeated
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        gameData.setPlayerAsDefeated((byte)1);
        assertFalse(gameData.isPlayerDefeated((byte)2));
        
        
        // Test for TError: when no game play is set
        gameData = new GameData();
        try {
            gameData.isPlayerDefeated((byte)1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
        
         // Test for TError: when player does not exist
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.isPlayerDefeated((byte)9);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getPlayerBallCount() throws TError {
        IGameData gameData;
        IPlayer player;

        // Test: when player one has 1 ball
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        assertEquals(1,gameData.getPlayerBallCount((byte)1));
        
        
         // Test for TError: when no game play is set
        gameData = new GameData();
        try {
            gameData.getPlayerBallCount((byte)1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
        
         // Test for TError: when player does not exist
        gameData = new GameData();
        gameData.setActualGamePlay(gamePlay001);
        try {
            gameData.getPlayerBallCount((byte)9);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getPlayerList() throws TError {

        
       /* 
        
         ArrayList<IPlayer> list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));*/
    }

    @Test
    public void test_getAllowedPlayerIndex() throws TError {

    }

    @Test
    public void test_setAllowedPlayerIndex() throws TError {

    }

    @Test
    public void test_getCells() throws TError {

    }

    @Test
    public void test_getCell() throws TError {

    }

    @Test
    public void test_isCellInactive() throws TError {

    }

    @Test
    public void test_isCellOccupied() throws TError {

    }

    @Test
    public void test_setCellOccupiedByPlayer() throws TError {

    }

    @Test
    public void test_setCellLiberated() throws TError {

    }

    @Test
    public void test_setBoardShapeOfActualGamePlay() throws TError {

    }

    @Test
    public void test_getPreviousClickedField() throws TError {

    }

    @Test
    public void test_setPreviousClickedField() throws TError {

    }

    @Test
    public void test_removeLastClickedField() throws TError {

    }

    @Test
    public void test_isBoardFull() throws TError {

    }

    @Test
    public void test_defeatedPlayerCount() throws TError {

    }

    @Test
    public void test_playerCount() throws TError {

    }

    @Test
    public void test_getPlayerPositions() throws TError {

    }

    @Test
    public void test_destroyActualGamePlay() throws TError {

    }

    @Test
    public void test_loadGamePlay() throws TError {

    }

    @Test
    public void test_saveGamePlay() throws TError {

    }

    @Test
    public void test_setActualGamePlay() throws TError {

    }

    @Test
    public void test_isGameFinished() throws TError {

    }

    @Test
    public void test_setIsGameFinished() throws TError {

    }

    @Test
    public void test_fabricateIGamePlay() throws TError {

    }

    @Test
    public void test_setGameProcessStatus() throws TError {

    }

    @Test
    public void test_getGameProcessStatus() throws TError {

    }

    @Test
    public void test_getStatus() throws TError {

    }

    @Test
    public void test_setGamePlayStatus() throws TError {

    }
}
