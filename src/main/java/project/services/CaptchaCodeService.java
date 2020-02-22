package project.services;

import org.springframework.stereotype.Service;
import project.repositories.CaptchaCodeRepository;

@Service
public class CaptchaCodeService {
    private CaptchaCodeRepository captchaCodeRepository;

    public CaptchaCodeService(CaptchaCodeRepository captchaCodeRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
    }
}
