package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.DimensionUIResource;
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
    final String JAVA_ICON = "Java-icon.png";
    final String EXIT_ICON = "Button-exit-icon.png";
    final String LOAD_ICON = "load1.png";
    final String SEARCH_ICON = "search-icon.png";
    final String PREV_ICON = "Button-Previous-icon.png";
    final String NEXT_ICON = "Button-Next-icon.png";

    final int WIDTH = 620;
    final int HEIGHT = 430;
    JTextArea textArea;
    Container appCont;
    JTextField filenameField;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JButton saveButton;
    JButton loadButton;
    JButton searchButton;
    JButton prevButton;
    JButton nextButton;
    JCheckBox regex;
    JFileChooser jfc;


    public TextEditor() {
        super("Text Editor");
      //  setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
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
//        appCont.add(new JLabel("    "), BorderLayout.WEST);
//        appCont.add(new JLabel("    "), BorderLayout.EAST);
        appCont.add(topBar(), BorderLayout.NORTH);

        setVisible(true);

        actionListener(saveButton, loadButton);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menu = new JMenu("File");
        menu.setName("MenuFile");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Open", KeyEvent.VK_O);
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

        menu = new JMenu("Search");
        menu.setName("MenuSearch");
        menuBar.add(menu);

        menuItem = new JMenuItem("Start search", KeyEvent.VK_L);
        menuItem.setName("MenuStartSearch");
        menu.add(menuItem);

        menuItem = new JMenuItem("Previous search", KeyEvent.VK_P);
        menuItem.setName("MenuPreviousMatch");
        menu.add(menuItem);

        menuItem = new JMenuItem("Next match");
        menuItem.setName("MenuNextMatch");
        menu.add(menuItem);

        menuItem = new JMenuItem("Use regular expression");
        menuItem.setName("MenuUseRegExp");
        menu.add(menuItem);

    }

    public JPanel topBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        filenameField = new JTextField(15);
        filenameField.setName("FilenameField");

        ImageIcon saveIcon = resizeImage(MAIN_DIR + SAVE_ICON);
        saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");

// TODO: Delete this section when done.
//      Reconfigure dir for images to src, Set fields to private and reduce code duplication,
//      Go back to last project and return an anonymous class object in place of the error dto
//        File file = new File("/Users/mac/Downloads/next.png");
//        System.err.println(file.exists());
//        System.out.println(new File("/Users/mac/Desktop/BetterReads/Text Editor/Text Editor/task/src/images/load1.png").exists());

        ImageIcon loadIcon = resizeImage(MAIN_DIR + LOAD_ICON);
        loadButton = new JButton(loadIcon);
        loadButton.setName("OpenButton");

        ImageIcon searchIcon = resizeImage(MAIN_DIR + SEARCH_ICON);
        searchButton = new JButton(searchIcon);
        searchButton.setName("StartSearchButton");

        ImageIcon prevIcon = resizeImage(MAIN_DIR + NEXT_ICON);
        prevButton = new JButton(prevIcon);
        prevButton.setName("PreviousMatchButton");

        ImageIcon nextIcon = resizeImage(MAIN_DIR + PREV_ICON);
        nextButton = new JButton(nextIcon);
        nextButton.setName("NextMatchButton");

        regex = new JCheckBox("Use regex");
        regex.setName("UseRegexCheckbox");

        topBar.add(loadButton);
        topBar.add(saveButton);
        topBar.add(filenameField);
        topBar.add(searchButton);
        topBar.add(prevButton);
        topBar.add(nextButton);
        topBar.add(prevButton);
        topBar.add(regex);
        return topBar;
    }

    private void actionListener(JButton saveButton, JButton loadButton) {
        saveButton.addActionListener(e -> {
            File file = new File(filenameField.getText());
            fileWriter(file, textArea.getText());
        });

        loadButton.addActionListener(e -> {
            try {
                jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    textArea.setText(readFileAsString(jfc.getSelectedFile().getAbsolutePath()));
                }
            } catch (IOException ex) {
                textArea.setText(null);
                ex.printStackTrace();
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

    private ImageIcon resizeImage(String file) {
        return new ImageIcon(new ImageIcon(file).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    }
}
