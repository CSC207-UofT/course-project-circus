package application.desktop;

import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.Toolbar;
import application.desktop.ui.components.common.Panel;
import application.desktop.ui.components.editor.WarehouseEditor;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import imgui.internal.flag.ImGuiDockNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import org.lwjgl.BufferUtils;
import utils.Pair;
import warehouse.Warehouse;
import warehouse.storage.StorageUnit;
import warehouse.storage.containers.InMemoryStorageUnitContainer;
import warehouse.storage.strategies.MultiTypeStorageUnitStrategy;
import warehouse.storage.strategies.SingleTypeStorageStrategy;
import warehouse.tiles.Rack;
import warehouse.tiles.ReceiveDepot;
import warehouse.tiles.ShipDepot;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

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

    private boolean hasInitialisedDockspaceLayout;
    private final Toolbar toolbar;
    private final WarehouseEditor warehouseEditor;
    private final Panel sidebar;

    /**
     * Construct a DesktopApplication.
     */
    public DesktopApplication() {
        // TODO: Dependency injection
        toolbar = new Toolbar();

        // Create dummy warehouse
        Warehouse warehouse = new Warehouse(12, 12);
        // TODO: Clean API
        warehouse.setTile(new Rack(1, 1, new StorageUnit(10, new SingleTypeStorageStrategy(),
                new InMemoryStorageUnitContainer())));
        warehouse.setTile(new Rack(1, 1, new StorageUnit(10, new SingleTypeStorageStrategy(),
                new InMemoryStorageUnitContainer())));
        warehouse.setTile(new Rack(2, 1, new StorageUnit(10, new SingleTypeStorageStrategy(),
                new InMemoryStorageUnitContainer())));
        warehouse.setTile(new Rack(3, 1, new StorageUnit(10, new SingleTypeStorageStrategy(),
                new InMemoryStorageUnitContainer())));
        warehouse.setTile(new ReceiveDepot(0, 5, new StorageUnit(-1, new MultiTypeStorageUnitStrategy(),
                new InMemoryStorageUnitContainer())));
        warehouse.setTile(new ShipDepot(11, 5, new StorageUnit(-1, new MultiTypeStorageUnitStrategy(),
                new InMemoryStorageUnitContainer())));

        warehouseEditor = new WarehouseEditor(warehouse);
        sidebar = new Panel("Sidebar##sidebar");
    }

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Circus");
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
        io.getFonts().addFontFromMemoryTTF(loadFromResources(DEFAULT_FONT_NAME), DEFAULT_FONT_SIZE,
                fontConfig, glyphRanges);
        // Merge icon glyphs
        fontConfig.setMergeMode(true);
        // Load FontAwesome glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("/fa-regular-400.ttf"),
                14, fontConfig, glyphRanges);
        io.getFonts().addFontFromMemoryTTF(loadFromResources("/fa-solid-900.ttf"),
                14, fontConfig, glyphRanges);
        io.getFonts().build();
        fontConfig.destroy();
    }

    /**
     * Setup the dock space for the application.
     */
    private void initDockspace() {
        int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);

        Pair<Integer, Integer> appWindowSize = getAppWindowSize();
        ImGui.setNextWindowSize(appWindowSize.getFirst(), appWindowSize.getSecond());

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

        // Setup dock space window so that it is behind every other window, and so that it doesn't
        // react to interaction.
        windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;
        // Start window
        ImGui.begin("Dockspace Content Root", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        // Create dock layout
        final int dockspaceId = ImGui.getID("Dockspace");
        if (!hasInitialisedDockspaceLayout) {
            hasInitialisedDockspaceLayout = true;
            imgui.internal.ImGui.dockBuilderRemoveNode(dockspaceId);
            imgui.internal.ImGui.dockBuilderAddNode(dockspaceId, ImGuiDockNodeFlags.DockSpace);
            imgui.internal.ImGui.dockBuilderSetNodeSize(dockspaceId, appWindowSize.getFirst(), appWindowSize.getSecond());

            ImInt dockMainId = new ImInt(dockspaceId);
            int dockIdLeft = imgui.internal.ImGui.dockBuilderSplitNode(dockMainId.get(), ImGuiDir.Left,
                    0.33f, null, dockMainId);

            imgui.internal.ImGui.dockBuilderDockWindow(sidebar.getTitle(), dockIdLeft);
            imgui.internal.ImGui.dockBuilderDockWindow(warehouseEditor.getTitle(), dockMainId.get());

            int dockIdLeftDown = imgui.internal.ImGui.dockBuilderSplitNode(dockIdLeft, ImGuiDir.Down,
                    0.5f, null, dockMainId);
            imgui.internal.ImGui.dockBuilderDockWindow(warehouseEditor.getInspector().getTitle(), dockIdLeftDown);
            imgui.internal.ImGui.dockBuilderFinish(dockspaceId);
        }
        ImGui.dockSpace(dockspaceId, 0.0f, 0.0f);
    }

    /**
     * Draw the UI.
     */
    @Override
    public void process() {
        initDockspace();
        // Render components
        toolbar.draw(this);
        warehouseEditor.draw(this);
        sidebar.draw(this);
        // End dockspace window
        ImGui.end();
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
     * Get the size of the application window size.
     * @return A pair of integers representing the width and height respectively.
     */
    private static Pair<Integer, Integer> getAppWindowSize() {
        long window = glfwGetCurrentContext();
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, width, height);
        return new Pair<>(width.get(0), height.get(0));
    }

    /**
     * Load file from resources folder.
     * @param name of the file.
     * @return file data as a byte array.
     */
    private static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Objects.requireNonNull(DesktopApplication.class.getResource(name)).toURI()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Entrypoint for the desktop application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        DesktopApplication application = new DesktopApplication();
        launch(application);
        System.exit(0);
    }
}
