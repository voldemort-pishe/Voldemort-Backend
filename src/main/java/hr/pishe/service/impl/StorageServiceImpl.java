package hr.pishe.service.impl;

import hr.pishe.config.StorageProperties;
import hr.pishe.service.StorageService;
import hr.pishe.service.util.RandomUtil;
import hr.pishe.web.rest.errors.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {


	private final StorageProperties storageProperties;

	public StorageServiceImpl(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
	}

	@Override
    public String store(MultipartFile file) {
		Path rootLocation = getPath();
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
        }

        String fileExtension =  FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = RandomUtil.shortUUID()+"."+fileExtension;
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(newFileName));
            return newFileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }

    }

	@Override
    public Stream<Path> loadAll() {
		Path rootLocation = getPath();
        try {
            return Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
		return Paths.get(storageProperties.getLocation()).resolve(filename);
    }

	@Override
	public Path loadTemp(String filename) {
		return Paths.get(storageProperties.getLocation() + "/temp").resolve(filename);
	}

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Resource loadAsClassPath(String filename){
		Resource resource = new ClassPathResource(filename);
		if(resource.exists() || resource.isReadable()) {
			return resource;
		}
		else {
			throw new StorageException("Could not read file: " + filename);

		}
	}

	@Override
	public Resource loadAsTemp(String filename) {
		try {
			Path file = loadTemp(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageException("Could not read file: " + filename, e);
		}
	}

    @Override
    public void deleteAll() {
		Path rootLocation = getPath();
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
		Path rootLocation = getPath();
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	private Path getPath() {
		return Paths.get(storageProperties.getLocation());
	}

}
