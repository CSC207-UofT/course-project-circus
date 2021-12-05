package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * Component for drawing text labels.
 */
public class Text extends Component {
    private String label;

    /**
     * Construct an enabled Text component given a label.
     * @param label The label to draw.
     */
    public Text(String label) {
        this(label, true);
    }

    /**
     * Construct a Text component.
     * @param label The label to draw.
     * @param enabled Whether this component is enabled.
     */
    public Text(String label, boolean enabled) {
        this.label = label;
        setEnabled(enabled);
    }

    /**
     * Render this Text component.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        if (isEnabled()) {
            ImGui.text(label);
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
