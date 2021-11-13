package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;
import imgui.ImGui;

/**
 * Editor window for warehouse layouts.
 */
public class WarehouseLayoutEditor extends UIComponent {
    private final WarehouseLayoutCanvas canvas;

    public WarehouseLayoutEditor() {
        canvas = new WarehouseLayoutCanvas();
    }

    @Override
    public void render(DesktopApplication application) {
        ImGui.begin("Warehouse Layout");
        canvas.render(application);
        ImGui.end();
    }

    public WarehouseLayoutCanvas getCanvas() {
        return canvas;
    }
}
