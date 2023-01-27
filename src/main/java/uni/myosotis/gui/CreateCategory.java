package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

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
    private JScrollPane indexCardsScrollPane;
    private JScrollPane parentCategoryScrollPane;

    private JList<String> indexcardsNamesList;

    private JList<String> categoryParentNamesList;



    /**
     * Constructor.
     *
     * @param controller The controller.
     */
    public CreateCategory(Controller controller) {
        this.controller = controller;
        setTitle("Kategorie erstellen");
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        String[] indexcardsNames = controller.getAllIndexcards().stream().
                map(Indexcard::getName).toList().toArray(new String[0]);
        // Multiple selection
        indexcardsNamesList = new JList<>(indexcardsNames);
        indexcardsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        indexCardsScrollPane.setViewportView(indexcardsNamesList);

        // Array of all Kategorie
        String[] categoriesNames = controller.getAllCategories().stream().
                map(Category::getCategoryName).toList().toArray(new String[0]);
        categoryParentNamesList = new JList<>(categoriesNames);
        categoryParentNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Parents options
        parentCategoryScrollPane.setViewportView(categoryParentNamesList);

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
     * Create a new Category, if the entered Text isn't empty, and close the window.
     */
    private void onOK() {
        final String name = categoryTextField.getText();
        final List<String> indexcardsSelectedItems = indexcardsNamesList.getSelectedValuesList();
        final List<String> categoryParentSelectedItems = categoryParentNamesList.getSelectedValuesList();
        if (!name.isBlank() && !indexcardsSelectedItems.isEmpty()){
            controller.createCategory(name, indexcardsSelectedItems);
            controller.setIndexCardPanel();
            controller.setKeywordComboBox();
            controller.setCategoryComboBox();
            dispose();
        }else if (!name.isBlank() && !indexcardsSelectedItems.isEmpty() && !categoryParentSelectedItems.isEmpty()){
            // TODO just for testing
            Category parent = controller.getCategoryByName(categoryParentSelectedItems.get(0)).get();
            controller.createCategory(name, indexcardsSelectedItems,parent);
            controller.setIndexCardPanel();
            controller.setKeywordComboBox();
            controller.setCategoryComboBox();
        }
        else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Kategorie nicht erstellt.", JOptionPane.ERROR_MESSAGE);
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
