package circus.application.commands;

import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArg;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;
import circus.inventory.InventoryCatalogue;
import circus.inventory.Item;
import circus.warehouse.WarehouseController;

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
public class InsertItemCommand extends ShellCommand{
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        InsertItemCommandArgContainer args = (InsertItemCommandArgContainer) argContainer;
        WarehouseController warehouseController = application.getWarehouseController();
        InventoryCatalogue inventoryCatalogue = warehouseController.getInventoryCatalogue();
        Item item = inventoryCatalogue.getItemById(args.getId());
        if (item == null) {
            return String.format("Could not find item with id \"%s\" in the inventory catalogue!", args.getId());
        } else {
            if (warehouseController.insertItem(item)) {
                return "Great Success! Inserted %s into Rack";
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

