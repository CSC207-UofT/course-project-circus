package application.shell.presenters;

import application.shell.presenters.coordinateparsers.PointParser;
import org.junit.jupiter.api.Test;
import warehouse.geometry.grid.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PointParserTest {
    @Test
    public void testParse() {
        PointParser parser = new PointParser();
        // Test valid format
        for (int x = -10; x < 10; x++) {
            for (int y = -10; y < 10; y++) {
                Point point = new Point(x, y);
                Point parsedPoint = parser.parse(String.format("(%d, %d)", x, y));
                assertEquals(point, parsedPoint);
            }
        }
        // Test invalid format
        assertNull(parser.parse("-10,10"));
        assertNull(parser.parse("(10)"));
        assertNull(parser.parse("(ten,ten)"));
        assertNull(parser.parse("(1d,1a)"));
    }
}
