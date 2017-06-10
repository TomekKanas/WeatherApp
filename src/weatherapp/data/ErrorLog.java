package weatherapp.data;

import java.time.LocalDateTime;

/**
 * Klasa przechowująca informacje o wystąpieniu błędu.
 */
public class ErrorLog {
    private final LocalDateTime timestamp;
    private final Throwable cause;

    public ErrorLog(Throwable cause) {
        this.timestamp = LocalDateTime.now();
        this.cause = cause;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public Throwable getCause() {
        return this.cause;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ErrorLog(timestamp=" + this.getTimestamp() + ", cause=" + this.getCause() + ")";
    }

}
