package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Category;
import uni.myosotis.persistence.CategoryRepository;

import java.util.List;
import java.util.Optional;


public class CategoryLogic {
    /**
     * The repository for the Categorys.
     */
    final CategoryRepository categoryRepository;

    /**
     * Creates a new CategorysLogic.
     */
    public CategoryLogic () {
        this.categoryRepository = new CategoryRepository();
    }



    /**
     * Creates a new Keyword and saves it in the database.
     * If already a Keyword with the same word exists, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     * @param indexcard The name of the Indexcard.
     */
    public void createCategory(String word, List<String> indexcards) {

        if (CategoryIsPresent(word)) {
            addIndexcardToCategory(word,indexcards);
        } else {
            Category category = new Category(word, indexcards);
            categoryRepository.saveCategory(category);
        }
    }

    /**
     * Adds an Indexcard to a Keyword.
     * @param word The word of the Keyword.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcardToCategory(String word, List<String> indexcards) {
        Optional<Category> category = categoryRepository.getCategoryByName(word);
        if (category.isPresent()) {
            Category category1 = category.get();
            category1.setIndexcardList(indexcards);
            categoryRepository.saveCategory(category1);
        }
    }
    /**
     * Returns if the Category already exists.
     * @param word The word of the Category.
     */
    public Boolean CategoryIsPresent(String word) {
        return categoryRepository.getCategoryByName(word).isPresent();
    }

    /**
     * edits the word of a Category.
     * @param word
     * @param newWord
     */
    public void editCategoryWord(String word, String newWord) {
        Optional<Category> Category = getCategoryByName(word);
        if (Category.isPresent()) {
            Category.get().setName(newWord);
            categoryRepository.updateCategory(Category.get(), newWord, Category.get().getIndexcardList());
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }
    /**
     * edits the Indexcards of a Category.
     * @param word
     * @param indexcards
     */
    public void editCategoryIndexcards(String word, List<String> indexcards) {
        Optional<Category> Category = getCategoryByName(word);
        if (Category.isPresent()) {
            Category.get().setIndexcardList(indexcards);
            categoryRepository.updateCategory(Category.get(), Category.get().getCategoryName(), indexcards);
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }

    /**
     * Deletes an existing Category and saves it in the database.
     * If there is no Category with the given word, it will throw a IllegalStateException.
     *
     * @param word The word of the Category.
     */
    public void deleteCategory(String word) {
        if (CategoryIsPresent(word)) {
            int checkvalue = categoryRepository.deleteCategory(word);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
            }
        }
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    /**
     * Return the category with the given word.
     *
     * @param category The word of the category.
     * @return The category if it exists.
     */
    public Optional<Category> getCategoryByName(String category) {
        return categoryRepository.getCategoryByName(category);
    }

    /**
     * Returns CategoryRepository.
     *
     * @return CategoryRepository
     */
    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    /**
     * Deletes a specific Indexcard from a category.
     * @param category
     * @param indexcards
     */
    public void deleteIndexcardFromCategory(Category category, List<String> indexcards) {
        categoryRepository.updateCategory(category, category.getCategoryName(), indexcards);
    }

    public void updateCategoryName(Category category, String name) {
        categoryRepository.updateCategory(category, name, category.getIndexcardList());
    }

    public Category getCategoryByWord(String categoryName) {
        if(categoryRepository.getCategoryByName(categoryName).isPresent()) {
            return categoryRepository.getCategoryByName(categoryName).get();
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }
}
