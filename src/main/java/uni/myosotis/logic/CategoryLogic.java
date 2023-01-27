package uni.myosotis.logic;

import uni.myosotis.objects.*;
import uni.myosotis.persistence.CategoryRepository;

import javax.swing.*;
import java.util.*;

public class CategoryLogic {

    /**
     * The repository for the Category's.
     */
    final CategoryRepository categoryRepository;

    final IndexcardLogic indexcardLogic;

    final IndexcardBoxLogic indexcardBoxLogic;

    /**
     * Creates a new CategoryLogic.
     */
    public CategoryLogic () {

        this.indexcardBoxLogic = new IndexcardBoxLogic();
        this.categoryRepository = new CategoryRepository();
        this.indexcardLogic = new IndexcardLogic();
    }

    /**
     * Search for a category in a CategoryGraph
     */
    public Category search(CategoryGraph graph, String name) {
        Set<Category> visited = new HashSet<>();
        for (Category root : graph.getRoots()) {
            Category result = DFS(root, name, visited);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    /**
     * Search for a category in a CategoryGraph DFS
     * FIXME
     */
    private Category DFS(Category node, String name, Set<Category> visited) {
        if (node.getCategoryName().equals(name)) {
            return node;
        }
        visited.add(node);

        return null;
    }



    /**
     * Creates a new Category and saves it in the database.
     * If already a Category with the same categoryName exists, it will throw a IllegalStateException.
     *
     * @param categoryName The categoryName of the Category.
     * @param indexcardList The Indexcards in this Category.
     */
    public void createCategory(String categoryName, List<String> indexcardList) {
        for(int i = 0; i < indexcardList.size(); i++)
            indexcardLogic.updateCategoryFromIndexcard(indexcardList.get(i),categoryName);

        if (CategoryIsPresent(categoryName)) {
            addIndexcardsToCategory(categoryName,indexcardList);
        } else {
            Category category = new Category(categoryName, indexcardList);
            categoryRepository.saveCategory(category);
        }
    }

    /**
     * Creates a new Category and saves it in the database.
     * If already a Category with the same categoryName exists, it will throw a IllegalStateException.
     *
     * @param categoryName The categoryName of the Category.
     * @param indexcardList The Indexcards in this Category.
     * @param parent The parent Category.
     */
    public void createCategory(String categoryName, List<String> indexcardList,Category parent) {
        for(int i = 0; i < indexcardList.size(); i++)
            indexcardLogic.updateCategoryFromIndexcard(indexcardList.get(i),categoryName);

        if (CategoryIsPresent(categoryName)) {
            addIndexcardsToCategory(categoryName,indexcardList);
        } else {
            Category category = new Category(categoryName, indexcardList,parent);
            categoryRepository.saveCategory(category);
        }
    }

    /**
     * Adds Indexcard to a Category.
     * @param name The name of the Category.
     * @param indexcards The Indexcards which should be added.
     */
    public void addIndexcardsToCategory(String name, List<String> indexcards) {
        Optional<Category> category = categoryRepository.getCategoryByName(name);
        if (category.isPresent()) {
            Category category2Save = category.get();
            category2Save.setIndexcardList(indexcards);
            categoryRepository.saveCategory(category2Save);
        }
    }
    /**
     * Returns if the Category already exists.
     * @param name The name of the Category.
     */
    public Boolean CategoryIsPresent(String name) {
        return categoryRepository.getCategoryByName(name).isPresent();
    }

    /**
     * Edits the name of a Category.
     * @param name The current name of the Category
     * @param newName The new name of the Category
     */
    public void editCategoryName(String name, String newName) {
        Optional<Category> category = getCategoryByName(name);
        if (category.isPresent()) {
            category.get().setName(newName);
            categoryRepository.updateCategory(category.get(), newName, category.get().getIndexcardList());
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }

    /**
     * Edits the Indexcards of a Category.
     *
     * @param name The name of the Category.
     * @param indexcards The updated Indexcards.
     */
    public void editCategoryIndexcards(String name, List<String> indexcards) {
        Optional<Category> Category = getCategoryByName(name);
        if (Category.isPresent()) {
            Category.get().setIndexcardList(indexcards);
            categoryRepository.updateCategory(Category.get(), Category.get().getCategoryName(), indexcards);
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }

    /**
     * Deletes an existing Category from the database.
     * If there is no Category with the given categoryName, it will throw a IllegalStateException.
     *
     * @param categoryName The categoryName of the Category.
     */
    public void deleteCategory(String categoryName) {

        if (CategoryIsPresent(categoryName)) {
            Category category2delete = getCategoryByName(categoryName).get();
            //Fix for Bug:
            List<IndexcardBox> boxContains = indexcardBoxLogic.getAllIndexcardBoxes();
            for(int i = 0; i < boxContains.size(); i++){
                if(Arrays.asList(boxContains.get(i).getCategoryNameList()).contains(categoryName)) {
                    List<Category> temp = boxContains.get(i).getCategoryList();
                    for(int j = 0; j < temp.size(); j++) {
                        temp.removeIf(s -> s.getName().equals(categoryName));
                        indexcardBoxLogic.updateIndexcardBox(boxContains.get(i).getName(), temp);
                    }
                }
            }
            //End of Fix!
            List<String> indexCardsNameList = category2delete.getIndexcardList();
            for(int i = 0; i < indexCardsNameList.size(); i++)
                indexcardLogic.removeCategoryFromIndexcard(indexCardsNameList.get(i),categoryName);
            if (categoryRepository.deleteCategory(categoryName) < 0) {
                throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
            }
        }
    }

    /**
     * Returns all Category's.
     *
     * @return All Category's.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    /**
     * Return the category with the given name.
     *
     * @param category The name of the category.
     * @return The category if it exists.
     */
    public Optional<Category> getCategoryByName(String category) {
        return categoryRepository.getCategoryByName(category);
    }

    /**
     * Deletes specific Indexcards from a category.
     *
     * @param category The Category
     * @param indexcards The indexcards
     */
    public void deleteIndexcardFromCategory(Category category, List<String> indexcards) {
        categoryRepository.updateCategory(category, category.getCategoryName(), indexcards);
    }

    /**
     * Edits an existing Category and saves it in the database.
     * If there is no Category with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Category.
     * @param indexCardsNameList  The Question of the Category.
     */
    public void updateCategory(String name, List<String> indexCardsNameList, Category parent) {
        if (categoryRepository.getCategoryByName(name).isPresent()) {
            Category category2Edit = categoryRepository.getCategoryByName(name).get();
            List<String> allIndexcardsList = indexcardLogic.getAllIndexcards().stream().
                    map(Indexcard::getName).toList();
            // Updates all values of the old category2Edit.
            category2Edit.setName(name);
            category2Edit.setIndexcardList(indexCardsNameList);
            // Update the indexCards categories
            for(int i = 0; i < allIndexcardsList.size(); i++)
                indexcardLogic.removeCategoryFromIndexcard(allIndexcardsList.get(i),name);
            // Update the indexCards categories
            for(int i = 0; i < indexCardsNameList.size(); i++)
                indexcardLogic.updateCategoryFromIndexcard(indexCardsNameList.get(i),name);

            // Update in database failed.
            if (categoryRepository.updateCategory(category2Edit,name, indexCardsNameList,parent) < 0) {
                throw new IllegalStateException("Die Kategorie konnte nicht aktualisiert werden.");
            }
        }
        // Invalid id, Category does not exist.
        else {
            throw new IllegalStateException("Die zu bearbeitende Kategorie existiert nicht.");
        }
    }

    /**
     * Edits an existing Category and saves it in the database.
     * If there is no Category with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Category.
     * @param indexCardsNameList  The Question of the Category.
     */
    public void updateCategory(String name, List<String> indexCardsNameList) {
        if (categoryRepository.getCategoryByName(name).isPresent()) {
            Category category2Edit = categoryRepository.getCategoryByName(name).get();
            List<String> allIndexcardsList = indexcardLogic.getAllIndexcards().stream().
                    map(Indexcard::getName).toList();
            // Updates all values of the old category2Edit.
            category2Edit.setName(name);
            category2Edit.setIndexcardList(indexCardsNameList);
            // Update the indexCards categories
            for(int i = 0; i < allIndexcardsList.size(); i++)
                indexcardLogic.removeCategoryFromIndexcard(allIndexcardsList.get(i),name);
            // Update the indexCards categories
            for(int i = 0; i < indexCardsNameList.size(); i++)
                indexcardLogic.updateCategoryFromIndexcard(indexCardsNameList.get(i),name);

            // Update in database failed.
            if (categoryRepository.updateCategory(category2Edit,name, indexCardsNameList) < 0) {
                throw new IllegalStateException("Die Kategorie konnte nicht aktualisiert werden.");
            }
        }
        // Invalid id, Category does not exist.
        else {
            throw new IllegalStateException("Die zu bearbeitende Kategorie existiert nicht.");
        }
    }


}
