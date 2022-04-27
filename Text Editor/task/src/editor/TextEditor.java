package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextEditor extends JFrame {
;
    final int WIDTH = 600;
    final int HEIGHT = 400;
    JTextArea textArea;
    Container appCont;
    JTextField filenameField;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    public TextEditor() {
        super("Text Editor");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        appCont = getContentPane();
        textArea = new JTextArea();
        textArea.setName("TextArea");

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setName("ScrollPane");
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        appCont.add(scrollableTextArea, BorderLayout.CENTER);
        appCont.add(new JLabel(" "), BorderLayout.SOUTH);
        appCont.add(new JLabel("    "), BorderLayout.WEST);
        appCont.add(new JLabel("    "), BorderLayout.EAST);

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        filenameField = new JTextField(30);
        filenameField.setName("FilenameField");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.setIcon();

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");

        topBar.add(filenameField);
        topBar.add(saveButton);
        topBar.add(loadButton);
        appCont.add(topBar, BorderLayout.NORTH);

        setVisible(true);

        actionListener(saveButton, loadButton);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menu = new JMenu("File");
        menu.setName("MenuFile");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Load", KeyEvent.VK_L);
        menuItem.setName("MenuLoad");
        menuItem.addActionListener(loadButton.getActionListeners()[0]);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
        menuItem.setName("MenuSave");
        menuItem.addActionListener(saveButton.getActionListeners()[0]);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        menuItem.setName("MenuExit");
        menuItem.addActionListener(actionEvent -> dispose());
        menu.add(menuItem);

        menuBar.add(menu);

    }

    private void actionListener(JButton saveButton, JButton loadButton) {
        saveButton.addActionListener(e -> {
            File file = new File(filenameField.getText());
            fileWriter(file, textArea.getText());
            System.err.println("File saved successfully");
        });

        loadButton.addActionListener(e -> {
            try {
                textArea.setText(readFileAsString(filenameField.getText()));
                System.err.println("File loaded successfully");
            } catch (IOException ex) {
                textArea.setText(null);
            }
        });
    }

    private void fileWriter(File file, String textArea) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void setMargin(JComponent aComponent, int aTop,
                                 int aRight, int aBottom, int aLeft) {

        Border border = aComponent.getBorder();

        Border marginBorder = new EmptyBorder(new Insets(aTop, aLeft,
                aBottom, aRight));
        aComponent.setBorder(border == null ? marginBorder
                : new CompoundBorder(marginBorder, border));
    }
}
