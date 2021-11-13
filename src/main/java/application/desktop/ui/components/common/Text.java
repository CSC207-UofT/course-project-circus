package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import imgui.ImGui;

/**
 * Component for drawing text labels.
 */
public class Text extends Component {
    private String label;

    public Text(String label) {
        this.label = label;
    }

    @Override
    protected void drawContent(DesktopApplication application) {
        ImGui.text(label);
        super.drawContent(application);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
