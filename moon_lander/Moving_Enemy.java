package moon_lander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Moving_Enemy {
    /* x좌표를 설정시에 랜덤한 값을 사용함 */
    private Random random;
    /* enemy x position */
    public int x;
    /* enemy y position */
    public int y;

    private String image = "/resources/images/movingEnemy.png";

    private BufferedImage movingEnemyImg;

    public Moving_Enemy() {
        initialize();
        LordImage();
    }

    public void initialize() {
        random = new Random();
        ResetMovingEnemy();
    }

    public void LordImage() {
        try {
            URL enemyImgUrl = this.getClass().getResource(image);
            movingEnemyImg = ImageIO.read(enemyImgUrl);
        } catch (Exception e) {
            Logger.getLogger(Moving_Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void ResetMovingEnemy() {
        x = random.nextInt(Framework.frameWidth - 60);
        y = Framework.frameHeight - 1;
    }

    // 움직임을 위한 메소드
    public void tick() {
        y += 5;
        if (y >= Framework.frameHeight) {
            y = 0;
        }
    }

    // 충돌 확인을 위한 Rectangle 메소드
    public Rectangle drawRect() {
        return new Rectangle(x, y, 30, 30);
    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.drawImage(movingEnemyImg, x, y, null);
    }
}
