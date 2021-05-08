/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.HoaDonDTO;
import java.time.LocalDate;
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
public class HoaDonBUSTest {
    
    public HoaDonBUSTest() {
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
     * Test of bigNum method, of class HoaDonBUS.
     */
    @Test
    public void testBigNum() {
        System.out.println("bigNum");
        HoaDonBUS instance = new HoaDonBUS();
        String expResult = "13";
        String result = instance.bigNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNextMaHD method, of class HoaDonBUS.
     */
    @Test
    public void testGetNextMaHD() {
        System.out.println("getNextMaHD");
        HoaDonBUS instance = new HoaDonBUS();
        String expResult = "HD13";
        String result = instance.getNextMaHD();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHD method, of class HoaDonBUS.
     */
    @Test
    public void testGetHD() {
        System.out.println("getHD");
        String mahd = "";
        HoaDonBUS instance = new HoaDonBUS();
        String expResult = null;
        HoaDonDTO result = instance.getHD(mahd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class HoaDonBUS.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        HoaDonBUS instance = new HoaDonBUS();
        instance.getData();
        // 0TODO review the generated test code and remove the default call to fail.
        assertFalse(instance.list.equals(null));
    }

    /**
     * Test of Search method, of class HoaDonBUS.
     */
    @Test
    public void testSearch() {
        System.out.println("Search");
        String value = "";
        String type = "";
        LocalDate date1 = null;
        LocalDate date2 = null;
        int gia1 = 0;
        int gia2 = 0;
        HoaDonBUS instance = new HoaDonBUS();
        ArrayList<HoaDonDTO> expResult = null;
        ArrayList<HoaDonDTO> result = instance.Search(value, type, date1, date2, gia1, gia2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
