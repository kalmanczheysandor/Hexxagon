/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexxagon.AI;

import Support.TError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ALX
 */
public class BoardStateTest {

    public BoardStateTest() {
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
    public void test_isThePlayerHasNoBall() throws TError {
        byte[][] board = {{0, 0, 2}, {0, 0, 0}, {0, 0, 0}};

        IBoardState boardState = new BoardState(board, (byte) 1);

        // Testing for player 1 who has no ball
        assertTrue(boardState.isThePlayerHasNoBall((byte) 1));

        // Testing for player 2 who has a ball
        assertFalse(boardState.isThePlayerHasNoBall((byte) 2));

        // Testing for TError: when player index is wrong
        try {
            boardState.isThePlayerHasNoBall((byte) 3);
            fail(); // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_isAnybodyHasNoBall() throws TError {
        IBoardState boardState;

        // Testing when somebody has no ball
        boardState = new BoardState(new byte[][]{{0, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertTrue(boardState.isAnybodyHasNoBall());

        // Testing when everybody has ball
        boardState = new BoardState(new byte[][]{{0, 1, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertFalse(boardState.isAnybodyHasNoBall());
    }

    @Test
    public void test_isBoardFull() throws TError {
        IBoardState boardState;

        // Testing when board is not full
        boardState = new BoardState(new byte[][]{{0, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertFalse(boardState.isBoardFull());

        // Testing when board is full
        boardState = new BoardState(new byte[][]{{1, 1, 2}, {-1, -1, -1}, {-1, -1, 1}}, (byte) 1);
        assertTrue(boardState.isBoardFull());

    }

    @Test
    public void test_isCellEmptyAndActive() throws TError {
        IBoardState boardState;

        // Testing when cell is empty and active
        boardState = new BoardState(new byte[][]{{0, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertTrue(boardState.isCellEmptyAndActive(0, 0));

        // Testing when cell is occupied and active
        boardState = new BoardState(new byte[][]{{1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertFalse(boardState.isCellEmptyAndActive(0, 0));

        // Testing when cell is inactive
        boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
        assertFalse(boardState.isCellEmptyAndActive(0, 0));

        // Testing for TError: coordinate x is wrong
        try {
            boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
            boardState.isCellEmptyAndActive(-1, 0);
            fail(); // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Testing for TError: coordinate y is wrong
        try {
            boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
            boardState.isCellEmptyAndActive(0, -1);
            fail(); // If no TError was thrown.
        }
        catch(TError exp) {

        }

    }

    @Test
    public void test_setCellValue() throws TError {
        IBoardState boardState;

        // Test for value changing
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        boardState.setCellValue(0, 0, (byte) 2);
        boardState.setCellValue(1, 1, (byte) 2);
        assertArrayEquals(new byte[][]{{2, 0}, {0, 2}}, boardState.getBoard());
        
        // Testing for TError: coordinate x is wrong
        try {
            boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
            boardState.setCellValue(-1, 0, (byte) 0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Testing for TError: coordinate y is wrong
        try {
            boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
            boardState.setCellValue(0, -1, (byte) 0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Testing for TError: given value is wrong
        try {
            boardState = new BoardState(new byte[][]{{-1, 0, 2}, {0, 0, 0}, {0, 0, 0}}, (byte) 1);
            boardState.setCellValue(0, 0, (byte) 7);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getEmptyCellCount() throws TError {
        IBoardState boardState;

        // Test when having 4 ball
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertEquals(4, boardState.getEmptyCellCount());

        // Test when having 0 ball
        boardState = new BoardState(new byte[][]{{-1, -1}, {1, 2}}, (byte) 1);
        assertEquals(0, boardState.getEmptyCellCount());
    }

    @Test
    public void test_getPlayerBallCount() throws TError {
        IBoardState boardState;

        // Testing when player has no ball
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertEquals(0, boardState.getPlayerBallCount((byte) 1));

        // Testing when player 1 has 2 ball
        boardState = new BoardState(new byte[][]{{1, 1}, {0, 0}}, (byte) 1);
        assertEquals(2, boardState.getPlayerBallCount((byte) 1));

        // Testing when player 2 has 1 ball
        boardState = new BoardState(new byte[][]{{1, 1}, {2, 0}}, (byte) 1);
        assertEquals(1, boardState.getPlayerBallCount((byte) 2));

        // Testing for TError: player index is too low
        try {
            boardState = new BoardState(new byte[][]{{1, 1}, {2, 0}}, (byte) 1);
            boardState.getPlayerBallCount((byte) -1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Testing for TError: player index is too low
        try {
            boardState = new BoardState(new byte[][]{{1, 1}, {2, 0}}, (byte) 1);
            boardState.getPlayerBallCount((byte) 0);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

        // Testing for TError: player index is too high
        try {
            boardState = new BoardState(new byte[][]{{1, 1}, {2, 0}}, (byte) 1);
            boardState.getPlayerBallCount((byte) 3);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

    }

    @Test
    public void test_getActiveCellCount() throws TError {
        IBoardState boardState;

        // Testing when there are active cells
        boardState = new BoardState(new byte[][]{{0, 1}, {2, -1}}, (byte) 1);
        assertEquals(3, boardState.getActiveCellCount());

        // Testing when there is no active cell
        boardState = new BoardState(new byte[][]{{-1, -1}, {-1, -1}}, (byte) 1);
        assertEquals(0, boardState.getActiveCellCount());

    }

    @Test
    public void test_getBoard() throws TError {
        IBoardState boardState;
     
        // Test whether the given input is equal with a given output
        boardState = new BoardState(new byte[][]{{0, 1}, {2, -1}}, (byte) 1);
        assertArrayEquals(new byte[][]{{0, 1}, {2, -1}}, boardState.getBoard());
    }

    @Test
    public void test_getActualPlayerIndex() throws TError {
        IBoardState boardState;
        
        // Testing fro player 1
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertEquals(1, boardState.getActualPlayerIndex());
        
        
        // Testing fro player 2
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 2);
        assertEquals(2, boardState.getActualPlayerIndex());
    }
    
    @Test
    public void test_getEnemyPlayerIndex() throws TError {
        IBoardState boardState;
        
        // Testing fro player 1
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertEquals(2, boardState.getEnemyPlayerIndex());
        
        
        // Testing fro player 2
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 2);
        assertEquals(1, boardState.getEnemyPlayerIndex());
    }
    
    @Test
    public void test_isOperatorInValidRange() throws TError {
        IBoardState boardState;
        
        // Test when fission operator is in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertTrue(boardState.isOperatorInValidRange(new FissionOperator(0, 0, 1, 1)));
        
        // Test when fission operator is not in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertFalse(boardState.isOperatorInValidRange(new FissionOperator(0, 0, 2, 2)));
        
        // Test when jump operator is in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertTrue(boardState.isOperatorInValidRange(new JumpOperator(0, 0, 1, 1)));
        
        // Test when jump operator is not in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertFalse(boardState.isOperatorInValidRange(new JumpOperator(0, 0, 2, 2)));
        
        // Test when impossible operator is in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertTrue(boardState.isOperatorInValidRange(new ImpossibleOperator(0, 0, 1, 1)));
        
        // Test when impossible operator is not in range
        boardState = new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1);
        assertFalse(boardState.isOperatorInValidRange(new ImpossibleOperator(0, 0, 2, 2)));
        
        
    } 
    
    
    
}
