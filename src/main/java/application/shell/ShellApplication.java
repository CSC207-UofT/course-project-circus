package application.shell;

import application.shell.commands.*;
import application.shell.commands.framework.ShellCommand;
import application.shell.commands.framework.ShellCommandExecutor;
import application.shell.presenters.coordinateparsers.CoordinateParser;
import application.shell.presenters.coordinateparsers.PointParser;
import application.shell.presenters.warehouse.GridWarehousePresenter;
import application.shell.presenters.warehouse.WarehousePresenter;
import serialization.FileObjectSaver;
import serialization.JsonFileObjectSaver;
import warehouse.Warehouse;
import warehouse.WarehouseLayout;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.RobotMapper;

import java.util.Scanner;

/**
 * Driver class for the shell application.
 *
 * @remark NOTE: The implementation of this shell application is unfinished, and is not intended to be used in
 * "production". Instead, it serves to demonstrate the versatility of our design. Due to the design of the business logic
 * packages, we are able to insert any sort of frontend on top of the backend layer (e.g. a shell application as shown here,
 * or a user interface as in the application.desktop package).
 */
public class ShellApplication<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> {
    private final Warehouse<T, U> warehouse;
    private final WarehousePresenter<T, U> warehousePresenter;
    private final CoordinateParser<U> coordinateParser;

    private final FileObjectSaver<PartCatalogue> partCatalogueFileSaver;

    private boolean isRunning;
    private final ShellCommandExecutor commandExecutor;

    /**
     * Construct a ShellApplication.
     */
    public  ShellApplication(Warehouse<T, U> warehouse, WarehousePresenter<T, U> warehousePresenter,
                             CoordinateParser<U> coordinateParser,
                             FileObjectSaver<PartCatalogue> partCatalogueFileSaver) {
        this.warehouse = warehouse;
        this.warehousePresenter = warehousePresenter;
        this.coordinateParser = coordinateParser;
        this.partCatalogueFileSaver = partCatalogueFileSaver;

        isRunning = false;
        commandExecutor = new ShellCommandExecutor(this, new ShellCommand[]{
                new CreatePartCommand<T, U>(),
                new CreateStorageUnitCommand<T, U>(),
                new ReceiveItemCommand(),
                new DisplayWarehouseCommand<T, U>(),
                new DisplayStorageUnitInfoCommand<T, U>(),
                new DisplayPartCatalogue<T, U>(),
                new SavePartCommand(),
                new HelpCommand(),
                new ExitCommand(),
        });
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
     * Get the Warehouse for this application.
     * @return the Warehouse instance.
     */
    public Warehouse<T, U> getWarehouse() {
        return warehouse;
    }

    /**
     * Get the warehouse presenter.
     */
    public WarehousePresenter<T, U> getWarehousePresenter() {
        return warehousePresenter;
    }

    /**
     * Get the coordinate parser for this application.
     */
    public CoordinateParser<U> getCoordinateParser() {
        return coordinateParser;
    }

    public FileObjectSaver<PartCatalogue> getPartCatalogueFileSaver() {
        return partCatalogueFileSaver;
    }

    /**
     * Get the ShellCommandExecutor for this application.
     * @return the ShellCommandExecutor instance.
     */
    public ShellCommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    /**
     * Entrypoint for the shell application.
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(10, 10);
        Warehouse<GridWarehouseCoordinateSystem, Point> warehouse = new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new OrderQueue()
        ));

        var application = new ShellApplication<>(warehouse,new GridWarehousePresenter(),
                new PointParser(), new JsonFileObjectSaver<>());
        application.run();
    }
}
