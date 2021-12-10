package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.internal.flag.ImGuiItemFlags;
import imgui.type.ImInt;
import utils.RandomUtils;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import java.util.List;
import java.util.Random;

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
        PartCatalogue partCatalogue = application.getWarehouse().getState().getPartCatalogue();
        boolean disable = orderBatchSize.get() == 0 || partCatalogue.getParts().isEmpty();
        if (disable) {
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, 0.5f);
        }
        if (ImGui.button("Batch Create PlaceOrders",
                ImGui.getContentRegionAvailX(), 0)) {
            List<Part> parts = partCatalogue.getParts();

            Random random = new Random();
            for (int i = 0; i < orderBatchSize.get(); i++) {
                // Choose random part from catalogue and try to add it to the warehouse
                Part part = parts.get(random.nextInt(parts.size()));
                application.getWarehouse().receiveItem(new Item(part));
            }
            orderBatchSize.set(0);
        }
        if (disable) {
            imgui.internal.ImGui.popItemFlag();
            ImGui.popStyleVar();
        }
    }
}
