package uni.myosotis;

import uni.myosotis.persistence.IndexcardRepository;
import uni.myosotis.objects.Indexcard;

import java.util.List;

public class App {
    public static void main(String[] args) {

        final IndexcardRepository ir = new IndexcardRepository();

        final List<Indexcard> cards = ir.findAllIndexcards();

        for (Indexcard x : cards) {
            System.out.println("Name:" + x.getName());
        }

    }
}
