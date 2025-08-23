package p2p;

import java.io.*;
import java.net.Socket;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class PeerLinkClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter invite code: ");
        int code = Integer.parseInt(scanner.nextLine().trim());

        // Query tracker for peer info
        String trackerUrl = "http://localhost:8080/api/tracker/lookup/" + code;
        String ip = null;
        int port = -1;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(trackerUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                JSONObject json = new JSONObject(sb.toString());
                ip = json.optString("ip", null);
                port = json.optInt("port", -1);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Error contacting tracker: " + e.getMessage());
            return;
        }
        if (ip == null || port == -1) {
            System.err.println("No peer found for code: " + code);
            return;
        }
        System.out.println("Connecting to peer at " + ip + ":" + port);
        try (Socket socket = new Socket(ip, port);
             InputStream in = socket.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String header = reader.readLine();
            String filename = "received_file";
            if (header != null && header.startsWith("Filename: ")) {
                filename = header.substring("Filename: ".length()).trim();
            }
            System.out.println("Receiving file: " + filename);
            try (FileOutputStream fos = new FileOutputStream(filename)) {
                char[] headerChars = (header + "\n").toCharArray();
                int headerLen = headerChars.length;
                for (int i = 0; i < headerLen; i++) in.read();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("File received and saved as: " + filename);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
