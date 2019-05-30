/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

import Window.TSelectItem;
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
public class TItemTest {

    public TItemTest() {
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
        IItem item;

        // Test: when key parameter is given
        item = new TItem(5);
        assertEquals(5, item.getKey());

        // Test: when value parameter is given
        item = new TItem(7, "Value");
        assertEquals("Value", item.getValue());
    }

    @Test
    public void test_getKey() throws TError {
        IItem item;

        // Test: when key is set by constructor
        item = new TItem(0);
        assertEquals(0, item.getKey());

        // Test: when key is changed by setter
        item = new TItem(0);
        item.setKey(7);
        assertEquals(7, item.getKey());
    }

    @Test
    public void test_setKey() throws TError {
        IItem item;

        // Test: when key is set by constructor
        item = new TItem(0);
        assertEquals(0, item.getKey());

        // Test: when key is changed by setter
        item = new TItem(0);
        item.setKey(7);
        assertEquals(7, item.getKey());
    }

    @Test
    public void test_getValue() throws TError {
        IItem item;

        // Test: when key is set by constructor
        item = new TItem(0, "ABC");
        assertEquals("ABC", item.getValue());

        // Test: when key is changed by setter
        item = new TItem(1, "ABC");
        item.setValue("CVB");
        assertEquals("CVB", item.getValue());
    }

    @Test
    public void test_setValue() throws TError {
        IItem item;

        // Test: when key is set by constructor
        item = new TItem(0, "ABC");
        assertEquals("ABC", item.getValue());

        // Test: when key is changed by setter
        item = new TItem(1, "ABC");
        item.setValue("CVB");
        assertEquals("CVB", item.getValue());
    }

    @Test
    public void test_getValueAsString() throws TError {
        // Not tested because function is not fully developed
    }

    @Test
    public void test_getValueAsInt() throws TError {
        // Not tested because function is not fully developed
    }

    @Test
    public void test_setValueAsString() throws TError {
        // Not tested because function is not fully developed
    }

    @Test
    public void test_equals() throws TError {
        IItem item;

        // Test: when it is equal
        item = new TItem(0);
        if(!item.equals(new TItem(0))) {
            fail();
        }

        // Test: when it is not equal
        item = new TItem(0);
        if(item.equals(new TItem(5))) {
            fail();
        }
    }

}
