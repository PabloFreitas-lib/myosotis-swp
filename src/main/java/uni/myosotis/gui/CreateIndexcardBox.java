package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CreateIndexcardBox extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;
    
    private JLabel indexcardLabel;
    private JTextField indexcardBoxName;
    private JScrollPane categoryScrollPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel contentPane;


    private JList<String> indexcardsNamesList;

    private JList<String> categoryNamesList;

    private JList<String> categoryChildNamesList;


    /**
     * Constructor.
     *
     * @param controller The controller.
     */
    public CreateIndexcardBox(Controller controller) {
        this.controller = controller;
        setTitle("Karteikasten erstellen");
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setCategoryScrollPane();

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

    public void setCategoryScrollPane(){
        // Array of all Kategorie
        String[] categoriesNames = controller.getAllCategories().stream().
                map(Category::getCategoryName).toList().toArray(new String[0]);
        categoryNamesList = new JList<>(categoriesNames);
        categoryNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Parents options
        categoryScrollPane.setViewportView(categoryNamesList);
    }
    /**
     * Create a new Category, if the entered Text isn't empty, and close the window.
     */
    private void onOK() {
        final String name = indexcardBoxName.getText();
        final List<String> selectedCategoriesNames = categoryNamesList.getSelectedValuesList();
        final List<Category> selectedCategories = new ArrayList<>();
        for (int i = 0; i < selectedCategoriesNames.size(); i ++){
            selectedCategories.add(controller.getCategoryByName(selectedCategoriesNames.get(i)).get());
        }
        if (!name.isBlank() && !selectedCategoriesNames.isEmpty()) {
            controller.createIndexcardBox(name, selectedCategories);
            controller.setIndexCardPanel();
            controller.setKeywordComboBox();
            controller.setCategoryComboBox();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.", JOptionPane.ERROR_MESSAGE);
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
