package application.desktop.ui.components;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.*;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imguifiledialog.flag.ImGuiFileDialogFlags;
import imgui.flag.ImGuiWindowFlags;
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

    /**
     * Construct a new AppToolbar.
     */
    public AppToolbar(DesktopApplication application) {
        this.application = application;

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
        // TODO: Implement New File callback
        newMenuItem.getOnClickedEvent().addListener((data) -> {
        });
        // TODO: Implement Open File callback
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
