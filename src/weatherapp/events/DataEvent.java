package weatherapp.events;

import java.time.LocalDateTime;

public class DataEvent extends AppEvent {
    private final LocalDateTime timestamp;

    public DataEvent() {
        timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "DataEvent(timestamp=" + this.getTimestamp() + ")";
    }

}
