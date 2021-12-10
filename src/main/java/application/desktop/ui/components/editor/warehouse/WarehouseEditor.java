package application.desktop.ui.components.editor.warehouse;

import application.desktop.ui.components.common.Panel;
import application.desktop.ui.components.editor.PartCatalogueEditor;
import application.desktop.ui.components.editor.warehouse.renderers.WarehouseCanvasRenderer;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

/**
 * Editor window for the WarehouseLayout.
 */
public class WarehouseEditor<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Warehouse Layout###warehouse_editor_panel";

    private final WarehouseState<T, U> warehouseState;
    private final WarehouseCanvas<T, U> canvas;
    private final WarehouseInspectorPanel<T, U> inspector;

    /**
     * Construct a new WarehouseEditor given a WarehouseState.
     * @param warehouseState The WarehouseState to edit.
     */
    public WarehouseEditor(WarehouseState<T, U> warehouseState,
                           WarehouseCanvasRenderer<T, U> canvasRenderer,
                           PartCatalogueEditor partCatalogueEditor) {
        super(PANEL_ID);
        setShowMenuBar(true);
        setCloseable(false);
        setMovable(false);

        this.warehouseState = warehouseState;

        canvas = new WarehouseCanvas<>(warehouseState, canvasRenderer);
        inspector = new WarehouseInspectorPanel<>(this, partCatalogueEditor);
        WarehouseEditorToolbar<T, U> toolbar = new WarehouseEditorToolbar<>(this);
        addChildren(toolbar, canvas, inspector);

    }

    /**
     * Get the WarehouseState.
     */
    public WarehouseState<T, U> getWarehouseState() {
        return warehouseState;
    }

    /**
     * Get the WarehouseCanvas component.
     */
    public WarehouseCanvas<T, U> getCanvas() {
        return canvas;
    }

    /**
     * Get the WarehouseInspectorPanel.
     */
    public WarehouseInspectorPanel<T, U> getInspector() {
        return inspector;
    }
}
