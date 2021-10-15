package circus.application.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MissingPositionalArgsException extends Exception {
    public MissingPositionalArgsException(String[] argNames) {
        super(String.format("missing %d required positional arguments: %s",
                argNames.length,
                Arrays.stream(argNames).collect(Collectors.joining(",", "'", "'"))));
    }
}
