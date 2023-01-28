package uni.myosotis.gui;

import uni.myosotis.controller.Controller;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

public class EditIndexcard extends JDialog {

    private final Controller controller;

    private JPanel contentPane;
    private JButton buttonOK, buttonCancel;
    private JTextArea textAreaQuestion, textAreaAnswer;
    private JComboBox comboBoxName;
    private JTextField textFieldName;
    private JTextField textFieldKeywords;
    private JTextField termField;
    private JButton addLinkButton;
    private JButton removeLinkButton;
    private JList linkList;
    private JList indexcardList;
    private String oldName;

    /**
     * Creates a new EditIndexcard-Dialog.
     *
     * @param controller The Controller of the application.
     */
    public EditIndexcard(Controller controller) {
        this.controller = controller;
        setModal(true);
        setTitle("Karteikarte bearbeiten");
        getRootPane().setDefaultButton(buttonOK);
        setContentPane(contentPane);
        //list of all indexcards
        List<Indexcard> indexcards = controller.getAllIndexcards();
        //Array of all indexcard names
        String[] indexcardsNames = new String[indexcards.size()];
        for (int i = 0; i < indexcards.size(); i++) {
            indexcardsNames[i] = indexcards.get(i).getName();
        }
        //ComboBox with all indexcard names
        comboBoxName.setModel(new DefaultComboBoxModel<>(indexcardsNames));

        // List of existing Indexcards
        DefaultListModel listModelForIndexcards = new DefaultListModel();
        listModelForIndexcards.addAll(controller.getAllIndexcardNames());
        indexcardList.setModel(listModelForIndexcards);

        //ActionListener for the ComboBox
        comboBoxName.addActionListener(e -> {
            Optional<Indexcard> indexcard = controller.getIndexcardByName((String) comboBoxName.getSelectedItem());
            if(indexcard.isPresent()){
                textFieldName.setText(indexcard.get().getName());
                textAreaQuestion.setText(indexcard.get().getQuestion());
                textAreaAnswer.setText(indexcard.get().getAnswer());
                StringBuilder keywords = new StringBuilder();
                for (Keyword keyword : indexcard.get().getKeywords()) {
                    keywords.append("#").append(keyword.getName()).append(" ");
                }
                textFieldKeywords.setText(keywords.toString());

                // List of all Links of the Indexcard.
                DefaultListModel listModel = new DefaultListModel();
                listModel.addAll(indexcard.get().getLinks().stream().map(link -> link.getTerm() + " => " + link.getIndexcard().getName()).toList());
                linkList.setModel(listModel);

                // List of existing Indexcards without the selected.
                DefaultListModel listModelOfIndexcards = new DefaultListModel();
                List<String> list = controller.getAllIndexcardNames().stream().filter(name -> !name.equals(comboBoxName.getSelectedItem())).toList();
                listModelOfIndexcards.addAll(list);
                indexcardList.setModel(listModelOfIndexcards);

                oldName = indexcard.get().getName();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addLinkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddLink();
            }
        });

        removeLinkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRemoveLink();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * When the OK-Button is pressed, the Indexcard is edited.
     */
    private void onOK() {

        Indexcard oldIndexcard;
        //Old Parameters
        System.out.println(oldName);
        if (controller.getIndexcardByName(oldName).isPresent()) {
            oldIndexcard = controller.getIndexcardByName(oldName).get();
        }
        else {
            throw new IllegalStateException("Karteikarte zum Bearbeiten existiert nicht!");
        }

        final Long oldIndexcardId = oldIndexcard.getId();

        // New Parameters
        final String name = textFieldName.getText();
        final String question = textAreaQuestion.getText();
        final String answer = textAreaAnswer.getText();

        // Separate Keywords
        String[] keywordStrings = textFieldKeywords.getText()
                .replaceAll(" ", "")
                .split("#");

        List<String> keywords = new ArrayList<>(Arrays.asList(keywordStrings));
        keywords.remove(0);

        // Separate Links
        List<String> links = new ArrayList<>();
        // Save added Links
        for (int i = 0; i < linkList.getModel().getSize(); i++) {
            links.add((String) linkList.getModel().getElementAt(i));
        }

        if (!name.isBlank() && !question.isBlank() && !answer.isBlank()) {
            controller.editIndexcard(name, question, answer, keywords, links, oldIndexcardId);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht verändert.",
                    JOptionPane.ERROR_MESSAGE);
        }

        controller.setKeywordComboBox();
        controller.setIndexCardPanel();
    }

    /**
     * Sets a indexcard to edit by setting the ComboBox to the indexcard name.
     * @param indexcard
     */
    public void setIndexcard(Indexcard indexcard){
        comboBoxName.setSelectedItem(indexcard.getName());
    }

    /**
     * Add a new Link.
     */
    private void onAddLink() {
        if (!termField.getText().isBlank() && indexcardList.getSelectedValue() != null) {
            DefaultListModel listModel = new DefaultListModel();
            // Save previous added Links
            for (int i = 0; i < linkList.getModel().getSize(); i++) {
                listModel.addElement(linkList.getModel().getElementAt(i));
            }
            // Add new Link
            listModel.addElement(termField.getText() + " => " + indexcardList.getSelectedValue());
            linkList.setModel(listModel);
            // Clear selection
            termField.setText("");
            indexcardList.clearSelection();
        }
    }

    /**
     * Removes a Link.
     */
    private void onRemoveLink() {
        if (linkList.getSelectedValue() != null) {
            DefaultListModel listModel = new DefaultListModel();
            // Save previous added Links without the deleted Link
            for (int i = 0; i < linkList.getModel().getSize(); i++) {
                int finalI = i;
                if (Arrays.stream(linkList.getSelectedIndices()).noneMatch(e -> e == finalI)) {
                    listModel.addElement(linkList.getModel().getElementAt(i));
                }
            }
            linkList.setModel(listModel);
        }
    }

    /**
     * When the Cancel-Button is pressed, the Dialog is closed.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
