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

}
