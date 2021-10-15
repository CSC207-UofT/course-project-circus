package circus.application.commands.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An empty command argument container. Can be extended to implement custom argument specifications.
 */
public class ShellCommandArgContainer {
    private final List<ShellCommandArg> positionalArgSpecs;
    private final Map<String, ShellCommandArg> argSpecsByName;
    private final Map<ShellCommandArg, Field> argFields;

    public ShellCommandArgContainer() {
        Class<?> clazz = this.getClass();
        positionalArgSpecs = new ArrayList<>();
        argSpecsByName = new HashMap<>();
        argFields = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(ShellCommandArg.class)) continue;
            ShellCommandArg argSpec = field.getAnnotation(ShellCommandArg.class);
            // Replace name, if empty
            if (argSpec.name().isEmpty()) {
                argSpec = new ShellCommandArg() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return field.getAnnotation(ShellCommandArg.class).annotationType();
                    }

                    @Override
                    public String name() {
                        return field.getName();
                    }

                    @Override
                    public boolean isPositional() {
                        return field.getAnnotation(ShellCommandArg.class).isPositional();
                    }

                    @Override
                    public int positionalIndex() {
                        return field.getAnnotation(ShellCommandArg.class).positionalIndex();
                    }

                    @Override
                    public String description() {
                        return field.getAnnotation(ShellCommandArg.class).description();
                    }
                };
            }
            // Add position arg spec
            if (argSpec.isPositional()) {
                positionalArgSpecs.add(argSpec);
            }
            // Add possible keyword arg specs
            argSpecsByName.put(argSpec.name(), argSpec);
            // Track the type of the argument
            argFields.put(argSpec, field);

        }
        // Sort positional arguments by index
        positionalArgSpecs.sort(Comparator.comparingInt(ShellCommandArg::positionalIndex));
    }

    /**
     * Populate this arg container.
     */
    public void populate(Map<String, Object> args) {
        for (String key : args.keySet()) {
            if (!argSpecsByName.containsKey(key)) continue;
            ShellCommandArg argSpec = argSpecsByName.get(key);
            if (!argFields.containsKey(argSpec)) continue;
            Object value = args.get(key);
            try {
                argFields.get(argSpec).set(this, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parse an arg string.
     * @param argString The string to parse.
     * @return a ParsedArgs instance containing the positional and keyword arguments.
     */
    public Map<String, Object> parse(String argString) throws MissingPositionalArgsException {
        String[] parts = parseArgString(argString);
        int numPositionalArgs = positionalArgSpecs.size();
        if (parts.length < numPositionalArgs) {
            String[] missingArgs = new String[numPositionalArgs - parts.length];
            for (int i = parts.length; i < numPositionalArgs; i++) {
                missingArgs[i - parts.length] = positionalArgSpecs.get(i).name();
            }
            throw new MissingPositionalArgsException(missingArgs);
        }

        Map<String, Object> args = new HashMap<>();
        for (int i = 0; i < positionalArgSpecs.size(); i++) {
            try {
                ShellCommandArg argSpec = positionalArgSpecs.get(i);
                Class<?> argType = argFields.get(argSpec).getType();
                args.put(argSpec.name(), convertString(parts[i], argType));
            } catch (IllegalArgException | ArgTypeConversionException e) {
                e.printStackTrace();
            }
        }
        return args;
    }

    /**
     * Convert a string to the given type. NOTE: This is a very simple implementation.
     * TODO: Support a modular approach for value conversion.
     *
     * @param value the String to convert.
     * @param clazz The type to convert to.
     * @return An Object representing the converted value.
     */
    private Object convertString(String value, Class<?> clazz) throws IllegalArgException, ArgTypeConversionException {
        try {
            if (String.class == clazz) {
                return value;
            }
            if(Boolean.class == clazz || Boolean.TYPE == clazz) {
                return Boolean.parseBoolean(value);
            }
            if(Byte.class == clazz || Byte.TYPE == clazz) {
                return Byte.parseByte(value);
            }
            if(Short.class == clazz || Short.TYPE == clazz) {
                return Short.parseShort(value);
            }
            if(Integer.class == clazz || Integer.TYPE == clazz) {
                return Integer.parseInt(value);
            }
            if(Long.class == clazz || Long.TYPE == clazz) {
                return Long.parseLong(value);
            }
            if(Float.class == clazz || Float.TYPE == clazz) {
                return Float.parseFloat(value);
            }
            if(Double.class == clazz || Double.TYPE == clazz) {
                return Double.parseDouble(value);
            }
        } catch (Exception e) {
            throw new ArgTypeConversionException();
        }
        throw new IllegalArgException();
    }

    /**
     * Parse a string containing arguments to a command. Splits the string by spaces, when not surrounded by quotes.
     * @param input string of the form "arg1 arg2 arg3" where any of the arguments may be surrounded by either single or
     *              double quotes.
     * @return an array of strings containing the value of each argument.
     */
    private static String[] parseArgString(String input) {
        Pattern splitRegex = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+");
        Pattern removeQuotesRegex = Pattern.compile("((?<=^\")(.*)(?=\"$))|((?<=^')(.*)(?='$))");

        Matcher splitMatcher = splitRegex.matcher(input);
        List<String> result = new ArrayList<>();
        while (splitMatcher.find()) {
            // Remove trailing and leading SINGLE and DOUBLE quotes, if they exist
            String match = splitMatcher.group();
            Matcher matcher = removeQuotesRegex.matcher(match);
            if (matcher.find()) {
                match = matcher.group();
            }
            result.add(match);
        }
        return result.toArray(String[]::new);
    }
}
