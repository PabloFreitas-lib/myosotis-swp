package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LeitnerLearnSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DisplayIndexcardToLearn extends JDialog{
    private final Controller controller;
    private final LeitnerLearnSystem learnSystem;
    //private final IndexcardBox indexcardBox;
    private final Language language;
    private Indexcard indexcard;

    private int index = 0;

    private List<Indexcard> indexCardList2Learn;

    private JPanel contentPane;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JButton backButton;
    private JButton nextButton;
    private JButton answeredButton;
    private JLabel nameLabel;
    private JProgressBar learnProgressBar;
    private JLabel procentageValue;
    private JButton wrongButton;
    private JButton correctButton;

    /**
     * This function is the basics to the logic from the LearnSystem and also the GUI from the LearnSystem.
     * @param controller
     * @param learnSystem
     * @param indexcardBox
     * @param language
     */
    public DisplayIndexcardToLearn(Controller controller, LeitnerLearnSystem learnSystem, IndexcardBox indexcardBox, Language language) {
        this.learnSystem = learnSystem;
        this.controller = controller;
        //this.indexcardBox = indexcardBox;
        this.indexCardList2Learn = controller.getAllIndexcards(learnSystem.getNextIndexcards());
        this.indexcard = this.indexCardList2Learn.get(index);
        this.learnProgressBar.setMinimum(0);
        this.learnProgressBar.setMaximum(indexcardBox.getIndexcardList().size());
        this.questionLabel.setText(indexcard.getQuestion());
        this.answerLabel.setText("");
        this.language = language;
        hiddenButtons();
        procentageValue.setText("0%");
        setContentPane(contentPane);
        setTitle(language.getName("indexcard"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        // Set Language
        backButton.setText(language.getName("back"));
        nextButton.setText(language.getName("next"));
        answeredButton.setText(language.getName("answered"));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNext();
            }
        });
        answeredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAnswered(indexcard);
            }
        });

        correctButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCorrect(indexcard);
            }
        });

        wrongButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onWrong(indexcard);
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 20);
        answerLabel.setFont(font);
        questionLabel.setFont(font);
    }
    /**
     * This method is called when the user clicks the "Answered" button.
     * It shows the answer.
     */

    private void onAnswered(Indexcard indexcard) {
        answerLabel.setText(indexcard.getAnswer());
        showButtons();
    }

    /**
     * This method is called when the user clicks the "Next" button.
     * It shows the next indexcard.
     * If there is no next indexcard, it closes the window.
     */
    private void onNext() {
        hiddenButtons();
        //List<Indexcard> indexcards = controller.getIndexcardsByIndexcardNameList(indexcardBox.getIndexcardList());
        //int index = indexcardBox.getIndexcardList().indexOf(indexcard.getName());

        if (index < this.indexCardList2Learn.size() - 1) {
            indexcard = indexCardList2Learn.get(index++);
            questionLabel.setText(indexcard.getQuestion());
            answerLabel.setText("");
            this.learnProgressBar.setValue(indexCardList2Learn.indexOf(indexcard)+1);
            procentageValue.setText(String.valueOf((indexCardList2Learn.indexOf(indexcard)+1)*100/indexCardList2Learn.size())+"%");
        }
        else {
            dispose();
        }
    }

    private void onBack() {
        hiddenButtons();
        //List<Indexcard> indexcards = controller.getIndexcardsByIndexcardNameList(indexcardBox.getIndexcardList());
        //int index = indexcardBox.getIndexcardList().indexOf(indexcard.getName());
        if (index > 0) {
            indexcard = indexCardList2Learn.get(index--);
            questionLabel.setText(indexcard.getQuestion());
            answerLabel.setText("");
            this.learnProgressBar.setValue(indexCardList2Learn.indexOf(indexcard)+1);
            procentageValue.setText(String.valueOf((indexCardList2Learn.indexOf(indexcard)+1)*100/indexCardList2Learn.size())+"%");
        }
    }

    private void onCancel() {
        dispose();
    }

    private void hiddenButtons(){
        correctButton.setVisible(false);
        wrongButton.setVisible(false);
    }

    private void showButtons(){
        correctButton.setVisible(true);
        wrongButton.setVisible(true);
    }

    private void onCorrect(Indexcard indexcard){
        learnSystem.correctAnswer(indexcard);
    }
    private void onWrong(Indexcard indexcard){
        learnSystem.wrongAnswer(indexcard);
    }

    /**
     * This methode will get all the index card from the LearsSystem which are in the in a box.
     */
    private List<Indexcard> getIndexcardFromBox(LeitnerLearnSystem learnSystem, int boxNumber){
        return controller.getAllIndexcards(learnSystem.getIndexcardFromBox(boxNumber));
    }
}
