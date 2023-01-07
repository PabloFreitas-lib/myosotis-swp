package uni.myosotis.controller;

import uni.myosotis.gui.MainMenu;
import uni.myosotis.logic.IndexcardLogic;

public class MainMenuController {

    final IndexcardLogic indexcardLogic;

    public MainMenuController(final IndexcardLogic indexcardLogic) {

        this.indexcardLogic = indexcardLogic;

    }

    public void startApplication() {
        MainMenu.main(null);
    }

}
