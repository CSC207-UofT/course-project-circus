package application.desktop.ui.events;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;

public record ComponentEventData(Component source,
                                 DesktopApplication application) {
    /**
     * Construct a ComponentEventData given a source Component and DesktopApplication instance..
     *
     * @param source      The Component that triggered the event.
     * @param application The DesktopApplication instance.
     */
    public ComponentEventData {
    }
}
