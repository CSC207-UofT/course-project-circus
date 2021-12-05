package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Panel;

public class WarehouseInspectorPanel extends Panel {
    /**
     * The id of this panel.
     */
    private static final String PANEL_ID = "Inspector##inspector_panel";

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
    }
}
