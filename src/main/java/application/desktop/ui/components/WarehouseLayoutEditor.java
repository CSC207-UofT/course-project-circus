package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;

/**
 * Editor window for warehouse layouts.
 */
public class WarehouseLayoutEditor extends Component {
    private final WarehouseLayoutCanvas canvas;

    public WarehouseLayoutEditor() {
        canvas = new WarehouseLayoutCanvas();
    }

    @Override
    public void draw(DesktopApplication application) {
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0.0f, 0.0f);
        ImGui.begin("Warehouse Layout");
        ImGui.popStyleVar();
        canvas.draw(application);
        ImGui.end();
    }

    public WarehouseLayoutCanvas getCanvas() {
        return canvas;
    }
}
