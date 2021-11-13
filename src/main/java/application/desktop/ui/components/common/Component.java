package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.events.Event;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic ImGui component.
 */
public class Component {
    protected boolean enabled;
    protected List<Component> children;

    private final Event whileMouseHoverEvent;

    /**
     * Construct a new Component with no children and that is enabled.
     */
    public Component() {
        this(true, new ArrayList<>());
    }

    /**
     * Construct a new Component.
     * @param enabled Whether this Component is enabled.
     * @param children Children of this Component.
     */
    public Component(boolean enabled, List<Component> children) {
        this.enabled = enabled;
        this.children = children;
        this.whileMouseHoverEvent = new Event();
    }

    /**
     * Add child to this Component.
     * @param component The child Component to add.
     */
    public void addChild(Component component) {
        children.add(component);
    }

    /**
     * Draw this component.
     */
    public void draw(DesktopApplication application) {
        for (Component child : children) {
            child.draw(application);
        }
        handleEvents();
    }

    protected void handleEvents() {
        boolean isHovering = ImGui.isItemHovered();
        if (isHovering) {
            whileMouseHoverEvent.execute(this);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Event getWhileMouseHoverEvent() {
        return whileMouseHoverEvent;
    }
}
