package weatherapp.data;

import java.time.LocalDateTime;

/**
 * Standardowa klasa na dane.
 */
public abstract class DataClass {
    private final LocalDateTime timestamp;

    public DataClass() {
        timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "DataClass(timestamp=" + this.getTimestamp() + ")";
    }

}
