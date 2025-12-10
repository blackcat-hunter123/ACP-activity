import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MusicPlaylistManagerGUI extends JFrame {
    private Playlist playlist;
    private JTextArea playlistDisplay;
    private JLabel totalSongsLabel;
    private AddSongPanel addSongPanel;
    private SearchSongPanel searchSongPanel;

    public MusicPlaylistManagerGUI() {
        playlist = new Playlist();
        setupUI();
        
        // Add window listener to close database connection on exit
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                playlist.closeDatabase();
                System.exit(0);
            }
        });
    }

    private void setupUI() {
        setTitle("Music Playlist Manager");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setOpaque(true);

        // Top Panel - Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setOpaque(true);
        JLabel titleLabel = new JLabel("♫ Music Playlist Manager ♫");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Middle Panel - Left side (Input panels)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setOpaque(true);

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
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setOpaque(true);

        playlistDisplay = new JTextArea();
        playlistDisplay.setEditable(false);
        playlistDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        playlistDisplay.setBackground(Color.BLACK);
        playlistDisplay.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(playlistDisplay);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setOpaque(true);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Buttons and Info
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setOpaque(true);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.setBackground(Color.BLACK);
        actionPanel.setOpaque(true);

        JButton playAllButton = new JButton("Play All Songs");
        playAllButton.setBackground(Color.RED);
        playAllButton.setForeground(Color.WHITE);
        playAllButton.addActionListener(e -> playAllSongs());
        actionPanel.add(playAllButton);

        JButton refreshButton = new JButton("Refresh Display");
        refreshButton.setBackground(Color.RED);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> updatePlaylistDisplay());
        actionPanel.add(refreshButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setBackground(Color.RED);
        clearAllButton.setForeground(Color.WHITE);
        clearAllButton.addActionListener(e -> clearAllSongs());
        actionPanel.add(clearAllButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            playlist.closeDatabase();
            System.exit(0);
        });
        actionPanel.add(exitButton);

        // Info Panel - Total songs
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.BLACK);
        infoPanel.setOpaque(true);
        totalSongsLabel = new JLabel("Total Songs: 0");
        totalSongsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalSongsLabel.setForeground(Color.WHITE);
        infoPanel.add(totalSongsLabel);

        bottomPanel.add(actionPanel, BorderLayout.CENTER);
        bottomPanel.add(infoPanel, BorderLayout.SOUTH);

        // Add panels to main
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setOpaque(true);
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

    private void clearAllSongs() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all songs?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            playlist.clearAllSongs();
            updatePlaylistDisplay();
            JOptionPane.showMessageDialog(this, "All songs cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlaylistManagerGUI::new);
    }
}