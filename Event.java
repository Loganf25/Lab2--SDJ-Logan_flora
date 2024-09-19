import java.time.LocalDateTime;
public abstract class Event implements Comparable<Event>{
    private String name;
    private LocalDateTime dateTime;

    public Event(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }
    public String getName() {return name;}

    public LocalDateTime getDateTime() {return this.dateTime;}

    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}

    public void setName(String name){this.name = name;}

    @Override
    public int compareTo(Event e) {return this.dateTime.compareTo(e.dateTime);}
}
