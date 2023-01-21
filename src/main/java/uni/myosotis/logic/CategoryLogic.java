package uni.myosotis.logic;

import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;
import uni.myosotis.persistence.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class CategoryLogic {

    /**
     * The repository for the Category's.
     */
    final CategoryRepository categoryRepository;

    /**
     * Creates a new CategoryLogic.
     */
    public CategoryLogic () {
        this.categoryRepository = new CategoryRepository();
    }



    /**
     * Creates a new Category and saves it in the database.
     * If already a Category with the same name exists, it will throw a IllegalStateException.
     *
     * @param name The name of the Category.
     * @param indexcards The Indexcards in this Category.
     */
    public void createCategory(String name, List<String> indexcards) {

        if (CategoryIsPresent(name)) {
            addIndexcardsToCategory(name,indexcards);
        } else {
            Category category = new Category(name, indexcards);
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
            Category category1 = category.get();
            category1.setIndexcardList(indexcards);
            categoryRepository.saveCategory(category1);
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
        Optional<Category> Category = getCategoryByName(name);
        if (Category.isPresent()) {
            Category.get().setName(newName);
            categoryRepository.updateCategory(Category.get(), newName, Category.get().getIndexcardList());
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
     * If there is no Category with the given name, it will throw a IllegalStateException.
     *
     * @param name The name of the Category.
     */
    public void deleteCategory(String name) {
        if (CategoryIsPresent(name)) {
            if (categoryRepository.deleteCategory(name) < 0) {
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
    public void updateCategory(String name, List<String> indexCardsNameList) {
        if (categoryRepository.getCategoryByName(name).isPresent()) {
            Category category2Edit = categoryRepository.getCategoryByName(name).get();

            // Updates all values of the old category2Edit.
            category2Edit.setName(name);
            category2Edit.setIndexcardList(indexCardsNameList);


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
