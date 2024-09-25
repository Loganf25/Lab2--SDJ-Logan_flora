import java.time.LocalDateTime;
//Not static as Event is a type of event as well, so event should be able to be made
//Especially when it's the only one the is not completable, and to not display the button
//For non-completable is a requirement of this lab
//Also things like Halloween and Christmas are events, not a deadline or meeting
//And you cannot initialize static classes,
//So, not static!!
public class Event implements Comparable<Event>{
    private String name;
    private LocalDateTime dateTime;

    //Constructor
    public Event(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }
    //Gets Event Name
    public String getName() {return name;}

    //Gets events start time
    public LocalDateTime getDateTime() {return this.dateTime;}

    //Sets start time
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}

    //Sets name when creating events
    public void setName(String name){this.name = name;}

    @Override
    public int compareTo(Event e) {return this.dateTime.compareTo(e.dateTime);}
}
