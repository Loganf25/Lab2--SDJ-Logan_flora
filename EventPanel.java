import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventPanel extends JPanel{
    private Event event;
    private final JButton completeButton = new JButton("Complete");

    public void addEvent(Event event)
    {
        this.event = event;
    }

    public EventPanel(){
        //Panel Creation
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.BLACK);

        //Display Event Information (Just Event class info first)


        //Button Creation only if event is completable (aka not just a event)
        if(event instanceof Meeting | event instanceof Deadline) {
            completeButton.setPreferredSize(new Dimension(150, 50));
            this.add(completeButton, BorderLayout.SOUTH);
            //Anonymous Method for ActionListener
            completeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (event instanceof Meeting meeting)
                        meeting.complete();
                    else if (event instanceof Deadline deadline)
                        deadline.complete();
                }
            });
        }
        //No Implemented
          //Add data to GUI of following:
            //Name, time, duration (if applicable),
            // location (if applicable), and completion status
            //As either drawStrings or JLabel

    }
}
