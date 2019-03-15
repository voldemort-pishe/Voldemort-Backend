package hr.pishe.web.rest;

import hr.pishe.service.FileService;
import hr.pishe.service.StorageService;
import hr.pishe.service.dto.FileDTO;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.errors.StorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
        Optional<FileDTO> fileDTO = filesService.findById(fileId);
        if (fileDTO.isPresent()) {
            try {
                Resource file = storageService.loadAsResource(fileDTO.get().getFilename());
                return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(fileDTO.get().getFileType()))
                    .body(file);
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
        Optional<FileDTO> fileDTO = filesService.findById(fileId);
        if (fileDTO.isPresent()) {
            try {
                Resource file = storageService.loadAsResource(fileDTO.get().getFilename());
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
