package application.shell.commands.framework;

import org.junit.jupiter.api.Test;
import warehouse.geometry.grid.Point;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExampleShellCommandArgContainer extends ShellCommandArgContainer {
    @ShellCommandArg
    private String foo;
    @ShellCommandArg
    private String bar;
    @ShellCommandArg
    private String baz;

    public String getFoo() {
        return foo;
    }

    public String getBar() {
        return bar;
    }

    public String getBaz() {
        return baz;
    }
}

public class ShellCommandArgContainerTest {
    @Test
    public void testPopulate1() {
        ExampleShellCommandArgContainer container = new ExampleShellCommandArgContainer();
        HashMap<String, Object> args = new HashMap<>();
        args.put("foo", "Hello world");
        args.put("bar", "What a wonderful day");
        args.put("baz", "Amazing grace");
        container.populate(args);
        // Now, check if the container populated the arguments from the map.
        assertEquals(args.get("foo"), container.getFoo());
        assertEquals(args.get("bar"), container.getBar());
        assertEquals(args.get("baz"), container.getBaz());
    }

    @Test
    public void testPopulate2() {
        ExampleShellCommandArgContainer container = new ExampleShellCommandArgContainer();
        HashMap<String, Object> args = new HashMap<>();
        // Fake field and missing argument ("foo")
        args.put("fake_field", "fake_value");
        args.put("bar", "What a wonderful day");
        args.put("baz", "Amazing grace");
        container.populate(args);
        assertNull(container.getFoo());
        assertEquals(args.get("bar"), container.getBar());
        assertEquals(args.get("baz"), container.getBaz());
    }

    @Test
    public void testPopulate3() {
        ExampleShellCommandArgContainer container = new ExampleShellCommandArgContainer();
        HashMap<String, Object> args = new HashMap<>();
        // Fake field and missing argument ("foo")
        args.put("fake_field", "fake_value");
        args.put("bar", "What a wonderful day");
        args.put("baz", new Point(2, 2));
        assertThrows(Exception.class,
                () -> container.populate(args));
    }

    @Test
    public void testParse() {
        ExampleShellCommandArgContainer container = new ExampleShellCommandArgContainer();
        HashMap<String, Object> expectedArgs = new HashMap<>();
        expectedArgs.put("foo", "Hello world");
        expectedArgs.put("bar", "What a wonderful day");
        expectedArgs.put("baz", "Amazing grace");
        try {
            Map<String, Object> args1 = container.parse("\"Hello world\" \"What a wonderful day\" \"Amazing grace\"");
            assertEquals(expectedArgs, args1);
            Map<String, Object> args2 = container.parse("'Hello world' 'What a wonderful day' \"Amazing grace\"");
            assertEquals(expectedArgs, args2);
        } catch (MissingPositionalArgsException e) {
            e.printStackTrace();
        }

        assertThrows(MissingPositionalArgsException.class,
                () -> container.parse("Hello world"));
    }
}
