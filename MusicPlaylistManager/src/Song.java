public class Song extends Media {
    private double duration;

    public Song(String title, String artist, double duration) {
        super(title, artist);
        this.duration = duration;
    }

    @Override
    public void play() {
        System.out.println("Playing: " + getTitle() + " by " + getArtist() + " (" + duration + " min)");
    }

    public double getDuration() {
        return duration;
    }
}