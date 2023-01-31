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

public class EditCategory extends JDialog {

    private final Controller controller;
    private JPanel contentPane;
    private JComboBox<String> comboBoxName;
    private JTextField nameTextField;
    private JButton editButton;
    private JButton cancelButton;
    private JTree categoryTree;
    private JList<String> parentList;
    private JList<String> indexcardList;

    /**
     * Creates a new EditCategory-Dialog.
     *
     * @param controller The Controller of the application.
     */
    public EditCategory(Controller controller) {
        this.controller = controller;
        setModal(true);
        setTitle("Kategorie bearbeiten");
        getRootPane().setDefaultButton(editButton);
        setContentPane(contentPane);

        // List of all Categories
        List<Category> categories = controller.getAllCategories();
        // Array of all Category-names
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).getCategoryName();
        }
        //ComboBox with all Category-names
        comboBoxName.setModel(new DefaultComboBoxModel<>(categoryNames));

        updateCategoryTree();
        updateParentList();
        updateIndexcardList();

        // Update infos, if another Category is selected.
        comboBoxName.addActionListener(e -> {
            nameTextField.setText((String) comboBoxName.getSelectedItem());
            updateParentList();
        });

        // TODO
        // Update possible parents with the selected parents and children of the edited Category.
        /*parentList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateParentList();
            }
        });*/

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEdit();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
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
        // All parents of the selected parent-category`s
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
        // All children of the edited Category
        List<String> allOwnChildrenNames = new ArrayList<>();
        if (controller.getCategoryByName((String) comboBoxName.getSelectedItem()).isPresent()) {
            Category categoryToEdit = controller.getCategoryByName((String) comboBoxName.getSelectedItem()).get();
            allOwnChildrenNames.addAll(categoryToEdit.getAllChildren().stream().map(Category::getCategoryName).toList());
        }
        // All Category-names that should be filtered
        List<String> categoryNamesToFilter = new ArrayList<>();
        categoryNamesToFilter.addAll(allParentNames);
        categoryNamesToFilter.addAll(allChildrenNames);
        categoryNamesToFilter.addAll(allOwnChildrenNames);
        categoryNamesToFilter.add((String) comboBoxName.getSelectedItem());
        // Filter parents and children of the selected parent-categories, together with own children
        DefaultListModel<String> newParentList = new DefaultListModel<>();
        newParentList.addAll(controller.getAllCategories().stream()
                .map(Category::getCategoryName)
                .filter(categoryName -> !categoryNamesToFilter.contains(categoryName)).toList());
        parentList.setModel(newParentList);
    }

    /**
     * Updates the list of Indexcards.
     */
    private void updateIndexcardList() {
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        defaultListModel.addAll(controller.getAllIndexcardNames());
        indexcardList.setModel(defaultListModel);
    }

    /**
     * Sets a Category to edit by setting the ComboBox to the Category-name.
     *
     * @param category The Category.
     */
    public void setCategory(Category category){
        comboBoxName.setSelectedItem(category.getCategoryName());
    }

    /**
     * When the Edit-Button is pressed, the Category is edited.
     */
    private void onEdit() {
        String newName = nameTextField.getText();
        if (controller.getAllIndexcardNames().stream().filter(n -> !n.equals(comboBoxName.getSelectedItem())).toList().contains(newName)) {
            JOptionPane.showMessageDialog(this, "Eine andere Kategorie hat bereits diesen Namen.", "Name bereits vergeben", JOptionPane.INFORMATION_MESSAGE);
        } else if (newName.isBlank()) {
            JOptionPane.showMessageDialog(this, "Die Kategorie muss einen Namen haben.", "Name fehlt", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Category oldCategory;
            if (controller.getCategoryByName((String) comboBoxName.getSelectedItem()).isPresent()) {
                oldCategory = controller.getCategoryByName((String) comboBoxName.getSelectedItem()).get();
            } else {
                throw new IllegalStateException("Kategorie zum Bearbeiten existiert nicht!");
            }
            final Long oldId = oldCategory.getId();
            List<Category> selectedParents = new ArrayList<>();
            for (String s : parentList.getSelectedValuesList()) {
                if (controller.getCategoryByName(s).isPresent()) {
                    selectedParents.add(controller.getCategoryByName(s).get());
                }
            }
            List<Indexcard> selectedIndexcards = new ArrayList<>();
            for (String s : indexcardList.getSelectedValuesList()) {
                if (controller.getIndexcardByName(s).isPresent()) {
                    selectedIndexcards.add(controller.getIndexcardByName(s).get());
                }
            }
            controller.editCategory(newName, selectedParents, selectedIndexcards, oldId);
            dispose();
        }
    }

    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        dispose();
    }
}
