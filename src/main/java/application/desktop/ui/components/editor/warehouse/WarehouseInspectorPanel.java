package application.desktop.ui.components.editor.warehouse;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.Panel;
import application.desktop.ui.utils.DrawingUtils;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.internal.flag.ImGuiItemFlags;
import warehouse.WarehouseLayout;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.inventory.Item;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.storage.StorageUnit;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;

import java.util.List;

public class WarehouseInspectorPanel<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends Panel {
    /**
     * The id of this panel.
     */
    private static final String PANEL_ID = "Properties##inspector_panel";
    /**
     * Normal colour for the part hyperlink.
     */
    private static final Colour PART_LINK_NORMAL_COLOUR = new Colour(254, 174, 222);
    /**
     * Active colour for the part hyperlink.
     */
    private static final Colour PART_LINK_ACTIVE_COLOUR = new Colour(249, 248, 113);
    /**
     * Tooltip for the part link.
     */
    private static final String PART_LINK_TOOLTIP = String.format("%s  Open in part catalogue",
            FontAwesomeIcon.ExternalLinkAlt.getIconCode());

    private final WarehouseEditor<T, U> warehouseEditor;

    /**
     * Construct an WarehouseInspectorPanel.
     */
    public WarehouseInspectorPanel(WarehouseEditor<T, U> warehouseEditor) {
        super(PANEL_ID);
        setCloseable(false);

        this.warehouseEditor = warehouseEditor;
    }

    @Override
    protected void drawContent() {
        Tile selectedTile = warehouseEditor.getCanvas().getSelectedTile();
        if (selectedTile != null) {
            drawTileInspector(selectedTile);
        }
    }

    /**
     * Draws the inspector for the given Tile.
     */
    private void drawTileInspector(Tile tile) {
        WarehouseCoordinateSystem<U> coordinateSystem = warehouseEditor.getWarehouseState().getCoordinateSystem();
        U tilePosition = coordinateSystem.projectIndexToCoordinate(tile.getIndex());
        ImGui.labelText("Position", tilePosition.toString());
        ImGui.labelText("Type", tile.getClass().getSimpleName());

        ImGui.separator();
        ImGui.spacing();

        if (tile instanceof StorageTile) {
            if (ImGui.treeNode("Storage")) {
                StorageUnit storageUnit = ((StorageTile) tile).getStorageUnit();
                ImGui.textDisabled(String.format("%d items...", storageUnit.getContainer().getSize()));
                // Draw table
                drawStorageTileTable((StorageTile) tile);
                ImGui.treePop();
            }
        } else {
            if (ImGui.treeNode("Robots")) {
                ImGui.textDisabled("View all robots on this tile...");

                drawRobotsTable(tile);

                ImGui.treePop();
            }
        }
    }

