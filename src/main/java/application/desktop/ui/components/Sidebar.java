package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;
import imgui.ImGui;
import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

/**
 * Sidebar component for the DesktopApplication.
 */
public class Sidebar extends UIComponent {
    private final WarehouseLayoutEditor warehouseLayoutEditor;

    /**
     * Construct a new Sidebar.
     * @param warehouseLayoutEditor A warehouse layout editor.
     */
    public Sidebar(WarehouseLayoutEditor warehouseLayoutEditor) {
        this.warehouseLayoutEditor = warehouseLayoutEditor;
    }

    @Override
    public void render(DesktopApplication application) {
        // Setup window so that it automatically hides its tab bar
        ImGuiWindowClass windowClass = new ImGuiWindowClass();
        windowClass.addDockNodeFlagsOverrideSet(ImGuiDockNodeFlags.AutoHideTabBar);
        ImGui.setNextWindowClass(windowClass);

        // Create sidebar window
        ImGui.begin("Sidebar", ImGuiWindowFlags.NoTitleBar);

        ImGui.beginGroup();
        ImGui.beginChild("item view", 0, -2.5f * ImGui.getFrameHeightWithSpacing());
        // TODO: ...
        ImGui.endChild();

        // Zoom controls
        ImGui.separator();

        WarehouseLayoutCanvas canvas = warehouseLayoutEditor.getCanvas();
        float[] value = {canvas.getZoom()};
        ImGui.pushItemWidth(-1);
        ImGui.sliderFloat("##label", value, canvas.getMinZoom(), canvas.getMaxZoom(), String.format("%.2f",
                canvas.getZoom()));
        ImGui.popItemWidth();
        canvas.setZoom(value[0]);

        ImGui.text("Zoom");
        ImGui.sameLine();
        if(ImGui.button("x0.5")) {
            canvas.setZoom(0.5f);
        }
        ImGui.sameLine();
        if(ImGui.button("x1")) {
            canvas.setZoom(1.0f);
        }
        ImGui.sameLine();
        if(ImGui.button("x2")) {
            canvas.setZoom(2.0f);
        }

        float offset = ImGui.getWindowContentRegionMaxX() - 100;
        ImGui.sameLine(offset);

        ImBoolean showGrid = new ImBoolean(canvas.isShowGrid());
        if(ImGui.checkbox("Show Grid", showGrid)) {
            canvas.setShowGrid(showGrid.get());
        }

        ImGui.endGroup();
        ImGui.end();
    }
}
