package com.example.auth.controller;

import com.example.auth.model.FileMetadata;
import com.example.auth.services.impl.UploadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
@SecurityRequirement(name = "TEST")
@Tag(name = "File upload")
public class UploadController {

    private final UploadService uploadService;

    @Value("${upload.default-chunk-size}")
    public Integer defaultChunkSize;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> saveObject(@RequestParam("file") MultipartFile file) {
        UUID fileUuid = uploadService.save(file);
        return ResponseEntity.ok(fileUuid);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> fetchObject(
            @PathVariable UUID uuid) {

        UploadService.ContentWithMetadata contentWithMetadata = uploadService.fetchFile(uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .header(CONTENT_TYPE, contentWithMetadata.metadata().getHttpContentType())
                .header(CONTENT_LENGTH, String.valueOf(contentWithMetadata.metadata().getSize()))
                .body(contentWithMetadata.chunk());
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<FileMetadata>> fetchMetadata() {

        List<FileMetadata> metadata = uploadService.getFileMetadata();
        return ResponseEntity.status(HttpStatus.OK)
                .body(metadata);
    }
}