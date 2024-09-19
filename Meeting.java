import java.time.LocalDateTime;
import java.time.Duration;

public class Meeting extends Event implements Completable{
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
    }
    @Override
    public void complete() {complete = true;}


    @Override
    public boolean isComplete() {
        return complete;
    }



    public LocalDateTime getEndDateTime() {return endDateTime;}

    public Duration getDuration() {return Duration.between(getDateTime(), endDateTime);}

    public String getLocation() {return location;}

    public void setEndDateTime(LocalDateTime end) {this.endDateTime = end;}

    public void setLocation(String location) {this.location = location;}
}
