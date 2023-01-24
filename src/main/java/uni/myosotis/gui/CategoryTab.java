package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CategoryTab extends JFrame{

    private final Controller controller;
    private JPanel contentPane;
    private JButton createCategoryButton;
    private JButton editCategoryButton;
    private JButton deleteCategoryButton;

    public CategoryTab(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Kategorien");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        createCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createCategory();
            }
        });
        editCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.editCategory();
            }
        });
        deleteCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.deleteCategory();
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JPanel getCategoryPane() {
        return contentPane;
    }
}
