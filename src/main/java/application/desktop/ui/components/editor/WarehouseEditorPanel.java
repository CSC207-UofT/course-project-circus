package application.desktop.ui.components.editor;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.*;
import application.desktop.ui.components.common.buttons.Button;
import application.desktop.ui.events.ComponentEventData;
import warehouse.Warehouse;
import warehouse.tiles.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Editor window for the Warehouse.
 */
public class WarehouseEditorPanel extends Panel {
    private final WarehouseCanvas canvas;

    private Button insertTileToolButton;
    private final List<Button> toolButtons;
    private final Map<TileType, Button> insertTileTypeButtons;

    /**
     * Construct a new WarehouseEditorPanel given a Warehouse.
     * @param warehouse The Warehouse to edit.
     */
    public WarehouseEditorPanel(Warehouse warehouse) {
        super("Warehouse");
        setShowMenuBar(true);
        setCloseable(false);

        canvas = new WarehouseCanvas(warehouse);
        toolButtons = new ArrayList<>();
        insertTileTypeButtons = new HashMap<>();

        initComponents();
    }

    private void initComponents() {
        // Create tool buttons
        Button selectTileButton = new Button("", FontAwesomeIcon.MousePointer, "Select tool");
        selectTileButton.setToggleable(true);
        selectTileButton.setToggled(true);
        selectTileButton.setNormalColour(Colour.TRANSPARENT);
        selectTileButton.getOnToggledOnEvent().addListener(this::onSelectTileButtonToggledOn);
        selectTileButton.getOnToggledOffEvent().addListener(this::onSelectTileButtonToggledOff);

        Button moveTileButton = new Button("", FontAwesomeIcon.ArrowsAlt, "Move tool");
        moveTileButton.setToggleable(true);
        moveTileButton.setNormalColour(Colour.TRANSPARENT);
        moveTileButton.getOnToggledOnEvent().addListener(this::onMoveTileButtonToggledOn);
        moveTileButton.getOnToggledOffEvent().addListener(this::onMoveTileButtonToggledOff);

        insertTileToolButton = new Button("", FontAwesomeIcon.PencilAlt, "Insert tool");
        insertTileToolButton.setToggleable(true);
        insertTileToolButton.setNormalColour(Colour.TRANSPARENT);
        insertTileToolButton.getOnToggledOnEvent().addListener(this::onInsertTileButtonToggledOn);
        insertTileToolButton.getOnToggledOffEvent().addListener(this::onInsertTileButtonToggledOff);

        Button eraseTileButton = new Button("", FontAwesomeIcon.Eraser, "Erase tool");
        eraseTileButton.setToggleable(true);
        eraseTileButton.setNormalColour(Colour.TRANSPARENT);
        eraseTileButton.getOnToggledOnEvent().addListener(this::onEraseTileButtonToggledOn);
        eraseTileButton.getOnToggledOffEvent().addListener(this::onEraseTileButtonToggledOff);

        toolButtons.clear();
        toolButtons.add(selectTileButton);
        toolButtons.add(moveTileButton);
        toolButtons.add(insertTileToolButton);
        toolButtons.add(eraseTileButton);

        // Create tile input buttons
        Button inputRackButton = new Button("Rack", FontAwesomeIcon.Table, "Rack");
        inputRackButton.setToggleable(true);
        inputRackButton.setToggled(true);
        inputRackButton.setNormalColour(Colour.TRANSPARENT);
        inputRackButton.setEnabled(false);
        inputRackButton.getOnClickedEvent().addListener(this::onInputRackButtonClicked);

        Button inputReceiveDepotButton = new Button("Receive", FontAwesomeIcon.SignInAlt, "Receive Depot");
        inputReceiveDepotButton.setToggleable(true);
        inputReceiveDepotButton.setNormalColour(Colour.TRANSPARENT);
        inputReceiveDepotButton.setEnabled(false);
        inputReceiveDepotButton.getOnClickedEvent().addListener(this::onInputReceiveDepotButtonClicked);

        Button inputShipDepotButton = new Button("Shipping", FontAwesomeIcon.SignOutAlt, "Ship Depot");
        inputShipDepotButton.setToggleable(true);
        inputShipDepotButton.setNormalColour(Colour.TRANSPARENT);
        inputShipDepotButton.setEnabled(false);
        inputShipDepotButton.getOnClickedEvent().addListener(this::onInputShipDepotButtonClicked);

        insertTileTypeButtons.clear();
        insertTileTypeButtons.put(TileType.RACK, inputRackButton);
        insertTileTypeButtons.put(TileType.RECEIVE_DEPOT, inputReceiveDepotButton);
        insertTileTypeButtons.put(TileType.SHIP_DEPOT, inputShipDepotButton);

        // Toolbar
        addChild(new MenuBar(
                selectTileButton,
                moveTileButton,
                insertTileToolButton,
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

    @Override
    protected void drawContent(DesktopApplication application) {
        for (TileType tileType : insertTileTypeButtons.keySet()) {
            Button button = insertTileTypeButtons.get(tileType);
            button.setToggled(tileType == canvas.getTileTypeToInsert());
            button.setEnabled(insertTileToolButton.isToggled());
        }
        super.drawContent(application);
    }

    /**
     * Called when the "select tile" button is toggled ON.
     */
    private void onSelectTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source);
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "select tile" button is toggled OFF.
     */
    private void onSelectTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "move tile" button is toggled ON.
     */
    private void onMoveTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source);
        canvas.setInputMode(WarehouseCanvasInputMode.MOVE_TILE);
    }

    /**
     * Called when the "move tile" button is toggled OFF.
     */
    private void onMoveTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "insert tile" button is toggled ON.
     */
    private void onInsertTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source);
        canvas.setInputMode(WarehouseCanvasInputMode.INSERT_TILE);
        if (!insertTileTypeButtons.containsKey(canvas.getTileTypeToInsert())) {
            canvas.setTileTypeToInsert(TileType.RACK);
        }
    }

    /**
     * Called when the "insert tile" button is toggled OFF.
     */
    private void onInsertTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "erase tile" button is toggled ON.
     */
    private void onEraseTileButtonToggledOn(ComponentEventData data) {
        Button source = (Button) data.getSource();
        onToolButtonToggledOn(source);
        canvas.setInputMode(WarehouseCanvasInputMode.INSERT_TILE);
        canvas.setTileTypeToInsert(TileType.EMPTY);
    }

    /**
     * Called when the "erase tile" button is toggled Off.
     */
    private void onEraseTileButtonToggledOff(ComponentEventData data) {
        canvas.setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    private void onToolButtonToggledOn(Button source) {
        for (Button toolButton : toolButtons) {
            if (toolButton.equals(source)) continue;
            toolButton.setToggled(false);
        }
    }

    /**
     * Called when the "input rack tile" button is clicked.
     */
    private void onInputRackButtonClicked(ComponentEventData data) {
        canvas.setTileTypeToInsert(TileType.RACK);
    }

    /**
     * Called when the "input receive depot tile" button is clicked.
     */
    private void onInputReceiveDepotButtonClicked(ComponentEventData data) {
        canvas.setTileTypeToInsert(TileType.RECEIVE_DEPOT);
    }

    /**
     * Called when the "input ship depot tile button" is clicked.
     */
    private void onInputShipDepotButtonClicked(ComponentEventData data) {
        canvas.setTileTypeToInsert(TileType.SHIP_DEPOT);
    }
}
