package editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextEditor extends JFrame {
    File filenameField = new File("textEditorFile.txt"); // some file
    FlowLayout flowLayout = new FlowLayout();
    JButton saveButton = new JButton("SaveButton");
    JButton loadButton = new JButton("LoadButton");
    JTextField title = new JTextField();
    JTextArea textArea = new JTextArea(20, 30);


    public TextEditor() {
        super("Text Editor");
    }

    private void fileWriter(File file, String textArea) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.print(textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fileReader(File file) {
        try (Scanner sc = new Scanner(file)) {
            if(sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private static void createShowGUI() {
        //Create and set up the window.
        TextEditor frame = new TextEditor();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);
    }

    private void initComponents(final Container pane) {
        final JPanel comps = new JPanel();
        comps.setLayout(new FlowLayout());

        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());

        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new FlowLayout());

        comps.add(saveButton, BorderLayout.EAST);
        comps.add(loadButton, BorderLayout.WEST);
        comps.setLayout(new FlowLayout());

        controls.add(title, BorderLayout.WEST);
        controls.add(comps, BorderLayout.LINE_END);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        pane.add(controls, BorderLayout.CENTER);
        pane.add(scrollPane, BorderLayout.SOUTH);



//    saveButton.addActionListener(e -> {
//        textArea.setName(title.getName());
//        String textBody = textArea.getText();
//        if (title.getName() != null && textBody.trim().length() > 0) {
//            fileWriter(file, textBody);
//        }
//    });
//
//    loadButton.addActionListener(e -> {
//        String body;
//        try {
//            body = readFileAsString(file.getPath());
//            title.setText(file.getName());
//            textArea.setText(body);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    });

    }
}
