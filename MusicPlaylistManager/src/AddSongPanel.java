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
        setBackground(Color.BLACK);
        setOpaque(true);

        JLabel titleLbl = new JLabel("Song Title:");
        titleLbl.setForeground(Color.WHITE);
        add(titleLbl);
        
        titleField = new JTextField(20);
        titleField.setBackground(Color.DARK_GRAY);
        titleField.setForeground(Color.WHITE);
        titleField.setCaretColor(Color.WHITE);
        add(titleField);
        add(Box.createVerticalStrut(5));

        JLabel artistLbl = new JLabel("Artist:");
        artistLbl.setForeground(Color.WHITE);
        add(artistLbl);
        
        artistField = new JTextField(20);
        artistField.setBackground(Color.DARK_GRAY);
        artistField.setForeground(Color.WHITE);
        artistField.setCaretColor(Color.WHITE);
        add(artistField);
        add(Box.createVerticalStrut(5));

        JLabel durationLbl = new JLabel("Duration (minutes):");
        durationLbl.setForeground(Color.WHITE);
        add(durationLbl);
        
        durationField = new JTextField(20);
        durationField.setBackground(Color.DARK_GRAY);
        durationField.setForeground(Color.WHITE);
        durationField.setCaretColor(Color.WHITE);
        add(durationField);
        add(Box.createVerticalStrut(10));

        JButton addButton = new JButton("Add Song");
        addButton.setBackground(Color.RED);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
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