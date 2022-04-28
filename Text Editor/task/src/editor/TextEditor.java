package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {


    final String MAIN_DIR = "/Users/mac/Desktop/BetterReads/Text Editor/Text Editor/task/src/images/";
    final String SAVE_ICON = "Save-icon.png";
    final String JAVA_ICON = "icons/Java-icon.png";
    final String EXIT_ICON = "icons/Button-exit-icon.png";
    final String LOAD_ICON = "load1.png";
    final int WIDTH = 600;
    final int HEIGHT = 400;
    JTextArea textArea;
    Container appCont;
    JTextField filenameField;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JButton saveButton;
    JButton loadButton;

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
        appCont.add(topBar(), BorderLayout.NORTH);

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

    public JPanel topBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        filenameField = new JTextField(30);
        filenameField.setName("FilenameField");

        ImageIcon saveIcon = resizeImage(MAIN_DIR + SAVE_ICON, 40, 40);
        saveButton = new JButton(saveIcon);
       // saveButton = new JButton(new ImageIcon(MAIN_DIR + SAVE_ICON));
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(30, 30));


        File file = new File("/Users/mac/Downloads/next.png");
        System.err.println(file.exists());
        System.out.println(new File("/Users/mac/Desktop/BetterReads/Text Editor/Text Editor/task/src/images/load1.png").exists());

        ImageIcon loadIcon = resizeImage(MAIN_DIR + LOAD_ICON, 40, 40);
        loadButton = new JButton(loadIcon);
      //  loadButton = new JButton(new ImageIcon(loadIcon));
        loadButton.setName("LoadButton");
        loadButton.setPreferredSize(new Dimension(30, 30));

        topBar.add(filenameField);
        topBar.add(saveButton);
        topBar.add(loadButton);
        return topBar;
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

    private ImageIcon resizeImage(String file, int width, int height) {
        return new ImageIcon(new ImageIcon(file).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

    }
}
