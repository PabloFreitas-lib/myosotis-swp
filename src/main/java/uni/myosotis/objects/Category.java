package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Category implements Serializable{

    @Id
    String name;

    //@OneToMany(mappedBy = "categoryList", fetch = FetchType.EAGER)
    private List<String> indexcardListNames;


    public Category() {
    }

    public Category(final String name, List<String> indexcardListNames){
        this.name = name;
        this.indexcardListNames = indexcardListNames;
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
    public List<String> getIndexcardList() {
        return this.indexcardListNames;
    }
    /*
     * Adds an Indexcard to the Category.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcard(String indexcard){
        indexcardListNames.add(indexcard);
    }
    /*
     * Sets the Indexcards of the Category.
     * @param indexcards The Indexcardslist which should be set.
     */
    public void setIndexcardList(List<String> indexcardList) {
        this.indexcardListNames = indexcardList;
    }

}
