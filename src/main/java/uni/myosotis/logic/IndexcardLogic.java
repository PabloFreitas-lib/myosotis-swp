package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.persistence.IndexcardRepository;
import uni.myosotis.objects.Keyword;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class IndexcardLogic {
    // Show all Indexcards
    // Sort by
    //

    /**
     * The repository for the Indexcards.
     */
    final IndexcardRepository indexcardRepository;


    /**
     * Creates a new IndexcardLogic.
     */
    public IndexcardLogic () {

        this.indexcardRepository = new IndexcardRepository();

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
     */
    public void createIndexcard(String name, String question, String answer) {
        if (indexcardRepository.findIndexcardByName(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Indexcard indexcard = new Indexcard(name, question, answer);
            indexcardRepository.saveIndexcard(indexcard);
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
    public void createIndexcard(String name, String question, String answer, Keyword keyword) {
        if (indexcardRepository.findIndexcardByName(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Indexcard indexcard = new Indexcard(name, question, answer, keyword);
            indexcardRepository.saveIndexcard(indexcard);
        }
    }
    /**
     * Edits a existing Indexcard and saves it in the database.
     * If there is no indexcard with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     */
    public void EditIndexcard(String name, String question, String answer, Long id) {
        if (indexcardRepository.findIndexcardById(id).isPresent()) {
            Indexcard indexcard = new Indexcard(name, question, answer,null, id);
            int checkvalue = indexcardRepository.updateIndexcard(indexcard);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
            }
        }
    }

    /**
     * Edits a existing Indexcard and saves it in the database.
     * If there is no indexcard with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     * @param keyword The keywords of the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void EditIndexcard(String name, String question, String answer, Keyword keyword, Long id) {
        if (indexcardRepository.findIndexcardById(id).isPresent()) {
            Indexcard indexcard = new Indexcard(name, question, answer, keyword, id);
            int checkvalue = indexcardRepository.updateIndexcard(indexcard);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
            }
        }
    }
    /**
     * Deletes a existing Indexcard and saves it in the database.
     * If there is no indexcard with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     */
    public void DeleteIndexcard(Long id) {
        if (indexcardRepository.findIndexcardById(id).isPresent()) {
            int checkvalue = indexcardRepository.deleteIndexcard(id);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
            }
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
