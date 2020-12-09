////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.User;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public class Bill implements TakeAwayBill {
    
    public static int giftedBills = 0;
    
    private final LocalTime billTime;
    private List<MenuItem> items;
    private User user;
    private double total = 0;
    private boolean gifted = false;
    
    public Bill(final LocalTime billTime, List<MenuItem> itemsOrdered, User user) throws TakeAwayBillException {
        if (billTime == null) {
            throw new TakeAwayBillException("The bill cannot have a null time");
        } else if (itemsOrdered == null) {
            throw new TakeAwayBillException("The bill cannot have a null list of items");
        } else if (user == null) {
            throw new TakeAwayBillException("The bill cannot have a null user");
        }

        
        this.billTime = billTime;
        this.items = itemsOrdered;
        this.user = user;
    }

    public static void resetGiveAways() {
        giftedBills = 0;
    }
    
    @Override
    public double getOrderPrice() throws TakeAwayBillException {
        if (items.size() <= 0) {
            throw new TakeAwayBillException("The bill cannot have an empty list of items");
        } else if (items.contains(null)) {
            throw new TakeAwayBillException("The list of items of the bill cannot have a null item");
        } else if (items.size() > 30) {
            throw new TakeAwayBillException("The bill cannot have more than 30 items");
        }
        
        total = items.stream()
                .mapToDouble(MenuItem::getPrice)
                .reduce(0d, Double::sum);
        
        return getSubTotal();
    }
    
    private double getSubTotal() {
        if (this.containsMoreThanFiveIceCreams()) {
            total -= getCheapestIceCreamPrice()*0.5;
        }
        if (this.isTotalWithoutDrinksOverFifty()) {
            total -= 0.1d*total;
        }
        if (total < 10) {
            total += 0.5d;
        }
        if (this.isEligibleForGiveAway()) {
            this.giftBill();
        }
        return total;
    }
    
    private boolean containsMoreThanFiveIceCreams() {
        return items.stream()
                .filter(
                        el -> el.getItemType().equals(ItemType.GELATO))
                .count() > 5;
    }
    
    private double getCheapestIceCreamPrice() {
        return items.stream()
                .filter(
                        el -> el.getItemType().equals(ItemType.GELATO))
                .min(
                        Comparator.comparing(MenuItem::getPrice))
                .get().getPrice();
    }
    
    private boolean isTotalWithoutDrinksOverFifty() {
        return items.stream()
                .filter(
                        el -> el.getItemType().equals(ItemType.GELATO) || el.getItemType().equals(ItemType.BUDINO))
                .mapToDouble(MenuItem::getPrice)
                .reduce(0d, Double::sum) > 50;
                
    }
    
    public boolean isEligibleForGiveAway() {
        return user.isUnderage() && (billTime.getHour() == 18) && (giftedBills < 10) && !gifted;
    }
    
    private void giftBill() {
        gifted = true;
        giftedBills++;
        total = 0;
    }
    
    public boolean isGifted() {
        return gifted;
    }
}