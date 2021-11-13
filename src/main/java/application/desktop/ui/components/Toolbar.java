package application.desktop.ui.components;

import application.desktop.ui.components.common.*;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class Toolbar extends MenuBar {
    /**
     * Construct a new Toolbar.
     */
    public Toolbar() {
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

        Panel panel = new Panel("Hello World");
        panel.getOnOpenedEvent().addListener((source, application) -> {
            panel.setTitle("Opened!");
            System.out.println("Panel opened");
        });
        panel.getOnClosedEvent().addListener((source, application) -> {
            panel.setTitle("Closed!");
            System.out.println("Panel closed");
        });
        addChild(panel);

        // Register event listeners
        // TODO: Implement New File callback
        newMenuItem.getOnClickedEvent().addListener((source, application) -> {
            panel.setOpen(true);
        });
        newMenuItem.getOnStartMouseHoverEvent().addListener((source, application) -> {
            newMenuItem.setLabel("Hovering");
        });
        newMenuItem.getOnStopMouseHoverEvent().addListener((source, application) -> {
            newMenuItem.setLabel("New");
        });
        // TODO: Implement Open File callback
        openMenuItem.getOnClickedEvent().addListener((source, application) -> {});
        // TODO: Implement Save File callback
        saveMenuItem.getOnClickedEvent().addListener((source, application) -> {});
        // TODO: Implement Save As callback
        saveAsMenuItem.getOnClickedEvent().addListener((source, application) -> {});
        saveAsMenuItem.getWhileMouseHoverEvent().addListener((source, application) -> {
            System.out.println("\"Save As\" menu item hovering");
        });

        exitMenuItem.getOnClickedEvent().addListener((source, application) -> {
            application.exit();
        });
    }
}
