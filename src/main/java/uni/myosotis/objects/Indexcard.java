package uni.myosotis.objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
@Embeddable
public class Indexcard implements Serializable {

    // id of the index card, needs to be unique within the persistence
    @Id
    String name;

    String question;

    String answer;

    public Indexcard(final String name, final String question, final String answer) {

        this.name = name;
        this.question = question;
        this.answer = answer;

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

    public void setName(final String newName) {
        name = newName;
    }

    public void setQuestion(final String newQuestion) {
        question = newQuestion;
    }

    public void setAnswer(final String newAnswer) {
        answer = newAnswer;
    }

}
