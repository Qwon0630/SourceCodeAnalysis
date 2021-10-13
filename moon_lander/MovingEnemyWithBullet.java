package moon_lander;

import java.awt.Color;

import java.awt.Graphics2D;
import java.net.URL;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.LinkedList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MovingEnemyWithBullet {
    
    private Random random;
    /* enemy x position */
    public static int x;
    /* enemy y position */
    public static int y;
    /* Image */
    private String image = "/resources/images/movingEnemy.png";

    private BufferedImage movingEnemyImg;
    
    public static Bullet bullet;

    public MovingEnemyWithBullet() {
       
        LordImage();
        random = new Random();
        x = random.nextInt(Framework.frameWidth - 60);
        y = Framework.frameHeight - 40;
        bullet = new Bullet(x+10,y);
        
    }

    public void Reset() {
        x = random.nextInt(Framework.frameWidth - 60);
        y = Framework.frameHeight - 40;
        bullet = new Bullet(x+10,y);
    }
    
    public void Update() {
    	bullet.tick();
    }

    public void LordImage() {
        try {
            URL enemyImgUrl = this.getClass().getResource(image);
            movingEnemyImg = ImageIO.read(enemyImgUrl);
        } catch (Exception e) {
            Logger.getLogger(Moving_Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.drawImage(movingEnemyImg, x, y, null);
        bullet.Draw(g2d);
    }
}
