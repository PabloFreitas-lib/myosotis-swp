package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;

public class DisplayIndexcard  extends JDialog{
    private final Controller controller;
    private JPanel contentPane;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JButton backButton;
    private JButton nextButton;
    private JButton answeredButton;


    public DisplayIndexcard(Controller controller, Indexcard indexcard) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Karteikarte");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        questionLabel.setText(indexcard.getQuestion());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNext();
            }
        });
        answeredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAnswered(indexcard);
            }
        });
    }

    private void onAnswered(Indexcard indexcard) {
        answerLabel.setText(indexcard.getAnswer());
    }

    private void onNext() {
    }

    private void onBack() {

    }

    private void onCancel() {
        dispose();
    }
}
