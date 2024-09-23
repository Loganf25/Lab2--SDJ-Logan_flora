import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EventFilter {
    public static Map<String, Predicate<Event>> getEventFilters(){
        Map<String, Predicate<Event>> filters = new HashMap<>();
        //Uncompleted-Filter
        filters.put("Uncompleted Events", getIncompleteFilter());
        //Meetings
        filters.put("Meetings", getMeetingFilter());
        //Deadlines
        filters.put("Deadlines", getDeadlineFilter());

        return filters;
    }

    public static Predicate<Event> getIncompleteFilter(){
        return (event) -> {
            if (event instanceof Meeting)
                return !((Meeting) event).isComplete();
            else if (event instanceof Deadline)
                return !((Deadline) event).isComplete();
            else
                return false;
        };
    }

    public static Predicate<Event> getMeetingFilter(){
        return (event) -> event instanceof Meeting;
    }
    public static Predicate<Event> getDeadlineFilter(){
        return (event) -> event instanceof Deadline;
    }
}
