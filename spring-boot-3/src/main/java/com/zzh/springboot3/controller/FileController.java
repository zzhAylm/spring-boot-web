package com.zzh.springboot3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/3/5 15:18
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final String TEMP_DIR = "temp_chunks/";
    @PostMapping("/upload")
    public ResponseEntity<String> uploadChunk(@RequestParam("file") MultipartFile file,
                                              @RequestParam("filename") String filename,
                                              @RequestParam("chunkIndex") int chunkIndex) throws IOException {
        File chunkFile = new File(TEMP_DIR + filename + "_" + chunkIndex);
        file.transferTo(chunkFile);
        return ResponseEntity.ok("Chunk " + chunkIndex + " uploaded.");
    }





    @PostMapping("/merge")
    public ResponseEntity<String> mergeChunks(@RequestBody Map<String, Object> request) throws IOException {
        String filename = (String) request.get("filename");
        int totalChunks = (Integer) request.get("totalChunks");

        File mergedFile = new File("uploads/" + filename);
        try (FileOutputStream outputStream = new FileOutputStream(mergedFile, true)) {
            for (int i = 0; i < totalChunks; i++) {
                File chunkFile = new File("temp_chunks/" + filename + "_" + i);
                Files.copy(chunkFile.toPath(), outputStream);
                chunkFile.delete(); // 删除分片
            }
        }
        return ResponseEntity.ok("File merged successfully.");
    }

}
