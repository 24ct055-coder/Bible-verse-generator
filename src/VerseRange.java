public class VerseRange {

    private String book;
    private int chapter;
    private int startVerse;
    private int endVerse;
    private String text;

    public VerseRange(String book, int chapter, int startVerse, int endVerse, String text) {
        this.book = book;
        this.chapter = chapter;
        this.startVerse = startVerse;
        this.endVerse = endVerse;
        this.text = text;
    }

    public String getReference() {
        if (startVerse == endVerse) {
            return book + " " + chapter + ":" + startVerse;
        }
        return book + " " + chapter + ":" + startVerse + "-" + endVerse;
    }

    public String getText() {
        return text;
    }

    public String getBook() {
        return book;
    }

    public int getChapter() {
        return chapter;
    }

    public int getStartVerse() {
        return startVerse;
    }

    public int getEndVerse() {
        return endVerse;
    }
}
