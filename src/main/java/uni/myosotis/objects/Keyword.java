package uni.myosotis.objects;

import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Keyword implements Serializable{

    @Id
    String word;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.EAGER)
    private List<Indexcard> indexcards;
    public Keyword() {
        // default constructor
    }

    public Keyword(String word) {
        this.word = word;
    }

    public Keyword(final String word, List<Indexcard> indexcards){
        this.word = word;
        this.indexcards = indexcards;
    }
    /*
    * Getter for the word of the Keyword.
    * @return The word of the Keyword.
    */
    public String getKeywordWord(){
        return word;
    }

    /*
    * setter for the word of the Keyword.
    * @param word The word of the Keyword.
    */
    public void setWord(String newWord) {
        word = newWord;
    }
    /*
    * Getter for Indexcard from Keyword.
    * @return The Indexcard of the Keyword.
    */
    public List<Indexcard> getIndexcards() {
        return indexcards;
    }
    /*
    * Adds an Indexcard to the Keyword.
    * @param indexcard The Indexcard which should be added.
    */
    public void addIndexcard(Indexcard indexcard){
        indexcards.add(indexcard);
    }
    /*
    * Sets the Indexcards of the Keyword.
    * @param indexcards The Indexcardslist which should be set.
     */
    public void setIndexcards(List<Indexcard> indexcards) {
        this.indexcards = indexcards;
    }

}
