package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.inventory.Part;

/**
 * Argument container for CreatePartCommand.
 */
class CreatePartCommandArgContainer extends ShellCommandArgContainer {
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
 * A command to create a Part and add it to the PartCatalogue.
 */
@ShellCommandSpec(name = "create-part", description = "Create a part and add it to the part catalogue.")
public class CreatePartCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        CreatePartCommandArgContainer args = (CreatePartCommandArgContainer) argContainer;
        Part part = new Part(args.getId(), args.getName(), args.getDescription());
        // Add it to the catalogue.
        boolean result = application.getWarehouseController().getState().getPartCatalogue().addPart(part);
        if (result) {
            return String.format("Created %s and added it to the part catalogue.", part);
        } else {
            return "Error - Part with same id already exists!";
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreatePartCommandArgContainer();
    }
}
