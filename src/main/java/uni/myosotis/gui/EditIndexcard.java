package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.logic.IndexcardLogic;
public class EditIndexcard extends JDialog {

    private final Controller controller;

    private JPanel contentPane;
    private JButton buttonOK, buttonCancel;
    private JRadioButton radioButtonDeleteStatisic;
    private JTextArea textAreaQuestion, textAreaAnswer;
    private JComboBox comboBoxName;
    private String oldName, oldQuestion, oldAnswer;

    /**
     * Creates a new EditIndexcard-Dialog.
     * @param controller The Controller of the application.
     * @param indexcard The Indexcard to edit.
     */
    public EditIndexcard(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //list of all indexcards
        List<Indexcard> indexcards = controller.getIndexcardLogic().findAllIndexcards();
        //Array of all indexcard names
        String[] indexcardsNames = new String[indexcards.size()];
        for (int i = 0; i < indexcards.size(); i++) {
            indexcardsNames[i] = indexcards.get(i).getName();
        }
        //ComboBox with all indexcard names
        comboBoxName.setModel(new DefaultComboBoxModel<>(indexcardsNames));
        //ActionListener for the ComboBox
        comboBoxName.addActionListener(e -> {
            Optional<Indexcard> indexcard = controller.getIndexcardLogic().findIndexcard((String) comboBoxName.getSelectedItem());
            if(indexcard.isPresent()){
                textAreaQuestion.setText(indexcard.get().getQuestion());
                textAreaAnswer.setText(indexcard.get().getAnswer());
                oldName = indexcard.get().getName();
                oldQuestion = indexcard.get().getQuestion();
                oldAnswer = indexcard.get().getAnswer();
            }
        });
        //Set old values
        textAreaQuestion.setText(oldQuestion);
        textAreaAnswer.setText(oldAnswer);

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
        final boolean deleteStatistic = radioButtonDeleteStatisic.isSelected();
        if (!oldName.isBlank() && !question.isBlank() && !answer.isBlank()
                && !deleteStatistic) {
            controller.editIndexcard(oldName, question, answer);
            dispose();
        }
        //if the user wants to delete the statistic, the controller has to delete the statistic
        else if(!oldName.isBlank() && !question.isBlank() && !answer.isBlank()
                && deleteStatistic) {
            controller.deleteIndexcard();
            //TODO: Controller muss noch Methode zum Löschen der Statistik implementieren
            controller.createIndexcard(oldName, question, answer);
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
