package uni.myosotis.controller;

import com.sun.tools.javac.Main;
import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.CategoryLogic;
import uni.myosotis.logic.IndexcardLogic;
import uni.myosotis.logic.KeywordLogic;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

import javax.swing.*;
import java.util.ArrayList;
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
     * The CategoryLogic of the application.
     */
    private final CategoryLogic categoryLogic;


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
    public Controller(final IndexcardLogic indexcardLogic, final KeywordLogic keywordLogic, final CategoryLogic categoryLogic) {

        this.indexcardLogic = indexcardLogic;
        this.keywordLogic = keywordLogic;
        this.categoryLogic = categoryLogic;

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
     * @param keywords A List of keywords linked to the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer, List<String> keywords) {
        try {
            final List<Keyword> keywordObjects = new ArrayList<>();

            for (String keyword : keywords) {
                if (keywordLogic.getKeywordByName(keyword).isEmpty()) {
                    keywordObjects.add(keywordLogic.createKeyword(keyword));
                } else {
                    keywordObjects.add(keywordLogic.getKeywordByName(keyword).get());
                }
            }

            indexcardLogic.createIndexcard(name, question, answer, keywordObjects);
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
     * @param keywords A List of keywords linked to the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer, List<String> keywords, boolean silentMode) {
        try {
            final List<Keyword> keywordObjects = new ArrayList<>();

            for (String keyword : keywords) {
                if (keywordLogic.getKeywordByName(keyword).isEmpty()) {
                    keywordObjects.add(keywordLogic.createKeyword(keyword));
                } else {
                    keywordObjects.add(keywordLogic.getKeywordByName(keyword).get());
                }
            }

            indexcardLogic.createIndexcard(name, question, answer, keywordObjects);
            if(!silentMode) {
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich erstellt.", "Karteikarte erstellt",
                        JOptionPane.INFORMATION_MESSAGE);
            }
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
     * @param keywords Keywords for the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer, List<String> keywords, Boolean deleteStatistic, Long id) {
        try {
            final List<Keyword> keywordObjects = new ArrayList<>();

            // Old Keywords from this Indexcard
            final List<Keyword> oldKeywords = indexcardLogic.getIndexcardById(id).getKeywords();

            // Create new added Keywords
            for (String keyword : keywords) {
                if (keywordLogic.getKeywordByName(keyword).isEmpty()) {
                    keywordObjects.add(keywordLogic.createKeyword(keyword));
                } else {
                    keywordObjects.add(keywordLogic.getKeywordByName(keyword).get());
                }
            }

            if (deleteStatistic) {
                indexcardLogic.deleteIndexcard(id);
                indexcardLogic.createIndexcard(name, question, answer, keywordObjects);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet und die Statistik zurückgesetzt",
                        "Karteikarte bearbeitet", JOptionPane.INFORMATION_MESSAGE);
            } else {
                indexcardLogic.updateIndexcard(name, question, answer, keywordObjects, id);
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Remove Keywords that are not used anymore
            for (Keyword keyword : oldKeywords) {
                if (indexcardLogic.getIndexcardsByKeyword(keyword.getName()).isEmpty()) {
                    keywordLogic.deleteKeyword(keyword.getName());
                }
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
     * @param id The id of the Indexcard.
     */
    public void deleteIndexcard(Long id) {
        try {
            Indexcard indexcard = indexcardLogic.getIndexcardById(id);
            id = indexcard.getId();
            List<Keyword> keywords = indexcard.getKeywords();

            // Delete the Indexcard
            indexcardLogic.deleteIndexcard(id);

            // Indexcard that should be deleted needs to be removed from the list
            // of Indexcards this keyword is attached to.
            for (Keyword keyword : keywords) {
                if (indexcardLogic.getIndexcardsByKeyword(keyword.getName()).isEmpty()) {
                    keywordLogic.deleteKeyword(keyword.getName());
                }
            }

            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich gelöscht.", "Karteikarte gelöscht",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte konnte nicht gelöscht werden.", "Karteikarte nicht gelöscht",
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
        return indexcardLogic.getIndexcardByName(indexcard);
    }

    /**
     * Delegates the exercise to find all Indexcards in this category to the IndexcardLogic.
     *
     * @param category The Category.
     * @return A list of all Indexcards in this category.
     */
    public List<Indexcard> getIndexcardsByCategory(String category) {
        return indexcardLogic.getIndexcardsByCategory(category);
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
     * Displays the Dialog to create a new Category.
     */
    public void createCategory() {
        mainMenu.displayCreateCategory();
    }

    /**
     * Delegates the exercise to create a new Category to the CategoryLogic.
     * Displays an error, if already a Category with the same categoryName exists.
     *
     * @param categoryName The categoryName of the Category.
     * @param indexcardList The Indexcards that should be in this Category.
     */
    public void createCategory(String categoryName, List<String> indexcardList){
        try {
            categoryLogic.createCategory(categoryName,indexcardList);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Kategorie wurde erfolgreich erstellt.", "Kategorie erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Kategorie mit diesem Namen." + e.toString(), "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to create a new Category to the CategoryLogic.
     * Displays an error, if already a Category with the same categoryName exists.
     *
     * @param categoryName The categoryName of the Category.
     * @param indexcardList The Indexcards that should be in this Category.
     */
    public void createCategory(String categoryName, List<String> indexcardList, boolean silentMode){
        try {
            categoryLogic.createCategory(categoryName,indexcardList);
            if (!silentMode) {
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Kategorie wurde erfolgreich erstellt.", "Kategorie erstellt",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Kategorie mit diesem Namen. " + e.toString(), "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the Dialog to edit an existing Category.
     */
    public  void editCategory(){
        mainMenu.displayEditCategory();
    }

    /**
     * Displays the Dialog to delete an existing Category.
     */
    public void deleteCategory() {
        mainMenu.displayDeleteCategory();
    }

    /**
     * Delegates the exercise to delete an existing Category to the CategoryLogic.
     * Displays an error, if no Category with the same name exists.
     *
     * @param name The name of the Category.
     */
    public void deleteCategory(String name){
        try {
            categoryLogic.deleteCategory(name);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Kategorie wurde erfolgreich gelöscht.", "Kategorie gelöscht",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Kategorie mit diesem Namen!.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to delete an existing Category to the CategoryLogic.
     * Displays an error, if no Category with the same name exists.
     *
     * @param name The name of the Category.
     */
    public void deleteCategory(String name, boolean silentMode){
        try {
            categoryLogic.deleteCategory(name);
            if (!silentMode) {
                JOptionPane.showMessageDialog(mainMenu,
                        "Die Kategorie wurde erfolgreich gelöscht.", "Kategorie gelöscht",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Kategorie mit diesem Namen!.", "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<Category> getAllCategories() {
        return categoryLogic.getAllCategories();
    }

    /**
     * Delegates the exercise to find a Category with the given name to the CategoryLogic.
     *
     * @param category The name of the Category.
     * @return The Category if it exists.
     */
    public Optional<Category> getCategoryByName(String category) {
        return categoryLogic.getCategoryByName(category);
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
     *
     * @param keyword The keyword.
     */
    public void filterIndexCardPanelByKeyword(String keyword) {
        List<Indexcard> indexcards = indexcardLogic.getIndexcardsByKeyword(keyword);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Indexcard indexcard : indexcards) {
            listModel.addElement(indexcard.getName());
        }
        JList<String> cardList = new JList<>(listModel);
        mainMenu.getIndexcardsPane().setViewportView(cardList);
    }

    /**
     * Display all Indexcards from the IndexCard repository into the IndexCardPanel
     * filtered with the specific Category.
     *
     * @param name The name of the Category
     */
    public void filterIndexCardPanelByCategories(String name) {
        if (getCategoryByName(name).isPresent()) {
            List<String> indexcardList = getCategoryByName(name).get().getIndexcardList();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String indexCardName : indexcardList) {
                listModel.addElement(indexCardName);
            }
            JList<String> cardList = new JList<>(listModel);
            mainMenu.getIndexcardsPane().setViewportView(cardList);
        }
    }

    /**
     * Sets the KeywordComboBox in the Main-Menu.
     */
    public void setKeywordComboBox(){
        mainMenu.setKeywordComboBox();
    }

    /**
     * Sets the CategoryComboBox in the Main-Menu.
     */
    public void setCategoryComboBox(){
        mainMenu.setCategoryComboBox();
    }

    // END OF CLASS

    /**
     * Delegates the exercise to edit an Category to the CategoryLogic.
     * Displays an error, if there is no Category with the given name.
     *
     * @param name The name of the Category.
     * @param indexCardListNames The question of the Category.
     */
    public void editCategory(String name, List<String> indexCardListNames) {
        try {
            categoryLogic.updateCategory(name, indexCardListNames);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Kategorie wurde erfolgreich bearbeitet.", "Kategorie bearbeitet",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Kategorie mit diesem Namen.", "Kategorie nicht vorhanden",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays glossar.
     */
    public void showGlossar(){
        mainMenu.displayCreateGlossar();
    }



    /**
     * Delegates the exercise to create a new Indexcard to the IndexcardLogic.
     * @param name
     * @param question
     * @param answer
     * @param keywords
     */
    /*private void createIndexcardLogic(String name, String question, String answer, String keywords){
        if(keywordLogic.KeywordIsPresent(keywords)) {
            Optional<Keyword> updateKeyword = keywordLogic.getKeyword(keywords);
            indexcardLogic.createIndexcard(name, question, answer, updateKeyword.get());
            Optional<Indexcard> newCard = indexcardLogic.getIndexcardByName(name);
            keywordLogic.addIndexcardToKeyword(name , newCard.get());
        }
        else{
            keywordLogic.createKeyword(keywords);
            Optional<Keyword> newKeyword = keywordLogic.getKeyword(keywords);
            indexcardLogic.createIndexcard(name, question, answer, newKeyword.get());
            Optional<Indexcard> newCard = indexcardLogic.getIndexcardByName(name);
            keywordLogic.addIndexcardToKeyword(name , newCard.get());
        }
    }*/



    /**
     * Delegates the exercise to update the Name of a Keyword to the KeywordLogic.
     * @param keyword
     * @param name
     */
    /*
    public void updateKeywordName(Keyword keyword, String name) {
        keywordLogic.updateKeywordName(keyword, name);
    }*/



    /*
    public void editKeyword(Keyword oldKeyword, Keyword newKeyword, String keywordName, Indexcard newIndexcard, Indexcard oldIndexcard){
        if(keywordLogic.KeywordIsPresent(keywordName)){ //Keyword exist already
            List<Indexcard> list = oldKeyword.getIndexcards();
            list.remove(newIndexcard);
            keywordLogic.deleteIndexcardFromKeyword(oldKeyword, list);
            if(oldKeyword.getIndexcards().isEmpty()){
                keywordLogic.deleteKeyword(oldKeyword.getName());
            }
        }
        //Indexcard add to new Keyword
        keywordLogic.addIndexcardToKeyword(newKeyword.getName(), newIndexcard);
    }*/

}