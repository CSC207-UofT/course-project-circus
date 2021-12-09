package application.shell.commands;


import application.shell.ShellApplication;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandArgContainer;
import application.shell.commands.framework.ShellCommandSpec;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import java.util.List;

/**
 * A command to display the PartCatalogue.
 */
@ShellCommandSpec(name = "display-parts", description = "Display the part catalogue.")
public class DisplayPartCatalogue<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends ShellCommand<T, U> {

    @Override
    public String execute(ShellApplication<T, U> application, ShellCommandArgContainer args) {
        PartCatalogue partCatalogue = application.getWarehouse().getState().getPartCatalogue();
        StringBuilder stringBuilder = new StringBuilder();
        List<Part> parts = partCatalogue.getParts();
        // Create header line
        stringBuilder.append(String.format("(%d) part%s in the part catalogue",
                parts.size(),
                parts.size() == 1 ? "" : "s"));
        // Create list of items
        if (parts.size() > 0) {
            stringBuilder.append("\n");
        }
        for (Part item : parts) {
            stringBuilder.append(String.format("- %s\n", item));
        }
        return stringBuilder.toString();
    }
}
