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
    private final Warehouse warehouse;

    private final List<Button> toolButtons;
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

        toolButtons = new ArrayList<>();
        tileInputButtons = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        // Create tool buttons
        Button selectTileButton = new Button("", FontAwesomeIcon.MousePointer, "Select tool");
        selectTileButton.setToggleable(true);
        selectTileButton.setNormalColour(Colour.TRANSPARENT);
        selectTileButton.getOnToggledOnEvent().addListener(this::onSelectTileButtonToggledOn);
        selectTileButton.getOnToggledOffEvent().addListener(this::onSelectTileButtonToggledOff);

        Button moveTileButton = new Button("", FontAwesomeIcon.ArrowsAlt, "Move tool");
        moveTileButton.setToggleable(true);
        moveTileButton.setNormalColour(Colour.TRANSPARENT);
        moveTileButton.getOnToggledOnEvent().addListener(this::onMoveTileButtonToggledOn);
        moveTileButton.getOnToggledOffEvent().addListener(this::onMoveTileButtonToggledOff);

        Button insertTileButton = new Button("", FontAwesomeIcon.PencilAlt, "Insert tool");
        insertTileButton.setToggleable(true);
        insertTileButton.setNormalColour(Colour.TRANSPARENT);
        insertTileButton.getOnToggledOnEvent().addListener(this::onInsertTileButtonToggledOn);
        insertTileButton.getOnToggledOffEvent().addListener(this::onInsertTileButtonToggledOff);

        Button eraseTileButton = new Button("", FontAwesomeIcon.Eraser, "Erase tool");
        eraseTileButton.setToggleable(true);
        eraseTileButton.setNormalColour(Colour.TRANSPARENT);
        eraseTileButton.getOnToggledOnEvent().addListener(this::onEraseTileButtonToggledOn);
        eraseTileButton.getOnToggledOffEvent().addListener(this::onEraseTileButtonToggledOff);

        toolButtons.clear();
        toolButtons.add(selectTileButton);
        toolButtons.add(moveTileButton);
        toolButtons.add(insertTileButton);
        toolButtons.add(eraseTileButton);

        // Create tile input buttons
        Button inputRackButton = new Button("Rack", FontAwesomeIcon.Table, "Rack");
        inputRackButton.setNormalColour(Colour.TRANSPARENT);
        inputRackButton.setEnabled(false);
        inputRackButton.getOnClickedEvent().addListener(this::onInputRackButtonClicked);

        Button inputReceiveDepotButton = new Button("Receive", FontAwesomeIcon.SignInAlt, "Receive Depot");
        inputReceiveDepotButton.setNormalColour(Colour.TRANSPARENT);
        inputReceiveDepotButton.setEnabled(false);
        inputReceiveDepotButton.getOnClickedEvent().addListener(this::onInputReceiveDepotButtonClicked);

        Button inputShipDepotButton = new Button("Shipping", FontAwesomeIcon.SignOutAlt, "Ship Depot");
        inputShipDepotButton.setNormalColour(Colour.TRANSPARENT);
        inputShipDepotButton.setEnabled(false);
        inputShipDepotButton.getOnClickedEvent().addListener(this::onInputShipDepotButtonClicked);

        tileInputButtons.clear();
        tileInputButtons.add(inputRackButton);
        tileInputButtons.add(inputReceiveDepotButton);
        tileInputButtons.add(inputShipDepotButton);

        // Toolbar
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
        // Canvas
        addChild(canvas);
    }

    /**
     * Called when the "select tile" button is toggled ON.
     */
    private void onSelectTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source, false);
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "select tile" button is toggled OFF.
     */
    private void onSelectTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.NONE);
    }

    /**
     * Called when the "move tile" button is toggled ON.
     */
    private void onMoveTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source, false);
        canvas.setInputMode(WarehouseCanvasInputMode.MOVE_TILE);
    }

    /**
     * Called when the "move tile" button is toggled OFF.
     */
    private void onMoveTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.NONE);
    }

    /**
     * Called when the "insert tile" button is toggled ON.
     */
    private void onInsertTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source, true);
    }

    /**
     * Called when the "insert tile" button is toggled OFF.
     */
    private void onInsertTileButtonToggledOff(ComponentEventData data) {
        setTileButtonsEnabled(false);
        canvas.setInputMode(WarehouseCanvasInputMode.NONE);
    }

    /**
     * Called when the "erase tile" button is toggled ON.
     */
    private void onEraseTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source, false);
        canvas.setInputMode(WarehouseCanvasInputMode.PLACE_EMPTY);
    }

    /**
     * Called when the "erase tile" button is toggled Off.
     */
    private void onEraseTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.NONE);
    }

    private void onToolButtonToggledOn(Button source, boolean tileButtonsEnabled) {
        for (Button toolButton : toolButtons) {
            if (toolButton.equals(source)) continue;
            toolButton.setToggled(false);
        }
        setTileButtonsEnabled(tileButtonsEnabled);
    }


    private void setTileButtonsEnabled(boolean enabled) {
        for (Button button : tileInputButtons) {
            button.setEnabled(enabled);
        }
    }

    /**
     * Called when the "input rack tile" button is clicked.
     */
    private void onInputRackButtonClicked(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.PLACE_RACK);
    }

    /**
     * Called when the "input receive depot tile" button is clicked.
     */
    private void onInputReceiveDepotButtonClicked(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.PLACE_RECEIVE_DEPOT);
    }

    /**
     * Called when the "input ship depot tile button" is clicked.
     */
    private void onInputShipDepotButtonClicked(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.PLACE_SHIP_DEPOT);
    }
}
