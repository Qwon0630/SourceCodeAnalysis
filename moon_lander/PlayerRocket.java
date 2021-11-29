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

import moon_lander.Framework.GameState;

import java.awt.Rectangle;

/**
 * The space rocket with which player will have to land.
 * 
 * @author www.gametutorial.net
 */

public class PlayerRocket {

    /**
     * We use this to generate a random number for starting x coordinate of the
     * rocket.
     */
    private Random random;

    public static int rocket1_X;
    public static int rocket1_Y;

    public static int rocket2_X;
    public static int rocket2_Y;

    public boolean landed_1p;
    public boolean crashed_1p;
    public boolean landed_2p;
    public boolean crashed_2p;

    public boolean getItem;

    private int speedAccelerating;
    private int speedStopping;

    public int topLandingSpeed;

    private int speed1p_X;
    public int speed1p_Y;
    private int speed2p_X;
    public int speed2p_Y;

    private BufferedImage rocketImg;
    private BufferedImage rocketLandedImg;
    private BufferedImage rocketCrashedImg;
    private BufferedImage rocketFireImg;
    private BufferedImage blackscreenImg;

    public int rocketImgWidth;
    public int rocketImgHeight;

    private BufferedImage rocket2pImg;
    private BufferedImage rocket2pLandedImg;
    private BufferedImage rocket2pCrashedImg;
    private BufferedImage rocket2pFireImg;

    public int rocket2pImgWidth;
    public int rocket2pImgHeight;

    private int fuelGauge;
    private int fuelGaugeHeight = 20;
    private int currentGameMode;

    public PlayerRocket(int level, int mode) {
        if (mode == 1) {
            switch (level) {
            case 1:
                Initialize(1);
                LoadContent(1);
                break;
            case 2:
                Initialize(2);
                LoadContent(1);
                break;
            case 3:
                Initialize(3);
                LoadContent(1);
                break;
            default:
                break;
            }
        } else if (mode == 2) {
            Initialize(1);
            LoadContent(2);
        }
        currentGameMode = mode;
        // Now that we have rocketImgWidth we set starting x coordinate.
        if ((Framework.frameWidth - rocketImgWidth) > 0) {
            rocket1_X = random.nextInt(Framework.frameWidth - rocketImgWidth);
        }

    }

    private void Initialize(int level) {
        random = new Random();
        fuelGauge = 150;
        ResetPlayer();
        switch (level) {
        case 1:
            speedAccelerating = 2;
            speedStopping = 1;
            topLandingSpeed = 5;
            break;
        case 2:
            speedAccelerating = 1;
            speedStopping = 5;
            topLandingSpeed = 10;
            break;
        default:
            speedAccelerating = 5;
            speedStopping = 4;
            topLandingSpeed = 6;
            break;
        }
    }

