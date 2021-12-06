package application.desktop.ui.components.editor.warehouse;

import application.desktop.ui.components.common.Panel;
import warehouse.Warehouse;
import warehouse.WarehouseState;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Warehouse###warehouse_editor_panel";

    private final WarehouseState warehouseState;
    private final WarehouseCanvas canvas;
    private final WarehouseInspectorPanel inspector;

    /**
     * Construct a new WarehouseEditor given a WarehouseState.
     * @param warehouseState The WarehouseState to edit.
     */
    public WarehouseEditor(WarehouseState warehouseState) {
        super(PANEL_ID);
        setShowMenuBar(true);
        setCloseable(false);
        setMovable(false);

        this.warehouseState = warehouseState;

        canvas = new WarehouseCanvas(warehouseState);
        inspector = new WarehouseInspectorPanel(this);
        WarehouseEditorToolbar toolbar = new WarehouseEditorToolbar(this);
        addChildren(toolbar, canvas, inspector);

    }

    /**
     * Get the WarehouseState.
     */
    public WarehouseState getWarehouseState() {
        return warehouseState;
    }

    /**
     * Get the WarehouseCanvas component.
     */
    public WarehouseCanvas getCanvas() {
        return canvas;
    }

    /**
     * Get the WarehouseInspectorPanel.
     */
    public WarehouseInspectorPanel getInspector() {
        return inspector;
    }
}
