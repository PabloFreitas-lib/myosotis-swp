package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.*;

public class CreateIndexcard extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextArea textAreaQuestion;
    private JTextArea textAreaAnswer;
    private JTextArea textAreaKeyword;


    /**
     * Create a new Dialog to create an Indexcard.
     *
     * @param controller The controller.
     */
    public CreateIndexcard(Controller controller) {
        this.controller = controller;
        setTitle("Karteikarte erstellen");
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
     * Create a new Indexcard, if the entered Text isn't empty, and close the window.
     */
    private void onOK() {
        final String name = textFieldName.getText();
        final String question = textAreaQuestion.getText();
        final String answer = textAreaAnswer.getText();
        final String keyword = textAreaKeyword.getText();
        if (!name.isBlank() && !question.isBlank() && !answer.isBlank() && keyword.isBlank()) {
            controller.createIndexcard(name, question, answer);
            dispose();
        } else if (!name.isBlank() && !question.isBlank() && !answer.isBlank() && !keyword.isBlank()) {
            controller.createIndexcard(name, question, answer, keyword);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.", JOptionPane.ERROR_MESSAGE);
        }
        controller.setIndexCardPanel();
        controller.setKeywordComboBox();
        dispose();


    }

    /**
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
