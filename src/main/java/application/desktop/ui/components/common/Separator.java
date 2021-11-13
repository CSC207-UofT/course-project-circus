package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * Separator component.
 */
public class Separator extends UIComponent {
    @Override
    public void render(DesktopApplication application) {
        ImGui.separator();
    }
}
