package uni.myosotis.logic;

import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.persistence.IndexcardBoxRepository;

import java.util.List;

public class IndexcardBoxLogic {
    /**
     * The repository for the IndexcardBox's.
     */
    final IndexcardBoxRepository indexcardBoxRepository;

    /**
     * Creates a new IndexcardBoxLogic.
     */
    public IndexcardBoxLogic () {
        this.indexcardBoxRepository = new IndexcardBoxRepository();
    }
    public IndexcardBox getIndexcardBoxByName(String name){
        return indexcardBoxRepository.getIndexcardBoxByName(name).get();
    }
    public void createIndexcardBox(String name, List<Category>categoryList){
        if (indexcardBoxRepository.getIndexcardBoxByName(name).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikästen mit diesem Namen.");
        } else {
            if (indexcardBoxRepository.saveIndexcardBox(new IndexcardBox(name, categoryList)) < 0) {
                throw new IllegalStateException("Karteikästen konnte nicht erstellt werden, Fehler beim Speichern in der Datenbank");
            }
        }
    }

    public List<IndexcardBox> getAllIndexcardBoxes() {
        return indexcardBoxRepository.getAllIndexcardBoxes();
    }

    public Boolean IndexcardBoxIsPresent(String name) {
        return indexcardBoxRepository.getIndexcardBoxByName(name).isPresent();
    }

    public void deleteIndexcardBox(String name){
        if (IndexcardBoxIsPresent(name)) {
            if (indexcardBoxRepository.deleteIndexcardBox(name) < 0) {
                throw new IllegalStateException("Das IndexcardBox konnte nicht gelöscht werden.");
            }
        }
    }
}
