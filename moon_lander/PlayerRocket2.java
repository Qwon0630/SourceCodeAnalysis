package moon_lander;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

/**
 * The space rocket with which player will have to land.
 * 
 * @author www.gametutorial.net
 */

public class PlayerRocket2 {

    /**
     * We use this to generate a random number for starting x coordinate of the
     * rocket.
     */
    private Random random;

    /**
     * X coordinate of the rocket.
     */
    public int x;
    /**
     * Y coordinate of the rocket.
     */
    public int y;

    /**
     * Is rocket landed?
     */
    public boolean landed;

    /**
     * Has rocket crashed?
     */
    public boolean crashed;

    public boolean getItem;

    /**
     * Accelerating speed of the rocket.
     */
    private int speedAccelerating;
    /**
     * Stopping/Falling speed of the rocket. Falling speed because, the gravity
     * pulls the rocket down to the moon.
     */
    private int speedStopping;

    /**
     * Maximum speed that rocket can have without having a crash when landing.
     */
    public int topLandingSpeed;

    /**
     * How fast and to which direction rocket is moving on x coordinate?
     */
    private int speedX;
    /**
     * How fast and to which direction rocket is moving on y coordinate?
     */
    public int speedY;

    /**
     * Image of the rocket in air.
     */
    private BufferedImage rocket2pImg;

    /**
     * Image of the rocket when landed.
     */
    private BufferedImage rocket2pLandedImg;
    /**
     * Image of the rocket when crashed.
     */
    private BufferedImage rocket2pCrashedImg;
    /**
     * Image of the rocket fire.
     */
    private BufferedImage rocket2pFireImg;

    /**
     * Width of rocket.
     */
    public int rocket2pImgWidth;

    /**
     * Height of rocket.
     */
    public int rocket2pImgHeight;

    public PlayerRocket2() {
        Initialize();
        LoadContent();

        // Now that we have rocketImgWidth we set starting x coordinate.
        x = random.nextInt(Framework.frameWidth - rocket2pImgWidth);
    }

    private void Initialize() {
        random = new Random();

        ResetPlayer();

        speedAccelerating = 2;
        speedStopping = 1;

        topLandingSpeed = 5;
    }

    private void LoadContent() {
        try {
            URL rocket2pImgUrl = this.getClass().getResource("/resources/images/rocket2p.png");
            rocket2pImg = ImageIO.read(rocket2pImgUrl);
            rocket2pImgWidth = rocket2pImg.getWidth();
            rocket2pImgHeight = rocket2pImg.getHeight();

            URL rocket2pLandedImgUrl = this.getClass().getResource("/resources/images/rocket_landed.png");
            rocket2pLandedImg = ImageIO.read(rocket2pLandedImgUrl);

            URL rocket2pCrashedImgUrl = this.getClass().getResource("/resources/images/rocket_crashed.png");
            rocket2pCrashedImg = ImageIO.read(rocket2pCrashedImgUrl);

            URL rocket2pFireImgUrl = this.getClass().getResource("/resources/images/rocket_fire.png");
            rocket2pFireImg = ImageIO.read(rocket2pFireImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(PlayerRocket2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer() {
        landed = false;
        crashed = false;

        x = random.nextInt(Framework.frameWidth - rocket2pImgWidth);
        y = 40;

        speedX = 0;
        speedY = 0;
    }

    public Rectangle makeRect() {
        return new Rectangle(x, y, rocket2pImgWidth, rocket2pImgHeight);
    }

    /**
     * Here we move the rocket.
     */

    public void Update2() {
        // Calculating speed for moving up or down.
        if (Canvas.keyboardKeyState(KeyEvent.VK_UP))
            speedY -= speedAccelerating;
        else
            speedY += speedStopping;

        // Calculating speed for moving or stopping to the left.
        if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT))
            speedX -= speedAccelerating;
        else if (speedX < 0)
            speedX += speedStopping;

        // Calculating speed for moving or stopping to the right.
        if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT))
            speedX += speedAccelerating;
        else if (speedX > 0)
            speedX -= speedStopping;

        // Moves the rocket.
        x += speedX;
        y += speedY;

        /* Collision */
        /* Border Collision */
        if (x < 0) {
            x = 0;
            speedX = 0;
        } else if (x > Framework.frameWidth - rocket2pImgWidth) {
            x = Framework.frameWidth - rocket2pImgWidth;
            speedX = 0;
        }
        if (y < 0) {
            y = 0;
            speedY = 0;
        }

    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.drawString("Rocket coordinates: " + x + " : " + y, 500, 15);

        // If the rocket is landed.
        if (landed) {
            g2d.drawImage(rocket2pLandedImg, x, y, null);
        }
        // If the rocket is crashed.
        else if (crashed) {
            g2d.drawImage(rocket2pCrashedImg, x, y + rocket2pImgHeight - rocket2pCrashedImg.getHeight(), null);
        }
        // If the rocket is still in the space.
        else {
            // If player hold down a W key we draw rocket fire.
            if (Canvas.keyboardKeyState(KeyEvent.VK_UP))
                g2d.drawImage(rocket2pFireImg, x + 12, y + 66, null);
            g2d.drawImage(rocket2pImg, x, y, null);
        }
    }

}