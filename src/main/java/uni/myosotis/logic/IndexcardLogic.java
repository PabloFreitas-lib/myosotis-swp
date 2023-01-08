package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.persistence.IndexcardRepository;

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

    /**
     * Creates a new Indexcard and saves it in the database.
     * If already an indexcard with the same name exists, it will throw a IllegalStateException.
     *
     * @param name The Name of the Indexcard.
     * @param question  The Question of the Indexcard.
     * @param answer The Answer of the Indexcard.
     */
    public void createIndexcard(String name, String question, String answer) {
        if (indexcardRepository.findIndexcard(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Indexcard indexcard = new Indexcard(name, question, answer);
            indexcardRepository.saveIndexcard(indexcard);
        }
    }
}
