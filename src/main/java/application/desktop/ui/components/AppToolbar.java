package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.FontAwesomeIcon;
import application.desktop.ui.components.common.*;
import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import serialization.FileObjectLoader;
import serialization.FileObjectSaver;
import warehouse.Warehouse;
import warehouse.WarehouseState;
import warehouse.geometry.WarehouseCoordinate;
import warehouse.geometry.WarehouseCoordinateSystem;

import java.io.IOException;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class AppToolbar<T extends WarehouseCoordinateSystem<U>, U extends WarehouseCoordinate> extends MenuBar {
    private static final String NEW_WAREHOUSE_POPUP_ID = "New WarehouseLayout##new_warehouse_popup";

    private final DesktopApplication<T, U> application;

    private String saveFilepath;

    private boolean openNewPopup;
    private final ImString newWarehouseName;
    private final ImInt newWarehouseWidth;
    private final ImInt newWarehouseHeight;

    /**
     * Construct a new AppToolbar.
     */
    public AppToolbar(DesktopApplication<T, U> application) {
        this.application = application;

        this.newWarehouseWidth = new ImInt(12);
        this.newWarehouseHeight = new ImInt(12);
        this.newWarehouseName = new ImString();

        // Create menu items
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As...");
        MenuItem exitMenuItem = new MenuItem("Exit");
        // Create menu and add it as a child to this MenuBar
        addChild(new Menu("File",
                newMenuItem,
                openMenuItem,
                new Separator(),
                saveMenuItem,
                saveAsMenuItem,
                new Separator(),
                exitMenuItem
        ));

        // Register event listeners
        newMenuItem.getOnClickedEvent().addListener((data) -> {
            openNewPopup = true;
            newWarehouseWidth.set(12);
            newWarehouseHeight.set(12);
        });
        openMenuItem.getOnClickedEvent().addListener((data) -> {
            FileObjectLoader<WarehouseState<T, U>> stateLoader = application.getWarehouseStateLoader();
            ImGuiFileDialog.openModal("LoadDialog", String.format("%s  Open", FontAwesomeIcon.FileAlt.getIconCode()),
                    stateLoader.getExtensionFilter(), ".", "",
                    1, 0, ImGuiFileDialogFlags.None);
        });
        saveMenuItem.getOnClickedEvent().addListener((data) -> {
            if (saveFilepath != null) {
                saveState(saveFilepath);
            } else {
                openSaveDialog(String.format("%s  Save", FontAwesomeIcon.Save.getIconCode()), false);
            }
        });
        saveAsMenuItem.getOnClickedEvent().addListener((data) -> {
            openSaveDialog("Save As...", true);
        });

        exitMenuItem.getOnClickedEvent().addListener((data) -> {
            application.exit();
        });
    }

    /**
     * Save the state at the given filepath.
     */
    private void saveState(String filepath) {
        FileObjectSaver<WarehouseState<T, U>> stateSaver = application.getWarehouseStateSaver();
        System.out.println(filepath);
        try {
            stateSaver.save(application.getWarehouse().getState(), filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the save dialog.
     */
    private void openSaveDialog(String title, boolean isSaveAsDialog) {
        FileObjectSaver<WarehouseState<T, U>> stateSaver = application.getWarehouseStateSaver();
        ImGuiFileDialog.openModal("SaveDialog", title, stateSaver.getExtensionFilter(), ".", "Untitled",
                1, isSaveAsDialog ? -1 : 0, ImGuiFileDialogFlags.ConfirmOverwrite);
    }

    /**
     * Draw the toolbar and dialog windows.
     */
    @Override
    protected void drawContent() {
        super.drawContent();
        int dialogWindowFlags = ImGuiWindowFlags.NoCollapse;
        drawNewWarehouseDialog(dialogWindowFlags);
        drawSaveDialog(dialogWindowFlags);
        drawLoadDialog( dialogWindowFlags);
    }

    private void drawNewWarehouseDialog(int dialogWindowFlags) {
        if (openNewPopup) {
            ImGui.openPopup(NEW_WAREHOUSE_POPUP_ID);
            openNewPopup = false;
        }

        dialogWindowFlags |= ImGuiWindowFlags.AlwaysAutoResize;
        if (ImGui.beginPopupModal(NEW_WAREHOUSE_POPUP_ID, dialogWindowFlags)) {
            ImGui.inputTextWithHint("Name", "Artem's Powerhouse of Production", newWarehouseName,
                    ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CallbackAlways);
            ImGui.inputInt("Width", newWarehouseWidth);
            ImGui.inputInt("Height", newWarehouseHeight);

            ImGui.spacing();

            if (ImGui.button("OK")) {
                // TODO: Implement warehouse factor for creating new empty warehouses
//                application.setWarehouse(DesktopApplication.makeEmptyGridWarehouse(
//                        newWarehouseWidth.get(), newWarehouseHeight.get()));
                ImGui.closeCurrentPopup();
            }
            ImGui.setItemDefaultFocus();
            ImGui.sameLine();
            if (ImGui.button("Cancel")) {
                ImGui.closeCurrentPopup();
            }

            ImGui.endPopup();
        }
    }

    private void drawSaveDialog(int dialogWindowFlags) {
        if (ImGuiFileDialog.display("SaveDialog", dialogWindowFlags, 0, 350, Float.MAX_VALUE, Float.MAX_VALUE)) {
            if (ImGuiFileDialog.isOk()) {
                boolean isSaveAsDialog = ImGuiFileDialog.getUserDatas() == -1;
                String selectedFilepath = ImGuiFileDialog.getCurrentPath() + "/" + ImGuiFileDialog.getCurrentFileName();
                if (saveFilepath == null) {
                    saveFilepath = selectedFilepath;
                }

                String filepath = isSaveAsDialog ? selectedFilepath : saveFilepath;
                saveState(filepath);
            }

            ImGuiFileDialog.close();
        }
    }

    private void drawLoadDialog(int dialogWindowFlags) {
        if (ImGuiFileDialog.display("LoadDialog", dialogWindowFlags, 0, 350, Float.MAX_VALUE, Float.MAX_VALUE)) {
            if (ImGuiFileDialog.isOk()) {
                String filepath = ImGuiFileDialog.getCurrentPath() + "/" + ImGuiFileDialog.getCurrentFileName();
                FileObjectLoader<WarehouseState<T, U>> stateLoader = application.getWarehouseStateLoader();
                try {
                    WarehouseState<T, U> state = stateLoader.load(filepath);
                    // TODO: Use factory here too!
                    application.setWarehouse(new Warehouse<>(state));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImGuiFileDialog.close();
        }
    }
}
