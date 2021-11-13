package application.desktop.ui.components;

import application.desktop.ui.components.common.Panel;

/**
 * Editor window for warehouse layouts.
 */
public class WarehouseLayoutEditor extends Panel {
    private final WarehouseLayoutCanvas canvas;

    public WarehouseLayoutEditor() {
        super("Warehouse Layout");
        canvas = new WarehouseLayoutCanvas();
        addChild(canvas);
    }

    public WarehouseLayoutCanvas getCanvas() {
        return canvas;
    }
}
