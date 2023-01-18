package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
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
    }

    /**
     * Filtern a Keyword. If no Indexcard is select, an error will be displayed.
     */
    private void onFiltern() {
        String keyword2Filtern = (String) KeywordComboBox.getSelectedItem();
        if (keyword2Filtern != null) {
            controller.filterIndexCardPanel(keyword2Filtern);
        } else {
            JOptionPane.showMessageDialog(this, "Keine Keyword ausgewählt.", "Löschen nicht möglich", JOptionPane.ERROR_MESSAGE);
        }
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
        indexcardMenu.add(createIndexcard);
        indexcardMenu.addSeparator();
        indexcardMenu.add(editIndexcard);
        indexcardMenu.addSeparator();
        indexcardMenu.add(deleteIndexcard);
        menuBar.add(indexcardMenu);
        createExampleIndexcards(); //TODO: Remove this line
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
     * Creates a ExampleMenu for Testing and Development.
     */
    public void createExampleIndexcards(){
        controller.createIndexcard("Testkarteikarte", "Testfrage", "Testantwort", "TestkeywordGRUPPE1");
        controller.createIndexcard("Testkarteikarte2", "Testfrage2", "Testantwort2", "TestkeywordGRUPPE1");
        controller.createIndexcard("Testkarteikarte3", "Testfrage3", "Testantwort3", "TestkeywordGRUPPE1");
        controller.createIndexcard("Testkarteikarte4", "Testfrage4", "Testantwort4", "TestkeywordGRUPPE1");
        controller.createIndexcard("Testkarteikarte5", "Testfrage5", "Testantwort5", "TestkeywordGRUPPE2");
        controller.createIndexcard("Testkarteikarte6", "Testfrage6", "Testantwort6", "TestkeywordGRUPPE2");
        controller.createIndexcard("Testkarteikarte7", "Testfrage7", "Testantwort7", "TestkeywordGRUPPE3");
        controller.createIndexcard("Testkarteikarte8", "Testfrage8", "Testantwort8", "TestkeywordGRUPPE4");
    }
}
