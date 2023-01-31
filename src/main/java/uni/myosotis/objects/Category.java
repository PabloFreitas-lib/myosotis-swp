package uni.myosotis.objects;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import uni.myosotis.persistence.CategoryRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "category")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    private List<String> indexcardListNames;

    private static final Logger logger = Logger.getLogger(Category.class.getName());
     /**
     * Constructor for the Category.
     */
    public Category() {
    }
    /**
     * Constructor for the Category.
     * @param name The name of the Category.
     * @param indexcardListNames The name of the Category.
     */
    public Category(String name, List<String> indexcardListNames) {
        this();
        this.name = name;
        this.indexcardListNames = indexcardListNames;
    }
    /**
     * Constructor for the Category.
     * @param indexcardListNames The name of the Category.
     * @param parent The parent of the Category.
     */
    public Category(String name, List<String> indexcardListNames, Category parent) {
        this(name, indexcardListNames);
        this.parent = parent;
    }


    /**
     * Getter for the word of the Category.
     * @return The word of the Category.
     */
    public String getCategoryName(){
        return name;
    }

    /**
     * Getter for Indexcard names from Category checking the children cateories also.
     * @return The Indexcard of the Category.
     *
     */
    public List<String> getIndexcardList() {
        return indexcardListNames;
    }
    /**
     * Adds an Indexcard to the Category.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcard(String indexcard){
        indexcardListNames.add(indexcard);
    }
    /**
     * Sets the Indexcards of the Category.
     * @param indexcardList The Indexcardslist which should be set.
     */
    public void setIndexcardList(List<String> indexcardList) {
        this.indexcardListNames = indexcardList;
    }


    /**
     * Getter for the id of the Category.
     * @return The id of the Category.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the name of the Category.
     * @return The name of the Category.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the parent of the Category.
     * @return The parent of the Category.
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Setter for the name of the Category.
     * @param name The name of the Category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the parent of the Category.
     * @param parent The parent of the Category.
     */
    public void setParent(Category parent) {
        try {
            if (parent != null) {
                if (this.getName() != parent.getName()) {
                    this.parent = parent;
                } else {
                    throw new IllegalArgumentException("Parent cannot be the same class as the current category.");
                }
            } else {
                this.parent = null;
            }
        } catch (IllegalArgumentException e) {
            // Log the error message
            logger.log(Level.SEVERE, "Error setting parent: " + e.getMessage());
        } catch (Exception e) {
            // Log the error message
            logger.log(Level.SEVERE,"Error setting parent: " + e.getMessage());
        }
    }
}
