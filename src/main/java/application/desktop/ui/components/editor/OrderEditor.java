package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;
import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import warehouse.logistics.orders.Order;
import warehouse.logistics.orders.OrderQueue;

import java.text.SimpleDateFormat;

/**
 * Panel for displaying and editing the OrderQueue.
 */
public class OrderEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Orders###order_editor_panel";

    private final OrderQueue orderQueue;

    /**
     * Construct an OrderEditor given an OrderQueue.
     * @param orderQueue The OrderQueue to edit.
     */
    public OrderEditor(OrderQueue orderQueue) {
        super(PANEL_ID);
        setCloseable(false);

        this.orderQueue = orderQueue;
    }

    @Override
    public void drawContent() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        // Initialise some flags for the table
        int tableFlags = ImGuiTableFlags.Resizable | ImGuiTableFlags.Reorderable | ImGuiTableFlags.Hideable |
                ImGuiTableFlags.RowBg | ImGuiTableFlags.BordersOuter | ImGuiTableFlags.BordersV |
                ImGuiTableFlags.NoBordersInBody | ImGuiTableFlags.ScrollY;
        // Make the table 25 rows tall...
        float tableHeight = ImGui.getTextLineHeightWithSpacing() * 15.0f;
        if (ImGui.beginTable("order_table", 5, tableFlags, 0, tableHeight, 0)) {
            // Declare columns
            ImGui.tableSetupColumn("ID", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Created At", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Type", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Status", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Assigned", ImGuiTableColumnFlags.NoSort |
                    ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupScrollFreeze(0, 1); // Make row always visible
            ImGui.tableHeadersRow();

            int rowId = 0;
            for (Order order : orderQueue.peekOrders()) {
                ImGui.pushID(rowId++);
                ImGui.tableNextRow();

                // ID column
                ImGui.tableNextColumn();
                ImGui.text(order.getId());
                // Created at column
                ImGui.tableNextColumn();
                ImGui.text(dateFormatter.format(order.getCreatedAt()));
                // Type column
                ImGui.tableNextColumn();
                ImGui.text(order.getClass().getSimpleName());
                // Status column
                ImGui.tableNextColumn();
                ImGui.text(order.getStatus().name());
                // Assigned
                if (order.getHandler() == null) {
                    ImGui.text("N/A");
                } else {
                    ImGui.text(String.format("Robot %s", order.getHandler().getId()));
                }
                ImGui.popID();
            }
            ImGui.endTable();
        }
    }
}
