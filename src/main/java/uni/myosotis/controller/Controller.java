package uni.myosotis.controller;

import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.IndexcardLogic;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

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
            okMsgCreateIndexcard();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * OVERLOAD
     * Delegates the exercise to create a new Indexcard to the IndexcardLogic.
     * Displays an error, if already an Indexcard with the same name exists.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     * @param silentMode Boolean variable to hide the notifications spam.
     */
    public void createIndexcard(String name, String question, String answer, boolean silentMode) {
        try {
            indexcardLogic.createIndexcard(name, question, answer);
            if (!silentMode) {
                okMsgCreateIndexcard();
            }
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays the information when an index card was created successfully.
     *
     */
    public void okMsgCreateIndexcard() {
        JOptionPane.showMessageDialog(mainMenu,
                "Die Karteikarte wurde erfolgreich erstellt.", "Karteikarte erstellt",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Delegates the exercise to edit an Indexcard to the IndexcardLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer) {
        try {
            indexcardLogic.EditIndexcard(name, question, answer);
            okMsgEditIndexCard();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Displays the dialog to delete an Indexcard.
     */
    public void deleteIndexcard() {
        mainMenu.displayDeleteIndexcard();
    }
    /**
     * Delegates the exercise to delete an Indexcard to the IndexcardLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     */
    public void deleteIndexcard(String name) {
        try {
            indexcardLogic.DeleteIndexcard(name);
            okMsgDeleteIndexCard();
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Karteikarte mit diesem Namen.", "Karteikarte nicht vorhanden",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to delete an Indexcard to the IndexcardLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     * @param silentMode Boolean variable to hide the notifications spam.
     */
    public void deleteIndexcard(String name,boolean silentMode) {
        try {
            indexcardLogic.DeleteIndexcard(name);
            if (!silentMode) {
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich gelöscht.", "Karteikarte gelöscht",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Karteikarte mit diesem Namen.", "Karteikarte nicht vorhanden",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<Indexcard> getAllIndexcards() {
        return indexcardLogic.getAllIndexcards();
    }

    /**
     * Delegates the exercise to find an Indexcard with the given name to the IndexcardLogic.
     *
     * @param indexcard The name of the Indexcard.
     * @return The Indexcard if it exists.
     */
    public Optional<Indexcard> getIndexcardByName(String indexcard) {
        return indexcardLogic.getIndexcard(indexcard);
    }

    /**
     * Displays the information when an index card was edit successfully.
     *
     */
    public void okMsgEditIndexCard() {
        JOptionPane.showMessageDialog(mainMenu,
                "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Displays the information when an index card was delete successfully.
     *
     */
    public void okMsgDeleteIndexCard(){
        JOptionPane.showMessageDialog(mainMenu,
                "Die Karteikarte wurde erfolgreich gelöscht.", "Karteikarte gelöscht",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the dialog to edit an Indexcard.
     */
    public void editIndexcard() {
        mainMenu.displayEditIndexcard();
    }
}
