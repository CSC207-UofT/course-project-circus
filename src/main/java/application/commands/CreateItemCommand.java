package application.commands;

import application.ShellApplication;
import application.commands.framework.ShellCommand;
import application.commands.framework.ShellCommandArg;
import application.commands.framework.ShellCommandArgContainer;
import application.commands.framework.ShellCommandSpec;
import inventory.Item;

/**
 * Argument container for CreateItemCommand.
 */
class CreateItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String id;
    @ShellCommandArg
    private String name;
    @ShellCommandArg
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

/**
 * A command to create an Item and add it to the Warehouse inventory catalogue.
 */
@ShellCommandSpec(name = "create-item", description = "Create an Item and add it to the Warehouse inventory catalogue.")
public class CreateItemCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        CreateItemCommandArgContainer args = (CreateItemCommandArgContainer) argContainer;
        Item item = new Item(args.getId(), args.getName(), args.getDescription());
        // Add it to the catalogue.
        boolean result = application.getWarehouseController().getInventoryCatalogue().addItem(item);
        if (result) {
            return String.format("Created %s and added it to the inventory catalogue.", item);
        } else {
            return "Error - Item with same id already exists!";
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateItemCommandArgContainer();
    }
}
