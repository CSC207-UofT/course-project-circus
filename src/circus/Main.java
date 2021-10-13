package circus;

import circus.inventory.Item;
import circus.warehouse.Depot;
import circus.warehouse.Rack;

public class Main {
    public static void main(String[] args) {
        System.out.println("Items:");
        Item item1 = new Item("Mango", "A delectable fruit");
        Item item2 = new Item("Crack Cocaine", "A children's candy");

        System.out.println(item1);
        System.out.println(item2);

        Rack rack = new Rack();
        Depot depot = new Depot();

        // Rack demo 1: order matters
        System.out.println("Rack demo 1: order matters; item1 is added first, then item2 is added second\n" +
                           "item2 cannot be added since it is of a different type.");
        rack.receiveItem(item1);
        // item2 cannot be added since it is of a different type.
        rack.receiveItem(item2);
        // rack only contains item1
        System.out.println(rack.getQueryItems());
        // Rack demo 2: removing items
        System.out.println("Rack demo 2: removing items");
        // NOTE: We could have also used rack.removeItem(item1)
        rack.distributeItem(value -> value.equals(item1));
        System.out.println(rack.getQueryItems());
    }
}
