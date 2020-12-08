////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {

    public enum ItemType {
        GELATO, BUDINO, BEVANDA
    }

    private final ItemType itemType;
    private final String name;
    private final double price;

    public MenuItem(final ItemType itemType, final String name, final double price) throws IllegalArgumentException {
        if (itemType == null) {
            throw new IllegalArgumentException("The item's type cannot be null");
        } else if (name == null || name.length() <= 0) {
            throw new IllegalArgumentException("The item's name cannot be null or empty");
        } else if (price < 0) {
            throw new IllegalArgumentException("The item's price cannot be null");
        }

        this.itemType = itemType;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public String getName() {
        return this.name;
    }
}