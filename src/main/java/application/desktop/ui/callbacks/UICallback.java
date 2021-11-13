package application.desktop.ui.callbacks;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.UIComponent;

/**
 * Callback for responding to UI events.
 */
public interface UICallback {
    void execute(UIComponent source, DesktopApplication application);
}
