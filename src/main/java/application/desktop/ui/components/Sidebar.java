package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import application.desktop.ui.components.editor.WarehouseEditorPanel;
import imgui.ImGui;
import imgui.ImGuiWindowClass;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;

/**
 * Sidebar component for the DesktopApplication.
 */
public class Sidebar extends Component {
    private final WarehouseEditorPanel warehouseLayoutEditor;

    /**
     * Construct a new Sidebar.
     * @param warehouseLayoutEditor A warehouse layout editor.
     */
    public Sidebar(WarehouseEditorPanel warehouseLayoutEditor) {
        this.warehouseLayoutEditor = warehouseLayoutEditor;
    }

    @Override
    public void draw(DesktopApplication application) {
        // Setup window so that it automatically hides its tab bar
        ImGuiWindowClass windowClass = new ImGuiWindowClass();
        windowClass.addDockNodeFlagsOverrideSet(ImGuiDockNodeFlags.AutoHideTabBar);
        ImGui.setNextWindowClass(windowClass);

        // Create sidebar window
        ImGui.begin("Sidebar", ImGuiWindowFlags.NoTitleBar);
        ImGui.end();
    }
}
