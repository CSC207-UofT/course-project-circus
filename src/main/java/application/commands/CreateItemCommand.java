package main.java.application.commands;

import main.java.application.ShellApplication;
import main.java.application.commands.framework.ShellCommand;
import main.java.application.commands.framework.ShellCommandArg;
import main.java.application.commands.framework.ShellCommandArgContainer;
import main.java.application.commands.framework.ShellCommandSpec;
import main.java.inventory.Item;

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
