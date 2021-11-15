package application.desktop.ui.events;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;
import messaging.Message;

/**
 * An event triggered as a response to a change in the UI.
 */
public class ComponentEvent extends Message<ComponentEventData> {
    public void execute(Component source, DesktopApplication application) {
        super.execute(new ComponentEventData(source, application));
    }
}
