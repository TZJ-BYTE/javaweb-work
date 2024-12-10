package org.example;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<String, Integer> items = new HashMap();

    public ShoppingCart() {
    }

    public boolean addItem(String item, int quantity) {
        if (item != null && item != "" && quantity > 0) {
            this.items.put(item, (Integer)this.items.getOrDefault(item, 0) + quantity);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeItem(String item, int quantity) {
        if (this.items.containsKey(item)) {
            int currentQuantity = (Integer)this.items.get(item);
            if (currentQuantity >= quantity) {
                this.items.put(item, currentQuantity - quantity);
                return true;
            }
        }

        return false;
    }

    public Map<String, Integer> getItems() {
        return this.items;
    }
}
