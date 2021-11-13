package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.events.Event;
import imgui.ImGui;
import imgui.flag.ImGuiMouseButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic ImGui component.
 */
public class Component {
    protected boolean enabled;
    protected List<Component> children;

    private final Event onStartMouseHoverEvent;
    private final Event onStopMouseHoverEvent;
    private final Event whileMouseHoverEvent;

    /**
     * Current hover state.
     */
    private boolean isHovering;

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

        // Events
        this.onStartMouseHoverEvent = new Event();
        this.onStopMouseHoverEvent = new Event();
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
        boolean previousIsHovering = isHovering;
        isHovering = ImGui.isItemHovered();
        if (previousIsHovering != isHovering) {
            // Our hover state has changed. This means that we've either started or stopped hovering.
            if (isHovering) {
                onStartMouseHoverEvent.execute(this);
            } else {
                onStopMouseHoverEvent.execute(this);
            }
        }
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

    /**
     * This event is called when the mouse starts hovering over this Component.
     */
    public Event getOnStartMouseHoverEvent() {
        return onStartMouseHoverEvent;
    }

    /**
     * This event is called when the mouse stops hovering over this Component.
     */
    public Event getOnStopMouseHoverEvent() {
        return onStopMouseHoverEvent;
    }

    /**
     * This event is called every frame while the mouse hovers over this Component.
     */
    public Event getWhileMouseHoverEvent() {
        return whileMouseHoverEvent;
    }
}
