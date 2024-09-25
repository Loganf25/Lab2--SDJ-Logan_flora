import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.JTextComponent;


public class AddEventModal extends JFrame {
    private final EventListPanel eventListPanel;
    AddEventModal modal;

    record Attribute(String name, JComponent value) {
    }

    ArrayList<Attribute> attributes;
    JPanel infoCollectorPanel;
    JComboBox<String> eventTypeComboBox;

    //Types of Events
    public static final String[] eventTypes = {"Event", "Meeting", "Deadline"};

    public AddEventModal(EventListPanel eventListPanelI) {
        modal = this;
        this.eventListPanel = eventListPanelI;

        //Panel Setup
        this.setTitle("Add Event");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(addEventPanel());
        this.pack();
        this.setVisible(true);
    }

    private JPanel addEventPanel() {
        //Initialize
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        attributes = new ArrayList<>();

        //Setup infoCollectorPanel
        infoCollectorPanel = new JPanel();
        infoCollectorPanel.setPreferredSize(new Dimension(400, 400));
        infoCollectorPanel.setBackground(Color.ORANGE);

        //Create Combo Box and the action listener
        eventTypeComboBox = new JComboBox<>(eventTypes);
        eventTypeComboBox.addActionListener(getEventChooser());

        //Add everything to the Panel, which is what everything is on
        panel.setPreferredSize(new Dimension(600, 400));
        panel.add(eventTypeComboBox);
        panel.add(infoCollectorPanel);

        //Submit button
        JButton addEventButton = new JButton("Submit");
        addEventButton.addActionListener(getEventCreator());

        panel.add(addEventButton);

        return panel;
    }

    private ActionListener getEventChooser() {
        return e -> {
            attributes.clear(); //From previous adds
            infoCollectorPanel.removeAll(); //Same here
            //Displays fields and labels vertical so it's easy to read
            infoCollectorPanel.setLayout(new GridLayout(0, 2, 5, 5));

            //Switch to distinguish Event(0), Meeting(1), and Deadline(2)
            switch (eventTypeComboBox.getSelectedIndex()){
                case 0: {
                    //Less Redundancy
                    //Name and start time
                    getBasics();
                    break;
                }
                case 1: {
                    //Name and start
                    getBasics();
                    //End LocalDateTime
                    attributes.add(new Attribute("End Day", new JTextField(10)));
                    attributes.add(new Attribute("End Month", new JTextField(10)));
                    attributes.add(new Attribute("End Year", new JTextField(10)));
                    attributes.add(new Attribute("End Hour", new JTextField(10)));
                    attributes.add(new Attribute("End Minute", new JTextField(10)));
                    attributes.add(new Attribute("Location", new JTextField(10)));
                    break;

                }
                case 2: {
                    getBasics();
                    //This one is just completable, nothing to do here

                }
            }
            attributes.forEach(attr -> {
                infoCollectorPanel.add(new JLabel(attr.name));
                infoCollectorPanel.add(attr.value);
            });
            infoCollectorPanel.revalidate();
            infoCollectorPanel.repaint();
        };
    }

    //Used above for less redundancy b/w cases
    //But gets individual time variables, will be put into LocalDateTime variable later
    private void getBasics() {
        attributes.add(new Attribute("Name", new JTextField(10)));
        attributes.add(new Attribute("Day", new JTextField(10)));
        attributes.add(new Attribute("Month", new JTextField(10)));
        attributes.add(new Attribute("Year", new JTextField(10)));
        attributes.add(new Attribute("Hour", new JTextField(10)));
        attributes.add(new Attribute("Minute", new JTextField(10)));
    }

    //Creates the new event of what type is selected and  will be added to EventListPanel
    private ActionListener getEventCreator() {
        return e -> {
            Event newEvent = null;
            switch (eventTypeComboBox.getSelectedIndex()){
                case 0: {
                    //Puts input data for event into a new event
                    //Name
                    newEvent = new Event(getInput(attributes.getFirst().value),
                            //Start LocalDateTime
                            getDateTime(Integer.parseInt(getInput(attributes.get(1).value)), 
                            Integer.parseInt(getInput(attributes.get(2).value)), 
                            Integer.parseInt(getInput(attributes.get(3).value)), 
                            Integer.parseInt(getInput(attributes.get(4).value)), 
                            Integer.parseInt(getInput(attributes.get(5).value))));
                    break;
                }
                case 1: {
                    //Puts input data for meeting into a new meeting
                    //Name
                    newEvent = new Meeting(getInput(attributes.getFirst().value),
                            //Start LocalDateTime
                            getDateTime(Integer.parseInt(getInput(attributes.get(1).value)),
                                    Integer.parseInt(getInput(attributes.get(2).value)),
                                    Integer.parseInt(getInput(attributes.get(3).value)),
                                    Integer.parseInt(getInput(attributes.get(4).value)),
                                    Integer.parseInt(getInput(attributes.get(5).value))),
                            //End LocalDateTime
                            getDateTime(Integer.parseInt(getInput(attributes.get(6).value)),
                                    Integer.parseInt(getInput(attributes.get(7).value)),
                                    Integer.parseInt(getInput(attributes.get(8).value)),
                                    Integer.parseInt(getInput(attributes.get(9).value)),
                                    Integer.parseInt(getInput(attributes.get(10).value))),
                            //Location
                            getInput(attributes.get(10).value));
                    break;
                }
                case 2: {
                    //Puts input data for deadline into a new deadline
                    //Name
                    newEvent = new Deadline(getInput(attributes.getFirst().value),
                            //Start LocalDateTime
                            getDateTime(Integer.parseInt(getInput(attributes.get(1).value)),
                                    Integer.parseInt(getInput(attributes.get(2).value)),
                                    Integer.parseInt(getInput(attributes.get(3).value)),
                                    Integer.parseInt(getInput(attributes.get(4).value)),
                                    Integer.parseInt(getInput(attributes.get(5).value))));
                    break;
                }
            }
            eventListPanel.addEvent(newEvent);
            modal.dispose();
        };
    }

    //Turns each individual time field into a single localDateTime, easier less redundant code
    private LocalDateTime getDateTime(int day, int month, int year, int hour, int minute) {
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    //Gets input from textFields as string
    private String getInput(JComponent c){
        if(c instanceof JTextComponent){
            return ((JTextComponent) c).getText();
        }
        return "";
    }
}



