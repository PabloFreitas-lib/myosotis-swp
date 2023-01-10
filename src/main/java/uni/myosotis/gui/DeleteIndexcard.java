package uni.myosotis.gui;

import org.h2.index.Index;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;

public class DeleteIndexcard extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;
    private JPanel contentPane;
    private JComboBox comboBoxName;
    private JButton buttonDelete;

    /**
     * Create a new Dialog to delete an Indexcard
     *
     * @param controller The controller.
     */
    public DeleteIndexcard(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        setTitle("Karteikarte löschen");
        getRootPane().setDefaultButton(buttonDelete);

        // Array of all Indexcardnames
        String[] indexcardsNames = controller.getAllIndexcards().stream().
                map(Indexcard::getName).toList().toArray(new String[0]);

        comboBoxName.setModel(new DefaultComboBoxModel<>(indexcardsNames));

        buttonDelete.addActionListener(new ActionListener() {
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
        String indexCardToDelete = (String) comboBoxName.getSelectedItem();
        if (indexCardToDelete != null) {
            controller.deleteIndexcard((String) comboBoxName.getSelectedItem());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Keine Karteikarte ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
