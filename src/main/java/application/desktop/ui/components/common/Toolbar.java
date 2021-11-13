package application.desktop.ui.components.common;

import application.desktop.DesktopApplication;
import application.desktop.ui.callbacks.ExitCallback;
import application.desktop.ui.callbacks.UICallback;

/**
 * Main toolbar component for the DesktopApplication.
 */
public class Toolbar extends MenuBar {
    /**
     * Construct a new Toolbar.
     */
    public Toolbar() {
        super(new Menu("File",
                new MenuItem("New", (source, application) -> {
                    // TODO: Implement New File callback
                }),
                new MenuItem("Open", (source, application) -> {
                    // TODO: Implement Open File callback
                }),
                new Separator(),
                new MenuItem("Save", ((source, application) -> {
                    // TODO: Implement Save File callback
                })),
                new MenuItem("Save As", ((source, application) -> {
                    // TODO: Implement Save As callback
                })),
                new Separator(),
                new MenuItem("Exit", new ExitCallback()))
        );
    }
}
