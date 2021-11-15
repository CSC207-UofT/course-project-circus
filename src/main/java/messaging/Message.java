package messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * A message for communicating between parts of the application.
 */
public class Message<T> implements MessageListener<T> {
    /**
     * A list of objects that listen to this message.
     */
    private final List<MessageListener<T>> listeners = new ArrayList<>();

    /**
     * Add a listener to this Message.
     * @remark The argument to this method can be another Message! In this case, this will chain the two messages
     * together so that whenever this Message is executed, all listeners of the other Message are notified as well.
     * @param listener The listener to add.
     */
    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from this Message by value.
     * @param listener The listener to remove.
     * @return True if the listener could be removed, and False otherwise.
     */
    public boolean removeListener(MessageListener<T> listener) {
        return listeners.remove(listener);
    }

    /**
     * Raise the event, notifying all listeners.
     */
    public void execute(T data) {
        for (MessageListener<T> listener : listeners) {
            listener.handle(data);
        }
    }

    @Override
    public void handle(T data) {
        execute(data);
    }
}

