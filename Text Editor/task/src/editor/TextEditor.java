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
    File file = new File("textEditorFile.txt"); // some file


    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);

        //BorderLayout borderLayout = new BorderLayout();
//        add(new JButton("North"), BorderLayout.NORTH);
//        add(new JButton("South"), BorderLayout.SOUTH);
//        add(new JButton("West"), BorderLayout.WEST);
//        add(new JButton("East"), BorderLayout.EAST);
//        add(new JButton("Center"), BorderLayout.CENTER);

        setTitle("Text Editor");

        initComponents();
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



    private void initComponents() {

    JTextArea textArea = new JTextArea();
    textArea.setBounds(20,20, 560,540);
    textArea.setName("TextArea");

    JScrollPane jScrollPane = new JScrollPane(textArea);
    jScrollPane.setBounds(20,20, 560,540);
    jScrollPane.add(textArea);

    add(jScrollPane);

    FlowLayout flowLayout = new FlowLayout();
    flowLayout.setAlignment(FlowLayout.TRAILING);

    JPanel jPanel = new JPanel();
    jPanel.setLayout(flowLayout);

    JTextField title = new JTextField();
    title.setBounds(140,20, 120,30);
    add(title);



    JButton saveButton = new JButton("SaveButton");

    JButton loadButton = new JButton("LoadButton");

    saveButton.addActionListener(e -> {
        textArea.setName(title.getName());
        String textBody = textArea.getText();
        if (title.getName() != null && textBody.trim().length() > 0) {
            fileWriter(file, textBody);
        }
    });

    loadButton.addActionListener(e -> {
        String body;
        try {
            body = readFileAsString(file.getPath());
            title.setText(file.getName());
            textArea.setText(body);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });

    }
}
