package circus.application;

import circus.application.commands.*;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandExecutor;
import circus.inventory.InventoryCatalogue;
import circus.warehouse.Warehouse;
import circus.warehouse.WarehouseController;

import java.util.Scanner;

/**
 * Driver class for the shell application.
 */
public class ShellApplication {
    private boolean isRunning;
    private final ShellCommandExecutor commandExecutor;
    private final WarehouseController warehouseController;

    /**
     * Construct a ShellApplication.
     */
    public ShellApplication() {
        isRunning = false;
        commandExecutor = new ShellCommandExecutor(this, new ShellCommand[]{
                new ExitCommand(),
                new DisplayWarehouseCommand(),
                new CreateItemCommand(),
                new CreateStorageUnitCommand(),
                new InsertItemCommand()
        });
        // TODO: Should the warehouse controller be made here?!
        // TODO: Replace empty warehouse with file loading or something
        // TODO: Don't hardcode warehouse dimensions
        warehouseController = new WarehouseController(
                new Warehouse(10, 10),
                new InventoryCatalogue()
        );
    }

    /**
     * Run the shell application.
     */
    private void run() {
        isRunning = true;
        Scanner in = new Scanner(System.in);
        while (isRunning) {
            // Read input
            System.out.print("> ");
            String input = in.nextLine();
            // Execute commands
            String output = commandExecutor.execute(input);
            // Display output
            if (output != null && !output.isEmpty()) {
                System.out.println(output);
            }
        }
        in.close();
    }

    /**
     * Stop running the application.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Get the WarehouseController for this application.
     * @return the WarehouseController instance.
     */
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    /**
     * Entrypoint for the shell application.
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        ShellApplication application = new ShellApplication();
        application.run();
    }
}
