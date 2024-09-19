import java.awt.*;
import java.time.*;
import javax.swing.*;

public class EventPlanner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Register and Purse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(800, 800));
        frame.setBackground(Color.WHITE);
        frame.getContentPane().add(new EventPanel(), BorderLayout.NORTH);
        EventPanel eventPanel = new EventPanel();
        frame.getContentPane().add(eventPanel, BorderLayout.SOUTH);
        addDefaultEvents(eventPanel);
        frame.pack();
        frame.setVisible(true);
    }

    static void addDefaultEvents(EventPanel events){
        LocalDateTime start = LocalDateTime.of(2024, Month.SEPTEMBER, 19, 0, 0);
        Deadline christmas = new Deadline("Study Sesh", start);
        events.addEvent(christmas);

    }

}
