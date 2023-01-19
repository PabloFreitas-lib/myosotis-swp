package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Embeddable
public class Indexcard implements Serializable {

    String name;

    String question;

    String answer;

    @ManyToOne
    private Keyword keyword;

    @ManyToOne
    private List<Category> categoryList;
    // id of the index card, needs to be unique within the persistence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Indexcard(final String name, final String question, final String answer) {

        this.name = name;
        this.question = question;
        this.answer = answer;

    }

    public Indexcard(final String name, final String question, final String answer, final Keyword keyword) {

        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keyword = keyword;
    }
    public Indexcard(final String name, final String question, final String answer, final Keyword keyword, Long id) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keyword = keyword;
        this.id = id;
    }
    public Indexcard(final String name, final String question, final String answer, final Keyword keyword, Long id,final List<Category> category) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keyword = keyword;
        this.id = id;
        this.categoryList = category;
    }

    public Indexcard () {

    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Long getId() {
        return id;
    }

    public void setName(final String newName) {
        name = newName;
    }

    public void setQuestion(final String newQuestion) {
        question = newQuestion;
    }

    public void setAnswer(final String newAnswer) {
        answer = newAnswer;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeywords(final Keyword keyword) {
        this.keyword = keyword;
    }

    public void emptyKeyword() {
        this.keyword = null;
    }

    public void setCategoryList(final List<Category> categoryList) { this.categoryList = categoryList;}

    public void addCategory(final Category category){ this.categoryList.add(category);}
    public List<Category> getCategoryList(){return categoryList;}
}
