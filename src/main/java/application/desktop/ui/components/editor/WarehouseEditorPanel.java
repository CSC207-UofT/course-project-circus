package application.desktop.ui.components.editor;

import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.*;
import application.desktop.ui.components.common.buttons.Button;
import application.desktop.ui.events.ComponentEventData;
import warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditorPanel extends Panel {
    private final WarehouseCanvas canvas;
    private Warehouse warehouse;

    private final Button insertTileButton;
    private final List<Button> tileInputButtons;

    /**
     * Construct a new WarehouseEditorPanel given a Warehouse.
     * @param warehouse The Warehouse to edit.
     */
    public WarehouseEditorPanel(Warehouse warehouse) {
        super("Warehouse Layout");
        setShowMenuBar(true);
        setCloseable(false);

        this.warehouse = warehouse;
        canvas = new WarehouseCanvas(warehouse);

        // Create UI components
        Button selectTileButton = new Button("", FontAwesomeIcon.MousePointer, "Select tool", 0, 0,
                Colour.TRANSPARENT, true);
        selectTileButton.getOnClickedEvent().addListener(this::onEraseTileButtonClicked);

        Button moveTileButton = new Button("", FontAwesomeIcon.ArrowsAlt, "Move tool", 0, 0,
                Colour.TRANSPARENT, true);
        moveTileButton.getOnClickedEvent().addListener(this::onEraseTileButtonClicked);

        insertTileButton = new Button("", FontAwesomeIcon.PencilAlt, "Insert tool", 0, 0,
                Colour.TRANSPARENT, true);
        insertTileButton.getOnClickedEvent().addListener(this::onInsertTileButtonClicked);

        Button eraseTileButton = new Button("", FontAwesomeIcon.Eraser, "Erase tool", 0, 0,
                Colour.TRANSPARENT, true);
        eraseTileButton.getOnClickedEvent().addListener(this::onEraseTileButtonClicked);

        Button inputRackButton = new Button("Rack", FontAwesomeIcon.Table, "Rack", 0, 0,
                Colour.TRANSPARENT, false);
        inputRackButton.getOnClickedEvent().addListener(this::onInputRackButtonClicked);

        Button inputReceiveDepotButton = new Button("Receive", FontAwesomeIcon.SignInAlt, "Receive Depot", 0, 0,
                Colour.TRANSPARENT, false);
        inputReceiveDepotButton.getOnClickedEvent().addListener(this::onInputReceiveDepotButtonClicked);

        Button inputShipDepotButton = new Button("Shipping", FontAwesomeIcon.SignOutAlt, "Ship Depot", 0, 0,
                Colour.TRANSPARENT, false);
        inputShipDepotButton.getOnClickedEvent().addListener(this::onInputShipDepotButtonClicked);

        tileInputButtons = new ArrayList<>();
        tileInputButtons.add(inputRackButton);
        tileInputButtons.add(inputReceiveDepotButton);
        tileInputButtons.add(inputShipDepotButton);

        // Initialise UI
        addChild(new MenuBar(
                selectTileButton,
                moveTileButton,
                insertTileButton,
                eraseTileButton,
                new Text("|", false),
                new Text("Tile Palette:\t", false),
                inputRackButton,
                inputReceiveDepotButton,
                inputShipDepotButton
        ));
        addChild(canvas);
    }

    /**
     * Called when the "insert tile" button is clicked.
     */
    private void onInsertTileButtonClicked(ComponentEventData data) {
        for (Button button : tileInputButtons) {
            button.setEnabled(true);
        }
    }

    /**
     * Called when the "erase tile" button is clicked.
     */
    private void onEraseTileButtonClicked(ComponentEventData data) {
        for (Button button : tileInputButtons) {
            button.setEnabled(false);
        }
        canvas.setInputMode(WarehouseCanvasInputMode.NONE);
    }

    /**
     * Called when the "input rack tile" button is clicked.
     */
    private void onInputRackButtonClicked(ComponentEventData data) {
        // TODO: Check to make sure that the insert tile button is actually toggled!
        canvas.setInputMode(WarehouseCanvasInputMode.RACK);
    }

    /**
     * Called when the "input receive depot tile" button is clicked.
     */
    private void onInputReceiveDepotButtonClicked(ComponentEventData data) {
        // TODO: Check to make sure that the insert tile button is actually toggled!
        canvas.setInputMode(WarehouseCanvasInputMode.RECEIVE_DEPOT);
    }

    /**
     * Called when the "input ship depot tile button" is clicked.
     */
    private void onInputShipDepotButtonClicked(ComponentEventData data) {
        // TODO: Check to make sure that the insert tile button is actually toggled!
        canvas.setInputMode(WarehouseCanvasInputMode.SHIP_DEPOT);
    }
}
