public abstract class Media {
    private String title;
    private String artist;
    private static int totalMediaCount = 0;

    public Media(String title, String artist) {
        this.title = title;
        this.artist = artist;
        totalMediaCount++;
    }

    public abstract void play();

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public static int getTotalMediaCount() {
        return totalMediaCount;
    }
}