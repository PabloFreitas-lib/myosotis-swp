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
        setIndexCardPanel();
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
            createIndexcardLogic(name, question, answer, keywords);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich erstellt.", "Karteikarte erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikarte mit diesem Namen.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to create a new Indexcard to the IndexcardLogic.
     * @param name
     * @param question
     * @param answer
     * @param keywords
     */
    private void createIndexcardLogic(String name, String question, String answer, String keywords){
        if(keywordLogic.KeywordIsPresent(keywords)) {
            Optional<Keyword> updateKeyword = keywordLogic.getKeyword(keywords);
            indexcardLogic.createIndexcard(name, question, answer, updateKeyword.get());
        }
        else{
            keywordLogic.createKeywordWithNoIndexcard(keywords);
            Optional<Keyword> newKeyword = keywordLogic.getKeyword(keywords);
            indexcardLogic.createIndexcard(name, question, answer, newKeyword.get());
        }
        Optional<Indexcard> newCard = indexcardLogic.getIndexcardByName(name);
        keywordLogic.addIndexcardToKeyword(name , newCard.get());
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
    public void editIndexcard(String name, String question, String answer, Boolean deleteStatistic, Long id) {
        try {
            if(deleteStatistic){
                indexcardLogic.DeleteIndexcard(id);
                indexcardLogic.createIndexcard(name, question, answer);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet und die Statistik zurückgesetzt",
                        "Karteikarte bearbeitet", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                indexcardLogic.EditIndexcard(name, question, answer, id);
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
     * @param keyword Keywords for the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer, Boolean deleteStatistic, Keyword keyword, Long id) {
        try {
            if(deleteStatistic){
                indexcardLogic.DeleteIndexcard(id);
                indexcardLogic.createIndexcard(name, question, answer, keyword);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet und die Statistik zurückgesetzt",
                        "Karteikarte bearbeitet", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                indexcardLogic.EditIndexcard(name, question, answer, keyword, id);
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
     * Delegates the exercise to delete the Indexcard from the Keyword to the KeywordLogic.
     * Displays an error, if there is no Indexcard with the given name.
     *
     * @param name The name of the Indexcard.
     */
    public void deleteIndexcard(String name) {
        try {
            Indexcard deleteCard = indexcardLogic.getIndexcardByName(name).get();
            Long id = deleteCard.getId();
            Keyword deleteKeyword = deleteCard.getKeyword();
            String word = deleteKeyword.getKeywordWord();
            List <Indexcard> indexcards = deleteKeyword.getIndexcards();
            indexcards.remove(deleteCard);
            keywordLogic.deleteIndexcardFromKeyword(deleteKeyword, indexcards);
            indexcardLogic.DeleteIndexcard(id);
            if (deleteKeyword.getIndexcards().isEmpty()) {
                keywordLogic.deleteKeyword(word);
            }

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
    public List<Indexcard> getAllIndexcardsByKeyword(String keyword) {
        return indexcardLogic.getAllIndexcardsByKeyword(keyword);
    }

    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<Keyword> getAllKeywords() {
        if(keywordLogic.getAllKeywords().isEmpty()){
            return null;
        }
        else {
            return keywordLogic.getAllKeywords().get();
        }
    }

    /**
     * Delegates the exercise to find an Indexcard with the given name to the IndexcardLogic.
     *
     * @param indexcard The name of the Indexcard.
     * @return The Indexcard if it exists.
     */
    public Optional<Indexcard> getIndexcardByName(String indexcard) {
        return indexcardLogic.getIndexcardByName(indexcard);
    }

    /**
     * Delegates the exercise to update the Name of an Keyword to the KeywordLogic.
     * @param keyword
     * @param name
     */
    public void updateKeywordName(Keyword keyword, String name) {
        keywordLogic.updateKeywordName(keyword, name);
    }


    /**
     * Display all Indexcards from the IndexCard repository into the IndexCardPanel.
     */
    public void setIndexCardPanel() {
        List<Indexcard> indexcardList = getAllIndexcards();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Indexcard card : indexcardList) {
            listModel.addElement(card.getName());
        }
        JList<String> cardList = new JList<>(listModel);
        mainMenu.getIndexcardsPane().setViewportView(cardList);
    }

    /**
     * Display all Indexcards from the IndexCard repository into the IndexCardPanel
     * filtered with the specific Keyword.
     * @param keyword
     */
    public void filterIndexCardPanel(String keyword) {
        List<Indexcard> indexcardList = getAllIndexcardsByKeyword(keyword);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Indexcard card : indexcardList) {
            listModel.addElement(card.getName());
        }
        JList<String> cardList = new JList<>(listModel);
        mainMenu.getIndexcardsPane().setViewportView(cardList);
    }

    public void setKeywordComboBox(){
        mainMenu.setKeywordComboBox();
    }

    public void editKeyword(Keyword oldKeyword, Keyword newKeyword, String keywordName, Indexcard newIndexcard, Indexcard oldIndexcard){
        if(keywordLogic.KeywordIsPresent(keywordName)){ //Keyword exist already
            List<Indexcard> list = oldKeyword.getIndexcards();
            list.remove(newIndexcard);
            keywordLogic.deleteIndexcardFromKeyword(oldKeyword, list);
            if(oldKeyword.getIndexcards().isEmpty()){
                keywordLogic.deleteKeyword(oldKeyword.getKeywordWord());
            }
        }
        //Indexcard add to new Keyword
        keywordLogic.addIndexcardToKeyword(newKeyword.getKeywordWord(), newIndexcard);
    }
}
