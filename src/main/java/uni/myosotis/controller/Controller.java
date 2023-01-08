package uni.myosotis.controller;

import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.IndexcardLogic;

import javax.swing.*;

public class Controller {

    /**
     * The IndexcardLogic of the application.
     */
    private final IndexcardLogic indexcardLogic;

    /**
     * The main-menu of the application.
     */
    private MainMenu mainMenu;

    /**
     * Creates a new Controller with the given logic.
     *
     * @param indexcardLogic The logic for the Indexcards.
     */
    public Controller(final IndexcardLogic indexcardLogic) {

        this.indexcardLogic = indexcardLogic;

    }

    /**
     * Starts the application and displays the MainMenu.
     */
    public void startApplication() {
        mainMenu = new MainMenu(this);
        mainMenu.setVisible(true);
    }

    /**
     * Displays the dialog to create a new Indexcard.
     */
    public void createIndexcard() {
        mainMenu.displayCreateIndexcard();
    }

    /**
     * Delegates the exercise to create a new Indexcard to the IndexcardLogic.
     * Displays an error, if already an Indexcard with the same name exists.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer) {
        try {
            indexcardLogic.createIndexcard(name, question, answer);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich erstellt.", "Karteikarte erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editIndexcard() {
    }

    public void deleteIndexcard() {
    }
}
