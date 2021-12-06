package application.desktop.ui.components.editor;

import application.desktop.ui.components.common.Panel;
import warehouse.logistics.orders.OrderQueue;

/**
 * Panel for displaying and editing the OrderQueue.
 */
public class OrderEditor extends Panel {
    /**
     * The id of the panel.
     */
    private static final String PANEL_ID = "Orders###order_editor_panel";

    private final OrderQueue orderQueue;

    /**
     * Construct an OrderEditor given an OrderQueue.
     * @param orderQueue The OrderQueue to edit.
     */
    public OrderEditor(OrderQueue orderQueue) {
        super(PANEL_ID);
        setCloseable(false);

        this.orderQueue = orderQueue;
    }
}
