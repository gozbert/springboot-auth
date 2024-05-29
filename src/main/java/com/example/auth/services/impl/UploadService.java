package com.example.auth.services.impl;

import com.example.auth.exception.StorageException;
import com.example.auth.model.FileMetadata;
import com.example.auth.repository.FileMetadataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {

    private final ObjectStorageService storageService;

    private final FileMetadataRepository fileMetadataRepository;

    public List<FileMetadata> getFileMetadata() {
        return fileMetadataRepository.findAll();
    }

    @Transactional
    public UUID save(MultipartFile file) {
        try {
            UUID uuid = UUID.randomUUID();
            FileMetadata metadata = FileMetadata.builder()
                    .id(uuid.toString())
                    .size(file.getSize())
                    .dateUploaded(new Timestamp(System.currentTimeMillis()))
                    .originalName(file.getOriginalFilename())
                    .httpContentType(file.getContentType())
                    .build();
            fileMetadataRepository.save(metadata);
            storageService.save(file, uuid);
            return uuid;
        } catch (Exception ex) {
            log.error("Exception occurred when trying to save the file", ex);
            throw new StorageException(ex);
        }
    }

    public ContentWithMetadata fetchFile(UUID uuid) {
        FileMetadata fileMetadata = fileMetadataRepository.findById(uuid.toString()).orElseThrow();
        return new ContentWithMetadata(fileMetadata, readFile(uuid, fileMetadata.getSize()));
    }

    private byte[] readFile(UUID uuid, long fileSize) {
        long startPosition = 0;
        int chunkSize = (int) (fileSize - startPosition + 1);
        try (InputStream inputStream = storageService.getInputStream(uuid, startPosition, chunkSize)) {
            return inputStream.readAllBytes();
        } catch (Exception exception) {
            log.error("Exception occurred when trying to read file with ID = {}", uuid);
            throw new StorageException(exception);
        }
    }

    public record ContentWithMetadata(
            FileMetadata metadata,
            byte[] chunk) {
    }
}