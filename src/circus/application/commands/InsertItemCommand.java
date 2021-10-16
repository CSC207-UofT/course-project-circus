package circus.application.commands;


import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArg;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;
import circus.inventory.InventoryCatalogue;
import circus.inventory.Item;
import circus.warehouse.*;

import java.util.Map;

class InsertItemCommandContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String id;

    public String getId() {
        return id;
    }
}

@ShellCommandSpec(name = "insert-item", description = "Inserts an item into the available Storage Unit.")
public class InsertItemCommand extends ShellCommand{
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        InsertItemCommandContainer args = (InsertItemCommandContainer) argContainer;
        WarehouseController wc = application.getWarehouseController();
        InventoryCatalogue catalogue = wc.getInventoryCatalogue();
        wc.insertItem(catalogue.getItemById(args.getId()));

        return "Success";
    }
    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateStorageUnitArgContainer();
    }
}

