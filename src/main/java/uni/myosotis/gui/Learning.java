package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Learnsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Learning extends JFrame{

    private final Controller controller;
    private JPanel contentPane;
    private JProgressBar learnProgressBar;
    private JLabel questionLabel;
    private JButton answerButton;
    private JButton wrongAnswerButton;
    private JButton rightAnswerButton;
    private JLabel answerLabel;

    /**
     * Creates a new Learning-Window.
     *
     * @param controller The controller.
     * @param learnsystem The learnsystem.
     */
    public Learning(Controller controller, Learnsystem learnsystem) {
        this.controller = controller;
        this.learnProgressBar.setMinimum(0);
        this.learnProgressBar.setMaximum(learnsystem.getIndexcardsToLearn().size() + learnsystem.getLearnedIndexcards().size());
        this.learnProgressBar.setValue(learnsystem.getProgress());

        setContentPane(contentPane);
        setTitle("Karteikarten");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.updateLearnsystem(learnsystem);
                dispose();
            }
        });

        this.questionLabel.setText(learnsystem.getIndexcardsToLearn().get(0).getQuestion());
        this.answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerLabel.setText(learnsystem.getIndexcardsToLearn().get(0).getAnswer());
            }
        });
        this.rightAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                learnsystem.getLearnedIndexcards().add(learnsystem.getIndexcardsToLearn().get(0));
                learnsystem.getIndexcardsToLearn().remove(0);
                learnsystem.setProgress(learnsystem.getProgress() + 1);
                controller.updateLearnsystem(learnsystem);
                learnProgressBar.setValue(learnsystem.getProgress());
                questionLabel.setText(learnsystem.getIndexcardsToLearn().get(0).getQuestion());
            }
        });
        this.wrongAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                learnsystem.getIndexcardsToLearn().add(learnsystem.getIndexcardsToLearn().get(0));
                learnsystem.getIndexcardsToLearn().remove(0);
                controller.updateLearnsystem(learnsystem);
                questionLabel.setText(learnsystem.getLearnedIndexcards().get(0).getQuestion());
            }
        });
    }
}
