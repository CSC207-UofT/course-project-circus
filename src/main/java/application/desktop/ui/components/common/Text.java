package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import imgui.ImGui;
import imgui.flag.ImGuiCol;

/**
 * Component for drawing text labels.
 */
public class Text extends Component {
    private String label;
    private Colour colour;

    /**
     * Construct an enabled Text component given a label.
     * @param label The label to draw.
     */
    public Text(String label) {
        this(label, null, true);
    }

    /**
     * Construct a Text component.
     * @param label The label to draw.
     * @param colour The colour of the text.
     * @param enabled Whether this component is enabled.
     */
    public Text(String label, Colour colour, boolean enabled) {
        this.label = label;
        this.colour = colour;
        setEnabled(enabled);
    }

    /**
     * Render this Text component.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        if (isEnabled()) {
            if (colour != null) {
                ImGui.pushStyleColor(ImGuiCol.Text, colour.toU32Colour());
            }

            ImGui.text(label);

            if (colour != null) {
                ImGui.popStyleColor();
            }
        } else {
            ImGui.textDisabled(label);
        }
        super.drawContent(application);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
