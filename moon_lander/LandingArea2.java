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

public class LandingArea2 {
    public Random random;

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
    private BufferedImage landingArea2Img;

    /**
     * Width of landing area.
     */
    public int landingArea2ImgWidth;

    public LandingArea2() {
        Initialize();
        LoadContent();
    }

    public void ResetLandingArea2() {
        random = new Random();
        // X coordinate of the landing area is at 46% frame width.
        x = random.nextInt(Framework.frameWidth - landingArea2ImgWidth-50);
        // Y coordinate of the landing area is at 86% frame height.
        y = (int) (Framework.frameHeight * 0.88);

    }

    private void Initialize() {
        random = new Random();
        // X coordinate of the landing area is at 46% frame width.
        x = random.nextInt(Framework.frameWidth - landingArea2ImgWidth-50);
        // Y coordinate of the landing area is at 86% frame height.
        y = (int) (Framework.frameHeight * 0.88);
    }

    private void LoadContent() {
        try {
            URL landingArea2ImgUrl = this.getClass().getResource("/resources/images/landing_area2p.png");
            landingArea2Img = ImageIO.read(landingArea2ImgUrl);
            landingArea2ImgWidth = landingArea2Img.getWidth();
        } catch (IOException ex) {
            Logger.getLogger(LandingArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(landingArea2Img, x, y, null);
    }

}