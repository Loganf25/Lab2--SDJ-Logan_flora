import java.awt.*;
import java.time.*;
import javax.swing.*;

public class EventPlanner {
    public static void main(String[] args) {
        //Set Up Window
        JFrame frame = new JFrame("Register and Purse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setBackground(Color.WHITE);

        //Things to be displayed
        EventPanel eventPanel = new EventPanel();
        addDefaultEvents(eventPanel);
        frame.add(eventPanel, BorderLayout.NORTH);
        frame.add(new EventListPanel(eventPanel), BorderLayout.SOUTH);


        //Set Up Window Pt.2
        frame.pack();
        frame.setVisible(true);
    }

    static void addDefaultEvents(EventPanel events){
        LocalDateTime start = LocalDateTime.of(2024, Month.SEPTEMBER, 19, 0, 0);
        Deadline studySession = new Deadline("Study Session", start);
        events.displayedEvent(studySession);
        events.revalidate();
        events.repaint();

    }

}
