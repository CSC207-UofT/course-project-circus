package messaging;

/**
 * An interface for responding to Messages.
 */
public interface MessageListener<T> {
    void handle(T data);
}