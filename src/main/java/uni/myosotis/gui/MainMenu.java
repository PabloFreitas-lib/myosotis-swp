package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.List;

public class MainMenu extends JFrame {
    private JTabbedPane tabbedPane;

    private JPanel statistikPane;
    private JPanel categoriePane;

    private JPanel keywordPane;

    private JPanel indexcardPane;
    private JPanel glossarPane;

    //private JPanel filterPane;

    private JPanel settingsPanel;

    private JScrollPane IndexcardsPane;
    //private DefaultComboBoxModel<String> keywordModel = new DefaultComboBoxModel<>();
    private JComboBox KeywordComboBox;
    private JLabel SchlagwortLabel;
    private JLabel KarteikartenLabel;
    private JButton filternKeyword;
    private JComboBox categoryComboBox;
    private JButton filternCategory;
    private JButton filternEntfernenButton;
    private JPanel indexCardBoxPane;

    private final transient Controller controller;

    /**
     * Creates a new Window of the MainMenu.
     *
     * @param controller The controller.
     */
    public MainMenu(final Controller controller) {
        this.controller = controller;
        //Tabs
        tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);
        setTitle("Myosotis");
        createExampleIndexcards(); //TODO: Remove this line FIXME
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        filternKeyword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFiltern();
            }
        });
        filternCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilternCategory();
            }
        });

        filternEntfernenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRemoveFiltern();
            }
        });

        //Add all the Tabs
        Glossar glossar = new Glossar(controller);
        glossarPane = glossar.getGlossarPane();

        IndexcardTab indexcardTab = new IndexcardTab(controller);
        indexcardPane = indexcardTab.getIndexcardPane();

        IndexcardBoxTab indexcardBoxTab = new IndexcardBoxTab(controller);
        indexCardBoxPane = indexcardBoxTab.getIndexcardPane();

        CategoryTab categoryTab = new CategoryTab(controller);
        categoriePane = categoryTab.getCategoryPane();

        settingsPanel = new JPanel();

        tabbedPane.addTab("Glossar", glossarPane);
        tabbedPane.addTab("Kategorien", categoriePane);
        tabbedPane.addTab("Karteikarten", indexcardPane);
        tabbedPane.addTab("Karteikästen",indexCardBoxPane);
        tabbedPane.addTab("Einstellungen", settingsPanel);
        //tabbedPane.addTab("Filter", filterPane);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // Get the index of the selected tab
                int selectedTab = tabbedPane.getSelectedIndex();

                // Call your function here, passing in the selected tab index as a parameter
                if (selectedTab == 0) {
                    glossar.setCategoryComboBox();
                    glossar.setKeywordComboBox();
                    glossar.setGlossar();
                }

            }
        });

    }

    /**
     * Filters on a Keyword. If no keyword is select, an error will be displayed.
     */
    private void onFiltern() {
        String keyword2Filtern = (String) KeywordComboBox.getSelectedItem();
        if (keyword2Filtern != null) {
            controller.filterIndexCardPanelByKeyword(keyword2Filtern);
        } else {
            JOptionPane.showMessageDialog(this, "Kein Keyword ausgewählt.", "Filtern nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Filters on a Category. If no Category is select, an error will be displayed.
     */
    private void onFilternCategory() {
        String category2Filtern = (String) categoryComboBox.getSelectedItem();
        if (category2Filtern != null) {
            controller.filterIndexCardPanelByCategories(category2Filtern);
        } else {
            JOptionPane.showMessageDialog(this, "Keine Kategorie ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes the used filters.
     */
    private void onRemoveFiltern() {
        List<Indexcard> indexcardList = controller.getAllIndexcards();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Indexcard card : indexcardList) {
            listModel.addElement(card.getName());
        }
        JList<String> cardList = new JList<>(listModel);
        getIndexcardsPane().setViewportView(cardList);
    }

    /**
     * Display all Keywords in the ComboBox.
     */
    public void setKeywordComboBox(){
        //list of all indexcards
        List<Keyword> keywordList = controller.getAllKeywords();
        // Array of all Keywords
        String[] keywordNames = controller.getAllKeywords().stream()
                        .map(Keyword::getName).toList().toArray(new String[0]);
        KeywordComboBox.setModel(new DefaultComboBoxModel<>(keywordNames));
    }

    /**
     * Display all Category's in the ComboBox.
     */
    public void setCategoryComboBox(){
        //list of all indexcards
        categoryComboBox.setModel(new DefaultComboBoxModel<>(controller.getAllCategoryNames()));

    }

    /**
     * Close the Window, and end the application.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Displays the Dialog to create a new Indexcard.
     */
    public void displayCreateIndexcard() {
        final CreateIndexcard createIndexcard = new CreateIndexcard(controller);
        createIndexcard.pack();
        createIndexcard.setMinimumSize(createIndexcard.getSize());
        createIndexcard.setSize(400, 300);
        createIndexcard.setLocationRelativeTo(this);
        createIndexcard.setVisible(true);
    }

    /**
     * Displays the Dialog to create a new Indexcard.
     */
    public void displayCreateIndexcardBox() {
        final CreateIndexcardBox createIndexcardBox = new CreateIndexcardBox(controller);
        createIndexcardBox.pack();
        createIndexcardBox.setMinimumSize(createIndexcardBox.getSize());
        createIndexcardBox.setSize(400, 300);
        createIndexcardBox.setLocationRelativeTo(this);
        createIndexcardBox.setVisible(true);
    }

    /**
     * Displays the Dialog to edit a new Indexcard.
     */
    public void displayEditIndexcardBox() {
        final EditIndexcardBox editIndexcardBox = new EditIndexcardBox(controller);
        editIndexcardBox.pack();
        editIndexcardBox.setMinimumSize(editIndexcardBox.getSize());
        editIndexcardBox.setSize(400, 300);
        editIndexcardBox.setLocationRelativeTo(this);
        editIndexcardBox.setVisible(true);
    }
    /**
     * Displays the Dialog to edit an existing Indexcard.
     */
    public void displayEditIndexcard() {
        final EditIndexcard editIndexcard = new EditIndexcard(controller);
        editIndexcard.pack();
        editIndexcard.setMinimumSize(editIndexcard.getSize());
        editIndexcard.setSize(400, 300);
        editIndexcard.setLocationRelativeTo(this);
        editIndexcard.setVisible(true);
    }
    /**
     * Displays the Dialog to create a new Category.
     * @param indexcard The Indexcard which is preset to be edited
     */
    public void displayEditIndexcard(Indexcard indexcard) {
        final EditIndexcard editIndexcard = new EditIndexcard(controller);
        editIndexcard.pack();
        editIndexcard.setMinimumSize(editIndexcard.getSize());
        editIndexcard.setSize(400, 300);
        editIndexcard.setLocationRelativeTo(this);
        editIndexcard.setIndexcard(indexcard);
        editIndexcard.setVisible(true);
    }

    /**
     * Displays the Dialog to delete an Indexcard.
     */
    public void displayDeleteIndexcard() {
        final DeleteIndexcard deleteIndexcard = new DeleteIndexcard(controller);
        deleteIndexcard.pack();
        deleteIndexcard.setMinimumSize(deleteIndexcard.getSize());
        deleteIndexcard.setLocationRelativeTo(this);
        deleteIndexcard.setVisible(true);
    }

    /**
     * Displays the Dialog to delete an Indexcard.
     */
    public void displayDeleteIndexcardBox() {
        final DeleteIndexcardBox deleteIndexcardBox = new DeleteIndexcardBox(controller);
        deleteIndexcardBox.pack();
        deleteIndexcardBox.setMinimumSize(deleteIndexcardBox.getSize());
        deleteIndexcardBox.setLocationRelativeTo(this);
        deleteIndexcardBox.setVisible(true);
    }

    /**
     * Displays the Dialog to create a new Indexcard.
     */
    public void displayCreateCategory() {
        final CreateCategory createCategory = new CreateCategory(controller);
        createCategory.pack();
        createCategory.setMinimumSize(createCategory.getSize());
        createCategory.setSize(400, 300);
        createCategory.setLocationRelativeTo(this);
        createCategory.setVisible(true);
    }

    /**
     * Displays the Dialog to edit an existing Indexcard.
     */
    public void displayEditCategory() {
        final EditCategory editCategory = new EditCategory(controller);
        editCategory.pack();
        editCategory.setMinimumSize(editCategory.getSize());
        editCategory.setSize(400, 300);
        editCategory.setLocationRelativeTo(this);
        editCategory.setVisible(true);
    }

    /**
     * Displays the Dialog to delete an Indexcard.
     */
    public void displayDeleteCategory() {
        final DeleteCategory deleteCategory = new DeleteCategory(controller);
        deleteCategory.pack();
        deleteCategory.setMinimumSize(deleteCategory.getSize());
        deleteCategory.setLocationRelativeTo(this);
        deleteCategory.setVisible(true);
    }

    /**
     * Displays the Menu for learning.
     */
    public void displayLearning(LearnSystem learnsystem, IndexcardBox indexCardBox){
        final DisplayIndexcard displayIndexcard = new DisplayIndexcard(controller, learnsystem, indexCardBox);
        displayIndexcard.pack();
        displayIndexcard.setMinimumSize(displayIndexcard.getSize());
        displayIndexcard.setLocationRelativeTo(this);
        displayIndexcard.setVisible(true);
    }

    public JScrollPane getIndexcardsPane(){
        return this.IndexcardsPane;
    }

    public JComboBox getKeywordComboBox(){
        return this.KeywordComboBox;
    }

    public void setKeywordComboBox(JComboBox keywordComboBox) {
        this.KeywordComboBox = keywordComboBox;
    }

    /**
     * Creates a ExampleMenu for Testing and Development (This is used without any notification).
     */
    public void createExampleIndexcards(){
        controller.createIndexcard("ITK1", "Was ist ein Computer?", "Ein Computer ist ein Gerät, das Daten verarbeitet.", List.of(new String[]{"#IT"}));
        controller.createIndexcard("ITK2", "Was ist ein Programm?", "Ein Programm ist eine Anweisung für einen Computer.", List.of(new String[]{"#IT"}));
        controller.createIndexcard("ITK3", "Was ist ein Programmierer?", "Ein Programmierer ist eine Person, die Programme schreibt.", List.of(new String[]{"#IT"}));
        controller.createCategory("IT", List.of(new String[]{"ITK1","ITK2","ITK3"}));
        controller.createIndexcardBox("ITBox",controller.getCategoriesByCategoryNameList(List.of(new String[]{"IT"})));
        controller.createIndexcard("BiologieK1", "Was ist ein Organismus?", "Ein Organismus ist ein Lebewesen.", List.of(new String[]{"#Biologie"}));
        controller.createIndexcard("BiologieK2", "Was ist ein Lebewesen?", "Ein Lebewesen ist ein Organismus.", List.of(new String[]{"#Biologie"}));
        controller.createIndexcard("BiologieK3", "Was ist ein Tier?", "Ein Tier ist ein Lebewesen.", List.of(new String[]{"#Biologie"}));
        controller.createCategory("Biologie", List.of(new String[]{"BiologieK1","BiologieK2","BiologieK3"}));
        controller.createIndexcardBox("BiologieBox",controller.getCategoriesByCategoryNameList(List.of(new String[]{"Biologie"})));
        controller.createIndexcard("PhysikK1", "Was ist ein Atom?", "Ein Atom ist ein kleinstes Teilchen, das aus einem Kern und Elektronen besteht.", List.of(new String[]{"#Physik"}));
        controller.createIndexcard("PhysikK2", "Was ist ein Kern?", "Ein Kern ist ein Teilchen, das aus Protonen und Neutronen besteht.", List.of(new String[]{"#Physik"}));
        controller.createIndexcard("PhysikK3", "Was ist ein Elektron?", "Ein Elektron ist ein Teilchen, das aus einem negativen Ladungsträger besteht.", List.of(new String[]{"#Physik"}));
        controller.createCategory("Physik", List.of(new String[]{"PhysikK1","PhysikK2","PhysikK3"}));
        controller.createIndexcardBox("PhysikBox",controller.getCategoriesByCategoryNameList(List.of(new String[]{"Physik"})));
        controller.createIndexcardBox("WissenschaftBox",controller.getAllCategories());
    }

}
