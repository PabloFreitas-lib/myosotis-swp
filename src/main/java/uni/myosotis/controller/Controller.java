package uni.myosotis.controller;

import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.IndexcardLogic;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.logic.KeywordLogic;
import uni.myosotis.objects.Keyword;

import javax.swing.*;
import java.security.Key;
import java.util.List;
import java.util.Optional;

public class Controller {

    /**
     * The IndexcardLogic of the application.
     */
    private final IndexcardLogic indexcardLogic;


    /**
     * The KeywordLogic of the application.
     */
    private final KeywordLogic keywordLogic;

    /**
     * The main-menu of the application.
     */
    private MainMenu mainMenu;

    /**
     * Creates a new Controller with the given logic.
     *
     * @param indexcardLogic The logic for the Indexcards.
     * @param keywordLogic The logic for the Keywords.
     */
    public Controller(final IndexcardLogic indexcardLogic, final KeywordLogic keywordLogic) {

        this.indexcardLogic = indexcardLogic;
;
        this.keywordLogic = keywordLogic;
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

    /**
     * Delegates the exercise to create a new Indexcard to the IndexcardLogic.
     * Displays an error, if already an Indexcard with the same name exists.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     * @param keywords The keywords of the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer, String keywords) {
        try {
            indexcardLogic.createIndexcard(name, question, answer,keywords);
            keywordLogic.createKeyword(keywords,name);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich erstellt.", "Karteikarte erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Displays the dialog to edit an Indexcard.
     */
    public void editIndexcard() {
        mainMenu.displayEditIndexcard();
    }
    /**
     * Delegates the exercise to edit an Indexcard to the IndexcardLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     * @param deleteStatistic Whether the statistic should be deleted or not.
     */
    public void editIndexcard(String name, String question, String answer, Boolean deleteStatistic) {
        try {
            if(deleteStatistic){
                indexcardLogic.DeleteIndexcard(name);
                indexcardLogic.createIndexcard(name, question, answer);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet und die Statistik zurückgesetzt",
                        "Karteikarte bearbeitet", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                indexcardLogic.EditIndexcard(name, question, answer);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Karteikarte mit diesem Namen.", "Karteikarte nicht vorhanden",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to edit an Indexcard to the IndexcardLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     * @param question The question of the Indexcard.
     * @param answer The answer of the Indexcard.
     * @param deleteStatistic Whether the statistic should be deleted or not.
     * @param keywords Keywords for the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer, Boolean deleteStatistic, String keywords) {
        try {
            if(deleteStatistic){
                indexcardLogic.DeleteIndexcard(name);
                indexcardLogic.createIndexcard(name, question, answer,keywords);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet und die Statistik zurückgesetzt",
                        "Karteikarte bearbeitet", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                indexcardLogic.EditIndexcard(name, question, answer,keywords);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Karteikarte mit diesem Namen.", "Karteikarte nicht vorhanden",
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
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich gelöscht.", "Karteikarte gelöscht",
                    JOptionPane.INFORMATION_MESSAGE);
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
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<String> getAllIndexcards(String keyword) {
        return indexcardLogic.getAllIndexcards(keyword);
    }

    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<Keyword> getAllKeywords() {
        return keywordLogic.getAllKeywords();
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

    public void createKeyword(String word, String indexcard_name){
        try {
            keywordLogic.createKeyword(word, indexcard_name);
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert schon so ein Schlagwort", "Wort bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Display all Indexcards from the IndexCard repository into the IndexCardPanel.
     *
     *
     */
    public void setIndexCardPanel() {
        List<Indexcard> indexcardList = getAllIndexcards();
        String[] indexcardsNames = new String[indexcardList.size()];
        for (int i = 0; i < indexcardList.size(); i++) {
            indexcardsNames[i] = indexcardList.get(i).getName();
        }
        //FIXME Return the names not the Objects
        JList<Indexcard> indexcardJList = new JList<>(indexcardList.toArray(new Indexcard[indexcardList.size()]));
        //indexcardJList.setListData(indexcardJList);
        mainMenu.getIndexcardsPane().setViewportView(indexcardJList);
    }

    /**
     * Display all Indexcards from the IndexCard repository into the IndexCardPanel.
     *
     * @param keyword
     */
    public void filternIndexCardPanel(String keyword) {
        List<String> indexcardList = getAllIndexcards(keyword);

        //FIXME Return the names not the Objects
        JList<String> indexcardJList = new JList<>(indexcardList.toArray(new String[indexcardList.size()]));
        //indexcardJList.setListData(indexcardJList);
        mainMenu.getIndexcardsPane().setViewportView(indexcardJList);
    }

    public void setKeywordComboBox(){
        mainMenu.setKeywordComboBox();
    }
}
