package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

public class EditCategory extends JDialog {
    private JComboBox categoryBoxName;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane indexCardsScrollPane;
    private JPanel contentPane;
    private final Controller controller;

    private String oldCategoryName;
    private Category selectedCategory;

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
            List<Category> allCategoriesList = controller.getAllCategories();
            String categorySelectedName = (String) categoryBoxName.getSelectedItem();
            for(int i = 0; i < allCategoriesList.size() ; i++){
                if(Objects.equals(allCategoriesList.get(i).getCategoryName(), categorySelectedName)){
                    selectedCategory = allCategoriesList.get(i);
                }
            }
            //selectedCategory = controller.getCategoryByName((String) categoryBoxName.getSelectedItem());
            if(selectedCategory != null){
                // Set the indexCardsScrollPane with the Indexcards inside the Category.
                //oldCategoryName = selectedCategory.get().getCategoryName();
                //String[] indexcardsNames = controller.getAllIndexcards().stream().
                  //      map(Indexcard::getName).toList().toArray(new String[0]);

                //String[] indexcardsNamesCategory = selectedCategory.getIndexcardList().toArray(new String[0]);
                //indexcardsNamesList = new JList<>(indexcardsNamesCategory);
                //indexcardsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                //indexcardsNamesList.setSelectedValue(indexcardsNamesCategory,true);
                //indexCardsScrollPane.setViewportView(indexcardsNamesList);
            }
            else {
                String[] indexcardsNames = controller.getAllIndexcards().stream().
                        map(Indexcard::getName).toList().toArray(new String[0]);
                indexcardsNamesList = new JList<>(indexcardsNames);
                indexcardsNamesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                indexCardsScrollPane.setViewportView(indexcardsNamesList);
            }
        });
        //Set old values

        //textAreaQuestion.setText(oldQuestion);
        //textAreaAnswer.setText(oldAnswer);
        //textAreaKeyword.setText(oldKeywords);

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
        //final Category oldCategory = controller.getCategoryByName(oldCategoryName).get();
        //Keyword oldKeyword = oldCategory.getKeyword();
        //final Long oldCategoryId = oldCategory.getId();


    }
    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
