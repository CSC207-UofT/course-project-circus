package circus.application.commands;

import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArg;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;
import circus.inventory.Item;

class CreateItemCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg (positionalIndex = 0)
    private String id;
    @ShellCommandArg (positionalIndex = 1)
    private String name;
    @ShellCommandArg (positionalIndex = 2)
    private String description;

    CreateItemCommandArgContainer() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
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
            return String.format("Added %s", item);
        } else {
            return String.format("Could not add %s", item);
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateItemCommandArgContainer();
    }
}
