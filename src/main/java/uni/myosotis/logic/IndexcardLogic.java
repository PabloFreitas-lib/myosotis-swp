package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;
import uni.myosotis.persistence.CategoryRepository;
import uni.myosotis.persistence.IndexcardRepository;

import java.util.List;
import java.util.Optional;

public class IndexcardLogic {

    /**
     * The repository for the Indexcards.
     */
    final IndexcardRepository indexcardRepository;

    /**
     * The repository for the Categorys.
     */
    final CategoryRepository categoryRepository;

    /**
     * Creates a new IndexcardLogic.
     */
    public IndexcardLogic () {

        this.indexcardRepository = new IndexcardRepository();
        this.categoryRepository = new CategoryRepository();

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
        if (indexcardRepository.getIndexcardByName(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            if (indexcardRepository.saveIndexcard(new Indexcard(name, question, answer, keywords)) < 0) {
                throw new IllegalStateException("Karteikarte konnte nicht erstellt werden, Fehler beim Speichern in der Datenbank");
            }
        }
    }

    /**
     * Edits an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given name, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     * @param keywords The keywords of the Indexcard.
     * @param id The id of the Indexcard.
     */
    public void updateIndexcard(String name, String question, String answer, List<Keyword> keywords, Long id) {
        if (indexcardRepository.getIndexcardById(id).isPresent()) {
            Indexcard indexcard = indexcardRepository.getIndexcardById(id).get();

            // Updates all values of the old indexcard.
            indexcard.setName(name);
            indexcard.setQuestion(question);
            indexcard.setAnswer(answer);
            indexcard.setKeywords(keywords);

            // Update in database failed.
            if (indexcardRepository.updateIndexcard(indexcard) < 0) {
                throw new IllegalStateException("Die Karteikarte konnte nicht aktualisiert werden.");
            }

        }
        // Invalid id, indexcard does not exist.
        else {
            throw new IllegalStateException("Die zu bearbeitende Karteikarte existiert nicht.");
        }
    }

    /**
     * Deletes an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given id, it will throw a IllegalStateException.
     *
     * @param id The id of the Indexcard.
     */
    public void deleteIndexcard(Long id) {
        if (indexcardRepository.getIndexcardById(id).isPresent()) {
            if (indexcardRepository.deleteIndexcard(id) < 0) {
                throw new IllegalStateException("Die Karteikarte konnte nicht gelöscht werden.");
            }
        } else {
            throw new IllegalStateException("Die zu löschende Karteikarte existiert nicht.");
        }
    }

    /**
     * Returns all Indexcards.
     *
     * @return          A list of all indexcards.
     */
    public List<Indexcard> getAllIndexcards() {
        return indexcardRepository.getAllIndexcards();
    }
    
    /**
     * Returns the Indexcard with the given id.
     *
     * @param id The id.
     * @return The Indexcard with this id.
     */
    public Indexcard getIndexcardById(Long id) {
        Optional<Indexcard> indexcard = indexcardRepository.getIndexcardById(id);
        if (indexcard.isPresent()) {
            return indexcard.get();
        } else {
            throw new IllegalStateException("Indexcard with id " + id + " not found");
        }
    }

    /**
     * Return the Indexcard with the given name.
     *
     * @param indexcard The name of the Indexcard.
     * @return The Indexcard if it exists.
     */
    public Optional<Indexcard> getIndexcardByName(String indexcard) {
        return indexcardRepository.getIndexcardByName(indexcard);
    }

    /**
     * Returns all Indexcards that have the given Keyword.
     *
     * @param           keyword The Keyword.
     * @return          All Indexcards with that Keyword.
     */
    public List<Indexcard> getIndexcardsByKeyword(String keyword) {

        List<Indexcard> indexcards = indexcardRepository.getAllIndexcards();

        // Remove the Indexcards that do not have the given keyword
        indexcards.removeIf(indexcard -> !indexcard.getKeywords().stream()
                .map(Keyword::getName)
                .toList()
                .contains(keyword));

        return indexcards;
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public List<Indexcard> getIndexcardsByCategory(String categoryName) {
        //List<Indexcard> all = indexcardRepository.findAllIndexcards();
        //all.removeIf(indexcard -> !indexcard.getCategoryList().getCategoryName().contains(categoryName));
        return indexcardRepository.getAllIndexcardByCategories(categoryName);
        //return all;
    }
    /**
     * Edits an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given indexCardName, it will throw a IllegalStateException.
     *
     * @param indexCardName The Name of the Indexcard.
     */
    public void updateCategoryFromIndexcard(String indexCardName, String categoryName) {
        if (indexcardRepository.getIndexcardByName(indexCardName).isPresent()) {
            Indexcard indexcard = indexcardRepository.getIndexcardByName(indexCardName).get();

            // Updates all values of the old indexcard.
            indexcard.setName(indexCardName);
            indexcard.addCategoryList(categoryName);

            // Update in database failed.
            if (indexcardRepository.updateIndexcard(indexcard) < 0) {
                throw new IllegalStateException("Die Karteikarte konnte nicht aktualisiert werden.");
            }

        }
        // Invalid id, indexcard does not exist.
        else {
            throw new IllegalStateException("Die zu bearbeitende Karteikarte existiert nicht." + indexCardName);
        }
    }

    /**
     * Edits an existing Indexcard and saves it in the database.
     * If there is no indexcard with the given indexCardName, it will throw a IllegalStateException.
     *
     * @param indexCardName The Name of the Indexcard.
     */
    public void removeCategoryFromIndexcard(String indexCardName, String categoryName) {
        if (indexcardRepository.getIndexcardByName(indexCardName).isPresent()) {
            Indexcard indexcard = indexcardRepository.getIndexcardByName(indexCardName).get();

            // Updates all values of the old indexcard.
            indexcard.setName(indexCardName);
            indexcard.removeCategoryList(categoryName);

            // Update in database failed.
            if (indexcardRepository.updateIndexcard(indexcard) < 0) {
                throw new IllegalStateException("Die Karteikarte konnte nicht aktualisiert werden.");
            }

        }
        // Invalid id, indexcard does not exist.
        else {
            throw new IllegalStateException("Die zu bearbeitende Karteikarte existiert nicht." + indexCardName);
        }
    }
}
