/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.BaoHanhDTO;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vind
 */
public class BaoHanhDAOTest {
    
    public BaoHanhDAOTest() {
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

    /**
     * Test of insertBaoHanh method, of class BaoHanhDAO.
     */
    @Test
    public void testInsertBaoHanh() {
        System.out.println("insertBaoHanh");
        BaoHanhDTO sp = null;
        boolean expResult = false;
        boolean result = BaoHanhDAO.insertBaoHanh(sp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaoHanh method, of class BaoHanhDAO.
     */
    @Test
    public void testGetBaoHanh() {
        System.out.println("getBaoHanh");
        ArrayList<BaoHanhDTO> expResult = null;
        ArrayList<BaoHanhDTO> result = BaoHanhDAO.getBaoHanh();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBaoHanh method, of class BaoHanhDAO.
     */
    @Test
    public void testUpdateBaoHanh() {
        System.out.println("updateBaoHanh");
        BaoHanhDTO sp = null;
        boolean expResult = false;
        boolean result = BaoHanhDAO.updateBaoHanh(sp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
