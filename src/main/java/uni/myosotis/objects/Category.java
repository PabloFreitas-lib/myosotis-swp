package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Category implements Serializable{

    @Id
    String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Indexcard> indexcards;
    public Category() {
        // default constructor
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(final String name, List<Indexcard> indexcards){
        this.name = name;
        this.indexcards = indexcards;
    }
    /*
     * Getter for the word of the Category.
     * @return The word of the Category.
     */
    public String getCategoryName(){
        return name;
    }

    /*
     * setter for the word of the Category.
     * @param word The word of the Category.
     */
    public void setName(String newWord) {
        name = newWord;
    }
    /*
     * Getter for Indexcard from Category.
     * @return The Indexcard of the Category.
     */
    public List<Indexcard> getIndexcards() {
        return indexcards;
    }
    /*
     * Adds an Indexcard to the Category.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcard(Indexcard indexcard){
        indexcards.add(indexcard);
    }
    /*
     * Sets the Indexcards of the Category.
     * @param indexcards The Indexcardslist which should be set.
     */
    public void setIndexcards(List<Indexcard> indexcards) {
        this.indexcards = indexcards;
    }

}
