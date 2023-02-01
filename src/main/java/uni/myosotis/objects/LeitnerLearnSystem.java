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

    private String name = "LeitnerLearnSystem";
    @OneToMany
    private List<Box> boxes;

    private List<String> indexcardList;
    private int progress;

    private final int numberOfBoxes = 5;

    public LeitnerLearnSystem() {
    }

    /**
     * Creates a new LeitnerLearnSystem.
     * All indexcards are places in the first box.
     * @param indexcardList The list of indexcards that should be learned.
     */
    public LeitnerLearnSystem(String name, List<String> indexcardList) {
        this.name = this.name + name;
        this.indexcardList = indexcardList;
        this.progress = 0;
        this.boxes = new ArrayList<>();
        for (int i = 0; i < numberOfBoxes; i++) {
            this.boxes.add(new Box());
        }
        // add all indexcards to the first box
        this.boxes.get(0).setIndexcardNames(indexcardList);
        logger.log(Level.INFO, "LeitnerLearnSystem created: ", getName());
    }


    public Long getId() {
        return id;
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
     * @return The indexcards that should be learned next.
     */
    public List<String> getNextIndexcards() {
        List<String> indexcards = new ArrayList<>();
        for (int i = 0; i < this.numberOfBoxes; i++) {
            indexcards.addAll(this.boxes.get(i).getIndexcardNames());
        }
        return indexcards;
    }

    public void increaseProgress() {
        if (indexcardList.size() > this.progress)
            this.progress++;
    }

    public void decreaseProgress() {
        if (this.progress > 0)
            this.progress--;
    }

    public int getProgress() {
        return progress;
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}
