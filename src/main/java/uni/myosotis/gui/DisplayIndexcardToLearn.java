package uni.myosotis.gui;
import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LeitnerLearnSystem;
import uni.myosotis.objects.Link;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DisplayIndexcardToLearn extends JDialog{
    private final Controller controller;
    private final LeitnerLearnSystem learnSystem;
    //private final IndexcardBox indexcardBox;
    private final Language language;
    private Indexcard indexcard;

    //private int progress = 0;

    private List<Indexcard> indexCardList2Learn;

    private JPanel contentPane;
    private JButton backButton;
    private JButton nextButton;
    private JButton answeredButton;
    private JLabel nameLabel;
    private JProgressBar learnProgressBar;
    private JLabel percentageValue;
    private JButton wrongButton;
    private JButton correctButton;
    private JTextArea questionArea;
    private JTextArea answerArea;
    private JList<String> linkedIndexcardsList;
    private JLabel linkedListLabel;
    private JLabel sorryLabel;

    /**
     * This function is the basics to the logic from the LearnSystem and also the GUI from the LearnSystem.
     * @param controller The controller that is used to get the indexcards.
     * @param learnSystem The learnSystem that is used to get the indexcards.
     * @param indexcardBox The indexcardBox that is used to get the indexcards.
     * @param language The language that is used to set the language.
     */
    public DisplayIndexcardToLearn(Controller controller, LeitnerLearnSystem learnSystem, IndexcardBox indexcardBox, Language language) {
        this.learnSystem = learnSystem;
        this.controller = controller;
        this.language = language;
        this.indexCardList2Learn = controller.getAllIndexcards(learnSystem.getNextIndexcardNames());
        this.indexcard = this.indexCardList2Learn.get(learnSystem.getProgress());
        this.learnProgressBar.setMinimum(0);
        this.learnProgressBar.setMaximum(learnSystem.getNextIndexcardNames().size());
        setProgressDisplay();
        setLabels();
        hiddenButtons();
        setContentPane(contentPane);
        setTitle(language.getName("indexcard"));
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        // Set Language
        backButton.setText(language.getName("back"));
        nextButton.setText(language.getName("next"));
        correctButton.setText(language.getName("correct"));
        wrongButton.setText(language.getName("wrong"));
        answeredButton.setText(language.getName("answered"));
        linkedListLabel.setText(language.getName("linkedIndexcardsList"));

        controller.updateLearnsystem(learnSystem);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        backButton.addActionListener(e -> onBack());
        nextButton.addActionListener(e -> onNext());
        answeredButton.addActionListener(e -> onAnswered(indexcard));

        correctButton.addActionListener(e -> onCorrect(indexcard));

        wrongButton.addActionListener(e -> onWrong(indexcard));

        linkedIndexcardsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    if (controller.getIndexcardByName(linkedIndexcardsList.getSelectedValue()).isPresent()) {
                        controller.displayIndexcard(controller.getIndexcardByName(linkedIndexcardsList.getSelectedValue()).get());
                    }
                }
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 20);
        answerArea.setFont(font);
        questionArea.setFont(font);
        sorryLabel.setVisible(false);
    }

    private void setLabels() {
        this.nameLabel.setText(String.format((language.getName("nameAndBox")),indexcard.getName(), learnSystem.getIndexcardBox(indexcard.getName())));
        this.questionArea.setText(indexcard.getQuestion());
        DefaultListModel<String> linkedListModel = new DefaultListModel<>();
        linkedListModel.addAll(indexcard.getLinks().stream().map(Link::getIndexcard).map(Indexcard::getName).toList());
        linkedIndexcardsList.setModel(linkedListModel);
    }

    /**
     * This method is called when the user clicks the "Answered" button.
     * It shows the answer.
     */

    private void onAnswered(Indexcard indexcard) {
        answerArea.setText(indexcard.getAnswer());
        showButtons();
        setLabels();
    }

    /**
     * This method is called when the user clicks the "Next" button.
     * It shows the next indexcard.
     * If there is no next indexcard, it closes the window.
     */
    private void onNext() {
        hiddenButtons();
        if (learnSystem.getProgress() < this.indexCardList2Learn.size() - 1) {
            learnSystem.increaseProgress();
            indexcard = indexCardList2Learn.get(learnSystem.getProgress());
            questionArea.setText(indexcard.getQuestion());
            answerArea.setText("");
            setProgressDisplay();
        }
        else {
            // The user has learned all the indexcards in the box.
            learnSystem.setProgress(0);
            JOptionPane.showMessageDialog(this, language.getName("boxEnded")
                    ,language.getName("boxEndedMessage"), JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        controller.updateLearnsystem(learnSystem);
        setLabels();
        sorryLabel.setVisible(false);
        this.answerArea.setText("");
    }

    private void onBack() {
        hiddenButtons();
        if (learnSystem.getProgress() > 0) {
            learnSystem.decreaseProgress();
            indexcard = indexCardList2Learn.get(learnSystem.getProgress());
            questionArea.setText(indexcard.getQuestion());
            answerArea.setText("");
            setProgressDisplay();
        }

        controller.updateLearnsystem(learnSystem);
        setLabels();
        this.answerArea.setText("");
    }

    private void onCancel() {
        controller.updateLearnsystem(learnSystem);
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
        controller.updateLearnsystem(learnSystem);
        onNext();
    }
    private void onWrong(Indexcard indexcard){
        learnSystem.wrongAnswer(indexcard);
        controller.updateLearnsystem(learnSystem);
        sorryLabel.setText(language.getName("sorryMessage"));
        sorryLabel.setVisible(true);

    }

    /**
     * This methode will get all the index card from the LearsSystem which are in the in a box.
     */
    private List<Indexcard> getIndexcardFromBox(LeitnerLearnSystem learnSystem, int boxNumber){
        return controller.getIndexcardsByIndexcardNameList(learnSystem.getIndexcardFromBox(boxNumber));
    }

    /**
     * This methode will set the progress bar to the right value.
     */
    public void setProgressDisplay(){
        this.learnProgressBar.setValue(learnSystem.getProgress());
        this.percentageValue.setText((learnSystem.getProgress()) * 100 / indexCardList2Learn.size() +"%");
    }



}
