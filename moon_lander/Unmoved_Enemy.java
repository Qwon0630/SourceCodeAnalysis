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

public class Unmoved_Enemy {
    /* x좌표를 설정시에 랜덤한 값을 사용함 */
    private Random random;
    /* enemy x position */
    public int x;
    /* enemy y position */
    public int y;
    /* Images */
    /* 공중에 떠있는 움직이지 않는 적 이미지 */
    private BufferedImage UnmovedEnemyImg;
    /* 이미지의 넓이 */
    public int UnmovedEnemyImgWidth;
    /* 이미지의 높이 */
    public int UnmovedEnemyImgHeight;

    private String image = "/resources/images/unmovedenemy.png";

    public Unmoved_Enemy() {
        initialize();
        LordEnemy();
    }

    public void initialize() {
        random = new Random();
        ResetUnmovedEnemy();
    }

    private void LordEnemy() {
        try {
            URL enemyImgUrl = this.getClass().getResource(image);
            UnmovedEnemyImg = ImageIO.read(enemyImgUrl);
            UnmovedEnemyImgWidth = UnmovedEnemyImg.getWidth();
            UnmovedEnemyImgHeight = UnmovedEnemyImg.getHeight();
        } catch (Exception e) {
            Logger.getLogger(Unmoved_Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void ResetUnmovedEnemy() {
        x = random.nextInt(Framework.frameWidth - UnmovedEnemyImgWidth - 50);
        y = 200;
    }

    public Rectangle drawRect() {
        return new Rectangle(x, y, UnmovedEnemyImgWidth, UnmovedEnemyImgHeight);
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(UnmovedEnemyImg, x, y, null);
    }
}