package messaging;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    public void testAddListener1() {
        Message<Integer> message = new Message<>();
        AtomicBoolean hasRun = new AtomicBoolean(false);
        message.addListener((x) -> {
            hasRun.set(true);
        });
        // Now, execute the message
        message.execute(0);
        assertTrue(hasRun.get());
    }

    @Test
    public void testAddListener2() {
        Message<Integer> message = new Message<>();
        AtomicInteger valueFromMessage = new AtomicInteger(-1);
        message.addListener(valueFromMessage::set);
        // Now, execute the message
        message.execute(0);
        assertEquals(valueFromMessage.get(), 0);
        message.execute(5);
        assertEquals(valueFromMessage.get(), 5);
        message.execute(10);
        assertNotEquals(valueFromMessage.get(), -1);
    }

    @Test
    public void testRemoveListener() {
        Message<AtomicBoolean> message = new Message<>();
        AtomicBoolean hasRun = new AtomicBoolean(false);
        MessageListener<AtomicBoolean> listener = data -> data.set(true);
        message.addListener(listener);
        // Now, execute the message
        message.execute(hasRun);
        assertTrue(hasRun.get());
        hasRun.set(false);
        // Now, remove the listener and run again
        assertTrue(message.removeListener(listener));
        message.execute(hasRun);
        assertFalse(hasRun.get());
    }
}
