package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.inventory.InventoryCatalogue;
import warehouse.inventory.Item;
import warehouse.WarehouseController;

import java.io.IOException;

/**
 * Argument container for SaveItemCommand.
 */
class SaveItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String id;
    @ShellCommandArg
    private String filePath;

    public String getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }
}

@ShellCommandSpec(name = "save-item", description = "Save item to file")
public class SaveItemCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        SaveItemCommandArgContainer args = (SaveItemCommandArgContainer) argContainer;
        WarehouseController warehouseController = application.getWarehouseController();
        InventoryCatalogue inventoryCatalogue = warehouseController.getInventoryCatalogue();
        Item item = inventoryCatalogue.getItemById(args.getId());
        if (item == null) {
            return String.format("Could not find item with id \"%s\" in the warehouse.inventory catalogue!", args.getId());
        } else {
            try {
                application.getItemFileSaver().save(item, args.getFilePath());
                return String.format("Saved item to \"%s\"", args.getFilePath());
            } catch (IOException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new SaveItemCommandArgContainer();
    }
}
