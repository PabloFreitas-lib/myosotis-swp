package uni.myosotis.objects;

import jakarta.persistence.*;

@Entity
public class LearnSystem {

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

    public LearnSystem() {

    }

    /**
     * Creates a new LearnSystem connected to the IndexcardBox.
     *
     * @param indexcardBox The connected IndexcardBox.
     */
    public LearnSystem(IndexcardBox indexcardBox)  {
        this.indexcardBox = indexcardBox;
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
     * Returns the id.
     *
     * @return The id.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the IndexcardBox.
     *
     * @return The IndexcardBox.
     */
    public IndexcardBox getIndexcardBox() {
        return indexcardBox;
    }

    /**
     * Sets the IndexcardBox.
     *
     * @param indexcardBox The IndexcardBox.
     */
    public void setIndexcardBox(IndexcardBox indexcardBox) {
        this.indexcardBox = indexcardBox;
    }
}
