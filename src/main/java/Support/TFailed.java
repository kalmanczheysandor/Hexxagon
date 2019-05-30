package Support;

/**
 * It is an exception class to provide simple divert of process flow.
 * Typically used inside of methods but not thrown by them.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class TFailed extends Exception {

    /**
     * Creates a new instance of {@code TError} without detail message.
     */
    public TFailed() {
    }

    /**
     * Constructs an instance of {@code TError} with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public TFailed(String msg) {
        super(msg);
    }
}
