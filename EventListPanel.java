import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;


public class EventListPanel extends JPanel {
    //Needed
    private final ArrayList<Event> events;
    private final JPanel controlPanel;
    private final JPanel displayPanel;
    private final JComboBox sortDropDown;
    private final JCheckBox filterDisplay = new JCheckBox();
    private final JButton addEventButton = new JButton("Add Event");
    //Extras
    private final String[] SORT_OPTIONS = {"NAME (A-Z)", "NAME (Z-A)", "Date (Sooner Events First)", "Date (Later Events First)"};
    private final Map<String, Predicate<Event>> filters;
    private final ArrayList<JCheckBox> filterBoxes;

    public EventListPanel() {
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.WHITE);
        events = new ArrayList<>();

        //Control Panel
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(200, 100));

        //Add Event Button
        addEventButton.setFont(new Font("Arial", Font.BOLD, 20));
        addEventButton.addActionListener(e -> {
            new AddEventModal(this);
        });
        controlPanel.add(addEventButton);

        //JComboBox for Sorting
        sortDropDown = new JComboBox(SORT_OPTIONS);
        sortDropDown.addActionListener(e -> {
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[0]))
                Collections.sort(events);
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[1]))
                events.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()) * -1);
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[2]))
                events.sort((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
            if (sortDropDown.getSelectedItem().equals(SORT_OPTIONS[3]))
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
            box.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateDisplay();
                }
            });
            filterBoxes.add(box);
        }

        add(controlPanel, BorderLayout.NORTH);

        //DisplayPanel
        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(200, 100));
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
        for(Event event : events){
            if(!isFiltered(event)){
                EventPanel newEP = new EventPanel();
                newEP.displayedEvent(event);
                displayPanel.add(newEP);
            }
        }
        revalidate();
        repaint();
    }
}
