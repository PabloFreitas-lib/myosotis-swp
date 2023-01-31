package uni.myosotis.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LeitnerLearnSystem extends LearnSystem {

    @OneToMany
    private List<Indexcard> box1;
    @OneToMany
    private List<Indexcard> box2;
    @OneToMany
    private List<Indexcard> box3;

    public LeitnerLearnSystem() {
    }

    public LeitnerLearnSystem(IndexcardBox indexcardBox) {
        super(indexcardBox);
        box1 = new ArrayList<>();
        for (Category category : indexcardBox.getCategoryList()) {
        }
        box2 = new ArrayList<>();
        box3 = new ArrayList<>();
    }

    public List<Indexcard> getBox1() {
        return box1;
    }

    public List<Indexcard> getBox2() {
        return box2;
    }

    public List<Indexcard> getBox3() {
        return box3;
    }

    public void setBoxOfIndexcard(Indexcard indexcard, List<Indexcard> currentBox) {

    }

}
