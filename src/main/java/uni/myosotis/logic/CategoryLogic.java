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
    final CategoryRepository CategoryRepository;

    /**
     * Creates a new CategorysLogic.
     */
    public CategoryLogic () {
        this.CategoryRepository = new CategoryRepository();
    }


    /**
     * Creates a new Category and saves it in the database.
     *
     * @param word The word of the Category.
     */
    public void createCategoryWithNoIndexcard(String word) {
        Category Category = new Category(word);
        CategoryRepository.saveCategory(Category);
    }

    /**
     * Creates a new Category and saves it in the database.
     * If already a Category with the same name exists, it will throw a IllegalStateException.
     *
     * @param name The name of the Category.
     * @param indexcardList The name of the Indexcard.
     */
    public void createCategory(String name, List<Indexcard> indexcardList) {
        if (CategoryIsPresent(name)) {
            for (int i = 0; i < indexcardList.size(); i++) {
                addIndexcardToCategory(indexcardList.get(i), name);
            }
        } else {
            Category category = new Category(name, indexcardList);
            CategoryRepository.saveCategory(category);
        }
    }
    /**
     * Adds an Indexcard to a Category.
     *
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcardToCategory(Indexcard indexcard, String name) {
        Optional<Category> Category = CategoryRepository.getCategoryByName(name);
        if (Category.isPresent()) {
            Category Category1 = Category.get();
            Category1.addIndexcard(indexcard);
            CategoryRepository.saveCategory(Category1);
        }
    }
    /**
     * Returns if the Category already exists.
     * @param word The word of the Category.
     */
    public Boolean CategoryIsPresent(String word) {
        return CategoryRepository.getCategoryByName(word).isPresent();
    }

    /**
     * edits the word of a Category.
     * @param word
     * @param newWord
     */
    public void editCategoryWord(String word, String newWord) {
        Optional<Category> Category = getCategory(word);
        if (Category.isPresent()) {
            Category.get().setName(newWord);
            CategoryRepository.updateCategory(Category.get(), newWord, Category.get().getIndexcards());
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }
    /**
     * edits the Indexcards of a Category.
     * @param word
     * @param indexcards
     */
    public void editCategoryIndexcards(String word, List<Indexcard> indexcards) {
        Optional<Category> Category = getCategory(word);
        if (Category.isPresent()) {
            Category.get().setIndexcards(indexcards);
            CategoryRepository.updateCategory(Category.get(), Category.get().getCategoryName(), indexcards);
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
            int checkvalue = CategoryRepository.deleteCategory(word);
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
    public Optional<List<Category>> getAllCategories() {
        return CategoryRepository.getAllCategories();
    }

    /**
     * Return the category with the given word.
     *
     * @param category The word of the category.
     * @return The category if it exists.
     */
    public Optional<Category> getCategory (String category) {
        return CategoryRepository.getCategoryByName(category);
    }

    /**
     * Returns CategoryRepository.
     *
     * @return CategoryRepository
     */
    public CategoryRepository getCategoryRepository() {
        return CategoryRepository;
    }

    /**
     * Deletes a specific Indexcard from a category.
     * @param category
     * @param indexcards
     */
    public void deleteIndexcardFromCategory(Category category, List<Indexcard> indexcards) {
        CategoryRepository.updateCategory(category, category.getCategoryName(), indexcards);
    }

    public void updateCategoryName(Category category, String name) {
        CategoryRepository.updateCategory(category, name, category.getIndexcards());
    }

    public Category getCategoryByWord(String categoryName) {
        if(CategoryRepository.getCategoryByName(categoryName).isPresent()) {
            return CategoryRepository.getCategoryByName(categoryName).get();
        } else {
            throw new IllegalStateException("Es existiert keine Kategorie mit diesem Namen.");
        }
    }
}
