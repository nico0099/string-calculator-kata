import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIKata extends JFrame{

    static Logger logger = Logger.getLogger("global");

    private JTextArea editingTextArea;
    private JTextPane textOutput;

    private static final String LABEL = "String Calculator Kata";
    private static final int FRAME_WIDTH = 850;
    private static final int FRAME_HEIGHT = 700;

    private StringCalculator stringCalculator;

    public GUIKata() {

        stringCalculator = new StringCalculator();

        //Label
        add(createLabel(), BorderLayout.NORTH);

        //Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        controlPanel.setLayout(new GridLayout(4, 0));

        //TextInfo
        controlPanel.add(createInfoTextArea());

        //TextArea
        controlPanel.add(createEditingTextArea());

        //Buttons
        controlPanel.add(createButtonsPanel());

        //TextOutput
        controlPanel.add(createTextOutputPanel());

        //Frame
        add(controlPanel, BorderLayout.CENTER);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createLabel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new EmptyBorder(0, 12, 0, 0));
        JLabel label = new JLabel(LABEL);
        label.setFont(new Font("Rockwell", Font.BOLD, 32));
        panel.add(label);
        return panel;
    }

    private JTextPane createTextOutputPanel() {
        textOutput = new JTextPane();
        textOutput.setBackground(null);
        textOutput.setFont(new Font("Rockwell", Font.PLAIN, 82));
        textOutput.setEditable(false);
        textOutput.setBorder(new TitledBorder(new EtchedBorder(), "OUTPUT"));
        StyledDocument doc = textOutput.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        return textOutput;
    }

    private JTextArea createEditingTextArea() {
        editingTextArea = new JTextArea("");
        editingTextArea.setFont(new Font("Helvetica", Font.PLAIN, 20));
        return editingTextArea;
    }

    private JTextArea createInfoTextArea() {
        String text = "Write a set of numbers splitted by comma and the String Calculator Kata calculate the sum.\nYou can:" + "\n" +
                      "1. Write the numbers in different rows." + "\n" +
                      "2. Change the delimiter writing it next the '//' characters on the first row and the numbers in the other rows." + "\n\n" +
                      "ATTENTION: Don't write spaces, negative numbers or numbers greater than a thousand.";
        JTextArea textArea = new JTextArea(text);
        textArea.setFont(new Font("Rockwell", Font.PLAIN, 15));
        textArea.setEditable(false);
        textArea.setBackground(null);
        return textArea;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton buttonGo = new JButton("GO");
        buttonGo.addActionListener(e -> {
            try {
                textOutput.setText("");
                int calculatorResult = stringCalculator.add(editingTextArea.getText());
                if(calculatorResult == -1) {
                    String message = "You must write delimiter between numbers!";
                    logger.log(Level.SEVERE, message);
                    JOptionPane.showMessageDialog(null, message, "INVALID INPUT", JOptionPane.WARNING_MESSAGE);
                } else {
                    textOutput.setText(String.valueOf(calculatorResult));
                }
            } catch (NegativeNumberException ex) {
                logger.log(Level.WARNING, ex.getMessage());
                JOptionPane.showMessageDialog(null,  ex.getMessage(), "ATTENTION", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                String message = "You must digit only numbers!";
                logger.log(Level.SEVERE, message);
                JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonCancel = new JButton("CANCEL");
        buttonCancel.addActionListener(e -> {
            editingTextArea.setText("");
        });
        buttonPanel.add(buttonGo);
        buttonPanel.add(buttonCancel);
        return buttonPanel;
    }
}
