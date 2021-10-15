package circus.application;

import circus.application.commands.ExitCommand;
import circus.application.commands.framework.ShellCommand;
import circus.application.commands.framework.ShellCommandExecutor;

import java.util.Scanner;

/**
 * Driver class for the shell application.
 */
public class ShellApplication {
    private boolean isRunning;
    private final ShellCommandExecutor commandExecutor;

    /**
     * Construct a ShellApplication.
     */
    public ShellApplication() {
        isRunning = false;
        commandExecutor = new ShellCommandExecutor(this, new ShellCommand[]{
                new ExitCommand()
        });
    }

    /**
     * Run the shell application.
     */
    private void run() {
        isRunning = true;
        Scanner in = new Scanner(System.in);
        while (isRunning) {
            // Read input
            System.out.print("> ");
            String input = in.nextLine();
            // Execute commands
            String output = commandExecutor.execute(input);
            // Display output
            if (output != null && !output.isEmpty()) {
                System.out.println(output);
            }
        }
        in.close();
    }

    /**
     * Stop running the application.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Entrypoint for the shell application.
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        ShellApplication application = new ShellApplication();
        application.run();
    }
}
