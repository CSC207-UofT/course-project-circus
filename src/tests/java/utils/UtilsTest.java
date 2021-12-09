package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    public void testPair()
    {
        Pair<Integer, String> p = new Pair<>(1, "hello");
        assertEquals(1, p.getFirst());
        assertEquals("hello", p.getSecond());
    }
}
