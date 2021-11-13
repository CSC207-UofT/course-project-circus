package application.desktop.ui.components;

import application.desktop.ui.components.common.Menu;
import application.desktop.ui.components.common.MenuBar;
import application.desktop.ui.components.common.MenuItem;
import application.desktop.ui.components.common.Separator;

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
        // Register event listeners
        // TODO: Implement New File callback
        System.out.println(newMenuItem.getParent());
        newMenuItem.getOnSelectedEvent().addListener((source) -> {});
        newMenuItem.getOnStartMouseHoverEvent().addListener((source) -> {
            newMenuItem.setLabel("Hovering");
            newMenuItem.setVisible(false);
        });
        newMenuItem.getOnStopMouseHoverEvent().addListener((source) -> {
            newMenuItem.setLabel("New");
            System.out.println("stop");
            newMenuItem.setVisible(true);
        });
        // TODO: Implement Open File callback
        openMenuItem.getOnSelectedEvent().addListener((source) -> {});
        // TODO: Implement Save File callback
        saveMenuItem.getOnSelectedEvent().addListener((source) -> {});
        // TODO: Implement Save As callback
        saveAsMenuItem.getOnSelectedEvent().addListener((source) -> {});
        saveAsMenuItem.getWhileMouseHoverEvent().addListener((source) -> {
            System.out.println("\"Save As\" menu item hovering");
        });
    }
}
