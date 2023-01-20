package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.persistence.IndexcardRepository;
import uni.myosotis.persistence.CategoryRepository;
import uni.myosotis.objects.Keyword;
import java.util.List;
import java.util.Optional;


public class IndexcardLogic {
    // Show all Indexcards
    // Sort by
    //

    /**
     * The repository for the Indexcards.
     */
    final IndexcardRepository indexcardRepository;
    final CategoryRepository categoryRepository;


    /**
     * Creates a new IndexcardLogic.
     */
    public IndexcardLogic () {

        this.indexcardRepository = new IndexcardRepository();
        this.categoryRepository = new CategoryRepository();

    }

    public Indexcard getIndexcardById(Long id) {
        Optional<Indexcard> indexcard = indexcardRepository.findIndexcardById(id);
        if (indexcard.isPresent()) {
            return indexcard.get();
        } else {
            throw new IllegalStateException("Indexcard with id " + id + " not found");
        }
    }

    /**
     * Creates a new Indexcard and saves it in the database.
     * If already an indexcard with the same name exists, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     * @param keywords Keywords which could be added to the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer, List<Keyword> keywords) {
        if (indexcardRepository.findIndexcardByName(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Indexcard indexcard = new Indexcard(name, question, answer, keywords);
            indexcardRepository.saveIndexcard(indexcard);
        }
    }

    /**
     * Edits an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     * @param keyword The keywords of the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void editIndexcard(String name, String question, String answer, List<Keyword> keywords, Long id) {
        if (indexcardRepository.findIndexcardById(id).isPresent()) {
            Indexcard indexcard = new Indexcard(name, question, answer, keywords, id);
            int checkvalue = indexcardRepository.updateIndexcard(indexcard);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
            }
        } else {
            throw new IllegalStateException("Die zu bearbeitende Karteikarte exisitiert nicht.");
        }
    }
    /**
     * Deletes an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given id, it will throw a IllegalStateException.
     *
     * @param id The id of the Indexcard.
     */
    public void deleteIndexcard(Long id) {
        if (indexcardRepository.findIndexcardById(id).isPresent()) {
            int checkvalue = indexcardRepository.deleteIndexcard(id);
            if (checkvalue != 0) {
                throw new IllegalStateException("Die Karteikarte konnte nicht verändert werden.");
            }
        } else {
            throw new IllegalStateException("Die zu löschende Karteikarte existiert nicht.");
        }
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public List<Indexcard> getAllIndexcards() {
        return indexcardRepository.findAllIndexcards();
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public List<Indexcard> getAllIndexcardsByKeyword(String keyword) {
        List<Indexcard> all = indexcardRepository.findAllIndexcards();
        all.removeIf(indexcard -> !indexcard.getKeyword().getKeywordWord().contains(keyword));
        return all;
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public List<Indexcard> getAllIndexcardsByCategory(String categoryName) {
        //List<Indexcard> all = indexcardRepository.findAllIndexcards();
        //all.removeIf(indexcard -> !indexcard.getCategoryList().getCategoryName().contains(categoryName));
        return indexcardRepository.getAllIndexcardByCategories(categoryName);
        //return all;
    }



    /**
     * Return the Indexcard with the given name.
     *
     * @param indexcard The name of the Indexcard.
     * @return The Indexcard if it exists.
     */
    public Optional<Indexcard> getIndexcardByName(String indexcard) {
        return indexcardRepository.findIndexcardByName(indexcard);
    }

    public Long getIndexcardId(String indexcard) {
        return indexcardRepository.findIndexcardIdByName(indexcard);
    }
    /**
     * Returns indexcardRepository.
     *
     * @return indexcardRepository
     */
    public IndexcardRepository getIndexcardRepository() {
        return indexcardRepository;
    }

}
