package uni.myosotis;

import uni.myosotis.database.IndexcardRepository;
import uni.myosotis.objects.Indexcard;

public class App {
    public static void main(String[] args) {

        final IndexcardRepository ir = new IndexcardRepository();

        final Indexcard card = new Indexcard();
        //System.out.println(ir.saveIndexcard(card));

        if (ir.findIndexcard(0).isPresent()) {
            System.out.println("Index card (0) has been found!");
        }
        else {
            System.out.println("Index card (0) does not exist!");
        }

        if (ir.findIndexcard(1).isPresent()) {
            System.out.println("Index card (1) has been found!");
        }
        else {
            System.out.println("Index card (1) does not exist!");
        }

    }
}
