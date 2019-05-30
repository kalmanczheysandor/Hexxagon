/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Hexxagon.Model.GameData;
import Hexxagon.Model.IGameData;
import Support.IItem;
import Support.TError;
import Support.TItem;
import Window.IStorage.IBoardModeItem;
import Window.Storage.BoardModeItem;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ALX
 */
public class StorageTest {

    public StorageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_setGameData() throws TError {
        IStorage storage;
        IGameData gameData;

        // Test: when everithing is proper
        storage = new Storage();
        gameData = new GameData();
        storage.setGameData(gameData);

        // Test for TError: When parameter is null
        storage = new Storage();
        try {
            storage.setGameData(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getGameData() throws TError {
        IStorage storage;
        IGameData gameData;

        // Test: when everithing is proper
        storage = new Storage();
        gameData = new GameData();
        storage.setGameData(gameData);
        assertEquals(gameData, storage.getGameData());

    }

    @Test
    public void test_setSelectedBoardIndex() throws TError {
        IStorage storage;

        // Test for TError: When index does not exists
        storage = new Storage();
        try {
            storage.setSelectedBoardIndex(-100);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getSelectedBoardIndex() throws TError {
    }

    @Test
    public void test_getSelectedDifficultyIndex() throws TError {

    }

    @Test
    public void test_setSelectedDifficultyIndex() throws TError {
        IStorage storage;

        // Test for TError: When index does not exists
        storage = new Storage();
        try {
            storage.setSelectedDifficultyIndex(-100);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_addBoardMode() throws TError {
        IStorage storage;

        // Test: when everithing is proper
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));

        // Test for TError: When parameter is null
        storage = new Storage();
        try {
            storage.addBoardMode(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    public void test_addDifficultyType() throws TError {

        IStorage storage;

        // Test: when everithing is proper
        storage = new Storage();
        storage.addDifficultyType(0, 1, "");

        // Test for TError: first parameter is lower than 0
        storage = new Storage();
        try {
            storage.addDifficultyType(-1, 1, "");
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: second parameter is lower than 1
        storage = new Storage();
        try {
            storage.addDifficultyType(0, 0, "");
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: third parameter is null
        storage = new Storage();
        try {
            storage.addDifficultyType(0, 1, null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getDifficultyTypeList() throws TError {
        IStorage storage;

        // Test: when everithing is proper
        storage = new Storage();
        storage.addDifficultyType(0, 1, "X1");
        storage.addDifficultyType(1, 2, "X2");
        storage.addDifficultyType(2, 3, "X3");
        ArrayList<TSelectItem> list = new ArrayList<>();
        list.add(new TSelectItem(0, 1, "X1"));
        list.add(new TSelectItem(1, 2, "X2"));
        list.add(new TSelectItem(2, 3, "X3"));

        assertEquals(list, storage.getDifficultyTypeList());
    }

    @Test
    public void test_getBoardModeList() throws TError {
        IStorage storage;

        // Test: when everithing is proper
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));
        storage.addBoardMode(new BoardModeItem(2));
        storage.addBoardMode(new BoardModeItem(3));
        ArrayList<IBoardModeItem> list = new ArrayList<>();
        list.add(new BoardModeItem(1));
        list.add(new BoardModeItem(2));
        list.add(new BoardModeItem(3));

        assertEquals(list, storage.getBoardModeList());
    }

    @Test
    public void test_isBoardModeExists() throws TError {
        IStorage storage;

        // Test: when exists
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));
        assertTrue(storage.isBoardModeExists(1));

        // Test: when does not exist
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(2));
        assertFalse(storage.isBoardModeExists(1));

    }

    @Test
    public void test_getBoardMode() throws TError {
        IStorage storage;

        // Test: when equals
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));
        assertEquals(new BoardModeItem(1), storage.getBoardMode(1));

        // Test: when not equals
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));
        assertNotEquals((IBoardModeItem) new BoardModeItem(2), storage.getBoardMode(1));

        // Test for TError: parameter is lower than 0
        storage = new Storage();
        try {
            storage.getBoardMode(0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when no item is found at index
        storage = new Storage();
        storage.addBoardMode(new BoardModeItem(1));
        try {
            storage.getBoardMode(2);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_isDifficultyExists() throws TError {

        IStorage storage;

        // Test: when exists
        storage = new Storage();
        storage.addDifficultyType(0, 1, "");
        assertTrue(storage.isDifficultyExists(0));

        // Test: when does not exist
        storage = new Storage();
        storage.addDifficultyType(0, 1, "");
        assertFalse(storage.isDifficultyExists(1));

    }

    @Test
    public void test_getDifficultyValue() throws TError {
        IStorage storage;

        // Test: when exists
        storage = new Storage();
        storage.addDifficultyType(0, 9, "");
        assertEquals(9, storage.getDifficultyValue(0));

        // Test for TError: when no idex is lower than 0
        storage = new Storage();
        storage.addDifficultyType(0, 9, "");
        try {
            storage.getDifficultyValue(-1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when no item is found at specified index
        storage = new Storage();
        storage.addDifficultyType(0, 9, "");
        try {
            storage.getDifficultyValue(1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_constructor() throws TError {
        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(5);

        // Test for TError: when index is lower than zero
        try {
            item = new BoardModeItem(-1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_isAIPlayerAdded() throws TError {

        IBoardModeItem item;

        // Test: when at least one AI palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", true);
        assertTrue(item.isAIPlayerAdded());

        // Test: when no AI palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", false);
        assertFalse(item.isAIPlayerAdded());

    }

    @Test
    public void test_BoardModeItemClass_setIndex() throws TError {
        IBoardModeItem item;

        // Test: when value is changed through setter
        item = new BoardModeItem(5);
        item.setIndex(6);
        assertEquals(6, item.getIndex());

        // Test for TError: when index is lower than zero
        item = new BoardModeItem(0);
        try {
            item.setIndex(-1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_getIndex() throws TError {
        IBoardModeItem item;

        // Test: when value is given through constructor
        item = new BoardModeItem(5);
        assertEquals(5, item.getIndex());

        // Test: when value is changed through setter
        item = new BoardModeItem(5);
        item.setIndex(6);
        assertEquals(6, item.getIndex());
    }

    @Test
    public void test_BoardModeItemClass_getCaption() throws TError {
        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(0);
        item.setCaption("XXX");
        assertEquals("XXX", item.getCaption());
    }

    @Test
    public void test_BoardModeItemClass_setCaption() throws TError {
        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(0);
        item.setCaption("XXX");
        assertEquals("XXX", item.getCaption());

        // Test for TError: when value is null
        item = new BoardModeItem(0);
        try {
            item.setCaption(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_getBoard() throws TError {
        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(0);
        item.setBoard(new byte[][]{{0, 0}, {0, 0}});
        assertArrayEquals(new byte[][]{{0, 0}, {0, 0}}, item.getBoard());
    }

    @Test
    public void test_BoardModeItemClass_setBoard() throws TError {
        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(0);
        item.setBoard(new byte[][]{{0, 0}, {0, 0}});
        assertArrayEquals(new byte[][]{{0, 0}, {0, 0}}, item.getBoard());

        // Test for TError: when value is null
        item = new BoardModeItem(0);
        try {
            item.setBoard(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_getHumanPlayerCount() throws TError {
        IBoardModeItem item;

        // Test: when 0 human palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", true);
        assertEquals(0, item.getHumanPlayerCount());

        // Test: when 1 human palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", false);
        assertEquals(1, item.getHumanPlayerCount());

        // Test: when 2 human palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", false);
        item.addPlayer((byte) 2, "", false);
        assertEquals(2, item.getHumanPlayerCount());

    }

    @Test
    public void test_BoardModeItemClass_getAutoPlayerCount() throws TError {
        IBoardModeItem item;

        // Test: when 0 AI palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", false);
        assertEquals(0, item.getAutoPlayerCount());

        // Test: when 1 AI palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", true);
        assertEquals(1, item.getAutoPlayerCount());

        // Test: when 2 AI palyer exists 
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", true);
        item.addPlayer((byte) 2, "", true);
        assertEquals(2, item.getAutoPlayerCount());
    }

    @Test
    public void test_BoardModeItemClass_addPlayer() throws TError {

        IBoardModeItem item;

        // Test: when everithing is proper
        item = new BoardModeItem(0);
        item.addPlayer((byte) 1, "", true);

        // Test for TError: when index is lower than 1
        item = new BoardModeItem(0);
        try {
            item.addPlayer((byte) 0, "", true);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Test for TError: when color parameter is null
        item = new BoardModeItem(0);
        try {
            item.addPlayer((byte) 1, null, true);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_BoardModeItemClass_getPlayerList() throws TError {
        // Impossible to test
    }

    @Test
    public void test_BoardModeItemClass_equals() throws TError {
        IBoardModeItem item;
        
        // Test: when it is equal
        item    = new BoardModeItem(0);
        if(!item.equals(new BoardModeItem(0))) {
            fail();
        }
        
        // Test: when it is not equal
        item    = new BoardModeItem(0);
        if(item.equals(new BoardModeItem(5))) {
            fail();
        }
        
    }
}
