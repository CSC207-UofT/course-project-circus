package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.inventory.Item;
import warehouse.Tile;
import warehouse.WarehouseController;

/**
 * Argument container for InsertItemCommand.
 */
class InsertItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String partId;

    public String getPartId() {
        return partId;
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
        PartCatalogue partCatalogue = warehouseController.getPartCatalogue();
        Part part = partCatalogue.getPartById(args.getPartId());
        if (part == null) {
            return String.format("Could not find part with id \"%s\" in the part catalogue!", args.getPartId());
        } else {
            Item item = new Item(part);
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

