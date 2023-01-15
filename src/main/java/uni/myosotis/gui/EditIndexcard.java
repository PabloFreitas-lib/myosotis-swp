package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import uni.myosotis.objects.Indexcard;
public class EditIndexcard extends JDialog {

    private final Controller controller;

    private JPanel contentPane;
    private JButton buttonOK, buttonCancel;
    private JRadioButton radioButtonDeleteStatisic;
    private JTextArea textAreaQuestion, textAreaAnswer;
    private JComboBox comboBoxName;
    private JTextArea textAreaKeyword;
    private JLabel Schlagwort;
    private String oldName, oldQuestion, oldAnswer;

    private String oldKeywords;

    /**
     * Creates a new EditIndexcard-Dialog.
     * @param controller The Controller of the application.
     */
    public EditIndexcard(Controller controller) {
        this.controller = controller;
        setModal(true);
        setTitle("Karteikarte bearbeiten");
        getRootPane().setDefaultButton(buttonOK);
        setContentPane(contentPane);
        //list of all indexcards
        List<Indexcard> indexcards = controller.getAllIndexcards();
        //Array of all indexcard names
        String[] indexcardsNames = new String[indexcards.size()];
        for (int i = 0; i < indexcards.size(); i++) {
            indexcardsNames[i] = indexcards.get(i).getName();
        }
        //ComboBox with all indexcard names
        comboBoxName.setModel(new DefaultComboBoxModel<>(indexcardsNames));
        
        //ActionListener for the ComboBox
        comboBoxName.addActionListener(e -> {
            Optional<Indexcard> indexcard = controller.getIndexcardByName((String) comboBoxName.getSelectedItem());
            if(indexcard.isPresent()){
                textAreaQuestion.setText(indexcard.get().getQuestion());
                textAreaAnswer.setText(indexcard.get().getAnswer());
                textAreaKeyword.setText(indexcard.get().getKeywordList());
                oldName = indexcard.get().getName();
                oldQuestion = indexcard.get().getQuestion();
                oldAnswer = indexcard.get().getAnswer();
                oldKeywords = indexcard.get().getKeywordList();
            }
        });
        //Set old values
        textAreaQuestion.setText(oldQuestion);
        textAreaAnswer.setText(oldAnswer);
        textAreaKeyword.setText(oldKeywords);

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
     * When the OK-Button is pressed, the Indexcard is edited.
     */
    private void onOK() {
        final String question = textAreaQuestion.getText();
        final String answer = textAreaAnswer.getText();
        final String keywords = textAreaKeyword.getText();
        final boolean deleteStatistic = radioButtonDeleteStatisic.isSelected();
        if (!oldName.isBlank() && !question.isBlank() && !answer.isBlank() && keywords.isBlank()) {
            controller.editIndexcard(oldName, question, answer, deleteStatistic);
            dispose();
        } else if (!oldName.isBlank() && !question.isBlank() && !answer.isBlank() && !keywords.isBlank()) {
            controller.editIndexcard(oldName, question, answer, deleteStatistic, keywords);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.",
                    JOptionPane.ERROR_MESSAGE);
        }
        controller.setIndexCardPanel();
        controller.setKeywordComboBox();
        dispose();
    }
    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
