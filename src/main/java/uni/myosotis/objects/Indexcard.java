package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Indexcard implements Serializable {

    String name;

    String question;

    String answer;

    @ManyToMany(mappedBy = "indexcards", fetch = FetchType.EAGER)
    private List<Keyword> keywords;

    //@ManyToOne
    //private List<Category> categoryList;

    // id of the index card, needs to be unique within the persistence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Indexcard(final String name, final String question, final String answer, final List<Keyword> keywords) {

        this.name = name;
        this.question = question;
        this.answer = answer;
        this.keywords = keywords;

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

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(final List<Keyword> keywords) {
        this.keywords = keywords;
    }

    //public void setCategoryList(final List<Category> categoryList) { this.categoryList = categoryList;}

    //public void addCategory(final Category category){ this.categoryList.add(category);}
    //public List<Category> getCategoryList(){return categoryList;}
}
