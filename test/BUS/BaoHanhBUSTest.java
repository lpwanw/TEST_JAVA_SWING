/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.BaoHanhDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import BUS.Tool;

/**
 *
 * @author Vind
 */
public class BaoHanhBUSTest {
    String filePath = "./TestFile/BHBUS.xlsx";
    public BaoHanhBUSTest() {
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
     * Test of getData method, of class BaoHanhBUS.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        BaoHanhBUS instance = new BaoHanhBUS();
        try {
            instance.getData();
            assertFalse(instance.list.equals(null));
        }catch(Exception e){
            fail("Cant Get Data from DB");
        }
    }

    /**
     * Test of getds method, of class BaoHanhBUS.
     */
    @Test
    public void testGetds() {
        System.out.println("getds");
        BaoHanhBUS instance = new BaoHanhBUS();
        try{
        ArrayList<BaoHanhDTO> result = instance.getds();
            assertFalse(result.equals(null));
        }catch(Exception e){
            fail("Cant get ds");
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of search method, of class BaoHanhBUS.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        BaoHanhBUS instance = new BaoHanhBUS();
        for(int i = 5; i<=10 ;i++){
        String value = Tool.getCellStringValue(0, i, 1, filePath);
        String Type = Tool.getCellStringValue(0, i, 2, filePath);
        LocalDate ngay_1 = Tool.getCellDate(0, i, 3, filePath);
        LocalDate ngay_2 = Tool.getCellDate(0, i, 4, filePath);       
        String expResult = Tool.getCellStringValue(0, i, 5, filePath);
        ArrayList<BaoHanhDTO> result = instance.search(value, Type, null, null);
        assertEquals(result.get(0).getMaHoaDon(), expResult);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        }
    }

}
