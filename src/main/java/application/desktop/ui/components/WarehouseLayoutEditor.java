package application.desktop.ui.components;

import application.desktop.ui.components.common.Panel;

/**
 * Editor window for warehouse layouts.
 */
public class WarehouseLayoutEditor extends Panel {
    private final WarehouseLayoutCanvas canvas;

    /**
     * Construct a new WarehouseLayoutEditor.
     */
    public WarehouseLayoutEditor() {
        super("Warehouse Layout");
        setCanClose(false);

        canvas = new WarehouseLayoutCanvas();
        addChild(canvas);
    }

    public WarehouseLayoutCanvas getCanvas() {
        return canvas;
    }
}
