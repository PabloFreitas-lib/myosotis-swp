package uni.myosotis.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Indexcard implements Serializable {

    // id of the index card, needs to be unique within the database
    @Id
    final double id;

    // @NoArgsConstructor ??
    public Indexcard() {

        this.id = 0;

    }



}
