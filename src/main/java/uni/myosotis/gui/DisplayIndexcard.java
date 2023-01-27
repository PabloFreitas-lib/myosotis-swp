package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LearnSystem;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;

public class DisplayIndexcard  extends JDialog{
    private final Controller controller;
    private final LearnSystem learnsystem;
    private final IndexcardBox indexcardBox;
    private Indexcard indexcard;

    private JPanel contentPane;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JButton backButton;
    private JButton nextButton;
    private JButton answeredButton;
    private JLabel nameLabel;
    private JProgressBar learnProgressBar;
    private JLabel procentageValue;


    public DisplayIndexcard(Controller controller, LearnSystem learnsystem, IndexcardBox indexcardBox) {
        this.learnsystem = learnsystem;
        this.controller = controller;
        this.indexcardBox = indexcardBox;
        this.indexcard = controller.getIndexcardsByIndexcardNameList(indexcardBox.getIndexcardList()).stream().findFirst().get();
        this.learnProgressBar.setMinimum(0);
        this.learnProgressBar.setMaximum(indexcardBox.getIndexcardList().size());
        procentageValue.setText("0%");
        setContentPane(contentPane);
        setTitle("Karteikarte");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);

        questionLabel.setText(indexcard.getQuestion());
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
    }
    /**
     * This method is called when the user clicks the "Answered" button.
     * It shows the answer.
     */

    private void onAnswered(Indexcard indexcard) {
        answerLabel.setText(indexcard.getAnswer());
    }

    /**
     * This method is called when the user clicks the "Next" button.
     * It shows the next indexcard.
     * If there is no next indexcard, it closes the window.
     */
    private void onNext() {
        List<Indexcard> indexcards = controller.getIndexcardsByIndexcardNameList(indexcardBox.getIndexcardList());
        int index = indexcards.indexOf(indexcard);
        if (index < indexcards.size() - 1) {
            indexcard = indexcards.get(index + 1);
            questionLabel.setText(indexcard.getQuestion());
            answerLabel.setText("");
            this.learnProgressBar.setValue(indexcards.indexOf(indexcard)+1);
            procentageValue.setText(String.valueOf((indexcards.indexOf(indexcard)+1)*100/indexcards.size())+"%");
        }
        else {
            dispose();
        }
    }

    private void onBack() {
        List<Indexcard> indexcards = controller.getIndexcardsByIndexcardNameList(indexcardBox.getIndexcardList());
        int index = indexcards.indexOf(indexcard);
        if (index > 0) {
            indexcard = indexcards.get(index - 1);
            questionLabel.setText(indexcard.getQuestion());
            answerLabel.setText("");
            this.learnProgressBar.setValue(indexcards.indexOf(indexcard)+1);
            procentageValue.setText(String.valueOf((indexcards.indexOf(indexcard)+1)*100/indexcards.size())+"%");
        }
    }

    private void onCancel() {
        dispose();
    }
}
