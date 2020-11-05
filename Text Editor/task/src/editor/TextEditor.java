package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class TextEditor extends JFrame {

    private final JButton saveButton;
    private final JButton loadButton;
    private final JTextField textField;
    private final JTextArea textArea;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 620);
        setTitle("TextArea");
        getContentPane().setBackground(Color.GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(800, 70));
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(30);
        flowLayout.setVgap(20);
        topPanel.setLayout(flowLayout);

        textField = new JTextField();
        textField.setName("FilenameField");
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(textField);

        saveButton = new JButton();
        saveButton.setName("SaveButton");
        saveButton.setText("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.setPreferredSize(new Dimension(200, 40));
        topPanel.add(saveButton);

        saveFile();

        loadButton = new JButton();
        loadButton.setName("LoadButton");
        loadButton.setText("Load");
        loadButton.setFont(new Font("Arial", Font.BOLD, 20));
        loadButton.setPreferredSize(new Dimension(200, 40));
        topPanel.add(loadButton);

        loadFile();

        JPanel textAreaPanel = new JPanel();
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setVgap(30);
        textAreaPanel.setLayout(flowLayout1);
        textAreaPanel.setPreferredSize(new Dimension(800, 530));

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setFont(new Font("Arial", Font.ITALIC, 20));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.setBorder(BorderFactory.createBevelBorder(Color.OPAQUE));
        textAreaPanel.add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(800, 30));
        menuBar.setBackground(Color.CYAN);
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 25));

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("MenuLoad");
        loadMenuItem.setFont(new Font("Arial", Font.PLAIN, 20));
        fileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(actionEvent -> {
            loadButton.doClick();
        });

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.setFont(new Font("Arial", Font.PLAIN, 20));
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(actionEvent -> {
            saveButton.doClick();
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.setFont(new Font("Arial", Font.BOLD, 20));
        fileMenu.addSeparator(); // adds line separator between save and exit
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(actionEvent -> {
            System.exit(0);
        });

        menuBar.add(fileMenu);
        add(topPanel, BorderLayout.NORTH);
        add(textAreaPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    private void saveFile() {
        if (textField.getText() == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        saveButton.addActionListener(actionEvent -> {
            String fileName = textField.getText();
            if (fileName != null) {
                try (FileWriter fileWriter = new FileWriter(fileName);
                     BufferedWriter bw = new BufferedWriter(fileWriter)) {
                    textArea.write(bw);
                    textArea.requestFocusInWindow();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadFile() {
        if (textField.getText() == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }

        loadButton.addActionListener(actionEvent -> {
            String fileName = textField.getText();
            File file = new File(fileName);

            if (!file.exists()) {
                textArea.setText("");
                return;
            }

            try (FileReader fileReader = new FileReader(fileName);
                 BufferedReader br = new BufferedReader(fileReader)) {
                textArea.read(br, null);
                textArea.requestFocusInWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
