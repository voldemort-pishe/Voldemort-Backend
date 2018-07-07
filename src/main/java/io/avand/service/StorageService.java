package io.avand.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Path loadTemp(String filename);

    Resource loadAsResource(String filename);

    Resource loadAsClassPath(String filename);

    Resource loadAsTemp(String filename);

    void deleteAll();

}
