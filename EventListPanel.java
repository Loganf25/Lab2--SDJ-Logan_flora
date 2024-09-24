import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;


public class EventListPanel extends JPanel {
    //Needed
    private final ArrayList<Event> events;
    private final EventPanel eventPanel;
    private final JPanel controlPanel;
    private final JPanel displayPanel;
    private final JComboBox sortDropDown;
    private final JCheckBox filterDisplay = new JCheckBox();
    private final JButton addEventButton = new JButton("Add Event");
    //Extras
    private final String[] SORT_OPTIONS = {"NAME (A-Z)", "NAME (Z-A)", "Date (Sooner Events First)", "Date (Later Events First)"};
    private final Map<String, Predicate<Event>> filters;
    private final ArrayList<JCheckBox> filterBoxes;

    public EventListPanel(EventPanel eventPanel) {
        this.eventPanel = eventPanel;
        this.setPreferredSize(new Dimension(1000, 445));
        this.setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        events = new ArrayList<>();

        //Control Panel
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(500, 50));
        controlPanel.setBackground(Color.CYAN);



        //Add Event Button
        addEventButton.setFont(new Font("Arial", Font.BOLD, 20));
        addEventButton.addActionListener(e -> new AddEventModal(this));
        controlPanel.add(addEventButton);

        //JComboBox for Sorting
        sortDropDown = new JComboBox(SORT_OPTIONS);
        sortDropDown.addActionListener(e -> {
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[0]))
                Collections.sort(events);
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[1]))
                events.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()) * -1);
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[2]))
                events.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
            if (Objects.equals(sortDropDown.getSelectedItem(), SORT_OPTIONS[3]))
                events.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()) * -1);
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
    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    public boolean isFiltered(Event event){
        boolean result = false;
        for(JCheckBox filter : filterBoxes){
            if(filter.isSelected()){
                Predicate<Event> pred = filters.get(filter.getText());
                if (pred.test(event))
                    result = true;
            }
        }
        return result;
    }

    public void updateDisplay() {
        displayPanel.removeAll();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        for (Event event : events){
            if(!isFiltered(event)){
                Font labelFont = new Font("Times New Roman", Font.BOLD, 25);
                JLabel newEvent = new JLabel(event.getName());
                newEvent.setFont(labelFont);
                JButton detailsButton = new JButton("Details");
                detailsButton.addActionListener(e -> {eventPanel.displayedEvent(event);});

                //Repositions so not side by side
                JPanel iePanel = new JPanel();
                iePanel.setBackground(Color.PINK);
                iePanel.setLayout(new BoxLayout(iePanel, BoxLayout.X_AXIS));
                iePanel.add(newEvent);
                iePanel.add(detailsButton);
                displayPanel.add(iePanel);
            }
        }
        revalidate();
        repaint();
    }
}
