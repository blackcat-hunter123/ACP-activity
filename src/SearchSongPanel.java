import javax.swing.*;
import java.awt.*;

public class SearchSongPanel extends JPanel {
    private JTextField searchField;
    private Playlist playlist;

    public SearchSongPanel(Playlist playlist) {
        this.playlist = playlist;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Search Song"));
        setMaximumSize(new Dimension(250, 100));

        add(new JLabel("Song Title:"));
        searchField = new JTextField(20);
        add(searchField);
        add(Box.createVerticalStrut(5));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchSong());
        add(searchButton);
    }

    private void searchSong() {
        String title = searchField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a song title to search!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean found = false;
        for (Song song : playlist.getSongs()) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                JOptionPane.showMessageDialog(this, 
                    "Song found: " + song.getTitle() + " by " + song.getArtist() + "\nDuration: " + song.getDuration() + " minutes",
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Song not found in playlist.", "Not Found", JOptionPane.ERROR_MESSAGE);
        }
        
        searchField.setText("");
    }
}