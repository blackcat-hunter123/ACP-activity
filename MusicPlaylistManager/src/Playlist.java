import java.util.ArrayList;

public class Playlist {
    private final String playlistName = "My Playlist";
    private ArrayList<Song> songs;
    private static int totalSongs = 0;

    public Playlist() {
        songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
        totalSongs++;
    }

    public void removeSong(String title) {
        Song toRemove = null;
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                toRemove = song;
                break;
            }
        }
        if (toRemove != null) {
            songs.remove(toRemove);
            totalSongs--;
            System.out.println("Song removed: " + title);
        } else {
            System.out.println("Song not found.");
        }
    }

    public void playAllSongs() {
        if (songs.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            for (Song song : songs) {
                song.play();
            }
        }
    }

    public void searchSong(String title) {
        boolean found = false;
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Song found: " + song.getTitle() + " by " + song.getArtist());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Song not found in playlist.");
        }
    }

    public void displayPlaylist() {
        if (songs.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            System.out.println("Songs in " + playlistName + ":");
            for (Song song : songs) {
                System.out.println(song.getTitle() + " by " + song.getArtist());
            }
        }
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public static int getTotalSongs() {
        return totalSongs;
    }

    void closeDatabase() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void clearAllSongs() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}