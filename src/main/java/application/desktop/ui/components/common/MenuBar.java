package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;

/**
 * A menu bar component.
 */
public class MenuBar extends Component {
    /**
     * Construct a MenuBar with the given menus.
     * @param menus The menus in the bar.
     */
    public MenuBar(Menu... menus) {
        for (Menu menu : menus) {
            addChild(menu);
        }
    }

    @Override
    protected boolean preDraw(DesktopApplication application) {
        return ImGui.beginMenuBar();
    }

    @Override
    protected void postDraw(DesktopApplication application) {
        ImGui.endMenuBar();
    }
}
