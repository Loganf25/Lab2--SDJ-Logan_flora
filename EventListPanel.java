import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;


public class EventListPanel extends JPanel {
    //Needed Initialization 
    private final ArrayList<Event> events;
    private final EventPanel eventPanel;
    private final JPanel displayPanel;
    private final JComboBox<String> sortDropDown;
    //Extras Initializations
    private final String[] SORT_OPTIONS = {"NAME (A-Z)", "NAME (Z-A)", "Date (Sooner Events First)", "Date (Later Events First)"};
    private final Map<String, Predicate<Event>> filters;
    private final ArrayList<JCheckBox> filterBoxes;

    public EventListPanel(EventPanel eventPanel) {
        //Also Initialization
        this.eventPanel = eventPanel;
        this.setPreferredSize(new Dimension(1000, 445));
        this.setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        events = new ArrayList<>();

        //Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(500, 50));
        controlPanel.setBackground(Color.CYAN);



        //Add Event Button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.setFont(new Font("Arial", Font.BOLD, 20));
        addEventButton.addActionListener(e -> new AddEventModal(this));
        controlPanel.add(addEventButton);

        //JComboBox for Sorting
        sortDropDown = new JComboBox<>(SORT_OPTIONS);
        sortDropDown.addActionListener(e -> {
            //Four options to sort by are made here
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[0]))
                events.sort(Comparator.comparing(Event::getName));
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[1]))
                events.sort(Comparator.comparing(Event::getName).reversed());
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[2]))
                events.sort(Comparator.comparing(Event::getDateTime));
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[3]))
                events.sort(Comparator.comparing(Event::getDateTime).reversed());
            updateDisplay();
        });
        controlPanel.add(sortDropDown);

        //JCheckBox for Filtering
        //Initialization
        filters = new HashMap<>();
        //Add pre-made filters to list
        filters.putAll(EventFilter.getEventFilters());
        filterBoxes = new ArrayList<>();
        for(String filter : filters.keySet()){
            JCheckBox box = new JCheckBox(filter);
            box.addItemListener(e -> updateDisplay());
            filterBoxes.add(box);
        }

        //Add individual checkboxes to controlPanel
        for(JCheckBox filter : filterBoxes)
            controlPanel.add(filter);
        add(controlPanel, BorderLayout.NORTH);

        //DisplayPanel
        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(200, 300));
        displayPanel.setBackground(Color.PINK);
        //Need to display the list of current Events available/added

        add(displayPanel, BorderLayout.SOUTH);
    }

    //Helper Functions
    //Adds event to list and then to GUI 
    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    //Filter helper, checks if an event needs to be filtered
    public boolean isFiltered(Event event){
        boolean result = false;
        for(JCheckBox filter : filterBoxes){
            if(filter.isSelected()){
                Predicate<Event> pred = filters.get(filter.getText());
                if (!pred.test(event))
                    result = true;
            }
        }
        return result;
    }

    //This displays each new event added by the AddEventModal to the GUI
    //And a corresponding button to get the details of an event displayed to the EventPanel
    public void updateDisplay() {
        displayPanel.removeAll();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        //Displays events that are not currently being filtered
        for (Event event : events){
            if(!isFiltered(event)){
                Font labelFont = new Font("Times New Roman", Font.BOLD, 25);
                JPanel iePanel = getPanel(event, labelFont);
                displayPanel.add(iePanel);
            }
        }
        revalidate();
        repaint();
    }

    //IDE though it would be better to have separate function from one above
    private JPanel getPanel(Event event, Font labelFont) {
        //Creates label and button for each event
        JLabel newEvent = new JLabel(event.getName());
        newEvent.setFont(labelFont);
        JButton detailsButton = new JButton("Details");
        //Anonymous method to display event to eventPanel
        detailsButton.addActionListener(e -> eventPanel.displayedEvent(event));

        //Repositions so not side by side
        JPanel iePanel = new JPanel();
        iePanel.setBackground(Color.PINK);
        iePanel.setLayout(new BoxLayout(iePanel, BoxLayout.X_AXIS));
        iePanel.add(newEvent);
        iePanel.add(detailsButton);
        return iePanel;
    }
}
