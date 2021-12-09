package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.events.ComponentEvent;
import imgui.ImGui;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.internal.flag.ImGuiItemFlags;

/**
 * A button component.
 */
public class Button extends Component {
    private String label;
    private FontAwesomeIcon icon;
    private String iconSeparator;
    private String tooltip;

    private boolean isToggleable;
    private boolean isToggled;

    private float borderWidth;
    private float borderRadius;

    private Colour normalColour;
    private Colour hoveredColour;
    private Colour activeColour;

    private final ComponentEvent onClickedEvent;
    private final ComponentEvent onToggledOnEvent;
    private final ComponentEvent onToggledOffEvent;

    /**
     * Construct a Button with a label.
     * @param label The label of this Button.
     */
    public Button(String label) {
        this(label, null);
    }

    /**
     * Construct a Button with a label and icon.
     * @param label The label of this Button.
     * @param icon The icon of this Button.
     */
    public Button(String label, FontAwesomeIcon icon) {
        this(label, icon, "");
    }

    /**
     * Construct a Button with a label, icon, and tooltip.
     * @param label The label of this Button.
     * @param icon The icon of this Button.
     * @param tooltip The tooltip for this Button.
     */
    public Button(String label, FontAwesomeIcon icon, String tooltip) {
        this(label, icon, tooltip, 0, 0, null, null, null, false, true);
    }

    /**
     * Construct a Button with a label.
     * @param label The label of this Button.
     * @param icon The icon of this Button.
     * @param enabled Whether this Button is enabled.
     */
    public Button(String label, FontAwesomeIcon icon, String tooltip,
                  float borderWidth, float borderRadius,
                  Colour normalColour, Colour hoveredColour, Colour activeColour,
                  boolean toggleable,
                  boolean enabled) {
        this.label = label;
        this.icon = icon;
        this.iconSeparator = "\t";
        this.tooltip = tooltip;

        this.isToggleable = toggleable;
        this.isToggled = false;

        this.borderWidth = borderWidth;
        this.borderRadius = borderRadius;

        this.normalColour = normalColour;
        this.hoveredColour = hoveredColour;
        this.activeColour = activeColour;

        onClickedEvent = new ComponentEvent();
        onToggledOnEvent = new ComponentEvent();
        onToggledOffEvent = new ComponentEvent();
        setEnabled(enabled);
    }

    /**
     * Render this Button.
     */
    @Override
    protected void drawContent() {
        boolean disabled = !isEnabled();
        if (disabled) {
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);
        }

        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, borderWidth);
        ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, borderRadius);

        int colourCount = 0;
        if (normalColour != null) {
            ImGui.pushStyleColor(ImGuiCol.Button, normalColour.toU32Colour());
            colourCount++;
        }
        if (hoveredColour != null) {
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, hoveredColour.toU32Colour());
            colourCount++;
        }
        if (activeColour != null) {
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, activeColour.toU32Colour());
            colourCount++;
        }

        if (isToggleable && isToggled) {
            int replacementColour;
            if (activeColour == null) {
                ImVec4 col = ImGui.getStyle().getColor(ImGuiCol.ButtonActive);
                replacementColour = ImGui.colorConvertFloat4ToU32(col.x, col.y, col.z, col.w);
            } else {
                replacementColour = activeColour.toU32Colour();
            }
            ImGui.pushStyleColor(ImGuiCol.Button, replacementColour);
            colourCount++;
        }

        boolean previousToggleState = isToggled;

        String iconCode = icon != null ? icon.getIconCode() : "";
        String iconSuffix = (icon != null && !label.isEmpty() ? iconSeparator : "");
        if (ImGui.button(iconCode + iconSuffix + label)) {
            onClickedEvent.execute(this);

            if (isToggleable) {
                isToggled = !isToggled;
            }
        }

        if (isToggleable && previousToggleState != isToggled) {
            if (isToggled) {
                onToggledOnEvent.execute(this);
            } else {
                onToggledOffEvent.execute(this);
            }
        }

        if (!tooltip.isEmpty()) {
            if (ImGui.isItemHovered()) {
                ImGui.beginTooltip();
                ImGui.pushTextWrapPos(ImGui.getFontSize() * 35.0f);
                ImGui.textUnformatted(tooltip);
                ImGui.popTextWrapPos();
                ImGui.endTooltip();
            }
        }

        // Pop button colour styles
        for (int i = 0; i < colourCount; i++) {
            ImGui.popStyleColor();
        }

        ImGui.popStyleVar(); // Frame rounding
        ImGui.popStyleVar(); // Frame border size

        if (disabled) {
            imgui.internal.ImGui.popItemFlag(); // Disabled flag
            ImGui.popStyleVar(); // Alpha variable
        }

        super.drawContent();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FontAwesomeIcon getIcon() {
        return icon;
    }

    public void setIcon(FontAwesomeIcon icon) {
        this.icon = icon;
    }

    public String getIconSeparator() {
        return iconSeparator;
    }

    public void setIconSeparator(String iconSeparator) {
        this.iconSeparator = iconSeparator;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isToggleable() {
        return isToggleable;
    }

    public void setToggleable(boolean toggleable) {
        this.isToggleable = toggleable;
    }

    public boolean isToggled() {
        return isToggled;
    }

    public void setToggled(boolean toggled) {
        isToggled = toggled;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }

    public Colour getNormalColour() {
        return normalColour;
    }

    public void setNormalColour(Colour normalColour) {
        this.normalColour = normalColour;
    }

    public Colour getHoveredColour() {
        return hoveredColour;
    }

    public void setHoveredColour(Colour hoveredColour) {
        this.hoveredColour = hoveredColour;
    }

    public Colour getActiveColour() {
        return activeColour;
    }

    public void setActiveColour(Colour activeColour) {
        this.activeColour = activeColour;
    }

    /**
     * This event is called when this Button is clicked on.
     */
    public ComponentEvent getOnClickedEvent() {
        return onClickedEvent;
    }

    /**
     * This event is called iff and this Button is toggled ON AND it is toggleable.
     */
    public ComponentEvent getOnToggledOnEvent() {
        return onToggledOnEvent;
    }

    /**
     * This event is called iff and this Button is toggled OFF AND it is toggleable.
     */
    public ComponentEvent getOnToggledOffEvent() {
        return onToggledOffEvent;
    }
}
