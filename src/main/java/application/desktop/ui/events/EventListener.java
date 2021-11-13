package application.desktop.ui.events;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;

/**
 * An interface for responding to events.
 */
public interface EventListener {
    void handle(Component component, DesktopApplication application);
}
