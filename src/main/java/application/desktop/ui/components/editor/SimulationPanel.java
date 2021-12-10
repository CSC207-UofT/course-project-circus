package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.internal.flag.ImGuiItemFlags;
import imgui.type.ImInt;

/**
 * Panel for controlling simulation options.
 */
public class SimulationPanel extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Simulation###simulation_panel";

    private final DesktopApplication<?, ?> application;
    private final ImInt orderBatchSize;
    private final ImInt partBatchSize;

    /**
     * Construct a SimulationPanel.
     */
    public SimulationPanel(DesktopApplication<?, ?> application) {
        super(PANEL_ID);
        this.application = application;
        orderBatchSize = new ImInt(0);
        partBatchSize = new ImInt(0);
    }

    @Override
    protected void drawContent() {
        boolean isSimulationEnabled = application.isSimulationEnabled();
        if (ImGui.button(isSimulationEnabled ?
                "Stop Simulation" :
                "Start Simulation",
                ImGui.getContentRegionAvailX(), 0)) {
            application.setSimulationEnabled(!isSimulationEnabled);
        }

        ImGui.spacing();
        ImGui.separator();
        ImGui.spacing();

        ImGui.inputInt("Order Batch Size", orderBatchSize);
        if (orderBatchSize.get() == 0) {
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, 0.5f);
        }
        if (ImGui.button("Batch Add Random Orders",
                ImGui.getContentRegionAvailX(), 0)) {
            orderBatchSize.set(0);
        }
        if (orderBatchSize.get() == 0) {
            imgui.internal.ImGui.popItemFlag();
            ImGui.popStyleVar();
        }

        ImGui.spacing();
        ImGui.separator();
        ImGui.spacing();

        ImGui.inputInt("Part Batch Size", partBatchSize);
        if (partBatchSize.get() == 0) {
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, 0.5f);
        }
        if (ImGui.button("Batch Add Random Parts",
                ImGui.getContentRegionAvailX(), 0)) {
            partBatchSize.set(0);
        }
        if (partBatchSize.get() == 0) {
            imgui.internal.ImGui.popItemFlag();
            ImGui.popStyleVar();
        }
    }
}
