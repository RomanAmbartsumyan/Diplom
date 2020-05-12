package project.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.config.StorageConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
@Transactional
public class ImageService {

    private final Path location;

    @Autowired
    public ImageService(StorageConfig storageConfig) {
        this.location = Paths.get(storageConfig.getLocation());
    }

    public void init() {
        try {
            Files.createDirectories(location);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @SneakyThrows
    public String saveImg(MultipartFile file) {
        if (file != null) {
            String generateDirs = randomAlphabetic(2).toLowerCase() + "/"
                    + randomAlphabetic(2).toLowerCase() + "/"
                    + randomAlphabetic(2).toLowerCase() + "/";

            String actualPath = location + "/" + generateDirs;

            File uploadFolder = new File(actualPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String resultFileName = actualPath + file.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(resultFileName), file.getBytes());
            return "http://localhost:8080/" + resultFileName;
        }
        return null;
    }

}

