import java.util.*;
import javax.swing.*;
import java.awt.*;


public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private final JPanel controlPanel = new JPanel();
    private final JPanel displayPanel = new JPanel();
    //private final JComboBox sortDropDown = new JComboBox((ComboBoxModel) events);
    private final JCheckBox filterDisplay = new JCheckBox("Filter Display");
    private final JButton addEventButton = new JButton("Add Event");

    public EventListPanel() {
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.WHITE);
    }
}
