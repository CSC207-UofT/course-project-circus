package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import application.desktop.ui.components.editor.OrderEditor;
import application.desktop.ui.components.editor.PartCatalogueEditor;
import application.desktop.ui.components.editor.SimulationPanel;
import application.desktop.ui.components.editor.warehouse.WarehouseEditor;
import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.flag.ImGuiDockNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import org.lwjgl.BufferUtils;
import utils.Pair;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;
import warehouse.logistics.orders.OrderQueue;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

/**
 * The root component for the DesktopApplication.
 */
public class RootAppComponent<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends Component {
    /**
     * Whether the dockspace has been initialised yet.
     */
    private boolean hasInitialisedDockspaceLayout;

    private final AppToolbar<T, U> toolbar;
    private final PartCatalogueEditor partCatalogueEditor;
    private final WarehouseEditor<T, U> warehouseEditor;
    private final OrderEditor orderEditor;
    private final SimulationPanel simulationPanel;

    public RootAppComponent(DesktopApplication<T, U> application) {
        toolbar = new AppToolbar<>(application);
        partCatalogueEditor = new PartCatalogueEditor(application.getWarehouse().getState().getPartCatalogue());
        warehouseEditor = new WarehouseEditor<>(
                application.getWarehouse().getState(),
                application.getWarehouseCanvasRenderer(),
                partCatalogueEditor
        );
        OrderQueue orderQueue = application.getWarehouse().getState().getOrderQueue();
        orderEditor = new OrderEditor(orderQueue, warehouseEditor);
        simulationPanel = new SimulationPanel();
    }

    /**
     * Initialise the dock space for the application.
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

            imgui.internal.ImGui.dockBuilderDockWindow(orderEditor.getTitle(), dockIdLeft);
            imgui.internal.ImGui.dockBuilderDockWindow(partCatalogueEditor.getTitle(), dockMainId.get());
            imgui.internal.ImGui.dockBuilderDockWindow(warehouseEditor.getTitle(), dockMainId.get());

            int dockIdLeftDown = imgui.internal.ImGui.dockBuilderSplitNode(dockIdLeft, ImGuiDir.Down,
                    0.5f, null, dockMainId);
            imgui.internal.ImGui.dockBuilderDockWindow(warehouseEditor.getInspector().getTitle(), dockIdLeftDown);
            imgui.internal.ImGui.dockBuilderDockWindow(simulationPanel.getTitle(), dockIdLeftDown);
            imgui.internal.ImGui.dockBuilderFinish(dockspaceId);
        }
        ImGui.dockSpace(dockspaceId, 0.0f, 0.0f);
    }

    /**
     * Draw this RootAppComponent.
     */
    @Override
    protected void drawContent() {
        initDockspace();
        // Render components
        toolbar.draw();
        warehouseEditor.draw();
        partCatalogueEditor.draw();
        orderEditor.draw();
        simulationPanel.draw();
        // End dockspace window
        ImGui.end();
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
}
