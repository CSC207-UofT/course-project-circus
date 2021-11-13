package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

import java.util.List;

/**
 * A menu component.
 */
public class Menu extends Component {
    /**
     * The label of this Menu.
     */
    private String label;

    /**
     * Construct a new Menu.
     * @param label The label of this Menu.
     * @param children The items of this Menu.
     */
    public Menu(String label, Component... children) {
        this.label = label;
        for (Component child : children) {
            addChild(child);
        }
    }

    @Override
    public void onDraw(DesktopApplication application) {
        if (ImGui.beginMenu(label, isEnabled())) {
            super.onDraw(application);
            ImGui.endMenu();
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
