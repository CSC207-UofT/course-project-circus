package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
import application.desktop.ui.components.common.SameLine;
import application.desktop.ui.components.common.Separator;
import application.desktop.ui.components.common.Text;
import imgui.ImGui;
import warehouse.Warehouse;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditorPanel extends Panel {
    private final WarehouseCanvas canvas;
    private Warehouse warehouse;

    /**
     * Construct a new WarehouseEditorPanel given a Warehouse.
     * @param warehouse The Warehouse to edit.
     */
    public WarehouseEditorPanel(Warehouse warehouse) {
        super("Warehouse Layout");
        setCloseable(false);

        this.warehouse = warehouse;
        canvas = new WarehouseCanvas(warehouse);
        addChild(new SameLine(
                new Text("Hello World"),
                new Text("Test")
        ));
        addChild(canvas);
    }
}
