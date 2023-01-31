package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;

import javax.swing.*;
import java.awt.event.*;

public class DeleteCategory extends JDialog{
    /**
     * The controller.
     */

    private final Controller controller;

    private JPanel contentPane;
    private JComboBox<String> categoryNamesComboBox;
    private JButton buttonDelete;

    /**
     * Constructor.
     * @param controller Controller.
     */

    public DeleteCategory(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        setTitle("Kategorie löschen");
        getRootPane().setDefaultButton(buttonDelete);

        // Array of all Kategorie
        String[] categoriesNames = controller.getAllCategories().stream().
                map(Category::getCategoryName).toList().toArray(new String[0]);

        categoryNamesComboBox.setModel(new DefaultComboBoxModel<>(categoriesNames));

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
     * Deletes a Category. If no Category is select, an error will be displayed.
     */
    private void onDelete() {
        String categoryToDelete = (String) categoryNamesComboBox.getSelectedItem();
        if (categoryToDelete != null) {
            if (controller.getCategoryByName(categoryToDelete).isPresent()) {
                controller.deleteCategory(controller.getCategoryByName(categoryToDelete).get());
                controller.setCategoryComboBox();
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Keine Kategorie ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
