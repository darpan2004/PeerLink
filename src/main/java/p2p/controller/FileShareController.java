package p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import p2p.service.FileSharer;

import java.io.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/files")
public class FileShareController {

    @Autowired
    private FileSharer fileSharer;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Use absolute path for uploads directory in project root
        File uploadDir = new File(System.getProperty("user.dir"), "uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();
        File destFile = new File(uploadDir, file.getOriginalFilename());
        file.transferTo(destFile);
        int code = fileSharer.offerFile(destFile.getAbsolutePath());
        // Return the code as JSON for easier frontend handling
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("code", code));
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<?> downloadFile(@PathVariable int code) throws IOException {
        String filePath = fileSharer.getFilePath(code);
        if (filePath == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found for code: " + code);
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found on disk.");
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String contentType = java.nio.file.Files.probeContentType(file.toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        // Always use the original file name in the header
        String originalFileName = file.getName();
        // If the file was saved with a unique name, try to extract the original name from the path
        // (not needed here since we save as original name)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
