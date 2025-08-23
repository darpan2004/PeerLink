package p2p;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class PeerRegister {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter invite code (port): ");
        int code = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter your server port (usually same as invite code): ");
        int port = Integer.parseInt(scanner.nextLine().trim());
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Registering with tracker as IP: " + ip + ", port: " + port);
        String params = "code=" + code + "&ip=" + URLEncoder.encode(ip, "UTF-8") + "&port=" + port;
        URL url = new URL("http://localhost:8080/api/tracker/register?" + params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Peer registered successfully.");
        } else {
            System.out.println("Failed to register peer. HTTP code: " + responseCode);
        }
        conn.disconnect();
    }
}

