package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;
import imgui.ImGui;
import imgui.ImGuiWindowClass;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;

/**
 * Sidebar component for the DesktopApplication.
 */
public class Sidebar extends UIComponent {
    @Override
    public void render(DesktopApplication application) {
        // Setup window so that it automatically hides its tab bar
        ImGuiWindowClass windowClass = new ImGuiWindowClass();
        windowClass.addDockNodeFlagsOverrideSet(ImGuiDockNodeFlags.AutoHideTabBar);
        ImGui.setNextWindowClass(windowClass);

        // Create sidebar window
        ImGui.begin("Sidebar", ImGuiWindowFlags.NoTitleBar);
        ImGui.text("test");
        ImGui.end();
    }
}
