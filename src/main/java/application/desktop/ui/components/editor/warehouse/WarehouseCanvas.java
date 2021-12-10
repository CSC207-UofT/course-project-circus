package application.desktop.ui.components.editor.warehouse;

import application.desktop.ui.Colour;
import application.desktop.ui.components.editor.warehouse.renderers.WarehouseCanvasRenderer;
import application.desktop.ui.utils.DrawingUtils;
import application.desktop.ui.utils.RectBorderType;
import application.desktop.ui.components.common.Component;
import imgui.*;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import warehouse.*;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.logistics.optimization.DistanceTileScorer;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.routefinding.algorithms.AStarRoutefinder;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.Tile;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.tiles.factory.TileFactory;
import warehouse.tiles.factory.TileType;

/**
 * A canvas that visualizes the WarehouseLayout.
 */
public class WarehouseCanvas<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends Component {
    /**
     * The name of the "erase tile popup dialog."
     */
    private static final String ERASE_TILE_POPUP_DIALOG_NAME = "Delete?##erase_tile_dialog_popup";

    private final WarehouseState<T, U> warehouseState;
    private final WarehouseCanvasColourScheme colourScheme;

    private final float minSizeX;
    private final float minSizeY;
    private final WarehouseCanvasRenderer<T, U> renderer;
    private final WarehouseCanvasTransform transform;

    private int frameCounter;
    private boolean isMovingTile;
    private ImVec2 moveTileDragDelta;
    private final ImBoolean rememberEraseTilePopupChoice;

    private TileType tileTypeToInsert;
    private final TileFactory tileFactory;

    /**
     * Current input mode of this WarehouseCanvas.
     */
    private WarehouseCanvasInputMode inputMode;
    /**
     * The currently selected tile, or null if no tile is selected.
     */
    private Tile selectedTile;

    /**
     * Construct a new WarehouseCanvas with a default colour scheme.
     *
     * @param warehouseState The WarehouseLayout to visualise.
     */
    public WarehouseCanvas(WarehouseState<T, U> warehouseState, WarehouseCanvasRenderer<T, U> renderer) {
        this(warehouseState, renderer, WarehouseCanvasColourScheme.DEFAULT,100.0f, 100.0f);
    }

    /**
     * Construct a new WarehouseCanvas with a custom colour scheme.
     *
     * @param warehouseState    The WarehouseState to visualise.
     * @param colourScheme The colour scheme of this WarehouseCanvas.
     * @param minSizeX     The minimum horizontal size of the canvas, in pixels.
     * @param minSizeY     The minimum vertical size of the canvas, in pixels.
     */
    public WarehouseCanvas(WarehouseState<T, U> warehouseState,
                           WarehouseCanvasRenderer<T, U> renderer,
                           WarehouseCanvasColourScheme colourScheme,
                           float minSizeX, float minSizeY) {
        this.warehouseState = warehouseState;
        this.colourScheme = colourScheme;
        this.minSizeX = minSizeX;
        this.minSizeY = minSizeY;
        this.renderer = renderer;

        tileTypeToInsert = TileType.RACK;
        tileFactory = new TileFactory();

        transform = new WarehouseCanvasTransform(
                new ImVec2(minSizeX, minSizeY), // content size
                new ImVec2(), // top left
                new ImVec2() // origin
        );
        inputMode = WarehouseCanvasInputMode.SELECT_TILE;

        frameCounter = 0;
        rememberEraseTilePopupChoice = new ImBoolean(false);
    }

    @Override
    public void drawContent() {
        updateTransform();
        handleInteraction();

        // NOTE: The frame counter is a hacky way of centering the contents on load. It works by waiting for the second
        // frame, so that all the contents have been rendered and updated at least once.
        if (frameCounter <= 1) {
            renderer.panToCentre(warehouseState, transform);
            frameCounter += 1;
        }

        ImDrawList drawList = ImGui.getWindowDrawList();
        drawBackground(drawList);
        renderer.drawWarehouse(drawList, warehouseState, transform, colourScheme);
        renderer.drawTileGizmo(drawList, warehouseState, transform,
                selectedTile, moveTileDragDelta,
                inputMode.equals(WarehouseCanvasInputMode.MOVE_TILE),
                getCurrentTileGizmoColour());

        drawEraseTilePopupDialog();
    }

