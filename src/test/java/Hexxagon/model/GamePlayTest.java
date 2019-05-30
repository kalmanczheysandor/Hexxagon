package Hexxagon.model;

import Hexxagon.Controller.Player;
import Support.TError;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Hexxagon.Controller.IPlayer;
import Hexxagon.Model.GamePlay;

/**
 *
 * @author ALX
 */
public class GamePlayTest {

    public GamePlayTest() {
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
    public void test_setPlayerList() throws TError {
        ArrayList<IPlayer> list;
        GamePlay gamePlay;

        // Test: When it is correct
        list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        list.add(new Player((byte) 3));
        gamePlay = new GamePlay();
        gamePlay.setPlayerList(list);

        // Test for TError: When null value not accepted
        gamePlay = new GamePlay();
        try {
            gamePlay.setPlayerList(null);
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When duplicated playerIndex
        list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        list.add(new Player((byte) 1));
        gamePlay = new GamePlay();
        try {
            gamePlay.setPlayerList(list);
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }
    }

    @Test
    public void test_setBoardShape() throws TError {
        GamePlay gamePlay;
        ArrayList<IPlayer> list;

        // Test: When enough item in both first and second dimension
        gamePlay = new GamePlay();
        list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        list.add(new Player((byte) 3));
        list.add(new Player((byte) 4));
        list.add(new Player((byte) 5));
        list.add(new Player((byte) 6));
        gamePlay.setPlayerList(list);
        gamePlay.setBoard(new byte[][]{{0, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}, {1, 2, 3, 4, 5, 6, 1, 2, 3}});

        // Test for TError: When null value not accepted
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(null);
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When Zero item in first dimension
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(new byte[][]{});
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When less than expected item in first dimension
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(new byte[][]{{1}, {1}, {1}});
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When more than expected item in first dimension
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(new byte[][]{{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}});
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When less than expected item in second dimension
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(new byte[][]{{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}});
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When more than expected item in second dimension
        gamePlay = new GamePlay();
        try {
            gamePlay.setBoard(new byte[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}});
            fail();                 // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

    }

    @Test
    public void test_setAllowedPlayerIndex() throws TError {
        GamePlay gamePlay;
        ArrayList<IPlayer> list;

        // Test: When it is correct
        list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        list.add(new Player((byte) 3));

        gamePlay = new GamePlay();
        gamePlay.setPlayerList(list);
        gamePlay.setAllowedPlayerIndex((byte) 3);

        // Test for TError: When referencing a not existing player on an empty list
        gamePlay = new GamePlay();
        try {
            gamePlay.setAllowedPlayerIndex((byte) 9);
            fail();             // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

        // Test for TError: When referencing a not existing player on a not empty list
        list = new ArrayList<>();
        list.add(new Player((byte) 1));
        list.add(new Player((byte) 2));
        list.add(new Player((byte) 3));
        gamePlay = new GamePlay();
        gamePlay.setPlayerList(list);
        try {
            gamePlay.setAllowedPlayerIndex((byte) 9);

            fail();             // if no error was thrown than test should be failed
        }
        catch(TError err) {

        }

    }
}
