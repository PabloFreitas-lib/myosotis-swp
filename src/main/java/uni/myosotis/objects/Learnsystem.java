package uni.myosotis.objects;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Learnsystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The progress.
     */
    private int progress;

    /**
     * The IndexcardBox.
     */
    @OneToOne
    private IndexcardBox indexcardBox;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Indexcard> indexcardsToLearn;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Indexcard> learnedIndexcards;

    public Learnsystem() {

    }

    /**
     * Creates a new Learnsystem connected to the IndexcardBox.
     *
     * @param indexcardBox The connected IndexcardBox.
     */
    public Learnsystem(IndexcardBox indexcardBox)  {
        this.indexcardBox = indexcardBox;
        learnedIndexcards = new ArrayList<>();
        indexcardsToLearn = new ArrayList<>();
        for (Category category : indexcardBox.getCategoryList()) {
            indexcardsToLearn.addAll(category.getIndexcardList());
        }
        this.progress = 0;
    }

    /**
     * Returns the progress.
     *
     * @return The progress.
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Sets the progress to a new Value.
     *
     * @param progress The updated progress-value.
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * Returns the Indexcards that are to learn.
     *
     * @return The not learned Indexcards.
     */
    public List<Indexcard> getIndexcardsToLearn() {
        return indexcardsToLearn;
    }

    /**
     * Returns the Indexcards that are learned.
     *
     * @return The learned Indexcards.
     */
    public List<Indexcard> getLearnedIndexcards() {
        return learnedIndexcards;
    }

    /**
     * Returns the id.
     *
     * @return The id.
     */
    public long getId() {
        return id;
    }
}
