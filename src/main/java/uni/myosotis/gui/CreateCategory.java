package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.*;

public class CreateCategory extends JDialog{
    /**
     * The controller.
     */
    private final Controller controller;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField categoryTextField;
    private JLabel categoryLabel;
    private JPanel contentPane;
    private JCheckBox karteiKartenBox;

    public CreateCategory(Controller controller) {
        this.controller = controller;
        setTitle("Kategorie erstellen");
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
        final String name = categoryTextField.getText();
        if (!name.isBlank()) {
            controller.createCategory(name, controller.getAllIndexcards());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.", JOptionPane.ERROR_MESSAGE);
        }
        controller.setIndexCardPanel();
        controller.setKeywordComboBox();
        controller.setCategoryComboBox();
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
