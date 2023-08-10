// Import necessary libraries
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

// Define the TextEditor class that extends JFrame and implements ActionListener
public class TextEditor extends JFrame implements ActionListener {

    // Declare GUI components
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox fontBox;

    // Declare menu bar and menu items
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    // Constructor for the TextEditor class
    TextEditor() {
        // Set up the main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Karan Text Editor");
        this.setSize(700, 700);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        // Initialize the text area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));

        // Set up the scroll pane for the text area
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Initialize GUI components
        fontLabel = new JLabel("Font: ");
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                // Change the font size based on spinner value
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN,
                        (int) fontSizeSpinner.getValue()));
            }

        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        // Get available fonts from the system
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        // Create a combo box for font selection
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        // Create the menu bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        // Add action listeners to menu items
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        // Build the menu structure
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Set up the menu bar
        this.setJMenuBar(menuBar);

        // Add GUI components to the frame
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);

        // Make the frame visible
        this.setVisible(true);
    }

    // ActionListener interface method
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fontColorButton) {
            // Open color chooser and set text color
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);
        }

        if (e.getSource() == fontBox) {
            // Change font based on selected font from combo box
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN,
                    textArea.getFont().getSize()));
        }

        if (e.getSource() == openItem) {
            // Open a text file and display content in the text area
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    fileIn.close();
                }
            }
        }
        if (e.getSource() == saveItem) {
            // Save the content of the text area to a file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    fileOut.close();
                }
            }
        }
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
            }
        });
    }
}
