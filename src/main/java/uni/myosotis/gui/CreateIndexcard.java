package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The dialog to create an Indexcard.
 *
 * @author Johannes Neugebauer
 * @author Pablo Santos
 * @author Kasim Shah
 * @author Aziz Tas
 * @author Omid Valipour
 */
public class CreateIndexcard extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;
    private final Language language;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextArea textAreaQuestion;
    private JTextArea textAreaAnswer;
    private JTextField textFieldKeywords;
    private JList<String> linkList;
    private JTextField termField;
    private JList<String> indexcardList;
    private JButton addLinkButton;
    private JButton removeLinkButton;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JLabel keywordLabel;
    private JLabel linkLabel;
    private JLabel termLabel;
    private JLabel indexcardLabel;
    private JLabel nameLabel;

    /**
     * Create a new Dialog to create an Indexcard.
     *
     * @param controller The controller.
     * @param language   The selected language.
     */
    public CreateIndexcard(Controller controller, Language language) {
        this.controller = controller;
        this.language = language;
        setTitle(language.getName("createIndexcardTitle"));
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        // Set Model
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addAll(controller.getAllIndexcardNames());
        // For Language
        indexcardList.setModel(listModel);
        buttonOK.setText(language.getName("confirm"));
        buttonCancel.setText(language.getName("cancel"));
        nameLabel.setText(language.getName("name"));
        questionLabel.setText(language.getName("question"));
        answerLabel.setText(language.getName("answer"));
        keywordLabel.setText(language.getName("keyword"));
        linkLabel.setText(language.getName("link"));
        termLabel.setText(language.getName("term"));
        indexcardLabel.setText(language.getName("indexcard"));
        addLinkButton.setText(language.getName("addLink"));
        removeLinkButton.setText(language.getName("removeLink"));
        // ActionListeners
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        addLinkButton.addActionListener(e -> onAddLink());
        removeLinkButton.addActionListener(e -> onRemoveLink());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        // Add a term to the termField, if [text] ist entered, with the text as the term
        textAreaAnswer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    String text = textAreaAnswer.getText();
                    if (text.contains("[")) {
                        String term = text.substring(text.lastIndexOf("[") + 1);
                        if (term.contains("]")) {
                            term = term.substring(0, term.indexOf("]"));
                            if (!term.isBlank() && !term.equals(textAreaAnswer.getText())) {
                                termField.setText(term);
                                onAddLink();
                                textAreaAnswer.setText(text.replace("[" + term + "]", term));
                            }
                        }
                    }
                }
            }
        });
        textAreaQuestion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    String text = textAreaQuestion.getText();
                    if (text.contains("[")) {
                        String term = text.substring(text.lastIndexOf("[") + 1);
                        if (term.contains("]")) {
                            term = term.substring(0, term.indexOf("]"));
                            if (!term.isBlank() && !term.equals(textAreaQuestion.getText())) {
                                termField.setText(term);
                                onAddLink();
                                textAreaQuestion.setText(text.replace("[" + term + "]", term));
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Create a new Indexcard, if the entered Text isn't empty, and close the window.
     */
    private void onOK() {
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
            links.add(linkList.getModel().getElementAt(i));
        }

        if (!name.isBlank() && !question.isBlank() && !answer.isBlank()) {
            controller.createIndexcard(name, question, answer, keywords, links);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, language.getName("notAllFieldsFilledError"), language.getName("indexcardNotCreated"), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add a new Link.
     */
    private void onAddLink() {
        if (termField.getText().contains(" => ")) {
            JOptionPane.showMessageDialog(this, language.getName("noValidTerm"), language.getName("noValidTerm"), JOptionPane.INFORMATION_MESSAGE);
        } else if (!termField.getText().isBlank() && indexcardList.getSelectedValue() != null) {
            DefaultListModel<String> listModel = new DefaultListModel<>();
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
            DefaultListModel<String> listModel = new DefaultListModel<>();
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
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(352, 38), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Abbrechen");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(352, 198), null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        panel3.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        questionLabel = new JLabel();
        questionLabel.setText("Frage");
        panel3.add(questionLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        answerLabel = new JLabel();
        answerLabel.setText("Antwort");
        panel3.add(answerLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldName = new JTextField();
        textFieldName.setText("");
        panel3.add(textFieldName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        panel3.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaAnswer = new JTextArea();
        textAreaAnswer.setLineWrap(true);
        textAreaAnswer.setRows(5);
        textAreaAnswer.setText("");
        textAreaAnswer.setWrapStyleWord(true);
        scrollPane1.setViewportView(textAreaAnswer);
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setHorizontalScrollBarPolicy(31);
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaQuestion = new JTextArea();
        textAreaQuestion.setLineWrap(true);
        textAreaQuestion.setRows(2);
        textAreaQuestion.setText("");
        textAreaQuestion.setWrapStyleWord(true);
        scrollPane2.setViewportView(textAreaQuestion);
        keywordLabel = new JLabel();
        keywordLabel.setText("Schlagwort");
        panel3.add(keywordLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldKeywords = new JTextField();
        panel3.add(textFieldKeywords, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        linkLabel = new JLabel();
        linkLabel.setHorizontalAlignment(0);
        linkLabel.setHorizontalTextPosition(0);
        linkLabel.setText("Verlinkungen");
        panel4.add(linkLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        termLabel = new JLabel();
        termLabel.setHorizontalAlignment(0);
        termLabel.setText("Begriff");
        panel4.add(termLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(181, 16), null, 0, false));
        termField = new JTextField();
        panel4.add(termField, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(181, 30), null, 0, false));
        indexcardLabel = new JLabel();
        indexcardLabel.setText("Karteikarte");
        panel4.add(indexcardLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addLinkButton = new JButton();
        addLinkButton.setHorizontalTextPosition(11);
        addLinkButton.setText("Verlinkung hinzufügen");
        panel4.add(addLinkButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(181, 30), null, 0, false));
        removeLinkButton = new JButton();
        removeLinkButton.setText("Verlinkungen entfernen");
        panel4.add(removeLinkButton, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        linkList = new JList();
        linkList.setEnabled(true);
        scrollPane3.setViewportView(linkList);
        final JScrollPane scrollPane4 = new JScrollPane();
        panel4.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        indexcardList = new JList();
        indexcardList.setSelectionMode(0);
        scrollPane4.setViewportView(indexcardList);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel4.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
