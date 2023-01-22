package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        setCategoryComboBox();
        setKeywordComboBox();
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

        filternKeyword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilternKeyword();
            }
        });

        filternCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilternCategory();
            }
        });

        filternEntfernenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilternEntfernen();
            }
        });
    }
    public JPanel getGlossarPane(){
        return contentPane;
    }

    private void onFilternEntfernen() {
        setGlossar();
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

    public void onFilternKeyword(){
        String keyword2Filtern = (String) KeywordComboBox.getSelectedItem();
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
            for (int j = 0; j < keywordList.get(i).size(); j++) {
                if (keywordList.get(i).get(j).equals(keyword2Filtern))
                    glossarModel.addRow(new Object[]{indexCardsNameList.get(i), questionList.get(i), answerList.get(i), keywordList.get(i), categoryList.get(i)});
            }
        }
        indexCardTable.setModel(glossarModel);
        // add data JTable
        IndexcardsPane.setViewportView(indexCardTable);
    }

    public void onFilternCategory(){
        String category2Filtern = (String) CategoryComboBox.getSelectedItem();
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
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                if (categoryList.get(i).get(j).equals(category2Filtern))
                    glossarModel.addRow(new Object[]{indexCardsNameList.get(i), questionList.get(i), answerList.get(i), keywordList.get(i), categoryList.get(i)});
            }
        }
        indexCardTable.setModel(glossarModel);
        // add data JTable
        IndexcardsPane.setViewportView(indexCardTable);
    }

    public void setCategoryComboBox(){
        //list of all indexcards
        List<Category> categoriesList = controller.getAllCategories();
        // Array of all Categories
        String[] categoriesNames = controller.getAllCategories().stream().
                map(Category::getCategoryName).toList().toArray(new String[0]);
        CategoryComboBox.setModel(new DefaultComboBoxModel<>(categoriesNames));
    }

    public void setKeywordComboBox(){
        //list of all indexcards
        List<Keyword> keywordList = controller.getAllKeywords();
        // Array of all Keywords
        String[] keywordNames = controller.getAllKeywords().stream()
                .map(Keyword::getName).toList().toArray(new String[0]);
        KeywordComboBox.setModel(new DefaultComboBoxModel<>(keywordNames));
    }
}
