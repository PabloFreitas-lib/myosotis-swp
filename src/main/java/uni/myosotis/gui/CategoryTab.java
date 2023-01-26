package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CategoryTab extends JFrame{

    private final Controller controller;
    private JPanel contentPane;
    private JButton createCategoryButton;
    private JButton editCategoryButton;
    private JButton deleteCategoryButton;
    private JList categoryList;
    private JTextField searchField;
    private JButton searchButton;

    public CategoryTab(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Kategorien");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        updateList(controller.getAllCategories());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        createCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreate();
            }
        });
        editCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onEdit();}
        });
        deleteCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onSearch();}
        });
    }

    private void onCreate(){
        controller.createCategory();
        updateList(controller.getAllCategories());
    }

    private void onEdit(){
        controller.editCategory();
        updateList(controller.getAllCategories());
    }

    private void onDelete(){
        controller.deleteCategory();
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
