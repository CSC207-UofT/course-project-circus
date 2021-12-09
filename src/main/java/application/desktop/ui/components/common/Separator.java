package application.desktop.ui.components.common;

import imgui.ImGui;

/**
 * Separator component.
 */
public class Separator extends Component {
    /**
     * Draw the separator line.
     */
    @Override
    protected void drawContent() {
        ImGui.separator();
    }
}
