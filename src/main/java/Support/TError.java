package Support;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * It is an exception class to provide cancellation of application at unrecoverable cases.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class TError extends Exception {

    /**
     * Creates a new instance of {@code TError} without detail message.
     */
    public TError() {
    }

    /**
     * Constructs an instance of {@code TError} with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public TError(String msg) {
        super(msg);
    }

    /**
     * Retrieves the stack trace as simple string.
     * @return A string.
     */
    @Override
    public String toString() {
        /*StringWriter sw = new StringWriter();
        this.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        return "TError{"+sw.toString()+'}';*/
        return "TError{"+this.getMessage()+"}";
    }

}
