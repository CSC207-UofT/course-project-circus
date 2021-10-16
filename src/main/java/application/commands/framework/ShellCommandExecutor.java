package main.java.application.commands.framework;

import main.java.application.ShellApplication;

import java.util.*;

/**
 * Executes shell commands.
 */
public class ShellCommandExecutor {
    private final ShellApplication application;
    private final Map<String, ShellCommand> commands;
    private final boolean isCaseSensitive;

    /**
     * Construct a ShellCommandExecutor with commands.
     * @param isCaseSensitive Whether the command parser is case-sensitive.
     * @param commands The commands to register.
     */
    public ShellCommandExecutor(ShellApplication application, boolean isCaseSensitive, ShellCommand[] commands) {
        this.application = application;
        this.commands = new HashMap<>();
        this.isCaseSensitive = isCaseSensitive;
        // Register commands
        for (ShellCommand command : commands) {
            try {
                register(command);
            } catch (ShellCommandSpecNotFoundException e) {
                // If the command has no spec, simply log it and don't register
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Construct a case-sensitive ShellCommandExecutor with commands.
     * @param commands The commands to register.
     */
    public ShellCommandExecutor(ShellApplication application, ShellCommand[] commands) {
        this(application,true, commands);
    }

    /**
     * Register the given ShellCommand.
     * @param command the command to register.
     * @param overwrite Whether to overwrite the registry if a command of the same name already exists.
     */
    public void register(ShellCommand command, boolean overwrite) throws ShellCommandSpecNotFoundException {
        ShellCommandSpec commandSpec = command.getSpec();
        // Convert command names to lowercase if case-sensitive.
        String commandName = isCaseSensitive ? commandSpec.name() : commandSpec.name().toLowerCase();
        if (!commands.containsKey(commandName) || overwrite) {
            commands.put(commandName, command);
        }
    }

    /**
     * Register the given ShellCommand, overwriting the registry if a command of the same name already exists.
     * @param command the command to register.
     */
    public void register(ShellCommand command) throws ShellCommandSpecNotFoundException {
        register(command, true);
    }

    /**
     * Parse and execute a command string.
     * @param input command string of the form "command_name arg1 arg2 arg3 ..."
     * @return The output of the command, or null if there was an error.
     */
    public String execute(String input) {
        String commandName = input.split(" ")[0];
        if (!commands.containsKey(commandName)) {
            System.out.printf("%s: command not found\n", commandName);
            return null;
        } else {
            // TODO: Parse args
            ShellCommand command = commands.get(commandName);
            ShellCommandArgContainer argContainer = command.createArgContainer();
            try {
                argContainer.populate(argContainer.parse(input.replaceFirst(commandName, "").strip()));
            } catch (MissingPositionalArgsException e) {
                System.out.println(e.getMessage());
                return null;
            }
            return command.execute(application, argContainer);
        }
    }

    /**
     * Get the ShellCommand with the given name.
     * @param name The name of the command.
     * @return the ShellCommand with the given name, or null if no such command exists.
     */
    public ShellCommand getCommandByName(String name) {
        return commands.getOrDefault(name, null);
    }
}
