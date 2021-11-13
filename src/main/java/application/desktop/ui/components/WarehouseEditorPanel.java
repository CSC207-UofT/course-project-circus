package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
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
        setShowMenuBar(true);

        canvas = new WarehouseCanvas();
        addChild(canvas);
    }


    public WarehouseCanvas getCanvas() {
        return canvas;
    }
}
