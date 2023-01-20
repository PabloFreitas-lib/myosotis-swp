package uni.myosotis.objects;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Keyword implements Serializable {

    @Id
    String name;

    @ManyToMany
    private List<Indexcard> indexcards;

    public Keyword() {
        // default constructor
    }

    /**
     * Constructor of Keyword.
     *
     * @param keyword      The name of the Keyword.
     */
    public Keyword(String keyword) {
        this.name = keyword;
    }

    /**
     * Method to obtain the name of a keyword.
     *
     * @return      The name of the keyword.
     */
    public String getName() {
        return name;
    }

    // END OF CLASS





























/*@OneToMany(mappedBy = "keyword", fetch = FetchType.EAGER)
    private List<Indexcard> indexcards;*/

    /*public Keyword(final String word, List<Indexcard> indexcards){
        this.word = word;
        this.indexcards = indexcards;
    }*/

    /**
    * Setter for the word of the Keyword.
    * @param newWord The word of the Keyword.
    */
    /*
    public void setWord(String newWord) {
        word = newWord;
    }*/

    /**
    * Getter for Indexcard from Keyword.
    * @return The Indexcard of the Keyword.
    */
    /*
    public List<Indexcard> getIndexcards() {
        return indexcards;
    }*/

    /**
    * Adds an Indexcard to the Keyword.
    * @param indexcard The Indexcard which should be added.
    */
    /*
    public void addIndexcard(Indexcard indexcard){
        indexcards.add(indexcard);
    }*/

    /**
    * Sets the Indexcards of the Keyword.
    * @param indexcards The Indexcardslist which should be set.
     */
    /*
    public void setIndexcards(List<Indexcard> indexcards) {
        this.indexcards = indexcards;
    }*/

}
