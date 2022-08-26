package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoseColor extends JPanel
{
    String simpleDialogDesc = "Some simple message dialogs";


    public ChoseColor(JFrame frame)
    {
        super(new BorderLayout());

        //Create the components.
        JPanel frequentPanel = createSimpleDialogBox();

        //Lay them out.
        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
        frequentPanel.setBorder(padding);


        add(frequentPanel, BorderLayout.CENTER);
    }
    private JPanel createSimpleDialogBox()
    {
        final int numButtons = 4;
        JRadioButton[] radioButtons = new JRadioButton[numButtons];
        final ButtonGroup group = new ButtonGroup();

        JButton showItButton = null;

        final String RED = "red";
        final String YELLOW = "yellow";
        final String GREEN = "green";
        final String BLUE = "blue";

        radioButtons[0] = new JRadioButton(RED);
        radioButtons[0].setActionCommand(RED);

        radioButtons[1] = new JRadioButton(YELLOW);
        radioButtons[1].setActionCommand(YELLOW);

        radioButtons[2] = new JRadioButton(GREEN);
        radioButtons[2].setActionCommand(GREEN);

        radioButtons[3] = new JRadioButton(BLUE);
        radioButtons[3].setActionCommand(BLUE);

        for (int i = 0; i < numButtons; i++)
            group.add(radioButtons[i]);

        radioButtons[0].setSelected(true);

        showItButton = new JButton("Show it!");

        return createPane(simpleDialogDesc + ":",
                radioButtons,
                showItButton);
    }
    private JPanel createPane(String description,
                              JRadioButton[] radioButtons,
                              JButton showButton) {

        int numChoices = radioButtons.length;
        JPanel box = new JPanel();
        JLabel label = new JLabel(description);

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        box.add(label);

        for (int i = 0; i < numChoices; i++) {
            box.add(radioButtons[i]);
        }

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(showButton, BorderLayout.PAGE_END);
        return pane;
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DialogDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ChoseColor newContentPane = new ChoseColor(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
