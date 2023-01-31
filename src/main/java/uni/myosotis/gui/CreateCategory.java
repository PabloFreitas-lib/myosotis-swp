package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreateCategory extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JButton createButton;
    private JButton cancelButton;
    private JTree categoryTree;
    private JList<String> parentList;
    private JList<String> indexcardList;

    /**
     * Creates the Dialog to create a new Category.
     *
     * @param controller The controller.
     */
    public CreateCategory(Controller controller) {
        this.controller = controller;
        setTitle("Kategorie erstellen");
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(createButton);

        updateCategoryTree();
        updateParentList();
        updateIndexcardList();

        // Update possible parents with the selected parents.
        parentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateParentList();
            }
        });

        // call onCreate() when the createButton is clicked.
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCreate();
            }
        });

        // call onCancel() when the cancelButton is clicked.
        cancelButton.addActionListener(new ActionListener() {
            @Override
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
     * Updates the tree of the current poly-hierarchy of the Category`s.
     */
    private void updateCategoryTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Kategorien");
        for (Category category : controller.getAllCategories()) {
            if (controller.getParentCategories(category).isEmpty()) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(category.getCategoryName());
                addNodes(node);
                root.add(node);
            }
        }
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
        categoryTree.setModel(defaultTreeModel);
    }

    /**
     * Adds the nodes to the categoryTree.
     */
    private void addNodes(DefaultMutableTreeNode node) {
        if (controller.getCategoryByName(String.valueOf(node.getUserObject())).isPresent()) {
            Category category = controller.getCategoryByName(String.valueOf(node.getUserObject())).get();
            for (Category child : category.getChildren()) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child.getCategoryName());
                addNodes(childNode);
                node.add(childNode);
            }
        }
    }

    /**
     * Updates the lists of possible parents of the Category.
     */
    private void updateParentList() {
        // All parents of the selected parent-categories
        List<Category> selectedParents = new ArrayList<>();
        for (String s : parentList.getSelectedValuesList()) {
            if (controller.getCategoryByName(s).isPresent()) {
                selectedParents.add(controller.getCategoryByName(s).get());
            }
        }
        List<String> allParentNames = new ArrayList<>();
        for (Category parent : selectedParents) {
            allParentNames.addAll(controller.getAllParentCategories(parent).stream().map(Category::getCategoryName).toList());
        }
        // All children of the selected parent-categories
        List<String> allChildrenNames = new ArrayList<>();
        for (Category parent : selectedParents) {
            allChildrenNames.addAll(parent.getAllChildren().stream().map(Category::getCategoryName).toList());
        }
        // All Category-names that should be filtered
        List<String> categoryNamesToFilter = new ArrayList<>();
        categoryNamesToFilter.addAll(allParentNames);
        categoryNamesToFilter.addAll(allChildrenNames);
        // Filter parents and children of the selected parent-categories
        DefaultListModel newParentList = new DefaultListModel();
        newParentList.addAll(controller.getAllCategories().stream()
                .filter(category -> !categoryNamesToFilter.contains(category.getCategoryName()))
                .map(Category::getCategoryName).toList());
        parentList.setModel(newParentList);
    }

    /**
     * Updates the list of Indexcards.
     */
    private void updateIndexcardList() {
        DefaultListModel defaultListModel = new DefaultListModel<>();
        defaultListModel.addAll(controller.getAllIndexcardNames());
        indexcardList.setModel(defaultListModel);
    }

    /**
     * Create a new Category, if the entered Text isn't empty, and close the window.
     */
    private void onCreate() {
        String name = nameTextField.getText();
        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Die Kategorie muss einen Namen haben.", "Name fehlt", JOptionPane.INFORMATION_MESSAGE);
        } else if (controller.getAllCategories().stream().map(Category::getCategoryName).toList().contains(name)) {
            JOptionPane.showMessageDialog(this, "Eine andere Kategorie hat bereits diesen Namen.", "Name bereits vergeben", JOptionPane.INFORMATION_MESSAGE);
        } else {
            List<Category> selectedParents = new ArrayList<>();
            for (Object o : parentList.getSelectedValuesList()) {
                if (controller.getCategoryByName((String) o).isPresent()) {
                    selectedParents.add(controller.getCategoryByName((String) o).get());
                }
            }
            List<Indexcard> selectedIndexcards = new ArrayList<>();
            for (Object o : indexcardList.getSelectedValuesList()) {
                if (controller.getIndexcardByName((String) o).isPresent()) {
                    selectedIndexcards.add(controller.getIndexcardByName((String) o).get());
                }
            }
            controller.createCategory(name, selectedParents, selectedIndexcards);
        }
    }

    /**
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
