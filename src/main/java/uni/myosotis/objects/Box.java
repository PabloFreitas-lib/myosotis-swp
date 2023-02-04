package uni.myosotis.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Box() {
    }

    public Box(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }

    List<String> indexcardNames = new ArrayList<>();

    public List<String> getIndexcardNames() {
        return indexcardNames;
    }

    public void setIndexcardNames(List<String> indexcardNames) {
        this.indexcardNames = indexcardNames;
    }

    public void addIndexcard(String indexcardName) {
        this.indexcardNames.add(indexcardName);
    }

    public void removeIndexcard(String indexcardName) {
        this.indexcardNames.remove(indexcardName);
    }

    public String getName() {
        return name;
    }
}