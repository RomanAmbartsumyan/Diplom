package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.CaptchaCode;
import project.repositories.CaptchaCodeRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class CaptchaCodeService {
    private CaptchaCodeRepository captchaCodeRepository;

    public CaptchaCode getCaptcha(){
        LocalDateTime time = LocalDateTime.now();
        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setTime(time);
        String code = getRandomString();
        captchaCode.setCode(getImageBase64(code, code.length()));
        captchaCode.setSecretCode(getSecretCode());
        captchaCodeRepository.save(captchaCode);
        return captchaCode;
    }

    public void deleteCaptcha(Integer id){
        captchaCodeRepository.deleteById(id);
    }


    public String getImageBase64(String code, int codeLength) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Times New Roman", Font.PLAIN, codeLength);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(code);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();

        g2d.setPaint (Color.WHITE);
        g2d.fillRect (0, 0, width, height);

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

    private String getRandomString() {
        Random random = new Random();

        String code = "";

        for (int i = 0; i < 8; i++) {
            int capitalLetter = 65 + random.nextInt(26);
            code += capitalLetter;
        }

        return code;
    }

    private String getSecretCode(){
        Random random = new Random();
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString().toLowerCase();
    }
}
