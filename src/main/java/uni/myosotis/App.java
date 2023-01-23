package uni.myosotis;

import uni.myosotis.controller.Controller;
import uni.myosotis.logic.CategoryLogic;
import uni.myosotis.logic.IndexcardBoxLogic;
import uni.myosotis.logic.IndexcardLogic;
import uni.myosotis.logic.KeywordLogic;

public class App {
    public static void main(String[] args) {

        /* Start the application */
        final KeywordLogic keywordLogic = new KeywordLogic();
        final IndexcardLogic indexcardLogic = new IndexcardLogic();
        final CategoryLogic categoryLogic = new CategoryLogic();
        final IndexcardBoxLogic indexcardBoxLogic = new IndexcardBoxLogic();
        final Controller controller = new Controller(indexcardLogic, keywordLogic, categoryLogic, indexcardBoxLogic);
        controller.startApplication();
    }
}
