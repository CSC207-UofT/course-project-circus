package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * A layout component for drawing other components on the same line. Draws its children on the same line.
 */
public class SameLine extends Component {
    public SameLine(Component... children) {
        super();
        for (Component child : children) {
            addChild(child);
        }
    }

    @Override
    protected void drawChildren(DesktopApplication application) {
        for (Component child : getChildren()) {
            child.draw(application);
            ImGui.sameLine();
        }
    }
}
