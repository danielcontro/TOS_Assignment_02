////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import it.unipd.tos.model.MenuItem.ItemType;

public class MenuItemTest {
    
    private static double DELTA = 1e-3;
    private MenuItem item;
    private ItemType itemType;
    private String name;
    private double price;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        itemType = ItemType.GELATO;
        name = "Banana Split";
        price = 2.5d;
        
        item = new MenuItem(itemType, name, price);
    }
    
    @Test
    public void test_GetItemType_ValidMenuItem_Calculated() {
        assertEquals(itemType, item.getItemType());
    }

    @Test
    public void test_GetName_ValidMenuItem_Calculated() {
        assertEquals(name, item.getName());
    }
    
    @Test
    public void test_GetPrice_ValidMenuItem_Calculated() {
        assertEquals(price, item.getPrice(), DELTA);
    }
    
    @Test
    public void test_MenuItemConstructor_ItemTypeNull_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The item's type cannot be null");
        new MenuItem(null, name, price);
    }
    
    @Test
    public void test_MenuItemConstructor_NameNull_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The item's name cannot be null or empty");
        new MenuItem(itemType, null, price);
    }
    
    @Test
    public void test_MenuItemConstructor_NameEmpty_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The item's name cannot be null or empty");
        new MenuItem(itemType, "", price);
    }
    
    @Test
    public void test_MenuItemConstructor_PriceNegative_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The item's price cannot be null");
        new MenuItem(itemType, name, -1.0d);
    }
}
