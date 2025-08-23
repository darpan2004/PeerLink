package p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p2p.service.FileSharer;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {
    @Autowired
    private FileSharer fileSharer;

    @PostMapping("/register")
    public Map<String, Object> registerPeer(@RequestParam int code, @RequestParam String ip, @RequestParam int port) {
        fileSharer.registerPeer(code, ip, port);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "Registered");
        return resp;
    }

    @GetMapping("/lookup/{code}")
    public Map<String, Object> getPeerInfo(@PathVariable int code) {
        FileSharer.PeerInfo info = fileSharer.getPeerInfo(code);
        Map<String, Object> resp = new HashMap<>();
        if (info != null) {
            resp.put("ip", info.ip);
            resp.put("port", info.port);
        }
        return resp;
    }
}
