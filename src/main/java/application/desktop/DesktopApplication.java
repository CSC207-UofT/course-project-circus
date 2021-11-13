package application.desktop;

import application.desktop.ui.FontAwesomeIcons;
import application.desktop.ui.components.WarehouseLayoutCanvas;
import application.desktop.ui.components.WarehouseLayoutEditor;
import application.desktop.ui.components.common.*;
import application.desktop.ui.components.common.Toolbar;
import imgui.*;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import imgui.type.ImBoolean;
import org.lwjgl.BufferUtils;
import utils.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private final List<UIComponent> components;

    /**
     * Construct a DesktopApplication.
     */
    public DesktopApplication() {
        components = new ArrayList<>();
        components.add(new Toolbar());
        components.add(new WarehouseLayoutEditor());
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
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

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
     * Draw the UI.
     */
    @Override
    public void process() {
        initDockspace();
        // Render components
        for (UIComponent component : components) {
            component.render(this);
        }
        // End dockspace window
        ImGui.end();
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
        final String DOCKSPACE_NAME = "dockspace_window";
        ImGui.begin(DOCKSPACE_NAME, new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        // Initialise dockspace
        ImGui.dockSpace(ImGui.getID(DOCKSPACE_NAME));
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
