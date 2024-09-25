import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

//Filters to be used in EventListPanel
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

    //When clicked, will show Incomplete events
    //Also hides non-completable events
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

    //Only shows Meetings
    public static Predicate<Event> getMeetingFilter(){
        return (event) -> event instanceof Meeting;
    }
    //Only shows Deadlines
    public static Predicate<Event> getDeadlineFilter(){
        return (event) -> event instanceof Deadline;
    }
}
