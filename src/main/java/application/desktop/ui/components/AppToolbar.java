package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.*;
import imgui.ImGui;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiButtonFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import serialization.FileObjectLoader;
import serialization.FileObjectSaver;
import warehouse.WarehouseState;

import java.io.IOException;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class AppToolbar extends MenuBar {
    private final DesktopApplication application;

    private String saveFilepath;

    private boolean openNewPopup;
    private final ImString newWarehouseName;
    private final ImInt newWarehouseWidth;
    private final ImInt newWarehouseHeight;

    /**
     * Construct a new AppToolbar.
     */
    public AppToolbar(DesktopApplication application) {
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
            FileObjectLoader<WarehouseState> stateLoader = application.getWarehouseStateLoader();
            ImGuiFileDialog.openModal("LoadDialog", "Open", stateLoader.getExtensionFilter(), ".", "",
                    1, 0, ImGuiFileDialogFlags.None);
        });
        saveMenuItem.getOnClickedEvent().addListener((data) -> {
            if (saveFilepath != null) {
                saveState(saveFilepath);
            } else {
                openSaveDialog("Save", false);
            }
        });
        saveAsMenuItem.getOnClickedEvent().addListener((data) -> {
            openSaveDialog("Save As...", true);
        });

        exitMenuItem.getOnClickedEvent().addListener((data) -> {
            data.getApplication().exit();
        });
    }

    /**
     * Save the state at the given filepath.
     */
    private void saveState(String filepath) {
        FileObjectSaver<WarehouseState> stateSaver = application.getWarehouseStateSaver();
        System.out.println(filepath);
        try {
            stateSaver.save(application.getState(), filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the save dialog.
     */
    private void openSaveDialog(String title, boolean isSaveAsDialog) {
        FileObjectSaver<WarehouseState> stateSaver = application.getWarehouseStateSaver();
        ImGuiFileDialog.openModal("SaveDialog", title, stateSaver.getExtensionFilter(), ".", "Untitled",
                1, isSaveAsDialog ? -1 : 0, ImGuiFileDialogFlags.ConfirmOverwrite);
    }

    /**
     * Draw the toolbar and dialog windows.
     * @param application The application instance.
     */
    @Override
    protected void drawContent(DesktopApplication application) {
        super.drawContent(application);
        int dialogWindowFlags = ImGuiWindowFlags.NoCollapse;
        drawNewWarehouseDialog(dialogWindowFlags);
        drawSaveDialog(dialogWindowFlags);
        drawLoadDialog( dialogWindowFlags);
    }

    private void drawNewWarehouseDialog(int dialogWindowFlags) {
        if (openNewPopup) {
            ImGui.openPopup("New Warehouse##new_warehouse_popup");
            openNewPopup = false;
        }

        dialogWindowFlags |= ImGuiWindowFlags.AlwaysAutoResize;
        if (ImGui.beginPopupModal("New Warehouse##new_warehouse_popup", dialogWindowFlags)) {
            ImGui.inputTextWithHint("Name", "Artem's Powerhouse of Production", newWarehouseName,
                    ImGuiInputTextFlags.CallbackResize | ImGuiInputTextFlags.CallbackAlways);
            ImGui.inputInt("Width", newWarehouseWidth);
            ImGui.inputInt("Height", newWarehouseHeight);

            ImGui.spacing();

            if (ImGui.button("OK")) {
                application.setState(DesktopApplication.makeEmptyWarehouseState(
                        newWarehouseWidth.get(), newWarehouseHeight.get()));
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
                FileObjectLoader<WarehouseState> stateLoader = application.getWarehouseStateLoader();
                try {
                    WarehouseState state = stateLoader.load(filepath);
                    application.setState(state);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImGuiFileDialog.close();
        }
    }
}
