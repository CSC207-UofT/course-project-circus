package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.callbacks.UICallback;
import imgui.ImGui;

/**
 * An item of the Menu component.
 */
public class MenuItem extends UIComponent {
    private String label;
    private String shortcut;
    private boolean enabled;
    private UICallback onSelectCallback;

    private boolean previousSelected;
    private boolean selected;

    /**
     * Construct a MenuItem with a label.
     * @param label The label of this MenuItem.
     */
    public MenuItem(String label) {
        this(label, null, true, null);
    }

    /**
     * Construct a MenuItem with a label and callback.
     * @param label The label of this MenuItem.
     * @param onSelectCallback The callback to execute when this menu item is selected.
     */
    public MenuItem(String label, UICallback onSelectCallback) {
        this(label, null, true, onSelectCallback);
    }

    /**
     * Construct a MenuItem.
     * @param label The label of this MenuItem.
     * @param shortcut Keyboard shortcut to access this Menu. Can be null.
     * @param enabled Whether this MenuItem is enabled.
     * @param onSelectCallback The callback to execute when this menu item is selected.
     */
    public MenuItem(String label, String shortcut, boolean enabled, UICallback onSelectCallback) {
        this.label = label;
        this.shortcut = shortcut;
        this.enabled = enabled;
        this.onSelectCallback = onSelectCallback;
    }

    /**
     * Render this MenuItem.
     */
    @Override
    public void render(DesktopApplication application) {
        previousSelected = selected;
        selected = ImGui.menuItem(label, shortcut, selected, enabled);
        if (previousSelected != selected) {
            onSelectCallback.execute(this, application);
        }
    }
}