    /**
     * Draw the "erase tile popup dialog." Confirms if the user wants to delete the tile.
     */
    private void drawEraseTilePopupDialog() {
        if (selectedTile == null || !ImGui.beginPopup(ERASE_TILE_POPUP_DIALOG_NAME,
                ImGuiWindowFlags.AlwaysAutoResize)) {
            return;
        }

        ImGui.text("All those beautiful tiles will be deleted.\nThis operation cannot be undone!\n\n");
        ImGui.separator();

        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, 0);
        ImGui.checkbox("Don't ask me next time", rememberEraseTilePopupChoice);
        ImGui.popStyleVar();

        if (ImGui.button("OK", 120 ,0)) {
            //warehouseState.getLayout().setTile(new EmptyTile(selectedTile.getX(), selectedTile.getY()));
            selectedTile = null;
            ImGui.closeCurrentPopup();
        }

        ImGui.setItemDefaultFocus();
        ImGui.sameLine();

        if (ImGui.button("Cancel", 120, 0)) {
            ImGui.closeCurrentPopup();
        }
        ImGui.endPopup();
    }

    /**
     * Update transform information such as size and position.
     */
    private void updateTransform() {
        ImVec2 contentAvailable = ImGui.getContentRegionAvail();
        transform.setContentSize(new ImVec2(Math.max(contentAvailable.x, minSizeX),
                Math.max(contentAvailable.y, minSizeY)));
        transform.setContentTopLeft(ImGui.getCursorScreenPos());
    }

    /**
     * Handle user interaction.
     */
    private void handleInteraction() {
        ImVec2 contentSize = transform.getContentSize();
        ImGui.invisibleButton("canvas", contentSize.x, contentSize.y, ImGuiButtonFlags.MouseButtonLeft |
                ImGuiButtonFlags.MouseButtonRight);
        renderer.handleInteraction();
        handleLeftClick();
        handleDragging();
    }

    /**
     * Handle left click interaction.
     */
    private void handleLeftClick() {
        boolean isHovered = ImGui.isItemHovered();
        if (!isHovered || !ImGui.isMouseClicked(ImGuiMouseButton.Left)) {
            return;
        }

        ImGui.setWindowFocus();
        // Update selected tile
        selectedTile = renderer.getTileAtMousePosition(warehouseState, transform);
        // Input modes
        if (inputMode == WarehouseCanvasInputMode.INSERT_TILE) {
            insertTileAtSelection();
        } else if (inputMode == WarehouseCanvasInputMode.ERASE_OBJECT) {
            eraseTileAtSelection();
        } else if (inputMode == WarehouseCanvasInputMode.PLACE_ROBOT) {
            placeRobotAtSelection();
        }
    }

    /**
     * Replaces the selected tile with a new tile.
     */
    private void insertTileAtSelection() {
        if (selectedTile != null) {
            Tile tileToInsert = tileFactory.createTile(tileTypeToInsert);
            RobotMapper<U> robotMapper = warehouseState.getRobotMapper();
            // Make sure the tile isn't null AND there are no robots on the tile
            if (tileToInsert != null && !robotMapper.isRobotAt(tileToInsert.getIndex())){
                warehouseState.getLayout().setTileAt(selectedTile.getIndex(), tileToInsert);
            }
        }
    }

    /**
     * Replaces the selected tile with an empty tile.
     */
    private void eraseTileAtSelection() {
        if (selectedTile != null) {
            WarehouseLayout<U> warehouseLayout = warehouseState.getLayout();
            RobotMapper<U> robotMapper = warehouseState.getRobotMapper();
            if (!warehouseLayout.isEmpty(selectedTile)) {
                if (rememberEraseTilePopupChoice.get()) {
                    warehouseLayout.setTileAt(selectedTile.getIndex(), new EmptyTile());
                } else {
                    ImGui.openPopup(ERASE_TILE_POPUP_DIALOG_NAME);
                }
            } else if (robotMapper.isRobotAt(selectedTile.getIndex())) {
                robotMapper.removeRobotsAt(selectedTile.getIndex());
            }
        }
    }

    /**
     * Places the Robot at the selected tile.
     */
    private void placeRobotAtSelection() {
        if (selectedTile != null) {
            DistanceTileScorer metric = new DistanceTileScorer(warehouseState.getCoordinateSystem());
            AStarRoutefinder<TileNode> routefinder = new AStarRoutefinder<>(metric, metric);
            warehouseState.getRobotMapper().addRobotAt(new Robot(routefinder),selectedTile.getIndex());
        }
    }

    /**
     * Handle dragging for moving tiles.
     */
    private void handleDragging() {
        ImGuiIO io = ImGui.getIO();
        boolean isHovered = ImGui.isItemHovered();

        // Move tool
        if (isHovered && ImGui.isMouseDragging(ImGuiMouseButton.Left, -1) &&
                inputMode == WarehouseCanvasInputMode.MOVE_TILE && selectedTile != null) {
            isMovingTile = true;
            if (moveTileDragDelta == null) {
                moveTileDragDelta = new ImVec2();
            }

            moveTileDragDelta.x += io.getMouseDeltaX();
            moveTileDragDelta.y += io.getMouseDeltaY();
        }

        if (isMovingTile && ImGui.isMouseReleased(ImGuiMouseButton.Left)) {
            isMovingTile = false;
            moveTileDragDelta = null;
            // TODO:
//            Pair<Integer, Integer> newTileCoords = screenToWarehousePoint(getRelativeMousePosition());
//            WarehouseLayout warehouseLayout = warehouseState.getLayout();
//            if (warehouseLayout.isTileCoordinateInRange(newTileCoords.getFirst(), newTileCoords.getSecond())) {
//                int oldX = selectedTile.getX();
//                int oldY = selectedTile.getY();
//            }
        }
    }

    /**
     * Draw the canvas background. Mutates the given ImDrawList.
     */
    private void drawBackground(ImDrawList drawList) {
        ImVec2 contentTopLeft = transform.getContentTopLeft();
        ImVec2 contentBottomRight = transform.getContentBottomRight();
        DrawingUtils.drawRect(drawList, contentTopLeft, contentBottomRight, colourScheme.getBackgroundColour(),
                colourScheme.getBorderColour(), 1, 0, RectBorderType.Middle);
    }

    /**
     * Get the WarehouseState.
     */
    public WarehouseState<T, U> getWarehouseState() {
        return warehouseState;
    }

    /**
     * Gets the current input mode.
     */
    public WarehouseCanvasInputMode getInputMode() {
        return inputMode;
    }

    /**
     * Sets the input mode of the canvas, e.g. which tool to use.
     */
    public void setInputMode(WarehouseCanvasInputMode inputMode) {
        this.inputMode = inputMode;
    }

    /**
     * Gets the tile type currently being inserted.
     */
    public TileType getTileTypeToInsert() {
        return tileTypeToInsert;
    }

    /**
     * Sets the type of Tile to insert when the user uses the "insert tile" tool.
     */
    public void setTileTypeToInsert(TileType tileTypeToInsert) {
        this.tileTypeToInsert = tileTypeToInsert;
    }

    /**
     * Get the currently selected Tile.
     * @return The currently selected Tile, or null if no Tile is selected.
     */
    public Tile getSelectedTile() {
        return selectedTile;
    }

    /**
     * Set the currently selected Tile.
     * @param selectedTile The new selected tile.
     */
    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    /**
     * Get the colour of the tile handle outline.
     */
    private Colour getCurrentTileGizmoColour() {
        if (inputMode == WarehouseCanvasInputMode.MOVE_TILE) {
            return colourScheme.getMoveOutlineColour();
        } else {
            return colourScheme.getSelectionOutlineColour();
        }
    }
}
