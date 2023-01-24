package uni.myosotis.objects;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    //private String description;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Indexcard> indexcardList;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Category> children;

    // Constructors

    public Category() {
        children = new ArrayList<>();
    }

    public Category(String name, List<Indexcard> indexcardListNames) {
        this();
        this.name = name;
        this.indexcardList = indexcardListNames;
    }

    public Category(String name, List<Indexcard> indexcardListNames, Category parent) {
        this(name, indexcardListNames);
        this.parent = parent;
        parent.addChild(this);
    }


    /*
     * Getter for the word of the Category.
     * @return The word of the Category.
     */
    public String getCategoryName(){
        return name;
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


    public void setChildren(List<Category> children) {
        this.children = children;
    }

    // getters
    // Getter for the id
    public Long getId() {
        return id;
    }

    // Getter for the name
    public String getName() {
        return name;
    }

    // Getter for the description
    //public String getDescription() {
    //    return description;
    //}

    // Getter for the parent category
    public Category getParent() {
        return parent;
    }

    //setters
    // Setter for the name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for the description
    //public void setDescription(String description) {
    //    this.description = description;
    //}

    // Setter for the parent category
    public void setParent(Category parent) {
        this.parent = parent;
    }

    //others methods
    // Method to add a child category
    public void addChild(Category child) {
        children.add(child);
        child.setParent(this);
    }

    // Method to remove a child category
    public void removeChild(Category child) {
        children.remove(child);
        child.setParent(null);
    }

    // Method to get all the children of a category
    public List<Category> getChildren() {
        return children;
    }

    // Method to check if a category has children
    public boolean hasChildren() {
        return !children.isEmpty();
    }

}
