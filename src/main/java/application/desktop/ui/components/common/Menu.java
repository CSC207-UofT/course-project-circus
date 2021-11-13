package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

import java.util.List;

public class Menu extends UIComponent {
    public String label;
    private List<UIComponent> components;
    private boolean enabled;


    /**
     * Construct a new Menu.
     * @param label The label of this Menu.
     * @param components The items of this Menu.
     */
    public Menu(String label, UIComponent... components) {
        this.label = label;
        this.components = List.of(components);
    }

    @Override
    public void render(DesktopApplication application) {
        if (ImGui.beginMenu(label)) {
            for (UIComponent component : components) {
                component.render(application);
            }
            ImGui.endMenu();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
