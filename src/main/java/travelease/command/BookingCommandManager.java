package travelease.command;

import java.util.Stack;

/**
 * Command Pattern: Invoker that executes commands and maintains history for undo operations.
 */
public class BookingCommandManager {
    // Command Invoker class - manages command execution and history

    private final Stack<CommandAPI> history = new Stack<>();

    public void execute(CommandAPI command) {
        command.execute();
        history.push(command);
    }

    public void undoLastAction() {
        if (!history.isEmpty()) {
            CommandAPI command = history.pop();
            command.undo();
        } else {
            System.out.println("No actions to undo.");
        }
    }
}