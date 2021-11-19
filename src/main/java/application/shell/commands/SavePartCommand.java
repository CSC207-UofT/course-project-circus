package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.WarehouseController;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import java.io.IOException;

/**
 * Argument container for SavePartCommand.
 */
class SavePartCommandArgContainer extends ShellCommandArgContainer {
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

@ShellCommandSpec(name = "save-part", description = "Save part to file")
public class SavePartCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        SavePartCommandArgContainer args = (SavePartCommandArgContainer) argContainer;
        WarehouseController warehouseController = application.getWarehouseController();
        PartCatalogue partCatalogue = warehouseController.getPartCatalogue();
        Part part = partCatalogue.getPartById(args.getId());
        if (part == null) {
            return String.format("Could not find part with id \"%s\" in the inventory catalogue!", args.getId());
        } else {
            try {
                application.getPartFileSaver().save(part, args.getFilePath());
                return String.format("Saved part to \"%s\"", args.getFilePath());
            } catch (IOException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new SavePartCommandArgContainer();
    }
}
