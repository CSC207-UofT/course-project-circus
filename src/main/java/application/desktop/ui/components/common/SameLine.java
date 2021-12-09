package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

import java.util.List;

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
    protected void drawChildren() {
        List<Component> children = getChildren();
        for (int i = 0; i < children.size(); ++i) {
           children.get(i).draw();
           if (i != children.size() - 1) {
               ImGui.sameLine();
           }
        }
    }
}
