public class Verse {

    private String book;
    private int chapter;
    private int verse;
    private String reference;
    private String text;

    public Verse(String book, int chapter, int verse,
                 String reference, String text) {
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.reference = reference;
        this.text = text;
    }

    public String getBook() {
        return book;
    }

    public int getChapter() {
        return chapter;
    }

    public int getVerse() {
        return verse;
    }

    public String getReference() {
        return reference;
    }

    public String getText() {
        return text;
    }
}

