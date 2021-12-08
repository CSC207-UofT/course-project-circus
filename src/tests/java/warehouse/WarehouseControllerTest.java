package warehouse;

import org.junit.jupiter.api.Test;
import warehouse.logistics.assignment.StorageTileAssignmentPolicy;
import warehouse.logistics.orders.OrderQueue;
import warehouse.logistics.orders.PlaceOrder;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.*;
import warehouse.Warehouse;
import warehouse.WarehouseController;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WarehouseControllerTest {
    // TODO: Fix this test after completed implementation of insertItem
    @Test
    public void testInsertItem() {
//        Warehouse warehouse = new Warehouse(10, 10);
//        PartCatalogue ic = new PartCatalogue();
//        Part p = new Part("Cucumber", "A vegetable");
//        ic.addPart(p);
//        WarehouseController wc = new WarehouseController(warehouse, ic);
//        Item item = new Item(p);
//        Tile destinationTile = warehouse.findRackFor(item);
//        assertEquals(destinationTile, wc.insertItem(item));
    }

    @Test
    public void testWareHouseController()
    {
        Warehouse warehouse = new Warehouse(10, 10);
        PartCatalogue ic = new PartCatalogue();
        Part p = new Part("Cucumber", "A vegetable");
        ic.addPart(p);

        RobotMapper rm = new RobotMapper();
        TilePosition tp = new TilePosition(0, 0);
        Robot r1 = new Robot();
        rm.addRobotAt(r1, tp);
        OrderQueue orderQueue = new OrderQueue();
        PartCatalogue pc = new PartCatalogue();
        Part part = new Part("649", "Tickets", "Lottery tickets");
        pc.addPart(part);
        WarehouseState ws = new WarehouseState(warehouse, pc, rm, orderQueue);

        StorageTileAssignmentPolicy<ReceiveDepot> stap1 = new StorageTileAssignmentPolicy<ReceiveDepot>() {
            @Override
            public ReceiveDepot assign(Item item) {
                return null;
            }
        };
        StorageTileAssignmentPolicy<ShipDepot> stap2 = new StorageTileAssignmentPolicy<ShipDepot>() {
            @Override
            public ShipDepot assign(Item item) {
                return null;
            }
        };
        StorageTileAssignmentPolicy<Rack> stap3 = new StorageTileAssignmentPolicy<Rack>() {
            @Override
            public Rack assign(Item item) {
                return null;
            }
        };

        WarehouseController wc = new WarehouseController(ws, stap1, stap2, stap3);
        WarehouseController wc2 = new WarehouseController(ws);
        Item item = new Item(part.getId(), part);
        assertNull(wc.receiveItem(item));

    }
}
