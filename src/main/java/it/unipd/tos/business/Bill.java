////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalTime;
import java.util.List;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.User;
import it.unipd.tos.model.MenuItem;

public class Bill implements TakeAwayBill {
    
    private final LocalTime billTime;
    private List<MenuItem> items;
    private User user;

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

    @Override
    public double getOrderPrice() throws TakeAwayBillException {
        if (items.size() <= 0) {
            throw new TakeAwayBillException("The bill cannot have an empty list of items");
        } else if (items.contains(null)) {
            throw new TakeAwayBillException("The list of items of the bill cannot have a null item");
        }
        return items.stream()
                .mapToDouble(MenuItem::getPrice)
                .reduce(0d, Double::sum);
        
    }
}