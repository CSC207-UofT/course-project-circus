package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

import java.util.List;

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

    /**
     * Render this MenuBar.
     */
    @Override
    public void onDraw(DesktopApplication application) {
        if (ImGui.beginMenuBar()) {
            super.onDraw(application);
            ImGui.endMenuBar();
        }
    }
}
