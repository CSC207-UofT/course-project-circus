package application.desktop.ui.components.common;

import application.desktop.ui.events.ComponentEvent;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic ImGui component.
 */
public class Component {
    private Component parent;
    private final List<Component> children;
    private boolean visible;
    private boolean enabled;
    private boolean inheritParentState;

    private final ComponentEvent onStartMouseHoverEvent;
    private final ComponentEvent onStopMouseHoverEvent;
    private final ComponentEvent whileMouseHoverEvent;

    protected ComponentState state;

    /**
     * Construct a new Component that is visible and enabled with no parent or children.
     */
    public Component() {
        this(null, new ArrayList<>(), true, true);
    }

    /**
     * Construct a new Component.
     * @param parent Parent of this Component.
     * @param children Children of this Component.
     * @param visible Whether to draw this Component.
     * @param enabled Whether this Component is enabled.
     */
    public Component(Component parent, List<Component> children, boolean visible, boolean enabled) {
        this.parent = parent;
        this.children = new ArrayList<>();
        for (Component child : children) {
            addChild(child);
        }
        this.visible = visible;
        this.enabled = enabled;
        this.state = ComponentState.DEFAULT;

        // Events
        this.onStartMouseHoverEvent = new ComponentEvent();
        this.onStopMouseHoverEvent = new ComponentEvent();
        this.whileMouseHoverEvent = new ComponentEvent();
    }

    /**
     * Set the parent of this Component.
     * @param component The new parent of this Component.
     */
    public void setParent(Component component) {
        setParent(component, false);
    }

    /**
     * Set the parent of this Component.
     * @param component The new parent of this Component.
     * @param inheritParentState Whether to inherit the state (e.g. mouse application.desktop.ui.events) of the parent.
     */
    public void setParent(Component component, boolean inheritParentState) {
        component.addChild(this, inheritParentState);
    }

    /**
     * Add a child to this Component.
     * @param child The child Component to add.
     */
    public void addChild(Component child) {
        addChild(child, false);
    }

    /**
     * Add child to this Component.
     * @param component The child Component to add.
     * @param inheritParentState Whether to inherit the state (e.g. mouse application.desktop.ui.events) of the parent.
     */
    public void addChild(Component component, boolean inheritParentState) {
        component.parent = this;
        component.inheritParentState = inheritParentState;
        children.add(component);
    }

    /**
     * Add children to this Component.
     * @param children The children Components to add.
     */
    public void addChildren(List<Component> children) {
        addChildren(children, false);
    }

    /**
     * Add children to this Component.
     * @param children The children Components to add.
     * @param inheritParentState Whether to inherit the state (e.g. mouse application.desktop.ui.events) of the parent.
     */
    public void addChildren(List<Component> children, boolean inheritParentState) {
        for (Component child : children) {
            addChild(child, inheritParentState);
        }
    }

    /**
     * Add children to this Component.
     * @param children The children Components to add.
     */
    public void addChildren(Component... children) {
        addChildren(false, children);
    }

    /**
     * Add children to this Component.
     * @param children The children Components to add.
     * @param inheritParentState Whether to inherit the state (e.g. mouse application.desktop.ui.events) of the parent.
     */
    public void addChildren(boolean inheritParentState, Component... children) {
        addChildren(List.of(children), inheritParentState);
    }

    /**
     * Draw this component.
     */
    public void draw() {
        if (!isVisible()) return;
        if (preDraw()) {
            drawContent();
            conditionalPostDraw();
        }
        postDraw();
        handleEvents();
    }

    /**
     * Called before drawContent.
     * @return A boolean indicating whether to continue drawing.
     */
    protected boolean preDraw() { return true; }

    /**
     * Called after drawContent if and only if preDraw returns True.
     */
    protected void conditionalPostDraw() { }

    /**
     * Called after drawContent regardless of what preDraw returns.
     */
    protected void postDraw() { }

    /**
     * Internal lifecycle method for drawing the component.
     * By default, draws the children of this component.
     */
    protected void drawContent() {
        drawChildren();
    }

    /**
     * Draw child Components.
     */
    protected void drawChildren() {
        for (Component child : children) {
            child.draw();
        }
    }

    /**
     * Update the state of this Component.
     */
    private void updateState() {
        if (inheritParentState && parent != null) {
            state = parent.state;
        } else {
            boolean isHovering = ImGui.isItemHovered();
            if (isHovering) {
                state = ComponentState.MOUSE_HOVERING;
            } else {
                state = ComponentState.DEFAULT;
            }
        }
    }

    /**
     * Process and handle application.desktop.ui.events.
     */
    protected void handleEvents() {
        ComponentState previousState = state;
        updateState();
        if (previousState != state) {
            // Our state has changed.
            if (state == ComponentState.MOUSE_HOVERING) {
                onStartMouseHoverEvent.execute(this);
            } else {
                onStopMouseHoverEvent.execute(this);
            }
        }
        if (state == ComponentState.MOUSE_HOVERING) {
            whileMouseHoverEvent.execute(this);
        }
    }

    public Component getParent() {
        return parent;
    }

    public List<Component> getChildren() {
        return children;
    }

    /**
     * Get if this Component is visible.
     * @return True if this Component is visible AND all ancestors are visible, and False otherwise.
     */
    public boolean isVisible() {
        Component current = this;
        while (current != null) {
            if(!current.visible) {
                return false;
            } else {
                current = current.parent;
            }
        }
        return true;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get if this Component is enabled.
     * @return True if this Component is enabled AND all ancestors are enabled, and False otherwise.
     */
    public boolean isEnabled() {
        Component current = this;
        while (current != null) {
            if(!current.enabled) {
                return false;
            } else {
                current = current.parent;
            }
        }
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInheritParentState() {
        return inheritParentState;
    }

    public void setInheritParentState(boolean inheritParentState) {
        this.inheritParentState = inheritParentState;
    }

    /**
     * This event is called when the mouse starts hovering over this Component.
     */
    public ComponentEvent getOnStartMouseHoverEvent() {
        return onStartMouseHoverEvent;
    }

    /**
     * This event is called when the mouse stops hovering over this Component.
     */
    public ComponentEvent getOnStopMouseHoverEvent() {
        return onStopMouseHoverEvent;
    }

    /**
     * This event is called every frame while the mouse hovers over this Component.
     */
    public ComponentEvent getWhileMouseHoverEvent() {
        return whileMouseHoverEvent;
    }
}
