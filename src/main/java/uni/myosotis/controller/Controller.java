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
     * The LinkLogic of the application.
     */
    private final LinkLogic linkLogic;

    /**
     * The main-menu of the application.
     */
    private MainMenu mainMenu;

    /**
     * Creates a new Controller with the given logic.
     *
     * @param indexcardLogic The logic for the Indexcards.
     * @param keywordLogic The logic for the Keywords.
     * @param categoryLogic The logic for the Category`s.
     * @param linkLogic The logic for the Links.
     * @param indexcardBoxLogic The logic for the IndexcardBoxes.
     * @param learnsystemLogic The logic for the LearnSystems.
     */
    public Controller(final IndexcardLogic indexcardLogic, final KeywordLogic keywordLogic, final CategoryLogic categoryLogic, final LinkLogic linkLogic, final IndexcardBoxLogic indexcardBoxLogic, final LearnsystemLogic learnsystemLogic) {

        this.indexcardLogic = indexcardLogic;
        this.keywordLogic = keywordLogic;
        this.categoryLogic = categoryLogic;
        this.linkLogic = linkLogic;
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
                    String.format("Die Karteikästen (%s) wurde erfolgreich erstellt.",name), "Karteikästen erstellt",
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
     * @param links A list of Links added to the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer, List<String> keywords, List<String> links) {
        try {

            // Create Keywords
            final List<Keyword> keywordObjects = new ArrayList<>();
            for (String keyword : keywords) {
                if (keywordLogic.getKeywordByName(keyword).isEmpty()) {
                    keywordObjects.add(keywordLogic.createKeyword(keyword));
                } else {
                    keywordObjects.add(keywordLogic.getKeywordByName(keyword).get());
                }
            }

            // Create Links
            final List<Link> linkObjects = new ArrayList<>();
            for (String link : links) {
                String[] splittedLink = link.split(" ");
                // Build the term
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < link.split(" ").length - 2; i++) {
                    builder.append(link.split(" ")[i]);
                }
                String term = builder.toString();
                String indexcardName = splittedLink[splittedLink.length - 1];
                if (getIndexcardByName(indexcardName).isPresent()) {
                    Indexcard indexcard = getIndexcardByName(indexcardName).get();
                    linkObjects.add(linkLogic.createLink(term, indexcard));
                }
            }

            indexcardLogic.createIndexcard(name, question, answer, keywordObjects, linkObjects);
            JOptionPane.showMessageDialog(mainMenu,
                    String.format("Die Karteikarte (%s) wurde erfolgreich erstellt.",name), "Karteikarte erstellt",
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

            indexcardLogic.createIndexcard(name, question, answer, keywordObjects, new ArrayList<>());
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
     * @param keywords Keywords for the Indexcard.
     * @param links The Links of the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer, List<String> keywords, List<String> links, Long id) {
        try {

            // Old Keywords from this Indexcard
            final List<Keyword> oldKeywords = indexcardLogic.getIndexcardById(id).getKeywords();

            // Create new added Keywords
            final List<Keyword> keywordObjects = new ArrayList<>();
            for (String keyword : keywords) {
                if (keywordLogic.getKeywordByName(keyword).isEmpty()) {
                    keywordObjects.add(keywordLogic.createKeyword(keyword));
                } else {
                    keywordObjects.add(keywordLogic.getKeywordByName(keyword).get());
                }
            }

            // Old Links from this Indexcard
            final List<Link> oldLinks = indexcardLogic.getIndexcardById(id).getLinks();

            // Create new added Links
            final List<Link> newLinks = new ArrayList<>();
            for (String link : links) {
                String[] splittedLink = link.split(" ");
                // Build the term
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < link.split(" ").length - 2; i++) {
                    builder.append(link.split(" ")[i]);
                }
                String term = builder.toString();
                String indexcardName = splittedLink[splittedLink.length - 1];
                if (!oldLinks.stream().map(Link::getTerm).toList().contains(term)) {
                    // Create new Link, if it not exists yet
                    if (getIndexcardByName(indexcardName).isPresent()) {
                        Indexcard indexcard = getIndexcardByName(indexcardName).get();
                        newLinks.add(linkLogic.createLink(term, indexcard));
                    }
                } else {
                    // Keep old Link, if it exists yet
                    newLinks.add(oldLinks.stream().filter(l -> l.getTerm().equals(term)).toList().get(0));
                }
            }

            // Update the Indexcard
            indexcardLogic.updateIndexcard(name, question, answer, keywordObjects, newLinks, id);
            JOptionPane.showMessageDialog(mainMenu,
                    "Die Karteikarte wurde erfolgreich bearbeitet.", "Karteikarte bearbeitet",
                    JOptionPane.INFORMATION_MESSAGE);

            // Remove Keywords that are not used anymore
            for (Keyword keyword : oldKeywords) {
                if (indexcardLogic.getIndexcardsByKeyword(keyword.getName()).isEmpty()) {
                    keywordLogic.deleteKeyword(keyword.getName());
                }
            }

            // Remove removed Links
            for (Link link : oldLinks) {
                if (!newLinks.contains(link)) {
                    linkLogic.deleteLink(link);
                }
            }
        } catch (final IllegalStateException e) {
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
    public void editIndexcardBox() {
        mainMenu.displayEditIndexcardBox();
    }

    /**
     * Displays the dialog to delete an Indexcard.
     */
    public void editIndexcardBox(String indexcardBoxName, List<Category> indexcardBoxList) {
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

            // Delete Links that are linked with this Indexcard
            for (Link link : linkLogic.getLinksByIndexcard(indexcard)) {
                // Remove the Link from Indexcards that contain that Link.
                for (Indexcard card : getIndexcardsByLink(link)) {
                    List<Link> removedLink = card.getLinks().stream().filter(l -> !l.getIndexcard().getName().equals(link.getIndexcard().getName())).toList();
                    indexcardLogic.updateIndexcard(card.getName(), card.getQuestion(), card.getAnswer(), card.getKeywords(), removedLink, card.getId());
                }
                linkLogic.deleteLink(link);
            }

            // Delete the Indexcard
            indexcardLogic.deleteIndexcard(id);

            // Indexcard that should be deleted needs to be removed from the list
            // of Indexcards this keyword is attached to.
            for (Keyword keyword : keywords) {
                if (indexcardLogic.getIndexcardsByKeyword(keyword.getName()).isEmpty()) {
                    keywordLogic.deleteKeyword(keyword.getName());
                }
            }

            // Delete Links from this Indexcard
            for (Link link : indexcard.getLinks()) {
                linkLogic.deleteLink(link);
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
    public List<String> getAllIndexcardNames(List<Indexcard> indexcards) {
        return indexcards.stream().map(Indexcard::getName).toList();
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

    /** Delegates the exercise to find an Indexcard with the given Link.
     *
     * @param link The Link.
     * @return A List of all Indexcards that contain that Link.
     */
    public List<Indexcard> getIndexcardsByLink(Link link) {
        return indexcardLogic.getIndexcardsByLink(link);
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
                if (getIndexcardByName(s).isPresent()) {
                    indexcardList.add(getIndexcardByName(s).get());
                }
            }
            categoryLogic.createCategory(categoryName,getAllIndexcardNames(indexcardList));
            JOptionPane.showMessageDialog(mainMenu,
                    String.format("Die Kategorie (%s) wurde erfolgreich erstellt.",categoryName), "Kategorie erstellt",
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
                if (getIndexcardByName(s).isPresent()) {
                    indexcardList.add(getIndexcardByName(s).get());
                }
            }
            categoryLogic.createCategory(categoryName,getAllIndexcardNames(indexcardList),parent);
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
            categoryLogic.createCategory(categoryName,getAllIndexcardNames(indexcardList));
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
     * Delegates the exercise to find all CategoryNames from a CategoryList to the CategoryLogic.
     *
     * @return A list of all Indexcards.
     */
    public String [] getAllCategoryNames(List<Category> categoryList) {
        return categoryList.stream().map(Category::getCategoryName).toList().toArray(new String[0]);
    }




    /**
     * Delegates the exercise to find a Category with the given name.
     *
     * @param category The name of the Category.
     * @return The Category if it exists.
     */
    public Optional<Category> getCategoryByName(String category) {
        return categoryLogic.getCategoryByName(category);
    }

    /**
     * Delegates the exercise to find all Categories from a CategoryNameList.
     */
    public List<Category> getCategoriesByCategoryNameList(List<String> categoryNameList) {
        return categoryLogic.getCategoriesByCategoryNameList(categoryNameList);
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
            List<Indexcard> indexcardList = getIndexcardsByIndexcardNameList(getCategoryByName(name).get().getIndexcardList());
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
                if (getIndexcardByName(s).isPresent()) {
                    indexCardList.add(getIndexcardByName(s).get());
                }
            }
            // verify if the category is a child of itself
            if (parent != null) {
                if (parent.getCategoryName().equals(name)) {
                    JOptionPane.showMessageDialog(mainMenu,
                            "Eine Kategorie kann nicht selbst eine Unter-Kategorie sein.", "Kategorie nicht bearbeitet",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            categoryLogic.updateCategory(name, indexCardListNames,parent);
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
                if (getIndexcardByName(s).isPresent()) {
                    indexCardList.add(getIndexcardByName(s).get());
                }
            }
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

    public List<Indexcard> searchIndexcard(String text) {
        return indexcardLogic.searchIndexcard(text);
    }

    /**
     * Delegates the exercise to learn an IndexcardBox to the LearnsystemLogic.
     *
     * @param indexcardBox The IndexcardBox that should be learned.
     */
    public void learn(IndexcardBox indexcardBox) {
        if (!indexcardBox.getCategoryList().isEmpty()) {
            mainMenu.displayLearning(learnsystemLogic.learn(indexcardBox, "Leitner"), indexcardBox);
        } else {
            JOptionPane.showMessageDialog(mainMenu,
                    "The indexcard box does not contain any categories.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    public void updateLearnsystem(LearnSystem learnsystem) {
        learnsystemLogic.updateLearnsystem(learnsystem);
    }

    public List<IndexcardBox> searchIndexcardBox(String text) {
        return indexcardBoxLogic.searchIndexcardBox(text);
    }

    /**
     * Search for Category`s with text in the category repository.
     *
     * @param text The Text.
     * @return A list of Category`s that contains the text.
     */
    public List<Category> searchCategory(String text) {
        return categoryLogic.searchCategory(text);
    }

    /**
     * Create a function which converts a list of indexcard names to a list of indexcards.
     * @param indexcardNames The list of indexcard names.
     *                       The list of indexcards.
     */
    public List<Indexcard> getIndexcardsByIndexcardNameList(List<String> indexcardNames) {
        return indexcardLogic.getIndexcardsByIndexcardNameList(indexcardNames);
    }
}