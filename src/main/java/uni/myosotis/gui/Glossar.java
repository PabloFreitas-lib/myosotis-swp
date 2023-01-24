package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class Glossar extends JDialog {

    private final Controller controller;
    private JPanel contentPane;
    private JScrollPane indexcardsPane;
    private JComboBox KeywordComboBox;
    private JLabel SchlagwortLabel;
    private JLabel KarteikartenLabel;
    private JButton filternKeyword;
    private JComboBox categoryComboBox;
    private JButton filternCategory;
    private JButton filternEntfernenButton;
    private JTable indexCardTable;
    private JRadioButton sortRadioButton;


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
        sortRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sortRadioButton.isSelected()){
                    sort((DefaultTableModel) indexCardTable.getModel(),false);
                } else {
                    sort((DefaultTableModel) indexCardTable.getModel(),true);
                }
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
        indexCardTable.setModel(sort(glossarModel,true));
        // add data JTable
        indexcardsPane.setViewportView(indexCardTable);
    }

    /**
     * sorts the glossar table by the first column (indexcard name)
     * @param glossarModel the table model
     * @param order if true ascending, if false descending
     * @return the sorted table model
     */
    public DefaultTableModel sort(DefaultTableModel glossarModel, boolean order){
        List<Vector> data = new ArrayList<>();
        for (int i = 0; i < glossarModel.getRowCount(); i++) {
            data.add((Vector) glossarModel.getDataVector().get(i));
        }
        Collections.sort(data, new Comparator<Vector>() {
            @Override
            public int compare(Vector o1, Vector o2) {
                return o1.get(0).toString().compareTo(o2.get(0).toString());
            }
        });
        if (!order) {
            Collections.reverse(data);
        }
        glossarModel.setRowCount(0);
        for (Vector row : data) {
            glossarModel.addRow(row);
        }
        return glossarModel;
    }

    public void onFilternKeyword(){
        String keyword2Filtern = (String) KeywordComboBox.getSelectedItem();
        List<String> indexCardsNameList = controller.getAllIndexcardNames();
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
        indexCardTable.setModel(sort(glossarModel,true));
        // add data JTable
        indexcardsPane.setViewportView(indexCardTable);
    }

    public void onFilternCategory(){
        String category2Filtern = (String) categoryComboBox.getSelectedItem();
        List<String> indexCardsNameList = controller.getAllIndexcardNames();
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
        indexCardTable.setModel(sort(glossarModel,true));
        // add data JTable
        indexcardsPane.setViewportView(indexCardTable);
    }

    public void setCategoryComboBox(){
        // Array of all Categories
        categoryComboBox.setModel(new DefaultComboBoxModel<>(controller.getAllCategoryNames()));
    }

    public void setKeywordComboBox(){
        KeywordComboBox.setModel(new DefaultComboBoxModel<>(controller.getAllKeywordNames()));
    }
}
