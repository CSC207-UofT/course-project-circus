package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.events.Event;
import imgui.ImGui;

/**
 * An item of the Menu component.
 */
public class MenuItem extends Component {
    private String label;
    private String shortcut;

    private final Event onSelectedEvent;

    private boolean selected;

    /**
     * Construct a MenuItem with a label.
     * @param label The label of this MenuItem.
     */
    public MenuItem(String label) {
        this(label, null, true);
    }

    /**
     * Construct a MenuItem.
     * @param label The label of this MenuItem.
     * @param shortcut Keyboard shortcut to access this Menu. Can be null.
     * @param enabled Whether this MenuItem is enabled.
     */
    public MenuItem(String label, String shortcut, boolean enabled) {
        this.label = label;
        this.shortcut = shortcut;
        this.enabled = enabled;
        this.onSelectedEvent = new Event();
    }

    /**
     * Render this MenuItem.
     */
    @Override
    public void draw(DesktopApplication application) {
        boolean previousSelected = selected;
        selected = ImGui.menuItem(label, shortcut, selected, enabled);
        if (previousSelected != selected) {
            onSelectedEvent.execute(this);
        }
        // Draw children of this component
        super.draw(application);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Event getOnSelectedEvent() {
        return onSelectedEvent;
    }

    public boolean isSelected() {
        return selected;
    }
}