    /**
     * Draws a table of the items stored in the given StorageTile.
     */
    private void drawStorageTileTable(StorageTile storageTile) {
        // Initialise some flags for the table
        int tableFlags = ImGuiTableFlags.Resizable | ImGuiTableFlags.Reorderable | ImGuiTableFlags.Hideable |
                ImGuiTableFlags.RowBg | ImGuiTableFlags.BordersOuter | ImGuiTableFlags.BordersV |
                ImGuiTableFlags.NoBordersInBody | ImGuiTableFlags.ScrollY;
        // Make the table 25 rows tall...
        float tableHeight = ImGui.getTextLineHeightWithSpacing() * 25.0f;
        String tableId = String.format("tile%s_storage_table", storageTile.getIndex());
        if (ImGui.beginTable(tableId, 3, tableFlags, 0, tableHeight, 0)) {
            // Declare columns
            ImGui.tableSetupColumn("ID", ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupColumn("Part", ImGuiTableColumnFlags.WidthStretch, 0);
            ImGui.tableSetupColumn("Action", ImGuiTableColumnFlags.NoSort |
                    ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupScrollFreeze(0, 1); // Make row always visible
            ImGui.tableHeadersRow();

            int rowId = 0;
            StorageUnit storageUnit = storageTile.getStorageUnit();
            for (Item item : storageUnit.getContainer().getItems()) {
                ImGui.pushID(rowId++);
                ImGui.tableNextRow();

                // ID column
                ImGui.tableNextColumn();
                ImGui.text(item.getId());
                // Part column
                ImGui.tableNextColumn();
                if (DrawingUtils.hyperlinkLabel(item.getPart().getName(),
                        PART_LINK_TOOLTIP,
                        PART_LINK_NORMAL_COLOUR, PART_LINK_ACTIVE_COLOUR)) {
                    // Jump to this part in the part catalogue...
                }
                // Action column
                ImGui.tableNextColumn();
                ImGui.pushStyleColor(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0, 0, 0, 0));
                if (ImGui.smallButton(FontAwesomeIcon.PencilAlt.getIconCode())) {
                    // Edit item button
                    // Tooltips
                    ImGui.beginTooltip();
                    ImGui.pushTextWrapPos(ImGui.getFontSize() * 17.5f);
                    ImGui.textUnformatted("Edit this item");
                    ImGui.popTextWrapPos();
                    ImGui.endTooltip();
                }
                ImGui.sameLine();
                if (ImGui.smallButton(FontAwesomeIcon.TrashAlt.getIconCode())) {
                    // Remove item button
                    // Tooltips
                    ImGui.beginTooltip();
                    ImGui.pushTextWrapPos(ImGui.getFontSize() * 17.5f);
                    ImGui.textUnformatted("Instantly removes this item from this tile's storage unit");
                    ImGui.popTextWrapPos();
                    ImGui.endTooltip();
                }
                ImGui.popStyleColor();

                ImGui.popID();
            }
            ImGui.endTable();
        }
    }

    /**
     * Draws a table of Robots on the given Tile.
     */
    private void drawRobotsTable(Tile tile) {
        RobotMapper<U> robotMapper = warehouseEditor.getWarehouseState().getRobotMapper();
        // Initialise some flags for the table
        int tableFlags = ImGuiTableFlags.Resizable | ImGuiTableFlags.Reorderable | ImGuiTableFlags.Hideable |
                ImGuiTableFlags.RowBg | ImGuiTableFlags.BordersOuter | ImGuiTableFlags.BordersV |
                ImGuiTableFlags.NoBordersInBody | ImGuiTableFlags.ScrollY;
        // Make the table 25 rows tall...
        float tableHeight = ImGui.getTextLineHeightWithSpacing() * 15.0f;
        String tableId = String.format("tile%d_robot_table", tile.getIndex());
        if (ImGui.beginTable(tableId, 3, tableFlags, 0, tableHeight, 0)) {
            // Declare columns
            ImGui.tableSetupColumn("ID", ImGuiTableColumnFlags.WidthStretch, 0);
            ImGui.tableSetupColumn("Action", ImGuiTableColumnFlags.NoSort |
                    ImGuiTableColumnFlags.WidthFixed, 0);
            ImGui.tableSetupScrollFreeze(0, 1); // Make row always visible
            ImGui.tableHeadersRow();

            List<Robot> robots = robotMapper.getRobotsAt(tile.getIndex());
            for (int i = 0; i < robots.size(); i++) {
                ImGui.pushID(i);
                ImGui.tableNextRow();

                // ID column
                ImGui.tableNextColumn();

                Robot robot = robots.get(i);
                ImGui.text(robot.getId());
                // Action column
                ImGui.tableNextColumn();
                ImGui.pushStyleColor(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0, 0, 0, 0));
                if (ImGui.smallButton(FontAwesomeIcon.ExternalLinkAlt.getIconCode())) {
                    // TODO: Implement move robot
                }
                ImGui.sameLine();

                if (ImGui.isItemHovered()) {
                    ImGui.beginTooltip();
                    ImGui.pushTextWrapPos(ImGui.getFontSize() * 17.5f);
                    ImGui.textUnformatted("Move this Robot to a new tile");
                    ImGui.popTextWrapPos();
                    ImGui.endTooltip();
                }

                if (ImGui.smallButton(FontAwesomeIcon.TrashAlt.getIconCode())) {
                    // Remove item button
                    robotMapper.removeRobot(robot);
                }
                if (ImGui.isItemHovered()) {
                    ImGui.beginTooltip();
                    ImGui.pushTextWrapPos(ImGui.getFontSize() * 17.5f);
                    ImGui.textUnformatted("Remove this Robot from the warehouse");
                    ImGui.popTextWrapPos();
                    ImGui.endTooltip();
                }

                ImGui.popStyleColor();
                ImGui.popID();
            }
            ImGui.endTable();
        }
    }
}