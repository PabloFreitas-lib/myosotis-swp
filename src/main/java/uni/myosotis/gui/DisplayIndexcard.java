package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayIndexcard extends JFrame {

    private final Controller controller;

    private final Indexcard indexcard;
    private JPanel contentPane;
    private JLabel answerLabel;
    private JLabel questionLabel;

    /**
     * Creates a new Window to display an Indexcard
     */
    public DisplayIndexcard(Controller controller, Indexcard indexcard) {
        this.controller = controller;
        this.indexcard = indexcard;
        setTitle(indexcard.getName());
        setContentPane(contentPane);
        Font font = new Font("Arial", Font.PLAIN, 20);
        answerLabel.setFont(font);
        questionLabel.setFont(font);
        this.questionLabel.setText(indexcard.getQuestion());
        this.answerLabel.setText(indexcard.getAnswer());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
