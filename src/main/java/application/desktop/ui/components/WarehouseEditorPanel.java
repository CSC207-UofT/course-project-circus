package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
import application.desktop.ui.components.common.SameLine;
import application.desktop.ui.components.common.Separator;
import application.desktop.ui.components.common.Text;
import imgui.ImGui;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditorPanel extends Panel {
    private final WarehouseCanvas canvas;

    /**
     * Construct a new WarehouseEditorPanel.
     */
    public WarehouseEditorPanel() {
        super("Warehouse Layout");
        setCloseable(false);

        canvas = new WarehouseCanvas();
        addChild(new SameLine(
                new Text("Hello World"),
                new Text("Test")
        ));
        addChild(canvas);
    }

    public WarehouseCanvas getCanvas() {
        return canvas;
    }
}
