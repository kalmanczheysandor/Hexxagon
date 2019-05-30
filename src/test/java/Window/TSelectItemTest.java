/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Support.IItem;
import Support.TError;
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
public class TSelectItemTest {

    public TSelectItemTest() {
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
        item = new TSelectItem(5);
        assertEquals(5, item.getKey());

        // Test: when value parameter is given
        item = new TSelectItem(7, "Value", "Caption");
        assertEquals("Value", item.getValue());

        // Test: when caption parameter is given
        TSelectItem selectItem = new TSelectItem(7, "Value", "Caption");
        assertEquals("Caption", selectItem.getCaption());
    }

    @Test
    public void test_getCaption() throws TError {
        TSelectItem item;
        
        // Test: when caption parameter is given
        item = new TSelectItem(5, "Value", "Caption");
        assertEquals("Caption", item.getCaption());
    }

    @Test
    public void test_setCaption() throws TError {
        TSelectItem item;
        
        // Test: when caption parameter is given
        item = new TSelectItem(5, "Value", "Caption");
        assertEquals("Caption", item.getCaption());
    }
}
