import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel{
    private Event event;
    private final JButton completeButton = new JButton("Complete");

    public void displayedEvent(Event newEvent)
    {
        this.event = newEvent;
        //This Panel will display one event at a time
        //So the last events data needs to be deleted
        this.removeAll();

        //Displays information in a vertical box as I wanted and font changes
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Font labelFont = new Font("Serif", Font.BOLD, 25);

        //Display Event Information (Just Event class info first)
        //Name
        JLabel name = new JLabel("Name: " + event.getName());
        name.setFont(labelFont);
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(name);
        //Time
        JLabel time = new JLabel("Time of Event: " + event.getDateTime().toString());
        time.setFont(labelFont);
        time.setForeground(Color.WHITE);
        time.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(time);
        //Meeting Specific Information
        if (event instanceof Meeting) {
            //Duration
            JLabel duration = new JLabel("Duration: " + ((Meeting) event).getDuration().toString());
            duration.setFont(labelFont);
            duration.setForeground(Color.WHITE);
            duration.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(duration);
            //Location
            JLabel location = new JLabel("Location: " + ((Meeting) event).getLocation());
            location.setFont(labelFont);
            location.setForeground(Color.WHITE);
            location.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(location);
            //Completed()
            JLabel comOrNo = new JLabel("Completed? - " + ((Meeting) event).isComplete());
            comOrNo.setFont(labelFont);
            comOrNo.setForeground(Color.WHITE);
            comOrNo.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(comOrNo);

        }
        //Deadline Specific Information
        if (event instanceof Deadline) {
            JLabel comOrNo = new JLabel("Completed? - " + ((Deadline) event).isComplete());
            comOrNo.setFont(labelFont);
            comOrNo.setForeground(Color.WHITE);
            comOrNo.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(comOrNo);
        }
        this.add(Box.createVerticalStrut(5));

        //Button Created if Needed
        if (event instanceof Meeting | event instanceof Deadline) {
            completeButton.setPreferredSize(new Dimension(150, 50));
            this.add(completeButton, BorderLayout.SOUTH);
            //Anonymous Method for ActionListener
            completeButton.addActionListener(e -> {
                if (event instanceof Meeting meeting)
                    meeting.complete();
                else if (event instanceof Deadline deadline)
                    deadline.complete();
                displayedEvent(event);
            });
        }
        revalidate();
        repaint();
    }

    public EventPanel() {
        //Panel Creation
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.BLACK);
    }
}
