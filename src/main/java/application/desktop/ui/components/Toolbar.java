package application.desktop.ui.components.common;

import application.desktop.ui.callbacks.ExitCallback;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class Toolbar extends MenuBar {
    /**
     * Construct a new Toolbar.
     */
    public Toolbar() {
        super(new Menu("File",
                new MenuItem("New"),
                new MenuItem("Open"),
                new Separator(),
                new MenuItem("Save"),
                new MenuItem("Save As"),
                new Separator(),
                new MenuItem("Exit", new ExitCallback()))
        );
    }
}
