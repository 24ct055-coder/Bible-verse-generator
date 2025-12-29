import java.awt.*;
import javax.swing.*;

public class BibleVerseUI {

    private Verse currentVerse;
    private JTextArea textArea;
    private JButton verseBtn, paraBtn, chapterBtn, newBtn;
    private JLabel titleLabel;

    public BibleVerseUI() {
        JFrame frame = new JFrame("Daily Bible Verse");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Color bg = new Color(245, 248, 250);
        frame.getContentPane().setBackground(bg);

        // Title
        titleLabel = new JLabel("📖 Word for Today", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.PLAIN, 17));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        verseBtn = new JButton("Show Verse");
        paraBtn = new JButton("Show Paragraph");
        chapterBtn = new JButton("Show Chapter");
        newBtn = new JButton("New Verse");

        Font btnFont = new Font("SansSerif", Font.PLAIN, 14);
        verseBtn.setFont(btnFont);
        paraBtn.setFont(btnFont);
        chapterBtn.setFont(btnFont);
        newBtn.setFont(btnFont);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bg);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        buttonPanel.add(verseBtn);
        buttonPanel.add(paraBtn);
        buttonPanel.add(chapterBtn);
        buttonPanel.add(newBtn);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load first verse
        loadNewVerse();

        verseBtn.addActionListener(e ->
            textArea.setText(
                currentVerse.getReference() + "\n\n\"" +
                currentVerse.getText() + "\""
            )
        );

        paraBtn.addActionListener(e -> showParagraph());

        chapterBtn.addActionListener(e -> showChapter());

        newBtn.addActionListener(e -> loadNewVerse());

        frame.setVisible(true);
    }

    private void loadNewVerse() {
        while (true) {
            try {
                currentVerse = BibleApiService.getRandomVerse();
                textArea.setText(
                    currentVerse.getReference() + "\n\n\"" +
                    currentVerse.getText() + "\""
                );
                break;
            } catch (Exception e) {
                // retry silently
            }
        }
    }
    private void showParagraph() {
        try {
            textArea.setText(
                BibleApiService.getParagraphVerse(
                    currentVerse.getBook(),
                    currentVerse.getChapter(),
                    currentVerse.getVerse()
                )
            );
        } catch (Exception e) {
            textArea.setText("Failed to load paragraph.");
        }
    }
    private void showChapter() {
        try {
            textArea.setText(
                BibleApiService.getChapter(
                    currentVerse.getBook(),
                    currentVerse.getChapter()
                )
            );
        } catch (Exception e) {
            textArea.setText("Failed to load chapter.");    
        }
    }



    public static void main(String[] args) {
        new BibleVerseUI();
    }
}


