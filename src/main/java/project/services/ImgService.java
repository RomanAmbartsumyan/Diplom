package project.services;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
public class ImgService {

    @SneakyThrows
    public String saveImg(MultipartFile file) {
        String firstDir = randomAlphabetic(2).toLowerCase() + "/";
        String secondDir = randomAlphabetic(2).toLowerCase() + "/";
        String thirdDir = randomAlphabetic(2).toLowerCase() + "/";
        String actualPath = "src/main/resources/uploads/" + firstDir + secondDir + thirdDir;
        File uploadFolder = new File(actualPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }
        String resultFileName = actualPath + file.getOriginalFilename();
        file.transferTo(new File(resultFileName));
        return actualPath;
    }
}
