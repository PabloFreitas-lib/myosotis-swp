package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Keyword;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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
        String[] keywordStrings = textAreaKeyword.getText()
                .replaceAll(" ", "")
                .split("#");

        List<String> keywords = new ArrayList<>();
        for (String keyword : keywordStrings) {
            if (!keyword.isBlank()) {
                keywords.add(keyword);
            }
        }

        if (!name.isBlank() && !question.isBlank() && !answer.isBlank()) {
            controller.createIndexcard(name, question, answer, keywords);
            controller.setIndexCardPanel();
            controller.setKeywordComboBox();
            controller.setCategoryComboBox();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
