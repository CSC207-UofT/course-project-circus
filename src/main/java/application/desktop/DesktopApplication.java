package application.desktop;

import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.RootAppComponent;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import serialization.FileObjectLoader;
import serialization.FileObjectSaver;
import serialization.JsonFileObjectLoader;
import serialization.JsonFileObjectSaver;
import warehouse.Warehouse;
import warehouse.WarehouseController;
import warehouse.WarehouseState;
import warehouse.inventory.PartCatalogue;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Driver class for the desktop application.
 */
public class DesktopApplication extends Application {
    /**
     * The filename of the default font relative to the resources root.
     */
    private final static String DEFAULT_FONT_NAME = "/Roboto-Medium.ttf";
    /**
     * The size of the default font.
     */
    private final static float DEFAULT_FONT_SIZE = 16.0f;

    /**
     * Current warehouse state.
     */
    private WarehouseState state;
    private WarehouseController warehouseController;

    private RootAppComponent root;

    /**
     * The loader for the WarehouseState.
     */
    private final FileObjectLoader<WarehouseState> warehouseStateLoader;
    /**
     * The saver for the WarehouseState.
     */
    private final FileObjectSaver<WarehouseState> warehouseStateSaver;

    /**
     * Construct a DesktopApplication.
     */
    public DesktopApplication(WarehouseState state,
                              FileObjectLoader<WarehouseState> warehouseStateLoader,
                              FileObjectSaver<WarehouseState> warehouseStateSaver) {
        setState(state);
        this.warehouseStateLoader = warehouseStateLoader;
        this.warehouseStateSaver = warehouseStateSaver;
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
        root.draw(this);
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
     * Get the warehouse state.
     */
    public WarehouseState getState() {
        return state;
    }

    /**
     * Set the warehouse state.
     */
    public void setState(WarehouseState state) {
        this.state = state;
        warehouseController = new WarehouseController(state);
        root = new RootAppComponent(this);
    }

    public FileObjectLoader<WarehouseState> getWarehouseStateLoader() {
        return warehouseStateLoader;
    }

    public FileObjectSaver<WarehouseState> getWarehouseStateSaver() {
        return warehouseStateSaver;
    }

    /**
     * Get the WarehouseController.
     */
    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    /**
     * Set the WarehouseController.
     */
    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    /**
     * Entrypoint for the desktop application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Use JSON for file serialization
        JsonFileObjectLoader<WarehouseState> stateLoader = new JsonFileObjectLoader<>(WarehouseState.class);
        JsonFileObjectSaver<WarehouseState> stateSaver = new JsonFileObjectSaver<>();
        // Create and launch DesktopApplication
        DesktopApplication application = new DesktopApplication(makeEmptyWarehouseState(), stateLoader, stateSaver);
        launch(application);
        System.exit(0);
    }

    /**
     * Create an empty WarehouseState with an empty PartCatalogue and empty 12x12 Warehouse
     */
    public static WarehouseState makeEmptyWarehouseState() {
        return new WarehouseState(new Warehouse(12, 12), new PartCatalogue());
    }
}
