package application.desktop.ui.events;

import application.desktop.DesktopApplication;
import application.desktop.ui.components.common.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * An event triggered as a response to a change in the UI.
 */
public class Event {
    /**
     * A list of objects that listen to this event.
     */
    private final List<EventListener> listeners = new ArrayList<>();

    /**
     * Add a listener to this Event.
     * @param listener The listener to add.
     */
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from this Event by value.
     * @param listener The listener to remove.
     * @return True if the listener could be removed, and False otherwise.
     */
    public boolean removeListener(EventListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Raise the event, notifying all listeners.
     */
    public void execute(Component component, DesktopApplication application) {
        for (EventListener listener : listeners) {
            listener.handle(component, application);
        }
    }
}
