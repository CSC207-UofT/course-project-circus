package application.desktop.ui.callbacks;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;

/**
 * A callback that exists the application.
 */
public class ExitCallback implements UICallback {
    @Override
    public void execute(UIComponent source, DesktopApplication application) {
        application.exit();
    }
}
