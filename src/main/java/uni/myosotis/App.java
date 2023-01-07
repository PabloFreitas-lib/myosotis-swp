package uni.myosotis;

import uni.myosotis.controller.MainMenuController;
import uni.myosotis.logic.IndexcardLogic;
import uni.myosotis.persistence.IndexcardRepository;
import uni.myosotis.objects.Indexcard;

import java.util.List;

public class App {
    public static void main(String[] args) {

        /* Start the application */
        final IndexcardLogic indexcardLogic = new IndexcardLogic();
        final MainMenuController mainMenuController = new MainMenuController(indexcardLogic);
        mainMenuController.startApplication();


        /* STUFF ZUM TESTEN */
        final IndexcardRepository ir = new IndexcardRepository();
        final List<Indexcard> cards = ir.findAllIndexcards();

        for (Indexcard x : cards) {
            System.out.println("Name:" + x.getName());
        }

    }
}
