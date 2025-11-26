import javax.swing.*;
import java.awt.*;

public class RemoveSongPanel extends JPanel {
    private JTextField removeField;
    private Playlist playlist;
    private MusicPlaylistManagerGUI mainFrame;

    public RemoveSongPanel(Playlist playlist, MusicPlaylistManagerGUI mainFrame) {
        this.playlist = playlist;
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Remove Song"));
        setMaximumSize(new Dimension(250, 100));
        setBackground(Color.BLACK);
        setOpaque(true);

        JLabel removeLbl = new JLabel("Song Title:");
        removeLbl.setForeground(Color.WHITE);
        add(removeLbl);
        
        removeField = new JTextField(20);
        removeField.setBackground(Color.DARK_GRAY);
        removeField.setForeground(Color.WHITE);
        removeField.setCaretColor(Color.WHITE);
        add(removeField);
        add(Box.createVerticalStrut(5));

        JButton removeButton = new JButton("Remove Song");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> removeSong());
        add(removeButton);
    }

    private void removeSong() {
        String title = removeField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a song title to remove!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Song toRemove = null;
        for (Song song : playlist.getSongs()) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                toRemove = song;
                break;
            }
        }

        if (toRemove != null) {
            playlist.getSongs().remove(toRemove);
            JOptionPane.showMessageDialog(this, "Song removed: " + title, "Removed", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.updatePlaylistDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Song not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        removeField.setText("");
    }
}