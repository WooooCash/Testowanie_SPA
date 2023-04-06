package ats.v1;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WindowMain extends JFrame {
    private List<File> defaultFilesList;
    private JPanel buttonPanel = new JPanel();
    private JPanel headPanel = new JPanel();
    private JPanel footerPanel = new JPanel();

    private JLabel fileNameLabel;

    private JLabel fileStatusLabel;

    private JTextArea headArea;

    private File file;
    private String filePath;

    private String currentFileName;
    private String fileStatus = "NOT LOADED";

    private final JFileChooser fileChooser = new JFileChooser();
//    private final JComboBox defaultFiles;
//        private DrawPanel drawPanel = new DrawPanel();

    public WindowMain() {
        this.setTitle("AiTSi - najlepszy squad");
        this.setBounds(250, 300, 1000, 600);
        initDefaultFiles();

        JButton loadOwnFile = (JButton) buttonPanel.add(new JButton("Load own file"));
        JButton loadDefaultFile = (JButton) buttonPanel.add(new JButton("Example files"));
        fileNameLabel = (JLabel) buttonPanel.add(new JLabel("FILENAME.XYZ"));
        buttonPanel.add(new JLabel("   "));
        buttonPanel.add(new JLabel("File Status: "));
        fileStatusLabel = (JLabel) buttonPanel.add(new JLabel(fileStatus));
        fileStatusLabel.setForeground(Color.RED);
        JTextArea queryArea = new JTextArea(1, 50);
        queryArea.setLineWrap(false);



        JButton executeButton = new JButton("EXECUTE");
        footerPanel.add(queryArea, BorderLayout.WEST);
        footerPanel.add(executeButton, BorderLayout.EAST);
        headArea = new JTextArea(60, 60);
        headArea.setEditable(false);
        headPanel.add(headArea);


        JScrollPane scroll = new JScrollPane(headArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultCaret carett = (DefaultCaret) headArea.getCaret();
        carett.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        headPanel.add(scroll);
        executeButton.addActionListener(e -> {
            sendQuery(queryArea.getText() + "\n");
            queryArea.selectAll();
            queryArea.replaceSelection("");
        });

        queryArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendQuery(queryArea.getText());
                    queryArea.selectAll();
                    queryArea.replaceSelection("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

//        defaultFiles = new JComboBox(defaultFilesList.stream().map(File::getName).toArray());
//        buttonPanel.add(defaultFiles);

        loadOwnFile.addActionListener(e -> {
                openFile();
        });

        loadDefaultFile.addActionListener(e -> createFrame());

//        JLabel yLabel = (JLabel)buttonPanel.add(new JLabel("Y:"));
//        JTextField yInput = (JTextField)buttonPanel.add(new JTextField(5));

        this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        this.getContentPane().add(headPanel, BorderLayout.CENTER);
        this.getContentPane().add(footerPanel, BorderLayout.SOUTH);
        headPanel.setVisible(false);
        footerPanel.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            //log.debug("File opened correctly");
            file = fileChooser.getSelectedFile();
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            String extension = "";

            int indexOf = file.getName().lastIndexOf('.');
            if (indexOf > 0) {
                extension = file.getName().substring(indexOf + 1);
            }
            //if (!extension.equals("txt")) {
                //file = null;
                //log.error("Bad format!");
                //throw or return error...
                //return;
            //}
            loadFile();
        }
        if (result == JFileChooser.CANCEL_OPTION) {
            //log.debug("Cancel was selected");
        }
    }

    public void loadFile() {
        //TUTAJ ODCZYT PLIKU I WPIĘCIE METODY POBIERAJACEJ
//        if(LOADED){
        fileNameLabel.setText(filePath);
        fileStatusLabel.setText("LOADED");
        fileStatusLabel.setForeground(Color.GREEN);
        headPanel.setVisible(true);
        footerPanel.setVisible(true);
//        }
//        else{
//
//        }
    }

    public void sendQuery(final String input) {
        headArea.append(input);
    }

    public void createFrame() {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Default Files");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            JPanel areaPanel = new JPanel();
            JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JPanel buttonPanel = new JPanel();
            listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            textPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            areaPanel.add(listPanel);
            areaPanel.add(textPanel);
            listPanel.setOpaque(true);
            JList list = new JList(defaultFilesList.stream().map(File::getName).toArray());
            listPanel.add(list);
            JButton loadButton = new JButton("Load");
            JButton cancelButton = new JButton("Cancel");

            buttonPanel.add(cancelButton);
            buttonPanel.add(loadButton);

            frame.getContentPane().add(BorderLayout.NORTH, areaPanel);
            frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);

            JTextArea textArea = new JTextArea(15, 60);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(Font.getFont(Font.SANS_SERIF));
            JScrollPane scroller = new JScrollPane(textArea);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            listPanel.add(scroller);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
            frame.setResizable(false);

            list.addListSelectionListener(e -> {
                JList source = (JList) e.getSource();
                String selectedValue = source.getSelectedValue().toString();
                try {
                    FileReader fileReader = new FileReader("defaultfiles/" + selectedValue);
                    BufferedReader reader = new BufferedReader(fileReader);
                    filePath = "defaultfiles/" + selectedValue;
                    textArea.read(reader, "test");}
                catch (IOException ioe) {
                    //log.debuf(ioe)
                }
            });

            list.setSelectedIndex(0);

            cancelButton.addActionListener(e -> frame.dispose());

            loadButton.addActionListener(e -> {
                        file = new File(filePath);
                        loadFile();
                        frame.dispose();
            });

        });
    }

    private void initDefaultFiles() {
        File folder = new File("defaultfiles");
        if (Objects.requireNonNull(folder.listFiles()).length > 0) {
            defaultFilesList = Arrays.asList(Objects.requireNonNull(folder.listFiles()));
        }
        //log.info(folder.listFiles().length + " default files available");
    }

    public static void main(String[] args) {
        new WindowMain();
    }

}
