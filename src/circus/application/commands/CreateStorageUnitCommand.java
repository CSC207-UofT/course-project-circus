package circus.application.commands;


import circus.application.ShellApplication;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandArg;
import circus.application.commands.framework.ShellCommandArgContainer;
import circus.application.commands.framework.ShellCommandSpec;
import circus.warehouse.*;

class CreateStorageUnitArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String type;
    @ShellCommandArg
    private int capacity;
    @ShellCommandArg
    private int x;
    @ShellCommandArg
    private int y;


    public int getX() {
        return x;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}

@ShellCommandSpec(name = "create-storage-unit", description = "Create a Storage Unit and add it to the Warehouse.")
public class CreateStorageUnitCommand extends ShellCommand{
    @Override
    public String execute(ShellApplication application, ShellCommandArgContainer argContainer) {
        StorageUnit s = null;
        CreateStorageUnitArgContainer args = (CreateStorageUnitArgContainer) argContainer;
        if (args.getType().equals("Depot")){
            s= new Depot(args.getCapacity() );
        }
        else if (args.getType().equals("Rack")) {
            s= new Rack(args.getCapacity());
        }
        else {
            return String.format("Storage Unit type %s - unknown", args.getType());
        }
        Warehouse w = application.getWarehouseController().getWarehouse();

        try {
            Tile tile = w.getTileAt(args.getX(), args.getY());
            tile.setStorageUnit(s);
        } catch (TileOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "Success";
    }
    @Override
    public ShellCommandArgContainer createArgContainer() {
        return new CreateStorageUnitArgContainer();
    }
}

