package application.desktop.ui.components.editor.inventory;

import application.desktop.ui.components.common.Panel;
import warehouse.inventory.PartCatalogue;

/**
 * Editor window for the Inventory.
 */
public class InventoryEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Inventory###inventory_editor_panel";

    private final PartCatalogue partCatalogue;

    /**
     * Construct an InventoryEditor given a PartCatalogue.
     * @param partCatalogue The PartCatalogue to edit.
     */
    public InventoryEditor(PartCatalogue partCatalogue) {
        super(PANEL_ID);
        setShowMenuBar(true);
        setCloseable(false);
        setMovable(false);

        this.partCatalogue = partCatalogue;
    }
}
