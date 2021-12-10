package application.desktop.ui.components.editor;

import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.Panel;
import application.desktop.ui.components.editor.warehouse.WarehouseEditor;
import application.desktop.ui.utils.DrawingUtils;
import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import warehouse.logistics.orders.Order;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Tile;

import java.text.SimpleDateFormat;

/**
 * Panel for displaying and editing the OrderQueue.
 */
public class OrderEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Orders###order_editor_panel";
    /**
     * Normal colour for the robot link.
     */
    private static final Colour ROBOT_LINK_NORMAL_COLOUR = new Colour(254, 174, 222);
    /**
     * Active colour for the robot link.
     */
    private static final Colour ROBOT_LINK_ACTIVE_COLOUR = new Colour(249, 248, 113);
    /**
     * Tooltip for the robot link.
     */
    private static final String ROBOT_LINK_TOOLTIP = String.format("%s  Highlight robot",
            FontAwesomeIcon.ExternalLinkAlt.getIconCode());

    private final OrderQueue orderQueue;
    private final WarehouseEditor<?, ?> warehouseEditor;

    /**
     * Construct an OrderEditor given an OrderQueue.
     * @param orderQueue The OrderQueue to edit.
     */
    public OrderEditor(OrderQueue orderQueue, WarehouseEditor<?, ?> warehouseEditor) {
        super(PANEL_ID);
        setCloseable(false);

        this.orderQueue = orderQueue;
        this.warehouseEditor = warehouseEditor;
    }

    @Override
    public void drawContent() {
        // Initialise some flags for the table
        int tableFlags = ImGuiTableFlags.Resizable | ImGuiTableFlags.Reorderable | ImGuiTableFlags.Hideable |
                ImGuiTableFlags.RowBg | ImGuiTableFlags.BordersOuter | ImGuiTableFlags.BordersV |
                ImGuiTableFlags.NoBordersInBody | ImGuiTableFlags.ScrollY;
        // Make the table 25 rows tall...
        float tableHeight = ImGui.getTextLineHeightWithSpacing() * 15.0f;
        if (ImGui.beginTable("order_table", 5, tableFlags, 0, tableHeight, 0)) {
            // Declare columns
            ImGui.tableSetupColumn("ID", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Created At", ImGuiTableColumnFlags.WidthStretch, 0);
            ImGui.tableSetupColumn("Type", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Status", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Assigned", ImGuiTableColumnFlags.NoSort |
                    ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupScrollFreeze(0, 1); // Make row always visible
            ImGui.tableHeadersRow();

            int rowId = 0;
            for (Order order : orderQueue.peekOrders()) {
                drawOrderRow(rowId++, order);
            }
            for (Order order : orderQueue.getProcessedOrders()) {
                drawOrderRow(rowId++, order);
            }
            ImGui.endTable();
        }
    }

    private void drawOrderRow(int rowId, Order order) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        ImGui.pushID(rowId);
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
        ImGui.tableNextColumn();
        if (order.getHandler() == null) {
            ImGui.text("N/A");
        } else {
            Robot robot = order.getHandler();
            if (DrawingUtils.hyperlinkLabel(robot.getId(),
                    ROBOT_LINK_TOOLTIP,
                    ROBOT_LINK_NORMAL_COLOUR, ROBOT_LINK_ACTIVE_COLOUR)) {
                // Select the tile that the robot is on
                RobotMapper<?> robotMapper = warehouseEditor.getWarehouseState().getRobotMapper();
                int tileIndex = robotMapper.getRobotTileIndex(robot);
                Tile tile = warehouseEditor.getWarehouseState().getLayout().getTileAt(tileIndex);
                warehouseEditor.getCanvas().setSelectedTile(tile);
                ImGui.setWindowFocus(warehouseEditor.getTitle());
            }
        }
        ImGui.popID();
    }
}
