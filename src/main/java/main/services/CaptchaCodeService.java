package main.services;

import main.repositories.CaptchaCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CaptchaCodeService {
    private CaptchaCodeRepository captchaCodeRepository;

    public CaptchaCodeService(CaptchaCodeRepository captchaCodeRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
    }
}
