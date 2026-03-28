package com.polycodefusion.controller;

import com.polycodefusion.service.FileProcessingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileProcessingService fileProcessingService;

    // ✅ Manual constructor (fixes your error)
    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("targetLanguage") String targetLanguage) {

        var processedFiles =
                fileProcessingService.processFiles(files);

        var result =
                fileProcessingService.convertToTargetLanguage(processedFiles, targetLanguage);

        return ResponseEntity.ok(result);
    }
}