import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.google.gson.*;
import java.util.Random;

public class BibleApiService {
    static String[] books = {
        //Old Testament
        "genesis","exodus","leviticus","numbers","deuteronomy","joshua","judges","ruth","1 samuel","2 samuel","1 kings","2 kings","1 chronicles",
        "2 chronicles","ezra","nehemiah","esther","job","psalms","proverbs","ecclesiastes","song of solomon","isaiah","jeremiah","lamentations",
        "ezekiel","daniel","hosea","joel","amos","obadiah","jonah","micah","nahum","habakkuk","zephaniah","haggai","zechariah","malachi",
        // New Testament
        "matthew","mark","luke","john","acts","romans","1 corinthians","2 corinthians","galatians","ephesians","philippians","colossians",
        "1 thessalonians","2 thessalonians","1 timothy","2 timothy","titus","philemon","hebrews","james","1 peter","2 peter","1 john","2 john",
        "3 john","jude","revelation"
    };
    static int[] maxChapters = {
        // Old Testament
        50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,12,8,66,52,5,48,12,14,3,9,1,4,7,3,3,3,2,14,4,
        // New Testament
        28,16,24,21,28,16,16,13,6,6,4,4,5,3,6,4,3,1,13,5,5,3,5,1,1,1,22
    };
    // Fetch a completely random valid verse
    static Verse getRandomVerse() throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        Random rand = new Random(System.nanoTime());

        while (true) {
            try {
                // Random book
                int bookIndex = rand.nextInt(books.length);
                String book = books[bookIndex];

                // Random chapter
                int chapter = rand.nextInt(maxChapters[bookIndex])+1;

                // Fetch entire chapter
                String url = "https://bible-api.com/" +
                        book + "%20" + chapter +
                        "?translation=web";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                JsonObject json =
                        JsonParser.parseString(response.body()).getAsJsonObject();

                JsonArray verses = json.getAsJsonArray("verses");
                if (verses == null || verses.size() == 0) {
                    continue;
                }

                // Random verse from chapter
                int randomIndex = rand.nextInt(verses.size());
                JsonObject verseObj = verses.get(randomIndex).getAsJsonObject();

                String reference = verseObj.get("book_name").getAsString()
                        + " " + chapter + ":" + verseObj.get("verse").getAsInt();

                String text = verseObj.get("text").getAsString().trim();

                return new Verse(
        book,
        chapter,
        verseObj.get("verse").getAsInt(),
        reference,
        text
);


            } catch (Exception e) {
                // retry silently
            }
        }
    }

    // Fetch a specific verse (user input)
    static Verse getVerse(String book, int chapter, int verse) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        String url = "https://bible-api.com/" +
                book.toLowerCase() + "%20" + chapter + ":" + verse +
                "?translation=web";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json =
                JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.has("reference")) {
            throw new Exception("Invalid verse");
        }

        String reference = json.get("reference").getAsString();

        String text = json.getAsJsonArray("verses")
                .get(0).getAsJsonObject()
                .get("text").getAsString().trim();

        return new Verse(
        book,
        chapter,
        verse,
        reference,
        text
);

    }
    public static String getChapter(String book, int chapter) throws Exception {
    String url = "https://bible-api.com/" +
            book + "%20" + chapter +
            "?translation=web";

    JsonObject json = fetchJson(url);

    StringBuilder sb = new StringBuilder();
    for (JsonElement v : json.getAsJsonArray("verses")) {
        sb.append(v.getAsJsonObject().get("verse").getAsInt())
          .append(". ")
          .append(v.getAsJsonObject().get("text").getAsString().trim())
          .append("\n\n");
    }
    return sb.toString();
}

public static String getParagraphVerse(String book, int chapter, int verse) throws Exception {
    int start = Math.max(1, verse - 2);
    int end = verse + 2;

    String url = "https://bible-api.com/" +
            book + "%20" + chapter + ":" + start + "-" + end +
            "?translation=web";

    JsonObject json = fetchJson(url);

    StringBuilder sb = new StringBuilder();
    for (JsonElement v : json.getAsJsonArray("verses")) {
        sb.append(v.getAsJsonObject().get("text").getAsString().trim())
          .append("\n\n");
    }
    return sb.toString();
}
private static JsonObject fetchJson(String url) throws Exception {
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    return JsonParser.parseString(response.body()).getAsJsonObject();
}

}
