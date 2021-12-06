package application.desktop.ui.components.editor.warehouse;

import application.desktop.ui.components.common.Panel;
import warehouse.Warehouse;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Warehouse###warehouse_editor_panel";

    private final Warehouse warehouse;
    private final WarehouseCanvas canvas;
    private final WarehouseInspectorPanel inspector;

    /**
     * Construct a new WarehouseEditor given a Warehouse.
     * @param warehouse The Warehouse to edit.
     */
    public WarehouseEditor(Warehouse warehouse) {
        super(PANEL_ID);
        setShowMenuBar(true);
        setCloseable(false);
        setMovable(false);

        this.warehouse = warehouse;

        canvas = new WarehouseCanvas(warehouse);
        inspector = new WarehouseInspectorPanel(this);
        WarehouseEditorToolbar toolbar = new WarehouseEditorToolbar(this);
        addChildren(toolbar, canvas, inspector);

    }

    /**
     * Get the Warehouse.
     */
    public Warehouse getWarehouse() {
        return warehouse;
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
