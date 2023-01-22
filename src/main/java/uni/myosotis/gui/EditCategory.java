package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EditCategory extends JDialog {
    private JComboBox categoryBoxName;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane indexCardsScrollPane;
    private JPanel contentPane;
    private JTree tree1;
    private final Controller controller;

    private String selectedCategoryName;
    private Optional<Category> selectedCategory;

    private JList<String> indexcardsNamesList;

    /**
     * Creates a new EditCategory-Dialog.
     * @param controller The Controller of the application.
     */
    public EditCategory(Controller controller) {
        this.controller = controller;
        setModal(true);
        setTitle("Kategorie bearbeiten");
        getRootPane().setDefaultButton(buttonOK);
        setContentPane(contentPane);
        //list of all Categories
        List<Category> allCategories = controller.getAllCategories();
        //Array of all Category names
        String[] allCategoriesNames = new String[allCategories.size()];
        for (int i = 0; i < allCategories.size(); i++) {
            allCategoriesNames[i] = allCategories.get(i).getCategoryName();
        }
        //ComboBox with all Category names
        categoryBoxName.setModel(new DefaultComboBoxModel<>(allCategoriesNames));
        //ActionListener for the ComboBox
        categoryBoxName.addActionListener(e -> {
            selectedCategoryName = (String) categoryBoxName.getSelectedItem();
            selectedCategory = controller.getCategoryByName(selectedCategoryName);

            if(selectedCategory.get() != null){
                selectedCategory.get().getIndexcardList();
                List<String> indexcardsNames = selectedCategory.get().getIndexcardList().stream().toList();
                //String[] allIndexcardsNames = selectedCategory.get().getIndexcardList().stream().toList().toArray(new String[0]);
                String[] allIndexcardsNames = controller.getAllIndexcards().stream().
                        map(Indexcard::getName).toList().toArray(new String[0]);
                List<String> allIndexcardsNamesList = controller.getAllIndexcards().stream().
                        map(Indexcard::getName).toList();
                indexcardsNamesList = new JList<>(allIndexcardsNames);
                ArrayList<Integer> indices = new ArrayList<>();
                indexcardsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                for(int i = 0; i < indexcardsNames.size(); i++) {
                    indices.add(allIndexcardsNamesList.indexOf(indexcardsNames.get(i)));
                }
                int[] indicesArray = indices.stream().mapToInt(i->i).toArray();
                indexcardsNamesList.setSelectedIndices(indicesArray);
                indexCardsScrollPane.setViewportView(indexcardsNamesList);
            }
            else {
                String[] indexcardsNames = controller.getAllIndexcards().stream().
                        map(Indexcard::getName).toList().toArray(new String[0]);
                indexcardsNamesList = new JList<>(indexcardsNames);
                indexcardsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                indexCardsScrollPane.setViewportView(indexcardsNamesList);
            }
        });

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
     * When the OK-Button is pressed, the Category is edited.
     */
    private void onOK() {
        //Old Parameters
        if(selectedCategory.isPresent()) {
            controller.editCategory(selectedCategoryName, indexcardsNamesList.getSelectedValuesList());
            dispose();
        }

    }
    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
