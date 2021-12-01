package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.WarehouseController;
import warehouse.tiles.Tile;
import warehouse.transactions.Order;

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
                "the Warehouse.")
public class ReceiveItemCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        ReceiveItemCommandArgContainer args = (ReceiveItemCommandArgContainer) argContainer;
        WarehouseController warehouseController = application.getWarehouseController();
        PartCatalogue partCatalogue = warehouseController.getPartCatalogue();
        Part part = partCatalogue.getPartById(args.getPartId());
        if (part == null) {
            return String.format("Could not find part with id \"%s\" in the part catalogue!", args.getPartId());
        } else {
            Item item = new Item(part);
            Order order = warehouseController.receiveItem(item);
            Tile tile = order.getSource().getTile();
            return String.format("Placed item into %s at (%d, %d)\nOrder id: %s", tile.getClass().getSimpleName(),
                    tile.getX(), tile.getY(), order.getId());
        }
    }
    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new ReceiveItemCommandArgContainer();
    }
}

