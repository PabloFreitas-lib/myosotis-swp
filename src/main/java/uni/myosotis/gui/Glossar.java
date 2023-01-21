package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

public class Glossar extends JDialog {

    private final Controller controller;
    private JPanel contentPane;
    private JScrollPane IndexcardsPane;
    private JComboBox KeywordComboBox;
    private JLabel SchlagwortLabel;
    private JLabel KarteikartenLabel;
    private JButton filternKeyword;
    private JComboBox CategoryComboBox;
    private JButton filternCategory;
    private JButton filternEntfernenButton;
    private JTable indexCardTable;

    public Glossar(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setGlossar();
        setTitle("Glossar");
        pack();
        setMinimumSize(getSize());
        setSize(800, 600);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setGlossar(){
        List<String> indexCardsNameList = controller.getAllIndexcards().stream().
                map(Indexcard::getName).toList();
        List<String> questionList = controller.getAllIndexcards().stream().
                map(Indexcard::getQuestion).toList();
        List<String> answerList = controller.getAllIndexcards().stream().
                map(Indexcard::getAnswer).toList();
        List<List<String>> keywordList = controller.getAllIndexcards().stream().
                map(Indexcard::getKeywordNames).toList();
        List<List<String>> categoryList = controller.getAllIndexcards().stream().
                map(Indexcard::getCategoryList).toList();

        String[] columnNames = {"Begriffen", "Fragen","Antworten", "Verschlagworten", "Kategorien"};
        DefaultTableModel glossarModel = new DefaultTableModel(columnNames, 0);
        for (int i = 0; i < controller.getAllIndexcards().size(); i++){
            glossarModel.addRow(new Object[] {indexCardsNameList.get(i),questionList.get(i),answerList.get(i),keywordList.get(i),categoryList.get(i)});
        }

        indexCardTable.setModel(glossarModel);
        // add data JTable
        IndexcardsPane.setViewportView(indexCardTable);


    }
}
