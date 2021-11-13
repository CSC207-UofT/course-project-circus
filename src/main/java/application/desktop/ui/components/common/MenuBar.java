package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

import java.util.List;

/**
 * A menu bar component.
 */
public class MenuBar extends UIComponent {
    private List<Menu> menus;

    /**
     * Construct a MenuBar with the given menus.
     * @param menus The menus in the bar.
     */
    public MenuBar(Menu... menus) {
        this.menus = List.of(menus);
    }

    /**
     * Render this MenuBar.
     */
    @Override
    public void render(DesktopApplication application) {
        if (ImGui.beginMenuBar()) {
            for (Menu menu : menus) {
                menu.render(application);
            }
            ImGui.endMenuBar();
        }
    }
}
