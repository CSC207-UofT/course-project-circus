package application.desktop.ui.components.editor;

import application.desktop.ui.FontAwesomeIcon;
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

    private boolean isSimulationActive;
    private ImInt orderBatchSize;
    private ImInt partBatchSize;

    /**
     * Construct a SimulationPanel.
     */
    public SimulationPanel() {
        super(PANEL_ID);
        orderBatchSize = new ImInt(0);
        partBatchSize = new ImInt(0);
    }

    @Override
    protected void drawContent() {
        if (ImGui.button(isSimulationActive ?
                "Stop Simulation" :
                "Start Simulation",
                ImGui.getContentRegionAvailX(), 0)) {
            isSimulationActive = !isSimulationActive;
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
