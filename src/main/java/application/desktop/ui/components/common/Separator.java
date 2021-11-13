package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * Separator component.
 */
public class Separator extends Component {
    /**
     * Draw the separator line.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        ImGui.separator();
    }
}
