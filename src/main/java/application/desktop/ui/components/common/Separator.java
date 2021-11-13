package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * Separator component.
 */
public class Separator extends Component {
    @Override
    public void draw(DesktopApplication application) {
        ImGui.separator();
    }
}
