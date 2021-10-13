package moon_lander;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * Landing area where rocket will land.
 * 
 * @author www.gametutorial.net
 */

public class LandingArea {
    private Random random;

    /**
     * X coordinate of the landing area.
     */
    public int x;
    /**
     * Y coordinate of the landing area.
     */
    public int y;

    /**
     * Image of landing area.
     */
    private BufferedImage landingArea1Img;

    /**
     * Width of landing area.
     */
    public int landingArea1ImgWidth;

    public LandingArea() {
        Initialize();
        LoadContent();
    }

    public void ResetLandingArea() {
        random = new Random();
        // X coordinate of the landing area is at 46% frame width.
        x = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
        // Y coordinate of the landing area is at 86% frame height.
        y = (int) (Framework.frameHeight * 0.88);
    }

    private void Initialize() {
        random = new Random();
        // X coordinate of the landing area is at 46% frame width.
        x = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
        // Y coordinate of the landing area is at 86% frame height.
        y = (int) (Framework.frameHeight * 0.88);
    }

    private void LoadContent() {
        try {
            URL landingArea1ImgUrl = this.getClass().getResource("/resources/images/landing_area.png");
            landingArea1Img = ImageIO.read(landingArea1ImgUrl);
            landingArea1ImgWidth = landingArea1Img.getWidth();
        } catch (IOException ex) {
            Logger.getLogger(LandingArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(landingArea1Img, x, y, null);
    }

}