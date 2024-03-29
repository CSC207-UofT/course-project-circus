package application.desktop.ui.events;

import application.desktop.ui.components.common.Component;
import messaging.MessageListener;

/**
 * An interface for responding to application.desktop.ui.events.
 */
public interface ComponentEventListener extends MessageListener<ComponentEventData> {
    @Override
    default void handle(ComponentEventData data) {
        handle(data.getSource());
    }

    void handle (Component source);
}
