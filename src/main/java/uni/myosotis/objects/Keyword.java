package uni.myosotis.objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Keyword implements Serializable{

    @Id
    String word;

    //@Embedded?
    //String name;?

    public Keyword(final String name){
        this.word = name;
    }

    public String getKeyword(){
        return word;
    }

}
