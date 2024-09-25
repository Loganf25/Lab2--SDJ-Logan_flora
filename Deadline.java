import java.time.LocalDateTime;

public class Deadline extends Event implements Completable{
    private boolean complete = false;

    //Constructor
    public Deadline (String name, LocalDateTime deadline)
    {
        super(name, deadline);
    }

    //Just a completable event
    @Override
    public void complete() {complete = true;}
    @Override
    public boolean isComplete() {return complete;}


}
