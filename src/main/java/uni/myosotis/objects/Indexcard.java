package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Indexcard implements Serializable {

    private String name;

    private String question;

    private String answer;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Keyword> keywords;

    //@ManyToOne
    private List<String> categoryList = new ArrayList<String>();

    /**
     * Id of the index card, needs to be unique within the persistence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Indexcard(final String name, final String question, final String answer, final List<Keyword> keywords) {

        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keywords = keywords;

    }

    public Indexcard(final String name, final String question, final String answer, final List<Keyword> keywords, List<String> categoryList) {

        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keywords = keywords;
        this.categoryList = categoryList;

    }

    public Indexcard () {

    }

    /**
     * Returns the name of the Indexcard.
     *
     * @return The name of the Indexcard.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the question of the Indexcard.
     *
     * @return The question of the Indexcard.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer of the Indexcard.
     *
     * @return The answer of the Indexcard.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the keywords of the Indexcard.
     *
     * @return The keywords of the Indexcard.
     */
    public List<Keyword> getKeywords() {
        return keywords;
    }

    /**
     * Returns the keywords names of the Indexcard.
     *
     * @return The keywords names of the Indexcard.
     */
    public List<String> getKeywordNames() {
        return keywords.stream().
                map(Keyword::getName).toList();
    }

    /**
     * Returns the id of the Indexcard.
     *
     * @return The id of the Indexcard.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the name of the Indexcard to the given name.
     *
     * @param name The name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the question of the Indexcard to the given name.
     *
     * @param question The question.
     */
    public void setQuestion(final String question) {
        this.question = question;
    }

    /**
     * Sets the answer of the Indexcard to the given name.
     *
     * @param answer The answer.
     */
    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    /**
     * Sets the name of the Indexcard to the given name.
     *
     * @param keywords The Keywords.
     */
    public void setKeywords(final List<Keyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * Sets the categories of the Indexcard to the given name.
     *
     * @param categoryList The Keywords.
     */
    public void setCategoryList(final List<String> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Add a category of the Indexcard to the given name.
     *
     * @param categoryName The Keywords.
     */
    public void addCategoryList(final String categoryName) {
        if (!categoryList.contains(categoryName))
            this.categoryList.add(categoryName);
    }

    /**
     * Add a category of the Indexcard to the given name.
     *
     * @param categoryName The Keywords.
     */
    public void removeCategoryList(final String categoryName) {
        if (categoryList.contains(categoryName))
            this.categoryList.remove(categoryName);
    }
    /**
     * Get a category of the Indexcard to the given name.
     */

    public List<String> getCategoryList(){
        return categoryList;
    }
}
