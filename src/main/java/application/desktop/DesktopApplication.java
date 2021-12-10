package application.desktop;

import application.desktop.adapters.PhysicalGridRobotAdapterFactory;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.RootAppComponent;
import application.desktop.ui.components.editor.warehouse.renderers.GridWarehouseCanvasRenderer;
import application.desktop.ui.components.editor.warehouse.renderers.WarehouseCanvasRenderer;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import serialization.FileObjectLoader;
import serialization.FileObjectSaver;
import warehouse.WarehouseLayout;
import warehouse.Warehouse;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;
import warehouse.logistics.optimization.DistanceTileScorer;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.routefinding.algorithms.AStarRoutefinder;
import warehouse.logistics.orders.OrderQueue;
import warehouse.robots.Robot;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;
import warehouse.geometry.WarehouseCoordinate;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Driver class for the desktop application.
 */
public class DesktopApplication<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends Application {
    /**
     * The filename of the default font relative to the resources root.
     */
    private final static String DEFAULT_FONT_NAME = "/Roboto-Medium.ttf";
    /**
     * The size of the default font.
     */
    private final static float DEFAULT_FONT_SIZE = 16.0f;

    private Warehouse<T, U> warehouse;
    private final WarehouseCanvasRenderer<T, U> warehouseCanvasRenderer;
    private RootAppComponent<T, U> root;

    /**
     * The loader for the WarehouseState.
     */
    private final FileObjectLoader<WarehouseState<T, U>> warehouseStateLoader;
    /**
     * The saver for the WarehouseState.
     */
    private final FileObjectSaver<WarehouseState<T, U>> warehouseStateSaver;

    /**
     * Construct a DesktopApplication.
     */
    public DesktopApplication(Warehouse<T, U> warehouse,
                              FileObjectLoader<WarehouseState<T, U>> warehouseStateLoader,
                              FileObjectSaver<WarehouseState<T, U>> warehouseStateSaver,
                              WarehouseCanvasRenderer<T, U> warehouseCanvasRenderer) {
        this.warehouseStateLoader = warehouseStateLoader;
        this.warehouseStateSaver = warehouseStateSaver;
        this.warehouseCanvasRenderer = warehouseCanvasRenderer;
        setWarehouse(warehouse);
    }

    @Override
    protected void initImGui(Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.setConfigViewportsNoTaskBarIcon(true);

        initFonts(io);
    }

