package uni.myosotis.gui;

import uni.myosotis.controller.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateIndexcard extends JDialog {

    /**
     * The controller.
     */
    private final Controller controller;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextArea textAreaQuestion;
    private JTextArea textAreaAnswer;
    private JTextField textFieldKeywords;
    private JList linkList;
    private JTextField termField;
    private JList indexcardList;
    private JButton addLinkButton;
    private JButton removeLinkButton;

    /**
     * Create a new Dialog to create an Indexcard.
     *
     * @param controller The controller.
     */
    public CreateIndexcard(Controller controller) {
        this.controller = controller;
        setTitle("Karteikarte erstellen");
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        DefaultListModel listModel = new DefaultListModel();
        listModel.addAll(controller.getAllIndexcardNames());
        indexcardList.setModel(listModel);

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

        // Links separieren
        List<String> links = new ArrayList<>();
        // Save added Links
        for (int i = 0; i < linkList.getModel().getSize(); i++) {
            links.add((String) linkList.getModel().getElementAt(i));
        }

        if (!name.isBlank() && !question.isBlank() && !answer.isBlank()) {
            controller.createIndexcard(name, question, answer, keywords, links);
            controller.setIndexCardPanel();
            controller.setKeywordComboBox();
            controller.setCategoryComboBox();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Es müssen alle Felder ausgefüllt sein.", "Karteikarte nicht erstellt.", JOptionPane.ERROR_MESSAGE);
        }
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
     * Close the Window.
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
