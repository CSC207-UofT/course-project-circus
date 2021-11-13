package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

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
    protected boolean preDraw(DesktopApplication application) {
        return ImGui.beginMenu(label, isEnabled());
    }

    @Override
    protected void postDraw(DesktopApplication application) {
        ImGui.endMenu();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
