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
    public void onDraw(DesktopApplication application) {
        ImGui.separator();
    }
}
