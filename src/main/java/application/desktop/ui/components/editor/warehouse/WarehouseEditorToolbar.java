package application.desktop.ui.components.editor.warehouse;

import application.desktop.DesktopApplication;
import application.desktop.ui.Colour;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.MenuBar;
import application.desktop.ui.components.common.Separator;
import application.desktop.ui.components.common.Text;
import application.desktop.ui.components.common.Button;
import application.desktop.ui.events.ComponentEventData;
import warehouse.WarehouseLayout;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.tiles.Tile;
import warehouse.tiles.factory.TileType;

import java.util.HashMap;
import java.util.Map;

/**
 * Toolbar for the WarehouseEditor.
 */
public class WarehouseEditorToolbar<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate>
        extends MenuBar {
    private final WarehouseEditor<T, U> warehouseEditor;

    private final Map<WarehouseCanvasInputMode, Button> toolButtons;
    private final Map<TileType, Button> insertTileTypeButtons;
    private final Text tilePaletteLabel;

    /**
     * Construct a WarehouseEditorToolbar.
     * @param warehouseEditor The root WarehouseEditor panel.
     */
    public WarehouseEditorToolbar(WarehouseEditor<T, U> warehouseEditor) {
        this.warehouseEditor = warehouseEditor;
        toolButtons = new HashMap<>();
        insertTileTypeButtons = new HashMap<>();

        // Create tool buttons
        Button selectTileToolButton  = new Button("", FontAwesomeIcon.MousePointer, "Select tool");
        selectTileToolButton.setToggleable(true);
        selectTileToolButton.setToggled(true);
        selectTileToolButton.setNormalColour(Colour.TRANSPARENT);
        selectTileToolButton.getOnClickedEvent().addListener(this::onSelectTileButtonClicked);

        Button moveTileButton = new Button("", FontAwesomeIcon.ArrowsAlt, "Move tool");
        moveTileButton.setToggleable(true);
        moveTileButton.setNormalColour(Colour.TRANSPARENT);
        moveTileButton.getOnToggledOnEvent().addListener(this::onMoveTileButtonClicked);

        Button insertTileToolButton = new Button("", FontAwesomeIcon.PencilAlt, "Insert tool");
        insertTileToolButton.setToggleable(true);
        insertTileToolButton.setNormalColour(Colour.TRANSPARENT);
        insertTileToolButton.getOnClickedEvent().addListener(this::onInsertTileButtonClicked);

        Button eraseTileButton = new Button("", FontAwesomeIcon.Eraser, "Erase tool");
        eraseTileButton.setToggleable(true);
        eraseTileButton.setNormalColour(Colour.TRANSPARENT);
        eraseTileButton.getOnClickedEvent().addListener(this::onEraseTileButtonClicked);

        Button placeRobotButton = new Button("", FontAwesomeIcon.Robot, "Place robot tool");
        placeRobotButton.setToggleable(true);
        placeRobotButton.setNormalColour(Colour.TRANSPARENT);
        placeRobotButton.getOnClickedEvent().addListener(this::onPlaceRobotButtonClicked);

        toolButtons.put(WarehouseCanvasInputMode.SELECT_TILE, selectTileToolButton);
        toolButtons.put(WarehouseCanvasInputMode.MOVE_TILE, moveTileButton);
        toolButtons.put(WarehouseCanvasInputMode.INSERT_TILE, insertTileToolButton);
        toolButtons.put(WarehouseCanvasInputMode.ERASE_OBJECT, eraseTileButton);
        toolButtons.put(WarehouseCanvasInputMode.PLACE_ROBOT, placeRobotButton);
        addChildren(
                selectTileToolButton,
                moveTileButton,
                insertTileToolButton,
                eraseTileButton,
                placeRobotButton
        );

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

        // add items to menu bar
        tilePaletteLabel = new Text(String.format("  %s\tTile Palette\t", FontAwesomeIcon.Palette.getIconCode()), null, false);
        addChildren(
                new Separator(),
                tilePaletteLabel,
                inputRackButton,
                inputReceiveDepotButton,
                inputShipDepotButton
        );
    }

    @Override
    protected void handleEvents() {
        super.handleEvents();
        for (WarehouseCanvasInputMode inputMode : toolButtons.keySet()) {
            Button button = toolButtons.get(inputMode);
            button.setToggled(inputMode.equals(warehouseEditor.getCanvas().getInputMode()));
        }

        Button insertTileToolButton = toolButtons.get(WarehouseCanvasInputMode.INSERT_TILE);
        for (TileType tileType : insertTileTypeButtons.keySet()) {
            Button button = insertTileTypeButtons.get(tileType);
            button.setToggled(tileType == warehouseEditor.getCanvas().getTileTypeToInsert());
            button.setEnabled(insertTileToolButton.isToggled());
            tilePaletteLabel.setEnabled(insertTileToolButton.isToggled());
        }

        // Disable the move tool if the selected tile is empty
        Button moveTileToolButton = toolButtons.get(WarehouseCanvasInputMode.MOVE_TILE);
        Tile selectedTile = warehouseEditor.getCanvas().getSelectedTile();
        WarehouseLayout<U> warehouseLayout = warehouseEditor.getWarehouseState().getLayout();
        boolean isMoveToolEnabled = selectedTile != null && !warehouseLayout.isEmpty(selectedTile);
        if (!isMoveToolEnabled && moveTileToolButton.isToggled()) {
            onSelectTileButtonClicked(null);
        }
        moveTileToolButton.setEnabled(isMoveToolEnabled);
    }

    /**
     * Called when the "select tile" button is clicked.
     */
    private void onSelectTileButtonClicked(ComponentEventData data) {
        warehouseEditor.getCanvas().setInputMode(WarehouseCanvasInputMode.SELECT_TILE);
    }

    /**
     * Called when the "move tile" button is clicked.
     */
    private void onMoveTileButtonClicked(ComponentEventData data) {
        warehouseEditor.getCanvas().setInputMode(WarehouseCanvasInputMode.MOVE_TILE);
    }

    /**
     * Called when the "insert tile" button is clicked.
     */
    private void onInsertTileButtonClicked(ComponentEventData data) {
        WarehouseCanvas<T, U> canvas = warehouseEditor.getCanvas();
        canvas.setInputMode(WarehouseCanvasInputMode.INSERT_TILE);
        if (!insertTileTypeButtons.containsKey(canvas.getTileTypeToInsert())) {
            canvas.setTileTypeToInsert(TileType.RACK);
        }
    }

    /**
     * Called when the "erase tile" button is clicked.
     */
    private void onEraseTileButtonClicked(ComponentEventData data) {
        WarehouseCanvas<T, U> canvas = warehouseEditor.getCanvas();
        canvas.setInputMode(WarehouseCanvasInputMode.ERASE_OBJECT);
    }

    /**
     * Called when the "place robot" button is clicked.
     */
    private void onPlaceRobotButtonClicked(ComponentEventData data) {
        WarehouseCanvas<T, U> canvas = warehouseEditor.getCanvas();
        canvas.setInputMode(WarehouseCanvasInputMode.PLACE_ROBOT);
    }

    /**
     * Called when the "input rack tile" button is clicked.
     */
    private void onInputRackButtonClicked(ComponentEventData data) {
        warehouseEditor.getCanvas().setTileTypeToInsert(TileType.RACK);
    }

    /**
     * Called when the "input receive depot tile" button is clicked.
     */
    private void onInputReceiveDepotButtonClicked(ComponentEventData data) {
        warehouseEditor.getCanvas().setTileTypeToInsert(TileType.RECEIVE_DEPOT);
    }

    /**
     * Called when the "input ship depot tile button" is clicked.
     */
    private void onInputShipDepotButtonClicked(ComponentEventData data) {
        warehouseEditor.getCanvas().setTileTypeToInsert(TileType.SHIP_DEPOT);
    }
}
