import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MusicPlaylistManagerGUI extends JFrame {
    private Playlist playlist;
    private JTextArea playlistDisplay;
    private JLabel totalSongsLabel;
    private AddSongPanel addSongPanel;
    private SearchSongPanel searchSongPanel;

    public MusicPlaylistManagerGUI() {
        playlist = new Playlist();
        setupUI();
    }

    private void setupUI() {
        setTitle("Music Playlist Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel - Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("♫ Music Playlist Manager ♫");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 100, 150));
        titlePanel.add(titleLabel);

        // Middle Panel - Left side (Input panels)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        addSongPanel = new AddSongPanel(playlist, this);
        leftPanel.add(addSongPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        searchSongPanel = new SearchSongPanel(playlist);
        leftPanel.add(searchSongPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        RemoveSongPanel removeSongPanel = new RemoveSongPanel(playlist, this);
        leftPanel.add(removeSongPanel);
        leftPanel.add(Box.createVerticalGlue());

        // Middle Panel - Right side (Playlist display)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Playlist"));

        playlistDisplay = new JTextArea();
        playlistDisplay.setEditable(false);
        playlistDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        playlistDisplay.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(playlistDisplay);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Buttons and Info
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton playAllButton = new JButton("Play All Songs");
        playAllButton.addActionListener(e -> playAllSongs());
        actionPanel.add(playAllButton);

        JButton refreshButton = new JButton("Refresh Display");
        refreshButton.addActionListener(e -> updatePlaylistDisplay());
        actionPanel.add(refreshButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        actionPanel.add(exitButton);

        // Info Panel - Total songs
        JPanel infoPanel = new JPanel();
        totalSongsLabel = new JLabel("Total Songs: 0");
        totalSongsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(totalSongsLabel);

        bottomPanel.add(actionPanel, BorderLayout.CENTER);
        bottomPanel.add(infoPanel, BorderLayout.SOUTH);

        // Add panels to main
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
        updatePlaylistDisplay();
    }

    public void updatePlaylistDisplay() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Song song : playlist.getSongs()) {
            count++;
            sb.append(count).append(". ").append(song.getTitle()).append(" - ").append(song.getArtist())
                    .append(" (").append(song.getDuration()).append(" min)\n");
        }
        if (count == 0) {
            sb.append("Playlist is empty.");
        }
        playlistDisplay.setText(sb.toString());
        totalSongsLabel.setText("Total Songs: " + Media.getTotalMediaCount());
    }

    private void playAllSongs() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Playlist is empty!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder("Now Playing:\n\n");
            for (Song song : playlist.getSongs()) {
                sb.append("♫ ").append(song.getTitle()).append(" by ").append(song.getArtist())
                        .append(" (").append(song.getDuration()).append(" min)\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Playing All Songs", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlaylistManagerGUI::new);
    }
}