    /**
     * Initialise font configuration.
     */
    private void initFonts(final ImGuiIO io) {
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(FontAwesomeIcon._IconRange);

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after atlas is built
        final ImFontConfig fontConfig = new ImFontConfig();
        final short[] glyphRanges = rangesBuilder.buildRanges();
        io.getFonts().addFontFromMemoryTTF(ResourcesUtils.loadFromResources(DEFAULT_FONT_NAME), DEFAULT_FONT_SIZE,
                fontConfig, glyphRanges);
        // Merge icon glyphs
        fontConfig.setMergeMode(true);
        // Load FontAwesome glyphs
        io.getFonts().addFontFromMemoryTTF(ResourcesUtils.loadFromResources("/fa-regular-400.ttf"),
                14, fontConfig, glyphRanges);
        io.getFonts().addFontFromMemoryTTF(ResourcesUtils.loadFromResources("/fa-solid-900.ttf"),
                14, fontConfig, glyphRanges);
        io.getFonts().build();
        fontConfig.destroy();
    }

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Circus");
    }

    /**
     * Draw the DesktopApplication.
     */
    @Override
    public void process() {
        warehouse.update();
        root.draw();
    }

    /**
     * Exit the application.
     */
    public void exit() {
        long window = glfwGetCurrentContext();
        glfwSetWindowShouldClose(window, true);
        System.exit(0);
    }

    /**
     * Get the warehouse.
     */
    public Warehouse<T, U> getWarehouse() {
        return warehouse;
    }

    /**
     * Set the warehouse.
     */
    public void setWarehouse(Warehouse<T, U> warehouse) {
        this.warehouse = warehouse;
        // Re-create ui
        root = new RootAppComponent<>(this);
    }

    public WarehouseCanvasRenderer<T, U> getWarehouseCanvasRenderer() {
        return warehouseCanvasRenderer;
    }

    public FileObjectLoader<WarehouseState<T, U>> getWarehouseStateLoader() {
        return warehouseStateLoader;
    }

    public FileObjectSaver<WarehouseState<T, U>> getWarehouseStateSaver() {
        return warehouseStateSaver;
    }

    /**
     * Entrypoint for the desktop application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Use JSON for file serialization
        //JsonFileObjectLoader<WarehouseState<?, ?>> stateLoader = new JsonFileObjectLoader<WarehouseState<?, ?>>(WarehouseState.class);
        //JsonFileObjectSaver<WarehouseState<T, U>> stateSaver = new JsonFileObjectSaver<>();

        // Use an example state for TESTING
        Warehouse<GridWarehouseCoordinateSystem, Point> exampleWarehouse = makeEmptyGridWarehouse();
        WarehouseState<GridWarehouseCoordinateSystem, Point> exampleState = exampleWarehouse.getState();
        exampleState.getLayout().setTileAt(new Point(0, 1), new Rack());
        exampleState.getLayout().setTileAt(new Point(1, 1), new Rack());
        exampleState.getLayout().setTileAt(new Point(2, 1), new Rack());
        exampleState.getLayout().setTileAt(new Point(0, 9), new ReceiveDepot());
        exampleState.getLayout().setTileAt(new Point(11, 9), new ShipDepot());
        // Add robots
        DistanceTileScorer metric = new DistanceTileScorer(exampleWarehouse.getState().getCoordinateSystem());
        AStarRoutefinder<TileNode> routefinder = new AStarRoutefinder<>(metric, metric);
        exampleState.getRobotMapper().addRobotAt(new Robot(routefinder), new Point(5, 5));
        exampleState.getRobotMapper().addRobotAt(new Robot(routefinder), new Point(7, 3));

        Part mangoPart = new Part("Mango", "A mango.");
        exampleState.getPartCatalogue().addPart(mangoPart);
        // Create and launch DesktopApplication
        // TODO: Fix serialization
        DesktopApplication<GridWarehouseCoordinateSystem, Point> application = new DesktopApplication<>(
                exampleWarehouse, null, null,
                new GridWarehouseCanvasRenderer());

        application.getWarehouse().receiveItem(new Item(mangoPart));
        application.getWarehouse().receiveItem(new Item(mangoPart));
        application.getWarehouse().receiveItem(new Item(mangoPart));
        application.getWarehouse().receiveItem(new Item(mangoPart));

        launch(application);
        System.exit(0);
    }

    /**
     * Create an empty Warehouse with a GridWarehouseCoordinateSystem, an empty PartCatalogue,
     * and empty WarehouseLayout with the given width and height.
     * @param width The width of the empty WarehouseLayout.
     * @param height The height of the empty WarehouseLayout.
     */
    public static Warehouse<GridWarehouseCoordinateSystem, Point> makeEmptyGridWarehouse(int width, int height) {
        GridWarehouseCoordinateSystem coordinateSystem = new GridWarehouseCoordinateSystem(width, height);
        return new Warehouse<>(new WarehouseState<>(
                new PartCatalogue(),
                coordinateSystem,
                new WarehouseLayout<>(coordinateSystem),
                new RobotMapper<>(coordinateSystem),
                new PhysicalGridRobotAdapterFactory(),
                new OrderQueue()
        ));
    }

    /**
     * Create an empty Warehouse with a GridWarehouseCoordinateSystem, an empty PartCatalogue,
     * and an empty 12x12 layout.
     */
    public static Warehouse<GridWarehouseCoordinateSystem, Point> makeEmptyGridWarehouse() {
        return makeEmptyGridWarehouse(12, 12);
    }
}