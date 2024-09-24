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
            //Switch to distinguish Event(0), Meeting(1), and Deadline(2)
            switch (eventTypeComboBox.getSelectedIndex()){
                case 0: {
                    //Less Redundancy
                    getBasics();
                    break;
                }
                case 1: {
                    getBasics();
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
    private void getBasics() {
        attributes.add(new Attribute("Name", new JTextField(10)));
        attributes.add(new Attribute("Day", new JTextField(10)));
        attributes.add(new Attribute("Month", new JTextField(10)));
        attributes.add(new Attribute("Year", new JTextField(10)));
        attributes.add(new Attribute("Hour", new JTextField(10)));
        attributes.add(new Attribute("Minute", new JTextField(10)));
    }

    private ActionListener getEventCreator() {
        return e -> {
            Event newEvent = null;
            switch (eventTypeComboBox.getSelectedIndex()){
                case 0: {
                    newEvent = new Event(getInput(attributes.getFirst().value), 
                            getDateTime(Integer.parseInt(getInput(attributes.get(1).value)), 
                            Integer.parseInt(getInput(attributes.get(2).value)), 
                            Integer.parseInt(getInput(attributes.get(3).value)), 
                            Integer.parseInt(getInput(attributes.get(4).value)), 
                            Integer.parseInt(getInput(attributes.get(5).value))));
                    break;
                }
                case 1: {
                    newEvent = new Meeting(getInput(attributes.getFirst().value),
                            getDateTime(Integer.parseInt(getInput(attributes.get(1).value)),
                                    Integer.parseInt(getInput(attributes.get(2).value)),
                                    Integer.parseInt(getInput(attributes.get(3).value)),
                                    Integer.parseInt(getInput(attributes.get(4).value)),
                                    Integer.parseInt(getInput(attributes.get(5).value))), getDateTime(Integer.parseInt(getInput(attributes.get(1).value)),
                                    Integer.parseInt(getInput(attributes.get(6).value)),
                                    Integer.parseInt(getInput(attributes.get(7).value)),
                                    Integer.parseInt(getInput(attributes.get(8).value)),
                                    Integer.parseInt(getInput(attributes.get(9).value))), 
                            getInput(attributes.get(10).value));
                    break;
                }
                case 2: {
                    newEvent = new Deadline(getInput(attributes.getFirst().value),
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

    private LocalDateTime getDateTime(int day, int month, int year, int hour, int minute) {
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private String getInput(JComponent c){
        if(c instanceof JTextComponent){
            return ((JTextComponent) c).getText();
        }
        return "";
    }
}



