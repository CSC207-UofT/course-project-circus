package application.desktop.ui.components;

import application.desktop.ui.components.common.*;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class ApplicationToolbar extends MenuBar {
    /**
     * Construct a new ApplicationToolbar.
     */
    public ApplicationToolbar() {
        // Create menu items
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As");
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
        newMenuItem.getOnClickedEvent().addListener((data) -> {});
        // TODO: Implement Open File callback
        openMenuItem.getOnClickedEvent().addListener((data) -> {});
        // TODO: Implement Save File callback
        saveMenuItem.getOnClickedEvent().addListener((data) -> {});
        // TODO: Implement Save As callback
        saveAsMenuItem.getOnClickedEvent().addListener((data) -> {});
        saveAsMenuItem.getWhileMouseHoverEvent().addListener((data) -> {
            System.out.println("\"Save As\" menu item hovering");
        });

        exitMenuItem.getOnClickedEvent().addListener((data) -> {
            data.getApplication().exit();
        });
    }
}
