package uni.myosotis.objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Keyword implements Serializable{

    @Id
    String word;

    String name;


    //@Embedded?
    //String name;?

    public Keyword(final String word, Indexcard indexcard){
        this.word = word;
        this.name = indexcard.getName();
    }

    public Keyword(final String word, String name){
        this.word = word;
        this.name = name;
    }

    public String getKeyword(){
        return word;
    }

}
