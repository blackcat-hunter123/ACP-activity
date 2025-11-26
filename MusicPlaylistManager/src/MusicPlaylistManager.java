import java.util.Scanner;

public class MusicPlaylistManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Playlist playlist = new Playlist();
        
        while (true) {
            System.out.println("\n**** Music Playlist Manager ****");
            System.out.println("1. Add Song");
            System.out.println("2. Remove Song");
            System.out.println("3. Show Playlist");
            System.out.println("4. Play All Songs");
            System.out.println("5. Search Song by Title");
            System.out.println("6. Show Total Songs");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter duration (minutes): ");
                    double duration = scanner.nextDouble();
                    scanner.nextLine();
                    playlist.addSong(new Song(title, artist, duration));
                    break;
                    
                case 2:
                    System.out.print("Enter song title to remove: ");
                    String removeTitle = scanner.nextLine();
                    playlist.removeSong(removeTitle);
                    break;
                    
                case 3:
                    playlist.displayPlaylist();
                    break;
                    
                case 4:
                    playlist.playAllSongs();
                    break;
                    
                case 5:
                    System.out.print("Enter song title to search: ");
                    String searchTitle = scanner.nextLine();
                    playlist.searchSong(searchTitle);
                    break;
                    
                case 6:
                    System.out.println("Total songs added: " + Media.getTotalMediaCount());
                    break;
                    
                case 7:
                    System.out.println("Exiting Music Playlist Manager...");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}