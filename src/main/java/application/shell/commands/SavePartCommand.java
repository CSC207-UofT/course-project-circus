package application.shell.commands;

import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArg;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.inventory.PartCatalogue;

import java.io.IOException;

/**
 * Argument container for SavePartCommand.
 */
class SavePartCatalogueCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String filePath;

    public String getFilePath() {
        return filePath;
    }
}

@ShellCommandSpec(name = "save-part-catalogue", description = "Save part catalogue to file")
public class SavePartCommand extends ShellCommand {
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        SavePartCatalogueCommandArgContainer args = (SavePartCatalogueCommandArgContainer) argContainer;
        PartCatalogue partCatalogue = application.getWarehouse().getState().getPartCatalogue();
        try {
            application.getPartCatalogueFileSaver().save(partCatalogue, args.getFilePath());
            return String.format("Saved part to \"%s\"", args.getFilePath());
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new SavePartCatalogueCommandArgContainer();
    }
}
