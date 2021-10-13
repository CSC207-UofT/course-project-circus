package circus.application;

/**
 * Driver class for the shell application.
 */
public class ShellApplication {
    private boolean isRunning;

    /**
     * Construct a ShellApplication.
     */
    public ShellApplication() {
        isRunning = false;
    }

    /**
     * Run the shell application.
     */
    private void run() {
        isRunning = true;
        while (isRunning) {
            System.out.println("> ");
//            String input = ...read line from stdin...
//            if (input.trim().equals("exit")) {
//              stop();
//            }
        }
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
