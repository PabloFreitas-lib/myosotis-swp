package uni.myosotis.objects;

import jakarta.persistence.*;
import uni.myosotis.persistence.IndexcardBoxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Entity
public class LeitnerLearnSystem {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LeitnerLearnSystem.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private final String name = "LeitnerLearnSystem";
    @OneToMany
    private List<Box> boxes;

    private List<String> indexcardList;
    private int progress;

    public LeitnerLearnSystem() {
    }

    /**
     * Creates a new LeitnerLearnSystem.
     * All indexcards are places in the first box.
     * @param indexcardList The list of indexcards that should be learned.
     */
    public LeitnerLearnSystem(List<String> indexcardList) {
        this.indexcardList = indexcardList;
        this.progress = 0;
        this.boxes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.boxes.add(new Box());
        }
        // add all indexcards to the first box
        this.boxes.get(0).setIndexcardNames(indexcardList);
        logger.log(Level.INFO, "LeitnerLearnSystem created");
    }


    public Long getId() {
        return id;
    }
    @Entity
    public class Box {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        public long getId() {
            return id;
        }

        List<String> indexcardNames = new ArrayList<>();

        public List<String> getIndexcardNames() {
            return indexcardNames;
        }

        public void setIndexcardNames(List<String> indexcardNames) {
            this.indexcardNames = indexcardNames;
        }

        public void addIndexcard(String indexcardName) {
            this.indexcardNames.add(indexcardName);
        }

        public void removeIndexcard(String indexcardName) {
            this.indexcardNames.remove(indexcardName);
        }
    }

    /**
     * This method is called when the user answers a question correctly.
     * The indexcard is moved to the next box.
     * @param indexcard
     */
    public void correctAnswer(Indexcard indexcard) {
        logger.log(Level.INFO, "correctAnswer for indexcard: " + indexcard.getName());
        logger.log(Level.INFO, "indexcard is moved to the next box");
    }

    /**
     * This method is called when the user answers a question incorrectly.
     * The indexcard is moved to the first box.
     * @param indexcard
     */
    public void wrongAnswer(Indexcard indexcard) {
        logger.log(Level.INFO, "wrongAnswer for indexcard: " + indexcard.getName());
        logger.log(Level.INFO, "indexcard is moved to the previously box");
    }


    public String getName(){
        return this.name;
    }

    public List<String> getIndexcardFromBox(int boxNumber) {
        return this.boxes.get(boxNumber).getIndexcardNames();
    }


    /**
     * this method return the indexcard that should be learned next.
     * If the progress is 0, the first box is returned
     * If the progress is 1, the first and the second box are returned
     * If the progress is 2, the first, the second and the third box are returned
     * If the progress is 3, the first, the second, the third and the fourth box are returned
     * If the progress is 4, the first, the second, the third, the fourth and the fifth box are returned
     * If the progress is 5 All the Indexcards were learned.
     * @return The indexcards that should be learned next.
     */
    public List<String> getNextIndexcards() {
        List<String> indexcards = new ArrayList<>();
        for (int i = 0; i <= this.progress; i++) {
            indexcards.addAll(this.boxes.get(i).getIndexcardNames());
        }
        return indexcards;
    }
}
