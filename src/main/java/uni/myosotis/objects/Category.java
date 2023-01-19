package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Category implements Serializable{

    @Id
    String name;

    @OneToMany(mappedBy = "categoryList", fetch = FetchType.EAGER)
    private List<Indexcard> indexcardList;


    public Category() {
    }

    public Category(final String name, List<Indexcard> indexcardList){
        this.name = name;
        this.indexcardList = indexcardList;
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
    public List<Indexcard> getIndexcardList() {
        return this.indexcardList;
    }
    /*
     * Adds an Indexcard to the Category.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcard(Indexcard indexcard){
        indexcardList.add(indexcard);
    }
    /*
     * Sets the Indexcards of the Category.
     * @param indexcards The Indexcardslist which should be set.
     */
    public void setIndexcardList(List<Indexcard> indexcardList) {
        this.indexcardList = indexcardList;
    }

}
