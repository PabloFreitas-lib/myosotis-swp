package uni.myosotis.objects;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public abstract class Learnsystem {

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

    public Learnsystem() {

    }

    /**
     * Creates a new Learnsystem connected to the IndexcardBox.
     *
     * @param indexcardBox The connected IndexcardBox.
     */
    public Learnsystem(IndexcardBox indexcardBox)  {
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
}
