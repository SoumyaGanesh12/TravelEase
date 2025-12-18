package travelease.command;

/**
 * Command Pattern Interface - Defines contract for encapsulating operations as objects with execute/undo capabilities.
 */
public interface CommandAPI {
    /**
     * Executes the command operation
     */
    void execute();
    
    /**
     * Reverses the command operation (undo functionality)
     */
    void undo();
}
