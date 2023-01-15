package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JScrollPane IndexcardsPane;
    private DefaultComboBoxModel<String> keywordModel = new DefaultComboBoxModel<>();
    private JComboBox KeywordComboBox = new JComboBox<>(keywordModel);
    private JLabel VerschlagwortungLabel;
    private JLabel KarteikartenLabel;

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
    }


    /**
     * Display all Keyword in the ComboBox.
     *
     *
     */
    public void setKeywordComboBox(){
        //list of all indexcards
        List<Keyword> keywordList = controller.getAllKeywords();

        //Array of all indexcard names
        //String[] keywordsNames = new String[keywordList.size()];
        for (int i = 0; i < keywordList.size(); i++) {
            keywordModel.addElement(keywordList.get(i).getKeyword());
        }
        KeywordComboBox.updateUI();
        //ComboBox with all indexcard names
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

    public void setIndexcardsPane(JScrollPane indexcardsPane) {
        this.IndexcardsPane = indexcardsPane;
    }

    public JComboBox getKeywordComboBox(){
        return this.KeywordComboBox;
    }

    public void setKeywordComboBox(JComboBox keywordComboBox) {
        this.KeywordComboBox = keywordComboBox;
    }
}
