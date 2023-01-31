package uni.myosotis.objects;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

/**
 * This class is used as base to all LearnSystem objects.
 * It has the attributes as name, boxes, progress, indexcardBox, etc.
 */
public class LearnSystemReal implements Serializable {

    private final String name;

    private int progress;

    private IndexcardBox indexcardBox;

    private List<Box> boxes;

    private final int numberOfBoxes;

    /**
     * Represents the boxes of the LearnSystem.
     */
    class Box implements Serializable {
        private String name;
        private List<String> indexCardNames;

        // here should be information about how frequently the box should be used
        private String description;

        public Box(String name) {
            this.name = name;
            this.description = "This is the description of the box " + name;
            this.indexCardNames = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void addIndexCard(String card) {
            indexCardNames.add(card);
        }

        public List<String> getIndexCards() {
            return indexCardNames;
        }

        public void setIndexCards(List<String> indexCards) {
            this.indexCardNames = indexCards;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        // end class Box
    }

    /**
     * Constructor of the LearnSystemReal class.
     */
    public LearnSystemReal(final String name, final int numberOfBoxes, final IndexcardBox indexcardBox){
        this.name = name;
        this.indexcardBox = indexcardBox;
        // create the boxes
        this.boxes = new ArrayList<>();
        this.numberOfBoxes = numberOfBoxes;
        for (int i = 0; i < this.numberOfBoxes; i++) {
            this.boxes.add(new Box("Box " + i));
        }
        // the progress is 0 at the beginning
        this.progress = 0;
        // all the indexcards inside the indexcardBox are added to the first box
        for (String indexcard : indexcardBox.getIndexcardList()) {
            this.boxes.get(0).addIndexCard(indexcard);
        }
    }
    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public void addBox(Box box) {
        // check if the box is already in the list
        if (this.boxes.contains(box)) {
            return;
        }
        this.boxes.add(box);
    }

    public void removeBox(Box box) {
        // check if the box is in the list
        if (!this.boxes.contains(box)) {
            return;
        }
        this.boxes.remove(box);
    }

    /**
     * Move a indexcard from one box to the next one.
     */
    public void moveIndexcard(String indexcard, int fromBox, int toBox) {
        // check if the boxes are in the list
        if (fromBox >= this.boxes.size() || toBox >= this.boxes.size()) {
            return;
        }
        // check if the indexcard is in the box
        if (!this.boxes.get(fromBox).getIndexCards().contains(indexcard)) {
            return;
        }
        // remove the indexcard from the box
        this.boxes.get(fromBox).getIndexCards().remove(indexcard);
        // add the indexcard to the next box
        this.boxes.get(toBox).addIndexCard(indexcard);
    }
    
    // end class LearnSystemReal
}
