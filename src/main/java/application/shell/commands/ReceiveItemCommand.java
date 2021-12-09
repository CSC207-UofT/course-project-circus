package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.Warehouse;
import warehouse.logistics.orders.PlaceOrder;
import warehouse.tiles.Tile;

/**
 * Argument container for ReceiveItemCommand.
 */
class ReceiveItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String partId;

    public String getPartId() {
        return partId;
    }
}

/**
 * A command to receive an Item from the outside world.
 */
@ShellCommandSpec(name = "receive-item",
        description = "Receive an Item from the outside world. This will place the Item into an available ReceiveDepot," +
                "and then issue an Order for the Item to be moved from that ReceiveDepot to an available StorageUnit in " +
                "the WarehouseLayout.")
public class ReceiveItemCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        ReceiveItemCommandArgContainer args = (ReceiveItemCommandArgContainer) argContainer;

        Warehouse<?, ?> warehouse = application.getWarehouse();
        WarehouseCoordinateSystem<?> coordinateSystem = warehouse.getState().getCoordinateSystem();
        PartCatalogue partCatalogue = warehouse.getState().getPartCatalogue();

        Part part = partCatalogue.getPartById(args.getPartId());
        if (part == null) {
            return String.format("Could not find part with id \"%s\" in the part catalogue!", args.getPartId());
        } else {
            Item item = new Item(part);
            PlaceOrder order = warehouse.receiveItem(item);
            Tile tile = order.getSource().getTile();
            WarehouseCoordinate position = coordinateSystem.projectIndexToCoordinate(tile.getIndex());
            return String.format("Placed item into %s at %s\nOrder id: %s", tile.getClass().getSimpleName(),
                    position.toString(), order.getId());
        }
    }
    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new ReceiveItemCommandArgContainer();
    }
}

