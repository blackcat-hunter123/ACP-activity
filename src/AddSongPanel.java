import javax.swing.*;
import java.awt.*;

public class AddSongPanel extends JPanel {
    private JTextField titleField;
    private JTextField artistField;
    private JTextField durationField;
    private Playlist playlist;
    private MusicPlaylistManagerGUI mainFrame;

    public AddSongPanel(Playlist playlist, MusicPlaylistManagerGUI mainFrame) {
        this.playlist = playlist;
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Add New Song"));
        setMaximumSize(new Dimension(250, 200));

        add(new JLabel("Song Title:"));
        titleField = new JTextField(20);
        add(titleField);
        add(Box.createVerticalStrut(5));

        add(new JLabel("Artist:"));
        artistField = new JTextField(20);
        add(artistField);
        add(Box.createVerticalStrut(5));

        add(new JLabel("Duration (minutes):"));
        durationField = new JTextField(20);
        add(durationField);
        add(Box.createVerticalStrut(10));

        JButton addButton = new JButton("Add Song");
        addButton.addActionListener(e -> addSong());
        add(addButton);
    }

    private void addSong() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String durationText = durationField.getText().trim();

        if (title.isEmpty() || artist.isEmpty() || durationText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double duration = Double.parseDouble(durationText);
            playlist.addSong(new Song(title, artist, duration));
            JOptionPane.showMessageDialog(this, "Song added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            mainFrame.updatePlaylistDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duration must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        titleField.setText("");
        artistField.setText("");
        durationField.setText("");
    }
}