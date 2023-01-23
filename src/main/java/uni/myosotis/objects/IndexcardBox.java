package uni.myosotis.objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class IndexcardBox implements Serializable{

    @Id
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categoryList;

    public IndexcardBox(){

    }

    public IndexcardBox(String name, List<Category> categoryList){
        this.name = name;
        this.categoryList = categoryList;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

}
