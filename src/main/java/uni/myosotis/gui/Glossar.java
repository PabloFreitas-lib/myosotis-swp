package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Glossar extends JDialog {

    private final Controller controller;
    private final Language language;
    private JPanel contentPane;
    private JScrollPane indexcardsPane;
    private JComboBox keywordComboBox;
    private JLabel keywordLabel;
    private JLabel indexcardLabel;
    private JButton filterButton;
    private JComboBox categoryComboBox;
    private JButton removeFilterButton;
    private JTable indexCardTable;
    private JRadioButton reverseSortRadioButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel searchLabel;
    private JLabel categoryLabel;
    private String[] columnNames = new String[5];
    private String selectedCategory;
    private String selectedKeyword;

    public Glossar(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        this.columnNames = new String[]{language.getName("name"),
                                        language.getName("question"),
                                        language.getName("answer"),
                                        language.getName("keywordTitle"),
                                        language.getName("categoryTitle")};
        this.selectedKeyword = language.getName("selectKeyword");
        this.selectedCategory = language.getName("selectCategory");
        setContentPane(contentPane);
        setCategoryComboBox();
        setKeywordComboBox();
        setGlossar();
        indexCardTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        pack();
        setMinimumSize(getSize());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Set Language
        searchButton.setText(language.getName("search"));
        searchLabel.setText(language.getName("search"));
        keywordLabel.setText(language.getName("keyword"));
        categoryLabel.setText(language.getName("category"));
        indexcardLabel.setText(language.getName("indexcard"));
        filterButton.setText(language.getName("filter"));
        removeFilterButton.setText(language.getName("removeFilter"));
        reverseSortRadioButton.setText(language.getName("reverseSort"));
        // Add Listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFiltern();
            }
        });

        removeFilterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFilternEntfernen();
            }
        });
        reverseSortRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reverseSortRadioButton.isSelected()){
                    sort((DefaultTableModel) indexCardTable.getModel(),false);
                } else {
                    sort((DefaultTableModel) indexCardTable.getModel(),true);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTextField.getText());
            }
        });
    }

    /**
     * Sucht nach dem übergebenen Text in den Indexkarten und zeigt die Ergebnisse in der Tabelle an
     * ist Case Insensitive
     * @param text
     */
    private void search(String text) {
        DefaultTableModel glossarModel = new DefaultTableModel(columnNames, 0);
        List<Indexcard> filteredIndexCards = controller.getAllIndexcards().stream()
                .filter(indexCard -> indexCard.getName().toLowerCase().contains(text.toLowerCase()) ||
                        indexCard.getQuestion().toLowerCase().contains(text.toLowerCase()) ||
                        indexCard.getAnswer().toLowerCase().contains(text.toLowerCase()) ||
                        indexCard.getKeywordNames().toString().toLowerCase().contains(text.toLowerCase()) ||
                        controller.getCategoriesByIndexcard(indexCard).toString().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        for (Indexcard indexCard : filteredIndexCards) {
            glossarModel.addRow(new Object[]{indexCard.getName(), indexCard.getQuestion(), indexCard.getAnswer(), indexCard.getKeywordNames(), controller.getCategoriesByIndexcard(indexCard)});
        }
        indexCardTable.setModel(sort(glossarModel, true));
        // add data JTable
        indexcardsPane.setViewportView(indexCardTable);
    }

    public JPanel getGlossarPane(){
        return contentPane;
    }

    private void onFilternEntfernen() {
        setGlossar();
        setCategoryComboBox();
        setKeywordComboBox();
        searchTextField.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void onFiltern(){
        if(!keywordComboBox.getSelectedItem().equals(selectedKeyword) && !categoryComboBox.getSelectedItem().equals(selectedCategory)){
            onFilternCategoryAndKeyword();
        } else if (!keywordComboBox.getSelectedItem().equals(selectedKeyword) && categoryComboBox.getSelectedItem().equals(selectedCategory)){
            onFilternKeyword();
        } else if (keywordComboBox.getSelectedItem().equals(selectedKeyword) && !categoryComboBox.getSelectedItem().equals(selectedCategory)){
            onFilternCategory();
        }
    }

    public void setGlossar() {
        DefaultTableModel glossarModel = new DefaultTableModel(columnNames, 0);
        for (Indexcard indexCard : controller.getAllIndexcards()) {
            glossarModel.addRow(new Object[]{indexCard.getName(), indexCard.getQuestion(), indexCard.getAnswer(), indexCard.getKeywordNames(), controller.getCategoriesByIndexcard(indexCard).stream().map(Category::getCategoryName).toList()});
        }
        indexCardTable.setModel(sort(glossarModel, true));
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

    public void onFilternKeyword() {
        String keywordToFilter = (String) keywordComboBox.getSelectedItem();
        DefaultTableModel glossarModel = new DefaultTableModel(columnNames, 0);
        List<Indexcard> filteredIndexCards = controller.getAllIndexcards().stream()
                .filter(indexCard -> indexCard.getKeywordNames().contains(keywordToFilter))
                .collect(Collectors.toList());
        for (Indexcard indexCard : filteredIndexCards) {
            glossarModel.addRow(new Object[]{indexCard.getName(), indexCard.getQuestion(), indexCard.getAnswer(), indexCard.getKeywordNames(), controller.getCategoriesByIndexcard(indexCard)});
        }
        indexCardTable.setModel(sort(glossarModel, true));
        indexcardsPane.setViewportView(indexCardTable);
    }

    public void onFilternCategory() {
        String categoryToFilter = (String) categoryComboBox.getSelectedItem();
        DefaultTableModel glossarModel = new DefaultTableModel(columnNames, 0);
        List<Indexcard> filteredIndexCards = controller.getAllIndexcards().stream()
                .filter(indexCard -> controller.getCategoriesByIndexcard(indexCard).contains(categoryToFilter))
                .collect(Collectors.toList());
        for (Indexcard indexCard : filteredIndexCards) {
            glossarModel.addRow(new Object[]{indexCard.getName(), indexCard.getQuestion(), indexCard.getAnswer(), indexCard.getKeywordNames(), controller.getCategoriesByIndexcard(indexCard)});
        }
        indexCardTable.setModel(sort(glossarModel, true));
        indexcardsPane.setViewportView(indexCardTable);
    }

    /**
     * filters the glossar table by the selected category and keyword
     */
    void onFilternCategoryAndKeyword(){
        String categoryToFilter = (String) categoryComboBox.getSelectedItem();
        String keywordToFilter = (String) keywordComboBox.getSelectedItem();
        DefaultTableModel glossaryModel = new DefaultTableModel(columnNames, 0);

        List<Indexcard> filteredIndexCards = controller.getAllIndexcards().stream()
                .filter(indexCard -> controller.getCategoriesByIndexcard(indexCard).contains(categoryToFilter) && indexCard.getKeywordNames().contains(keywordToFilter))
                .collect(Collectors.toList());
        for (Indexcard indexCard : filteredIndexCards) {
            glossaryModel.addRow(new Object[]{indexCard.getName(), indexCard.getQuestion(), indexCard.getAnswer(), indexCard.getKeywordNames(), controller.getCategoriesByIndexcard(indexCard)});
        }
        indexCardTable.setModel(sort(glossaryModel, true));
        indexcardsPane.setViewportView(indexCardTable);
    }

    /**
     * Set the CategoryComboBox with all Categories, which are in the Database.
     * The first Item is "Wählen Sie eine Kategorie aus" which if selected is "null".
     */
    public void setCategoryComboBox(){
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(controller.getCategoryNames());
        comboModel.setSelectedItem(selectedCategory);
        categoryComboBox.setModel(comboModel);
    }

    public void setKeywordComboBox(){
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(controller.getAllKeywordNames().toArray(new String[0]));
        comboModel.setSelectedItem(selectedKeyword);
        keywordComboBox.setModel(comboModel);
    }
}
