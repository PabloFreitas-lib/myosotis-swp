package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class IndexcardBox implements Serializable{

    @Id
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categoryList;

    public IndexcardBox(){

    }
    /**
     * Creates a new indexcard box
     * @param name Name of the box
     * @param categoryList List of categories
     */
    public IndexcardBox(String name, List<Category> categoryList){
        this.name = name;
        this.categoryList = categoryList;

    }
    /**
    * get Name of the box.
    * @return Name of the box.
    * */
    public String getName() {
        return name;
    }
    /**
     * set Name of the box.*/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * get the category list.
    * @return List of Categories*/
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Returns a list of all indexcards in the box, without duplicates
     * By checking if the indexcard is already in the list, duplicates are avoided
     * @return List of indexcards
     */
    public List<Indexcard> getIndexcardList() {
        List<Indexcard> indexcardList = new ArrayList<>();
        for (Category category : categoryList) {
            for (Indexcard indexcard : category.getIndexcardList()) {
                if (!indexcardList.contains(indexcard)) {
                    indexcardList.add(indexcard);
                }
            }
        }
        return indexcardList;
    }
    /**
     * Returns a list of all indexcards in the box
     * @return List of indexcards
     */
    /*public HashSet<Indexcard> getIndexcardList() {
        HashSet<Indexcard> indexcardList = new HashSet<>();
        for (Category category : categoryList) {
            indexcardList.addAll(category.getIndexcardList());
        }
        return indexcardList;
    }*/

    /**
     * Returns a list of all categories names in the box
     * @return List of categories names
     */
    public String[] getCategoryNameList() {
        return categoryList.stream().map(Category::getName).toList().toArray(new String[0]);
    }
    
    /*
     * set the category list.
     */
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

}