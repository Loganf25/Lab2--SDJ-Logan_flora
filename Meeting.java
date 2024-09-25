import java.time.LocalDateTime;
import java.time.Duration;

public class Meeting extends Event implements Completable{
    //Initialization
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete = false;

    //Constructor
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
    }

    //Completes meeting
    @Override
    public void complete() {complete = true;}


    //Returns state of complete
    @Override
    public boolean isComplete() {
        return complete;
    }

    //Meetings have end times, this gets it
    public LocalDateTime getEndDateTime() {return endDateTime;}

    //Finds and returns duration of meeting
    public Duration getDuration() {return Duration.between(getDateTime(), endDateTime);}

    //Meeting location
    public String getLocation() {return location;}

    //Set end time
    public void setEndDateTime(LocalDateTime end) {this.endDateTime = end;}

    //Set location
    public void setLocation(String location) {this.location = location;}
}
