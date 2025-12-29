public class Main {
    public static void main(String[] args) {

        try {
            Verse verse;

            if (args.length == 0) {
                verse = BibleApiService.getRandomVerse();
            } else if (args.length == 3) {
                String book = args[0];
                int chapter = Integer.parseInt(args[1]);
                int verseNum = Integer.parseInt(args[2]);
                if(chapter<=0 || verseNum<=0){
                    System.out.println("Chapter and verse must be positive numbers.");
                    return;
                }

                verse = BibleApiService.getVerse(book, chapter, verseNum);
            } else {
                System.out.println("Usage:");
                System.out.println("java Main");
                System.out.println("java Main <Book> <Chapter> <Verse>");
                return;
            }

            System.out.println(verse.getReference());
            System.out.println("\"" + verse.getText() + "\"");

        } catch (Exception e) {
            System.out.println("Unable to fetch Bible verse.");
            System.out.println("Check your internet connection or input format");
        }
    }
}
