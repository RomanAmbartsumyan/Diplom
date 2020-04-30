package project.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
public class ImgService {

    @Value("${upload.path}")
    private String path;

    @SneakyThrows
    public String saveImg(MultipartFile file) {
        if (file != null) {
            String generateDirs = randomAlphabetic(2).toLowerCase() + "/"
                    + randomAlphabetic(2).toLowerCase() + "/" +
                    randomAlphabetic(2).toLowerCase() + "/";

            String actualPath = path + generateDirs;

            File uploadFolder = new File(actualPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String resultFileName = actualPath + file.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(resultFileName), file.getBytes());
            return "uploads/" + generateDirs + file.getOriginalFilename();
        }
        return null;
    }
}
