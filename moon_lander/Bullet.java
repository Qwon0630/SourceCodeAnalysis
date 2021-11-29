package moon_lander;

import java.awt.Graphics2D;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Bullet {
    /* 총알 좌표 */
    private int x;

    private int y;

    private int bulletSpeedY;

    private BufferedImage bulletImage;

    private int bulletImageWidth;

    private int bulletImageHeight;

    private Rectangle bounds;

    public Bullet(int _x, int _y) {
        this.x = _x;
        this.y = _y;
        LordImage();
        this.bulletSpeedY = 25;
        bounds = updateBounds();
    }

    // 움직임을 위한 메소드
    public void tick() {
        y += bulletSpeedY;
        bounds = updateBounds();
    }

    public int getY() {
        return this.y;
    }

    public void LordImage() {
        try {
            URL bulletImageURL = this.getClass().getResource("/resources/images/bullet.png");
            bulletImage = ImageIO.read(bulletImageURL);
            bulletImageWidth = bulletImage.getWidth();
            bulletImageHeight = bulletImage.getHeight();
        } catch (Exception e) {
            Logger.getLogger(Moving_Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // 충돌 확인을 위한 Rectangle 메소드
    public Rectangle updateBounds() {
        return new Rectangle(x, y, bulletImageWidth, bulletImageHeight);
    }

    public boolean collision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(bulletImage, x + (bulletImageWidth / 2), y, bulletImageWidth, -bulletImageHeight, null);
    }
}