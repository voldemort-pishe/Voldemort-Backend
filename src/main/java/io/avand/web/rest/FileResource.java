package io.avand.web.rest;

import io.avand.security.AuthoritiesConstants;
import io.avand.service.FileService;
import io.avand.service.StorageService;
import io.avand.service.dto.FileDTO;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.errors.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing FileEntity.
 */
@RestController
@RequestMapping("/api/file")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "fileEntity";

    private final StorageService storageService;
    private final FileService filesService;

    public FileResource(StorageService storageService,
                        FileService filesService) {
        this.storageService = storageService;
        this.filesService = filesService;
    }


    @GetMapping("/load/{fileId}")
    public ResponseEntity<Resource> serveFile(@PathVariable("fileId") Long fileId,
                                              HttpServletRequest request) {
        FileDTO fileDTO = filesService.findById(fileId);
        if (fileDTO != null) {
            try {
                Resource file = storageService.loadAsResource(fileDTO.getFilename());
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
            } catch (StorageException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new ServerErrorException("File Not Found");
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId,
                                                 HttpServletRequest request) {
        FileDTO fileDTO = filesService.findById(fileId);
        if (fileDTO != null) {
            try {
                Resource file = storageService.loadAsResource(fileDTO.getFilename());
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
            } catch (StorageException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new ServerErrorException("File Not Found");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                           HttpServletRequest request) {

        String fileName = storageService.store(file);
        FileDTO newFile = new FileDTO();
        newFile.setFilename(fileName);
        newFile.setFileType(file.getContentType());
        FileDTO savedFile = filesService.save(newFile);

        return new ResponseEntity<>(savedFile, HttpStatus.OK);
    }
}
