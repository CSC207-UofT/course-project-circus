package application.desktop.ui.components.common.buttons;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.Component;
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

    private float borderWidth;
    private float borderRadius;
    private Colour activeColour;

    private final ComponentEvent onClickedEvent;

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
        this(label, icon, "", 0, 0, null, true);
    }

    /**
     * Construct a Button with a label.
     * @param label The label of this Button.
     * @param icon The icon of this Button.
     * @param enabled Whether this Button is enabled.
     */
    public Button(String label, FontAwesomeIcon icon, String tooltip,
                  float borderWidth, float borderRadius,
                  Colour backgroundColour,
                  boolean enabled) {
        this.label = label;
        this.icon = icon;
        this.iconSeparator = "\t";
        this.tooltip = tooltip;

        this.borderWidth = borderWidth;
        this.borderRadius = borderRadius;
        this.activeColour = backgroundColour;

        onClickedEvent = new ComponentEvent();
        setEnabled(enabled);
    }

    /**
     * Render this Button.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        boolean disabled = !isEnabled();
        if (disabled) {
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);
        }

        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, borderWidth);
        ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, borderRadius);

        int currentColour;
        if (activeColour == null) {
            // Use default button colour if none was provided
            ImVec4 col = ImGui.getStyle().getColor(ImGuiCol.Button);
            currentColour = ImGui.colorConvertFloat4ToU32(col.x, col.y, col.z, col.w);
        } else {
            currentColour = activeColour.toU32Colour();
        }
        ImGui.pushStyleColor(ImGuiCol.Button, currentColour);

        String iconCode = icon != null ? icon.getIconCode() : "";
        String iconSuffix = (icon != null && !label.isEmpty() ? iconSeparator : "");
        if (ImGui.button(iconCode + iconSuffix + label)) {
            onClickedEvent.execute(this, application);
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

        ImGui.popStyleColor(); // Button colour
        ImGui.popStyleVar(); // Frame rounding
        ImGui.popStyleVar(); // Frame border size

        if (disabled) {
            imgui.internal.ImGui.popItemFlag(); // Disabled flag
            ImGui.popStyleVar(); // Alpha variable
        }

        super.drawContent(application);
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

    public Colour getActiveColour() {
        return activeColour;
    }

    public void setActiveColour(Colour activeColour) {
        this.activeColour = activeColour;
    }

    /**
     * This event is called when this MenuItem is clicked on.
     */
    public ComponentEvent getOnClickedEvent() {
        return onClickedEvent;
    }
}
