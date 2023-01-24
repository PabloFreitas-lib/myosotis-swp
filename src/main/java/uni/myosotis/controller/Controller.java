package uni.myosotis.controller;

import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.*;
import uni.myosotis.objects.*;

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
     * The IndexcardBoxLogic of the application.
     */
    private final IndexcardBoxLogic indexcardBoxLogic;

    /**
     * The KeywordLogic of the application.
     */
    private final KeywordLogic keywordLogic;

    /**
     * The CategoryLogic of the application.
     */
    private final CategoryLogic categoryLogic;

    /**
     * The LearnsystemLogic of the application.
     */
    private final LearnsystemLogic learnsystemLogic;

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
    public Controller(final IndexcardLogic indexcardLogic, final KeywordLogic keywordLogic, final CategoryLogic categoryLogic, final IndexcardBoxLogic indexcardBoxLogic, final LearnsystemLogic learnsystemLogic) {

        this.indexcardLogic = indexcardLogic;
        this.keywordLogic = keywordLogic;
        this.categoryLogic = categoryLogic;
        this.indexcardBoxLogic = indexcardBoxLogic;
        this.learnsystemLogic = learnsystemLogic;

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
     * Displays the dialog to create a new Indexcard.
     */
    public void createIndexcardBox() {
        mainMenu.displayCreateIndexcardBox();
    }

    /**
     * Delegates the exercise to create a new Indexcardbox to the IndexcardBoxLogic.
     * Displays an error, if already an IndexcardBox with the same name exists.
     *
     * @param name The name of the Indexcard.
     */
    public void createIndexcardBox(String name, List<Category> categoryList) {
        try {
            //logic
            indexcardBoxLogic.createIndexcardBox(name,categoryList);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikästen wurde erfolgreich erstellt.", "Karteikästen erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Karteikaste mit diesem Namen.", "Name bereits vergeben",
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

    public void editIndexcard(Indexcard indexcard) {
        mainMenu.displayEditIndexcard(indexcard);
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
    public void editIndexcard(String name, String question, String answer, List<String> keywords, Long id) {
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
            indexcardLogic.updateIndexcard(name, question, answer, keywordObjects, id);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                    JOptionPane.INFORMATION_MESSAGE);
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
     * Displays the dialog to delete an Indexcard.
     */
    public void deleteIndexcardBox() {
        mainMenu.displayDeleteIndexcardBox();
    }

    /**
     * Displays the dialog to delete an Indexcard.
     */
    public void updateIndexcardBox() {
        mainMenu.displayEditIndexcardBox();
    }

    /**
     * Displays the dialog to delete an Indexcard.
     */
    public void updateIndexcardBox(String indexcardBoxName, List<Category> indexcardBoxList) {
        indexcardBoxLogic.updateIndexcardBox(indexcardBoxName, indexcardBoxList);
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
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<String> getAllIndexcardNames() {
        return getAllIndexcards().stream().map(Indexcard::getName).toList();
    }

    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public String[] getAllIndexcardBoxNames() {
        return getAllIndexcardBoxes().stream().map(IndexcardBox::getName).toList().toArray(new String[0]);
    }

    /**
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public List<IndexcardBox> getAllIndexcardBoxes() {
        return indexcardBoxLogic.getAllIndexcardBoxes();
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
     * Delegates the exercise to find an Indexcard with the given name to the IndexcardLogic.
     *
     * @param indexcardBoxName The name of the Indexcard.
     * @return The Indexcard if it exists.
     */
    public Optional<IndexcardBox> getIndexcardBoxByName(String indexcardBoxName) {
        return Optional.ofNullable(indexcardBoxLogic.getIndexcardBoxByName(indexcardBoxName));
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
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public String[] getAllKeywordNames() {
        return getAllKeywords().stream().map(Keyword::getName).toList().toArray(new String[0]);
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
     * @param indexcardListNames The Indexcards that should be in this Category.
     */
    public void createCategory(String categoryName, List<String> indexcardListNames){
        try {
            List<Indexcard> indexcardList = new ArrayList<>();
            for (String s : indexcardListNames) {
                indexcardList.add(getIndexcardByName(s).get());
            }
            categoryLogic.createCategory(categoryName,indexcardList);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Kategorie wurde erfolgreich erstellt.", "Kategorie erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Kategorie mit diesem Namen." + e, "Name bereits vergeben",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delegates the exercise to create a new Category to the CategoryLogic.
     * Displays an error, if already a Category with the same categoryName exists.
     *
     * @param categoryName The categoryName of the Category.
     * @param indexcardListNames The Indexcards that should be in this Category.
     * @param parent The parent category.
     */
    public void createCategory(String categoryName, List<String> indexcardListNames,Category parent){
        try {
            List<Indexcard> indexcardList = new ArrayList<>();
            for (String s : indexcardListNames) {
                indexcardList.add(getIndexcardByName(s).get());
            }
            categoryLogic.createCategory(categoryName,indexcardList,parent);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Kategorie wurde erfolgreich erstellt.", "Kategorie erstellt",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert bereits eine Kategorie mit diesem Namen." + e, "Name bereits vergeben",
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
    public void createCategory(String categoryName, List<Indexcard> indexcardList, boolean silentMode){
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
                    "Es existiert bereits eine Kategorie mit diesem Namen. " + e, "Name bereits vergeben",
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
    public void deleteIndexcardBox(String name){
        try {
            indexcardBoxLogic.deleteIndexcardBox(name);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikasten wurde erfolgreich gelöscht.", "Karteikasten gelöscht",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (final IllegalStateException e) {
            JOptionPane.showMessageDialog(mainMenu,
                    "Es existiert keine Karteikasten mit diesem Namen!.", "Name bereits vergeben",
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
     * Delegates the exercise to find all Indexcards to the IndexcardLogic.
     *
     * @return A list of all Indexcards.
     */
    public String [] getAllCategoryNames() {
        return getAllCategories().stream().map(Category::getCategoryName).toList().toArray(new String[0]);
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
            List<Indexcard> indexcardList = getCategoryByName(name).get().getIndexcardList();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Indexcard indexCard : indexcardList) {
                listModel.addElement(indexCard.getName());
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
     * Delegates the exercise to edit a Category to the CategoryLogic.
     * Displays an error, if there is no Category with the given name.
     *
     * @param name The name of the Category.
     * @param indexCardListNames The question of the Category.
     */
    public void editCategory(String name, List<String> indexCardListNames, Category parent) {
        try {
            List<Indexcard> indexCardList = new ArrayList<>();
            for (String s : indexCardListNames) {
                indexCardList.add(getIndexcardByName(s).get());
            }
            categoryLogic.updateCategory(name, indexCardList,parent);
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
     * Delegates the exercise to edit a Category to the CategoryLogic.
     * Displays an error, if there is no Category with the given name.
     *
     * @param name The name of the Category.
     * @param indexCardListNames The question of the Category.
     */
    public void editCategory(String name, List<String> indexCardListNames) {
        try {
            List<Indexcard> indexCardList = new ArrayList<>();
            for (String s : indexCardListNames) {
                indexCardList.add(getIndexcardByName(s).get());
            }
            categoryLogic.updateCategory(name, indexCardList);
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

    public List<Indexcard> searchIndexcard(String text) {
        return indexcardLogic.searchIndexcard(text);
    }

    /**
     * Delegates the exercise to learn an IndexcardBox to the LearnsystemLogic.
     *
     * @param indexcardBox The IndexcardBox that should be learned.
     */
    public void learn(IndexcardBox indexcardBox) {
        mainMenu.displayLearning(learnsystemLogic.learn(indexcardBox));
    }

    public void updateLearnsystem(Learnsystem learnsystem) {
        learnsystemLogic.updateLearnsystem(learnsystem);
    }
}