    private void LoadContent(int mode) {
        try {
            URL rocketImgUrl = this.getClass().getResource("/resources/images/rocket.png");
            rocketImg = ImageIO.read(rocketImgUrl);
            rocketImgWidth = rocketImg.getWidth();
            rocketImgHeight = rocketImg.getHeight();
            URL rocketLandedImgUrl = this.getClass().getResource("/resources/images/rocket_landed.png");
            rocketLandedImg = ImageIO.read(rocketLandedImgUrl);
            URL rocketCrashedImgUrl = this.getClass().getResource("/resources/images/rocket_crashed.png");
            rocketCrashedImg = ImageIO.read(rocketCrashedImgUrl);
            URL rocketFireImgUrl = this.getClass().getResource("/resources/images/rocket_fire.png");
            rocketFireImg = ImageIO.read(rocketFireImgUrl);
            URL blackscreenImgUrl = this.getClass().getResource("/resources/images/blackscreen.png");
            blackscreenImg = ImageIO.read(blackscreenImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mode == 2) {
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
                Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer() {
        landed_1p = false;
        crashed_1p = false;
        landed_2p = false;
        crashed_2p = false;

        if ((Framework.frameWidth - rocketImgWidth) > 0) {
            rocket1_X = random.nextInt(Framework.frameWidth - rocketImgWidth);
            rocket2_X = random.nextInt(Framework.frameWidth - rocketImgWidth);
        }
        rocket1_Y = 40;
        rocket2_Y = 40;

        speed1p_X = 0;
        speed1p_Y = 0;
        speed2p_X = 0;
        speed2p_Y = 0;

        fuelGauge = 150;

    }

    private void drawFuelGuage(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(0, 40, fuelGauge, fuelGaugeHeight);
    }

    public void addFeul(int plus) {
        this.fuelGauge += plus;
    }

    public Rectangle makeRect1p() {
        return new Rectangle(rocket1_X, rocket1_Y, rocketImgWidth, rocketImgHeight);
    }

    public Rectangle makeRect2p() {
        return new Rectangle(rocket2_X, rocket2_Y, rocket2pImgWidth, rocket2pImgHeight);
    }

    /**
     * Here we move the rocket.
     */
    public void Update() {

        if (Canvas.keyboardKeyState(KeyEvent.VK_W) && (Canvas.getBlock() == false)) {
            speed1p_Y -= speedAccelerating;
            fuelGauge -= 2;
        }

        else
            speed1p_Y += speedStopping;

        if (Canvas.keyboardKeyState(KeyEvent.VK_A))
            speed1p_X -= speedAccelerating;
        else if (speed1p_X < 0)
            speed1p_X += speedStopping;

        if (Canvas.keyboardKeyState(KeyEvent.VK_D))
            speed1p_X += speedAccelerating;
        else if (speed1p_X > 0)
            speed1p_X -= speedStopping;

        rocket1_X += speed1p_X;
        rocket1_Y += speed1p_Y;
        /* Border Collision */
        if (rocket1_X < 0) {
            rocket1_X = 0;
            speed1p_X = 0;
        } else if (rocket1_X > Framework.frameWidth - rocketImgWidth) {
            rocket1_X = Framework.frameWidth - rocketImgWidth;
            speed1p_X = 0;
        }
        if (rocket1_Y < 0) {
            rocket1_Y = 0;
            speed1p_Y = 0;
        }

        if (Canvas.keyboardKeyState(KeyEvent.VK_UP)) {
            speed2p_Y -= speedAccelerating;
        } else
            speed2p_Y += speedStopping;

        if (Canvas.keyboardKeyState(KeyEvent.VK_LEFT))
            speed2p_X -= speedAccelerating;
        else if (speed2p_X < 0)
            speed2p_X += speedStopping;

        if (Canvas.keyboardKeyState(KeyEvent.VK_RIGHT))
            speed2p_X += speedAccelerating;
        else if (speed2p_X > 0)
            speed2p_X -= speedStopping;

        rocket2_X += speed2p_X;
        rocket2_Y += speed2p_Y;

        /* Collision */
        /* Border Collision */
        if (rocket2_X < 0) {
            rocket2_X = 0;
            speed2p_X = 0;
        } else if (rocket2_X > Framework.frameWidth - rocket2pImgWidth) {
            rocket2_X = Framework.frameWidth - rocket2pImgWidth;
            speed2p_X = 0;
        }
        if (rocket2_Y < 0) {
            rocket2_Y = 0;
            speed2p_Y = 0;
        }
        /* If User spent all fleu */
        if (fuelGauge == 0)
            Canvas.changeBlockState(true);
    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.white);

        g2d.drawString("Rocket1p coordinates: " + rocket1_X + " : " + rocket1_Y, 5, 15);
        if (Framework.gameState == GameState.PLAYING2P)
            g2d.drawString("Rocket2p coordinates: " + rocket2_X + " : " + rocket2_Y, 500, 15);
        if (currentGameMode == 1) {
            drawFuelGuage(g2d);
        }
        // If the rocket is landed.
        if (landed_1p) {
            g2d.drawImage(rocketLandedImg, rocket1_X, rocket1_Y, null);
        }
        // If the rocket is crashed.
        else if (crashed_1p) {
            g2d.drawImage(rocketCrashedImg, rocket1_X, rocket1_Y + rocketImgHeight - rocketCrashedImg.getHeight(),
                    null);
        }
        // If the rocket is still in the space.
        else {
            // If player hold down a W key we draw rocket fire.
            if (Canvas.keyboardKeyState(KeyEvent.VK_W) && (Canvas.getBlock() == false)) {
                g2d.drawImage(rocketFireImg, rocket1_X + 12, rocket1_Y + 66, null);
            }
            g2d.drawImage(rocketImg, rocket1_X, rocket1_Y, null);
        }
        if (landed_2p) {
            g2d.drawImage(rocket2pLandedImg, rocket2_X, rocket2_Y, null);
        }
        // If the rocket is crashed.
        else if (crashed_2p) {
            g2d.drawImage(rocket2pCrashedImg, rocket2_X, rocket2_Y + rocket2pImgHeight - rocket2pCrashedImg.getHeight(),
                    null);
        }
        // If the rocket is still in the space.
        else {
            // If player hold down a W key we draw rocket fire.
            if (Canvas.keyboardKeyState(KeyEvent.VK_UP))
                g2d.drawImage(rocket2pFireImg, rocket2_X + 12, rocket2_Y + 66, null);
            g2d.drawImage(rocket2pImg, rocket2_X, rocket2_Y, null);
        }
        if (getItem) {
            g2d.drawImage(blackscreenImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        }
    }
}