package Window;

import Support.TError;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
public class ContainerTest {

    public ContainerTest() {
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
    public void test_constructor() throws TError {
        IContainer container;

        // Test: when no error
        container = new Container(new Pane());

        // Test for TError: When parameter is null
        try {
            container = new Container(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_setStorage() throws TError {
        IContainer container;

        // Test: when everithing is proper
        container = new Container(new Pane());
        IStorage storage = new Storage();
        container.setStorage(storage);
        assertEquals(storage, container.getStorage());

        // Test for TError: When parameter is null
        container = new Container(new Pane());
        try {
            container.setStorage(null);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_getStorage() throws TError {
        IContainer container;

        // Test: when everithing is proper
        container = new Container(new Pane());
        IStorage storage = new Storage();
        container.setStorage(storage);
        assertEquals(storage, container.getStorage());
    }

    @Test
    public void test_setIntroPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When parameter is null
        IIntroPage page = new IntroPage(root);
        container = new Container(root);
        //container.setIntroPage(page);         // Impossible to test

    }

    @Test
    public void test_setSettingsPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When parameter is null
        ISettingsPage page = new SettingsPage(root);
        container = new Container(root);
        //container.setIntroPage(page);         // Impossible to test
    }

    @Test
    public void test_setPlayPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When parameter is null
        IPlayPage page = new PlayPage(root);
        container = new Container(root);
        //container.setIntroPage(page);         // Impossible to test
    }

    @Test
    public void test_getIntroPage() throws TError {
        // Impossible to test
    }

    @Test
    public void test_getSettingsPage() throws TError {
        // Impossible to test
    }

    @Test
    public void test_getPlayPage() throws TError {
        // Impossible to test
    }

    @Test
    public void test_showIntroPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When no page is set
        container = new Container(root);
        try {
            container.showIntroPage();
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_showSettingsPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When no page is set
        container = new Container(root);
        try {
            container.showSettingsPage();
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_showPlayPage() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When no page is set
        container = new Container(root);
        try {
            container.showPlayPage();
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

    }

    @Test
    public void test_selectDifficulty() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When difficulty index not exists
        container = new Container(root);
        IStorage storage = new Storage();
        container.setStorage(storage);
        try {
            container.selectDifficulty(99999);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_selectBoardMode() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: When index not exists
        container = new Container(root);
        IStorage storage = new Storage();
        container.setStorage(storage);
        try {
            container.selectBoardMode(99999);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_startGame() throws TError {
        IContainer container;
        StackPane root = new StackPane();

        // Test for TError: 
        container = new Container(root);
        IStorage storage = new Storage();
        container.setStorage(storage);
        try {
            container.startGame();
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

    @Test
    public void test_stopGame() throws TError {
 
    }

}
