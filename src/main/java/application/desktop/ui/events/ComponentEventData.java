package application.desktop.ui.events;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;

public class ComponentEventData {
    private final Component source;
    private final DesktopApplication application;

    /**
     * Construct a ComponentEventData given a source Component and DesktopApplication instance..
     *
     * @param source      The Component that triggered the event.
     * @param application The DesktopApplication instance.
     */
    public ComponentEventData(Component source,
                              DesktopApplication application) {
        this.source = source;
        this.application = application;
    }

    public Component getSource() {
        return source;
    }

    public DesktopApplication getApplication() {
        return application;
    }
}
