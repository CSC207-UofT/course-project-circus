package circus.application.commands;


import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;
import circus.inventory.InventoryCatalogue;
import circus.inventory.Item;

import java.util.List;

/**
 * A command to display the InventoryCatalogue.
 */
@ShellCommandSpec(name = "display-inventory", description = "Display the inventory catalogue.")
public class DisplayInventoryCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer args) {
        InventoryCatalogue inventoryCatalogue = application.getWarehouseController().getInventoryCatalogue();
        StringBuilder stringBuilder = new StringBuilder();
        List<Item> items = inventoryCatalogue.getItems();
        // Create header line
        stringBuilder.append(String.format("(%d) item%s in the inventory catalogue",
                items.size(),
                items.size() > 0 ? "s" : ""));
        // Create list of items
        if (items.size() > 0) {
            stringBuilder.append("\n");
        }
        for (Item item : items) {
            stringBuilder.append(String.format("- %s\n", item));
        }
        return stringBuilder.toString();
    }
}
