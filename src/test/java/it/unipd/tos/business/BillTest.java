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
}

