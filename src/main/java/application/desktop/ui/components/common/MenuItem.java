package application.desktop.ui.components.common;

import application.desktop.ui.events.ComponentEvent;
import imgui.ImGui;
import imgui.type.ImBoolean;

/**
 * An item of the Menu component.
 */
public class MenuItem extends Component {
    private String label;
    private String shortcut;
    private boolean toggle;

    private final ComponentEvent onSelectedToggleEvent;
    private final ComponentEvent onClickedEvent;

    private final ImBoolean selected;

    /**
     * Construct a MenuItem with a label.
     * @param label The label of this MenuItem.
     */
    public MenuItem(String label) {
        this(label, null, true, false);
    }

    /**
     * Construct a MenuItem.
     * @param label The label of this MenuItem.
     * @param shortcut Keyboard shortcut to access this Menu. Can be null.
     * @param enabled Whether this MenuItem is enabled.
     * @param toggle Whether this MenuItem is toggleable.
     */
    public MenuItem(String label, String shortcut, boolean enabled, boolean toggle) {
        this.label = label;
        this.shortcut = shortcut;
        this.toggle = toggle;

        onSelectedToggleEvent = new ComponentEvent();
        onClickedEvent = new ComponentEvent();
        selected = new ImBoolean(false);
        setEnabled(enabled);
    }

    /**
     * Render this MenuItem.
     */
    @Override
    protected void drawContent() {
        boolean previousSelected = selected.get();
        if(ImGui.menuItem(label, shortcut, selected, isEnabled())) {
            if (!toggle) {
                selected.set(false);
            }
            onClickedEvent.execute(this);
        }

        if (previousSelected != selected.get()) {
            onSelectedToggleEvent.execute(this);
        }

        super.drawContent();
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

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    /**
     * This event is called when this MenuItem is selected.
     */
    public ComponentEvent getOnSelectedToggleEvent() {
        return onSelectedToggleEvent;
    }

    /**
     * This event is called when this MenuItem is clicked on.
     */
    public ComponentEvent getOnClickedEvent() {
        return onClickedEvent;
    }
}
