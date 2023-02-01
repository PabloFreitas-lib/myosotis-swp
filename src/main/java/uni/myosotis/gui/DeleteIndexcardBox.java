package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.IndexcardBox;

import javax.swing.*;
import java.awt.event.*;

public class DeleteIndexcardBox extends JDialog{
    /**
     * The controller.
     */
    private final Controller controller;
    private final Language language;

    private JButton deleteButton;

    private JPanel contentPane;
    private JComboBox comboBoxName;
    private JLabel whichIndexcardBox;


    /**
     * Create a new Dialog to delete an Indexcard
     *
     * @param controller The controller.
     */
    public DeleteIndexcardBox(Controller controller, Language language){
        this.controller = controller;
        this.language = language;
        setContentPane(contentPane);
        setModal(true);
        setTitle(language.getName("deleteIndexcardBoxTitle"));
        getRootPane().setDefaultButton(deleteButton);
        // Set Language
        deleteButton.setText(language.getName("delete"));
        whichIndexcardBox.setText(language.getName("whichIndexcardBox"));

        String[] indexcardBoxesNames = controller.getAllIndexcardBoxes().stream().
                map(IndexcardBox::getName).toList().toArray(new String[0]);

        comboBoxName.setModel(new DefaultComboBoxModel<>(indexcardBoxesNames));

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
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
     * Deletes an Indexcard. If no Indexcard is select, an error will be displayed.
     */
    private void onDelete() {
        String indexcardBoxToDelete = (String) comboBoxName.getSelectedItem();
        if (indexcardBoxToDelete != null) {
            controller.deleteIndexcardBox(controller.getIndexcardBoxByName(indexcardBoxToDelete).get().getName());
            //controller.setIndexCardPanel();
            //controller.setKeywordComboBox();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, language.getName("noIndexcardBoxSelectedError"), language.getName("deletionNotPossibleError"), JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
