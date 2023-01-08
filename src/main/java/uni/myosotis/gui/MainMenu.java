package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private JPanel contentPane;

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
     * Displays the Dialog to create a new Indexcard and delegate this exercise to the controller.
     */
    public void displayCreateIndexcard() {
        final CreateIndexcard createIndexcard = new CreateIndexcard(controller);
        createIndexcard.pack();
        createIndexcard.setMinimumSize(createIndexcard.getSize());
        createIndexcard.setSize(400, 300);
        createIndexcard.setLocationRelativeTo(this);
        createIndexcard.setVisible(true);
    }
}
