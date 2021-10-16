package main.java.application.commands;

import main.java.application.ShellApplication;
import main.java.application.commands.framework.ShellCommand;
import main.java.application.commands.framework.ShellCommandArg;
import main.java.application.commands.framework.ShellCommandArgContainer;
import main.java.application.commands.framework.ShellCommandSpec;
import main.java.inventory.Item;
import main.java.warehouse.Tile;
import main.java.warehouse.WarehouseController;
import main.java.inventory.InventoryCatalogue;

/**
 * Argument container for InsertItemCommand.
 */
class InsertItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String id;

    public String getId() {
        return id;
    }
}

/**
 * A command to insert an Item into an available Rack.
 */
@ShellCommandSpec(name = "insert-item", description = "Inserts an item into the available Rack.")
public class InsertItemCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        InsertItemCommandArgContainer args = (InsertItemCommandArgContainer) argContainer;
        WarehouseController warehouseController = application.getWarehouseController();
        InventoryCatalogue inventoryCatalogue = warehouseController.getInventoryCatalogue();
        Item item = inventoryCatalogue.getItemById(args.getId());
        if (item == null) {
            return String.format("Could not find item with id \"%s\" in the inventory catalogue!", args.getId());
        } else {
            Tile tile = warehouseController.insertItem(item);
            if (tile != null) {
                return String.format("Great Success! Inserted item into %s at (%d, %d)",
                        tile.getStorageUnit().getClass().getSimpleName(),
                        tile.getX(),
                        tile.getY());
            } else {
                return String.format("Could not insert %s into the warehouse!", item);
            }
        }
    }
    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new InsertItemCommandArgContainer();
    }
}

