package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CategoryTab extends JFrame{

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

    private void onCreate() {
        controller.createCategory();
        updateList(controller.getAllCategories());
    }

    private void onEdit(){
        if (categoryList.getSelectedValue() != null) {
            if (controller.getCategoryByName(categoryList.getSelectedValue()).isPresent()) {
                controller.editCategory(controller.getCategoryByName(categoryList.getSelectedValue()).get());
            }
        }
        else {
            controller.editCategory();
        }
        updateList(controller.getAllCategories());
    }

    /**
     * Deletes the selected Categories or delegate the exercise to display the dialog to delete a Category.
     */
    private void onDelete(){
        if (categoryList.getSelectedValue() != null) {
            for (String categoryName : categoryList.getSelectedValuesList()) {
                if (controller.getCategoryByName(categoryName).isPresent()) {
                    controller.deleteCategory(controller.getCategoryByName(categoryName).get());
                }
            }
        }
        else {
            controller.deleteIndexcard();
        }
        updateList(controller.getAllCategories());
    }

    private void updateList(List<Category> categoryList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Category category : categoryList) {
            listModel.addElement(category.getCategoryName());
        }
        this.categoryList.setModel(listModel);
    }

    private void onSearch(){
        updateList(controller.searchCategory(searchField.getText()));
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JPanel getCategoryPane() {
        return contentPane;
    }
}
