import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:playlist.db";
    private Connection connection;

    public DatabaseManager() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Establish connection
            connection = DriverManager.getConnection(DB_URL);
            createTableIfNotExists();
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    // Create songs table if it doesn't exist
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS songs (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "title TEXT NOT NULL, " +
                     "artist TEXT NOT NULL, " +
                     "duration REAL NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    // Add song to database
    public boolean addSong(Song song) {
        String sql = "INSERT INTO songs (title, artist, duration) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setDouble(3, song.getDuration());
            pstmt.executeUpdate();
            System.out.println("Song added to database: " + song.getTitle());
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding song: " + e.getMessage());
            return false;
        }
    }

    // Remove song from database by title
    public boolean removeSong(String title) {
        String sql = "DELETE FROM songs WHERE LOWER(title) = LOWER(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Song removed from database: " + title);
                return true;
            } else {
                System.out.println("Song not found in database.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error removing song: " + e.getMessage());
            return false;
        }
    }

    // Load all songs from database
    public ArrayList<Song> loadAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT title, artist, duration FROM songs";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                double duration = rs.getDouble("duration");
                songs.add(new Song(title, artist, duration));
            }
            System.out.println("Loaded " + songs.size() + " songs from database.");
        } catch (SQLException e) {
            System.err.println("Error loading songs: " + e.getMessage());
        }
        return songs;
    }

    // Search song in database
    public Song searchSong(String title) {
        String sql = "SELECT title, artist, duration FROM songs WHERE LOWER(title) = LOWER(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String songTitle = rs.getString("title");
                String artist = rs.getString("artist");
                double duration = rs.getDouble("duration");
                return new Song(songTitle, artist, duration);
            }
        } catch (SQLException e) {
            System.err.println("Error searching song: " + e.getMessage());
        }
        return null;
    }

    // Get total count of songs
    public int getTotalSongCount() {
        String sql = "SELECT COUNT(*) as count FROM songs";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error counting songs: " + e.getMessage());
        }
        return 0;
    }

    // Clear all songs from database
    public boolean clearAllSongs() {
        String sql = "DELETE FROM songs";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All songs cleared from database.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error clearing songs: " + e.getMessage());
            return false;
        }
    }

    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}