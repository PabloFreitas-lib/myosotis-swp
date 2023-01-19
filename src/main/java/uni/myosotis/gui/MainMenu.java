package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JScrollPane IndexcardsPane;
    //private DefaultComboBoxModel<String> keywordModel = new DefaultComboBoxModel<>();
    private JComboBox KeywordComboBox;
    private JLabel VerschlagwortungLabel;
    private JLabel KarteikartenLabel;
    private JButton filternButton;
    private JComboBox CategoryComboBox;
    private JButton filternCategory;
    private JButton filternEntfernenButton;

    private final transient Controller controller;

    /**
     * Creates a new Window of the MainMenu.
     *
     * @param controller The controller.
     */
    public MainMenu(final Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setTitle("Myosotis");
        createMenu();
        setKeywordComboBox();
        setCategoryComboBox();
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
        filternButton.addActionListener(new ActionListener() {
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
    }

    /**
     * Filtern a Keyword. If no Indexcard is select, an error will be displayed.
     */
    private void onFiltern() {
        String keyword2Filtern = (String) KeywordComboBox.getSelectedItem();
        if (keyword2Filtern != null) {
            controller.filterIndexCardPanelByKeyword(keyword2Filtern);
        } else {
            JOptionPane.showMessageDialog(this, "Keine Keyword ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Filtern a Keyword. If no Indexcard is select, an error will be displayed.
     */
    private void onFilternCategory() {
        String category2Filtern = (String) CategoryComboBox.getSelectedItem();
        if (category2Filtern != null) {
            controller.filterIndexCardPanelByCategories(category2Filtern);
        } else {
            JOptionPane.showMessageDialog(this, "Keine Kategorie ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Filtern a Keyword. If no Indexcard is select, an error will be displayed.
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
     * Display all Keyword in the ComboBox.
     */
    public void setKeywordComboBox(){
        //list of all indexcards
        List<Keyword> keywordList = controller.getAllKeywords();
        // Array of all Keywords
        String[] keywordNames = controller.getAllKeywords().stream().
                map(Keyword::getKeywordWord).toList().toArray(new String[0]);
        KeywordComboBox.setModel(new DefaultComboBoxModel<>(keywordNames));
    }

    /**
     * Display all Category in the ComboBox.
     */
    public void setCategoryComboBox(){
        //list of all indexcards
        List<Category> categoriesList = controller.getAllCategories();
        // Array of all Categories
        String[] categoriesNames = controller.getAllCategories().stream().
                map(Category::getCategoryName).toList().toArray(new String[0]);
        CategoryComboBox.setModel(new DefaultComboBoxModel<>(categoriesNames));
    }

    /**
     * Creates the Menu of the MainMenu-Window.
     */
    private void createMenu() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu indexcardMenu = new JMenu("Karteikarte");
        final JMenuItem createIndexcard = new JMenuItem("Erstellen");
        createIndexcard.addActionListener(e -> controller.createIndexcard());
        final JMenuItem editIndexcard = new JMenuItem("Bearbeiten");
        editIndexcard.addActionListener(e -> controller.editIndexcard());
        final JMenuItem deleteIndexcard = new JMenuItem("Entfernen");
        deleteIndexcard.addActionListener(e -> controller.deleteIndexcard());

        final JMenu categoryMenu = new JMenu("Kategorie");
        final JMenuItem createCategory = new JMenuItem("Erstellen");
        createCategory.addActionListener(e -> controller.createCategory());
        final JMenuItem deleteCategory = new JMenuItem("Entfernen");
        deleteCategory.addActionListener(e -> controller.deleteCategory());
        final JMenuItem editCategory = new JMenuItem("Bearbeiten");
        editCategory.addActionListener(e -> controller.editCategory());

        indexcardMenu.add(createIndexcard);
        indexcardMenu.addSeparator();
        indexcardMenu.add(editIndexcard);
        indexcardMenu.addSeparator();
        indexcardMenu.add(deleteIndexcard);

        categoryMenu.add(createCategory);
        categoryMenu.addSeparator();
        categoryMenu.add(editCategory);
        categoryMenu.addSeparator();
        categoryMenu.add(deleteCategory);
        categoryMenu.addSeparator();

        menuBar.add(indexcardMenu);
        menuBar.add(categoryMenu);
        createExampleIndexcards(); //TODO: Remove this line FIXME
        setJMenuBar(menuBar);
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
        final DeleteIndexcard deleteIndexcard = new DeleteIndexcard(controller);
        deleteIndexcard.pack();
        deleteIndexcard.setMinimumSize(deleteIndexcard.getSize());
        deleteIndexcard.setLocationRelativeTo(this);
        deleteIndexcard.setVisible(true);
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

        controller.createIndexcard("Testkarteikarte", "Testfrage", "Testantwort", "TestkeywordGRUPPE1",true);
        controller.createIndexcard("Testkarteikarte2", "Testfrage2", "Testantwort2", "TestkeywordGRUPPE1",true);
        controller.createIndexcard("Testkarteikarte3", "Testfrage3", "Testantwort3", "TestkeywordGRUPPE1",true);
        controller.createIndexcard("Testkarteikarte4", "Testfrage4", "Testantwort4", "TestkeywordGRUPPE1",true);
        controller.createIndexcard("Testkarteikarte5", "Testfrage5", "Testantwort5", "TestkeywordGRUPPE2",true);
        controller.createIndexcard("Testkarteikarte6", "Testfrage6", "Testantwort6", "TestkeywordGRUPPE2",true);
        //controller.createCategory("CategorieTest", controller.getAllIndexcards(),true);
        controller.createIndexcard("Testkarteikarte7", "Testfrage7", "Testantwort7", "TestkeywordGRUPPE3",true);
        controller.createIndexcard("Testkarteikarte8", "Testfrage8", "Testantwort8", "TestkeywordGRUPPE4",true);
    }
}
