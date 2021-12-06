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
import warehouse.inventory.Item;
import warehouse.storage.StorageUnit;
import warehouse.tiles.StorageTile;
import warehouse.tiles.Tile;

public class WarehouseInspectorPanel extends Panel {
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

    private final WarehouseEditor warehouseEditor;

    /**
     * Construct an WarehouseInspectorPanel.
     */
    public WarehouseInspectorPanel(WarehouseEditor warehouseEditor) {
        super(PANEL_ID);
        setCloseable(false);
        setMovable(false);

        this.warehouseEditor = warehouseEditor;
    }

    @Override
    protected void drawContent(DesktopApplication application) {
        Tile selectedTile = warehouseEditor.getCanvas().getSelectedTile();
        if (selectedTile == null) {
            // no selection, so draw the world properties

        } else {
            int[] tilePosition = {selectedTile.getX(), selectedTile.getY()};
            imgui.internal.ImGui.pushItemFlag(ImGuiItemFlags.Disabled, true);
            ImGui.pushStyleVar(ImGuiStyleVar.Alpha, ImGui.getStyle().getAlpha() * 0.5f);

            ImGui.inputInt2("Position", tilePosition);

            imgui.internal.ImGui.popItemFlag();
            ImGui.popStyleVar();

            ImGui.labelText("Type", selectedTile.getClass().getSimpleName());

            if (selectedTile instanceof StorageTile) {
                // Draw heading
                ImGui.spacing();
                ImGui.separator();
                ImGui.spacing();
                ImGui.text("Storage");

                StorageUnit storageUnit = ((StorageTile) selectedTile).getStorageUnit();
                ImGui.textDisabled(String.format("%d items...", storageUnit.getContainer().getSize()));
                // Draw table
                drawStorageTileTable((StorageTile) selectedTile);
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
        String tableId = String.format("tile(%s,%s)_storage_table", storageTile.getX(), storageTile.getY());
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
                }
                ImGui.sameLine();
                if (ImGui.smallButton(FontAwesomeIcon.TrashAlt.getIconCode())) {
                    // Remove item button
                }
                ImGui.popStyleColor();

                ImGui.popID();
            }
            ImGui.endTable();
        }
    }
}
