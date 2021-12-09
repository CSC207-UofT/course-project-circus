package application.desktop.ui.events;

import application.desktop.ui.components.common.Component;

public class ComponentEventData {
    private final Component source;

    /**
     * Construct a ComponentEventData given a source Component and DesktopApplication instance..
     *
     * @param source      The Component that triggered the event.
     */
    public ComponentEventData(Component source) {
        this.source = source;
    }

    public Component getSource() {
        return source;
    }
}
