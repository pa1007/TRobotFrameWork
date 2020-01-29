package fr.pa1007.trobotframework.exception;

public class ModuleNotFoundException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public ModuleNotFoundException() {
        super("Module not found");
    }
}
