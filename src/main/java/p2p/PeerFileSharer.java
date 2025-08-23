package p2p;

import p2p.service.FileSharer;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PeerFileSharer {
    public static void main(String[] args) {
        try {
            FileSharer fileSharer = new FileSharer();
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== Peer-to-Peer File Sharing ===");
            System.out.print("Enter the full path of the file to share: ");
            String filePath = scanner.nextLine().trim();
            int port = fileSharer.offerFile(filePath);
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println("\nYour invite code: " + port);
            System.out.println("Your IP address: " + ip);
            System.out.println("\nRegistering with tracker...");
            String params = "code=" + port + "&ip=" + URLEncoder.encode(ip, "UTF-8") + "&port=" + port;
            URL url = new URL("http://localhost:8080/api/tracker/register?" + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Peer registered successfully with tracker.");
            } else {
                System.out.println("Failed to register peer. HTTP code: " + responseCode);
            }
            conn.disconnect();
            System.out.println("\nShare this invite code with your peer.");
            System.out.println("Waiting for a peer to connect and download the file...");
            fileSharer.startFileServer(port);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
