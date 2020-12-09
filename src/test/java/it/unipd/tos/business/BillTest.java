////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.User;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public class BillTest {
    
    private static double DELTA = 1e-3;
    
    private Bill bill;
    private List<MenuItem> items;
    private User user;
    
/*   Assuming:
 *      total >= 10,
 *      totalWitoutDrinks <= 50
 *      numberOfIceCreams <= 5
 *      numberOfItems <= 30 
 *   unless otherwise made explicit in the test's name
 */
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() throws TakeAwayBillException {
        user = new User("Mario", "Rossi", 20);
        MenuItem item1 = new MenuItem(ItemType.GELATO, "gelato 01", 3.5d);
        MenuItem item2 = new MenuItem(ItemType.GELATO, "gelato 02", 4.0d);
        MenuItem item3 = new MenuItem(ItemType.GELATO, "gelato 03", 2.5d);
        MenuItem item4 = new MenuItem(ItemType.GELATO, "gelato 04", 3.0d);
        MenuItem item5 = new MenuItem(ItemType.GELATO, "gelato 05", 5.0d);
        
        this.items = Stream.of(item1, item2, item3, item4, item5)
                .collect(Collectors.toList());

        bill = new Bill(LocalTime.of(18, 5), items, user);
    }
    
    @Test
    public void test_GetOrderPrice() throws TakeAwayBillException{
        assertEquals(18, bill.getOrderPrice(), DELTA);
    }
    
    @Test
    public void test_BillContructor_BillTimeNull_ExceptionThrown() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The bill cannot have a null time");
        new Bill(null, items, user);
    }
    
    @Test
    public void test_BillContructor_ItemsOrderedNull_ExceptionThrown() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The bill cannot have a null list of items");
        new Bill(LocalTime.of(11, 30), null, user);
    }
    
    @Test
    public void test_BillContructor_UserNull_ExceptionThrown() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The bill cannot have a null user");
        new Bill(LocalTime.of(11, 30), items, null);
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedEmpty_ExceptionThrown() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The bill cannot have an empty list of items");
        List<MenuItem> emptyList = new ArrayList<>();
        bill = new Bill(LocalTime.of(11, 30), emptyList, user);
        bill.getOrderPrice();
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedContainsNull_ExceptionThrow() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The list of items of the bill cannot have a null item");
        
        List<MenuItem> listWithNullItem = Stream.of(new MenuItem(ItemType.BEVANDA, "Coca cola", 3.0d), null).collect(Collectors.toList());
        bill = new Bill(LocalTime.of(11, 30), listWithNullItem, user);
        bill.getOrderPrice();
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedWithMoreThan5IceCreams_Calculated() throws TakeAwayBillException {
        MenuItem item1 = new MenuItem(ItemType.GELATO, "gelato 01", 3.5d);
        MenuItem item2 = new MenuItem(ItemType.GELATO, "gelato 02", 4.0d);
        MenuItem item3 = new MenuItem(ItemType.GELATO, "gelato 03", 2.5d);
        MenuItem item4 = new MenuItem(ItemType.GELATO, "gelato 04", 3.0d);
        MenuItem item5 = new MenuItem(ItemType.GELATO, "gelato 05", 5.0d);
        MenuItem item6 = new MenuItem(ItemType.GELATO, "gelato 06", 4.0d);
        this.items = Stream.of(item1, item2, item3, item4, item5, item6)
                .collect(Collectors.toList());

        bill = new Bill(LocalTime.of(11, 30), items, user);
        
//        22 - 0.5*2.5 (CheapestIceCreamPrice) = 20.75
        assertEquals(20.75d, bill.getOrderPrice(), DELTA);
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedWithoutDrinksOverFifty_Calculated() throws TakeAwayBillException {
        MenuItem item1 = new MenuItem(ItemType.GELATO, "gelato 01", 10d);
        MenuItem item2 = new MenuItem(ItemType.GELATO, "gelato 02", 12d);
        MenuItem item3 = new MenuItem(ItemType.GELATO, "gelato 03", 15d);
        MenuItem item4 = new MenuItem(ItemType.BUDINO, "budino 01", 9d);
        MenuItem item5 = new MenuItem(ItemType.BUDINO, "budino 02", 11d);
        MenuItem item6 = new MenuItem(ItemType.BUDINO, "budino 03", 10d);
        MenuItem item7 = new MenuItem(ItemType.BEVANDA, "bevanda 01", 5d);
        
        this.items = Stream.of(item1, item2, item3, item4, item5, item6, item7)
                .collect(Collectors.toList());
        
        bill = new Bill(LocalTime.of(11, 30), items, user);
        
//        72 - 0.1*72 = 64.8
        assertEquals(64.8d, bill.getOrderPrice(), DELTA);
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedWithoutDrinksOverFiftyAndMoreThanFiveIceCreams_Calculated() throws TakeAwayBillException {
        MenuItem item1 = new MenuItem(ItemType.GELATO, "gelato 01", 10d);
        MenuItem item2 = new MenuItem(ItemType.GELATO, "gelato 02", 12d);
        MenuItem item3 = new MenuItem(ItemType.GELATO, "gelato 03", 15d);
        MenuItem item4 = new MenuItem(ItemType.GELATO, "gelato 04", 9d);
        MenuItem item5 = new MenuItem(ItemType.GELATO, "gelato 05", 11d);
        MenuItem item6 = new MenuItem(ItemType.GELATO, "gelato 06", 10d);
        MenuItem item7 = new MenuItem(ItemType.BUDINO, "budino 01", 9d);
        MenuItem item8 = new MenuItem(ItemType.BEVANDA, "bevanda 01", 5d);
        
        this.items = Stream.of(item1, item2, item3, item4, item5, item6, item7, item8)
                .collect(Collectors.toList());
        
        bill = new Bill(LocalTime.of(11, 30), items, user);
        
//        81 - 9*0.5 - 0.1*76.5 (SubTotal)  = 68.85 
        assertEquals(68.85d, bill.getOrderPrice(), DELTA);
    }
    
    @Test
    public void test_GetOrderPrice_ItemsOrderedMoreThanThirty_ExceptionThrown() throws TakeAwayBillException {
        thrown.expect(TakeAwayBillException.class);
        thrown.expectMessage("The bill cannot have more than 30 items");
        MenuItem item01 = new MenuItem(ItemType.GELATO, "gelato 01", 10d);
        MenuItem item02 = new MenuItem(ItemType.GELATO, "gelato 02", 12d);
        MenuItem item03 = new MenuItem(ItemType.GELATO, "gelato 03", 15d);
        MenuItem item04 = new MenuItem(ItemType.GELATO, "gelato 04", 9d);
        MenuItem item05 = new MenuItem(ItemType.GELATO, "gelato 05", 11d);
        MenuItem item06 = new MenuItem(ItemType.GELATO, "gelato 06", 10d);
        MenuItem item07 = new MenuItem(ItemType.BUDINO, "budino 03", 9d);
        MenuItem item08 = new MenuItem(ItemType.BEVANDA, "bevanda 01", 5d);
        MenuItem item09 = new MenuItem(ItemType.GELATO, "gelato 07", 10d);
        MenuItem item10 = new MenuItem(ItemType.GELATO, "gelato 08", 12d);
        MenuItem item11 = new MenuItem(ItemType.GELATO, "gelato 09", 15d);
        MenuItem item12 = new MenuItem(ItemType.GELATO, "gelato 10", 9d);
        MenuItem item13 = new MenuItem(ItemType.GELATO, "gelato 11", 11d);
        MenuItem item14 = new MenuItem(ItemType.GELATO, "gelato 12", 10d);
        MenuItem item15 = new MenuItem(ItemType.BUDINO, "budino 02", 9d);
        MenuItem item16 = new MenuItem(ItemType.BEVANDA, "bevanda 02", 5d);
        MenuItem item17 = new MenuItem(ItemType.GELATO, "gelato 13", 10d);
        MenuItem item18 = new MenuItem(ItemType.GELATO, "gelato 14", 12d);
        MenuItem item19 = new MenuItem(ItemType.GELATO, "gelato 15", 15d);
        MenuItem item20 = new MenuItem(ItemType.GELATO, "gelato 16", 9d);
        MenuItem item21 = new MenuItem(ItemType.GELATO, "gelato 17", 11d);
        MenuItem item22 = new MenuItem(ItemType.GELATO, "gelato 18", 10d);
        MenuItem item23 = new MenuItem(ItemType.BUDINO, "budino 01", 9d);
        MenuItem item24 = new MenuItem(ItemType.BEVANDA, "bevanda 03", 5d);
        MenuItem item25 = new MenuItem(ItemType.GELATO, "gelato 19", 10d);
        MenuItem item26 = new MenuItem(ItemType.GELATO, "gelato 20", 12d);
        MenuItem item27 = new MenuItem(ItemType.GELATO, "gelato 21", 15d);
        MenuItem item28 = new MenuItem(ItemType.GELATO, "gelato 22", 9d);
        MenuItem item29 = new MenuItem(ItemType.GELATO, "gelato 23", 11d);
        MenuItem item30 = new MenuItem(ItemType.GELATO, "gelato 24", 10d);
        MenuItem item31 = new MenuItem(ItemType.BUDINO, "budino 04", 9d);
        MenuItem item32 = new MenuItem(ItemType.BEVANDA, "bevanda 04", 5d);

        this.items = Stream.of(
                item01, item02, item03, item04, item05, item06, item07, item08, item09, item10, 
                item11, item12, item13, item14, item15, item16, item17, item18, item19, item20, 
                item21, item22, item23, item24, item25, item26, item27, item28, item29, item30, 
                item31, item32).collect(Collectors.toList());
        bill = new Bill(LocalTime.of(11, 30), items, user);
        
        bill.getOrderPrice();
    }
}

