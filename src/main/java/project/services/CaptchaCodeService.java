package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.CaptchaDto;
import project.models.CaptchaCode;
import project.repositories.CaptchaCodeRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Service
@AllArgsConstructor
public class CaptchaCodeService {
    private final CaptchaCodeRepository captchaCodeRepository;

    public CaptchaCode createCaptcha() {
        LocalDateTime time = LocalDateTime.now();
        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setTime(time);
        String code = randomNumeric(4);
        captchaCode.setCode(code);
        captchaCode.setSecretCode(randomAlphanumeric(10));
        captchaCodeRepository.save(captchaCode);
        return captchaCode;
    }

    public boolean isValid(String code, String secretCode) {
        Optional<CaptchaCode> optionalCaptchaCode =
                captchaCodeRepository.findByCodeAndSecretCode(code, secretCode);
        return optionalCaptchaCode.isPresent();
    }

    public CaptchaDto getCaptchaDto() {
        CaptchaCode captchaCode = createCaptcha();
        String captcha = getImageBase64(captchaCode.getCode(), 20);
        LocalDateTime time = LocalDateTime.now().minusHours(1);
        deleteCaptcha(time);
        return new CaptchaDto(captchaCode.getSecretCode(), captcha);
    }

    public void deleteCaptcha(LocalDateTime time) {
        captchaCodeRepository.deleteAllByTimeBefore(time);
    }


    public String getImageBase64(String code, int codeSize) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Times New Roman", Font.PLAIN, codeSize);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(code);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();

        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);

        // Draw code...
        g2d.drawString(code, 0, fm.getAscent());

        g2d.dispose();

        String base64EncodedImage = "";

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", os);

            base64EncodedImage = "data:image/png;charset=utf-8;base64, " +
                    java.util.Base64.getEncoder().encodeToString(os.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64EncodedImage;
    }
}
