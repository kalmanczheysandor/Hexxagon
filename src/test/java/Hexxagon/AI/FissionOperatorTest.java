/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexxagon.AI;

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
public class FissionOperatorTest {

    public FissionOperatorTest() {
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
    public void test_generateAvailableOperatorsOnState() throws TError {

        // Test when 
        FissionOperator.generateAvailableOperatorsOnState(new BoardState(new byte[][]{{0, 0}, {0, 0}}, (byte) 1), (byte) 1);

        // Testing for TError: null board state
        try {
            FissionOperator.generateAvailableOperatorsOnState(null, (byte) 1);
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }

    }

    @Test
    public void test_isApliciableOnState() throws TError {
        IOperator operator;

        // Test: If step is permitted
        operator = new FissionOperator(0, 0, 1, 0);
        assertTrue(operator.isApliciableOnState(new BoardState(new byte[][]{{1, 0}, {0, 0}}, (byte) 1)));

        // Test: If base and target cell are same
        operator = new FissionOperator(0, 0, 0, 0);
        assertFalse(operator.isApliciableOnState(new BoardState(new byte[][]{{1, 0}, {0, 0}}, (byte) 1)));

        // Test: If step out of range
        operator = new FissionOperator(0, 0, 10, 10);
        assertFalse(operator.isApliciableOnState(new BoardState(new byte[][]{{1, 0}, {0, 0}}, (byte) 1)));

        // Test: If target is occupied
        operator = new FissionOperator(0, 0, 1, 0);
        assertFalse(operator.isApliciableOnState(new BoardState(new byte[][]{{1, 0}, {1, 0}}, (byte) 1)));
    }

    @Test
    public void test_applyIt() throws TError {
        IOperator operator;

        // Test: If step is permitted
        operator = new FissionOperator(0, 0, 1, 0);
        if(!(new BoardState(new byte[][]{{1, 0}, {1, 0}}, (byte) 2)).equals(operator.applyIt(new BoardState(new byte[][]{{1, 0}, {0, 0}}, (byte) 1)))) {
            fail();
        }
        
        // Testing for TError: out of range
        try {
            operator = new FissionOperator(0, 0, 2, 0);
            operator.applyIt(new BoardState(new byte[][]{{1, 0}, {0, 0}}, (byte) 1));
            fail();                                 // If no TError was thrown.
        }
        catch(TError exp) {

        }
    }

}
