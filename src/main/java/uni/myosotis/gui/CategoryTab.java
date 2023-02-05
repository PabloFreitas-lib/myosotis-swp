package uni.myosotis.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * The tab for the Categories.
 *
 * @author Johannes Neugebauer
 * @author Pablo Santos
 * @author Kasim Shah
 * @author Aziz Tas
 * @author Omid Valipour
 */
public class CategoryTab extends JFrame {

    private final Controller controller;
    private final Language language;
    private JPanel contentPane;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JList<String> categoryList;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel categoryLabel;

    /**
     * Creates a new CategoryTab.
     *
     * @param controller The controller of the application.
     * @param language   The selected language.
     */
    public CategoryTab(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        setContentPane(contentPane);
        setTitle(language.getName("categoryTitle"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        updateList(controller.getAllCategories());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        //Set Language
        createButton.setText(language.getName("create"));
        editButton.setText(language.getName("edit"));
        deleteButton.setText(language.getName("delete"));
        categoryLabel.setText(language.getName("category"));
        searchButton.setText(language.getName("search"));
        //Set Action Listener
        createButton.addActionListener(e -> onCreate());
        editButton.addActionListener(e -> onEdit());
        deleteButton.addActionListener(e -> onDelete());
        searchButton.addActionListener(e -> onSearch());
    }

    /**
     * Gets executed when the createButton is pressed.
     * Delegates the exercise to display the dialog to create a new Category
     * and updates the displayed list of all Categories.
     */
    private void onCreate() {
        controller.createCategory();
        updateList(controller.getAllCategories());
    }

    /**
     * Gets executed when the editButton is pressed.
     * Delegates the exercise to display the dialog to edit a Category
     * and updates the displayed list of all Indexcards.
     */
    private void onEdit() {
        if (categoryList.getSelectedValue() != null) {
            if (controller.getCategoryByName(categoryList.getSelectedValue()).isPresent()) {
                controller.editCategory(controller.getCategoryByName(categoryList.getSelectedValue()).get());
            }
        } else {
            controller.editCategory();
        }
        updateList(controller.getAllCategories());
    }

    /**
     * Gets executed when the deleteButton is pressed.
     * Delegates the exercise to delete the selected Categories or
     * to display the dialog to delete a Category
     * and updates the displayed list of all Indexcards.
     */
    private void onDelete() {
        if (categoryList.getSelectedValue() != null) {
            for (String categoryName : categoryList.getSelectedValuesList()) {
                if (controller.getCategoryByName(categoryName).isPresent()) {
                    controller.deleteCategory(controller.getCategoryByName(categoryName).get());
                }
            }
        } else {
            controller.deleteCategory();
        }
        updateList(controller.getAllCategories());
    }

    /**
     * Updates the displayed list of Categories.
     *
     * @param categoryList The updated elements of the displayed list.
     */
    private void updateList(List<Category> categoryList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Category category : categoryList) {
            listModel.addElement(category.getCategoryName());
        }
        this.categoryList.setModel(listModel);
    }

    /**
     * Gets executed when the searchButton is pressed.
     * Delegates the exercise to search in the Categories
     * for the selected text.
     */
    private void onSearch() {
        updateList(controller.searchCategory(searchField.getText()));
    }

    /**
     * Gets executed when the cancelButton is pressed.
     * Disposes the displayed CategoryTab.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Returns the contentPane of the CategoryTab.
     *
     * @return The contentPane.
     */
    public JPanel getCategoryPane() {
        return contentPane;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(6, 2, new Insets(10, 10, 10, 10), -1, -1));
        searchField = new JTextField();
        contentPane.add(searchField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        categoryLabel = new JLabel();
        categoryLabel.setHorizontalAlignment(0);
        categoryLabel.setHorizontalTextPosition(0);
        categoryLabel.setText("Kategorien");
        contentPane.add(categoryLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        categoryList = new JList();
        panel1.add(categoryList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        createButton = new JButton();
        createButton.setText("Erstellen");
        contentPane.add(createButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Bearbeiten");
        contentPane.add(editButton, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Entfernen");
        contentPane.add(deleteButton, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchButton = new JButton();
        searchButton.setText("Suchen");
        contentPane.add(searchButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
