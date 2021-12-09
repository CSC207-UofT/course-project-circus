package application.desktop.ui.components.common;

import imgui.ImGui;

/**
 * A menu bar component.
 */
public class MenuBar extends Component {
    /**
     * Construct a MenuBar with the given menus.
     * @param menus The menus in the bar.
     */
    public MenuBar(Component... menus) {
        for (Component menu : menus) {
            addChild(menu);
        }
    }

    @Override
    protected boolean preDraw() {
        return ImGui.beginMenuBar();
    }

    @Override
    protected void conditionalPostDraw() {
        ImGui.endMenuBar();
    }
}
