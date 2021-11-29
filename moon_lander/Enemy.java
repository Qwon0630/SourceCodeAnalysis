package moon_lander;

import java.util.Random;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.net.URL;

public class Enemy {
    private Random random;
    private Rectangle bounds;
    private BufferedImage image;
    private String imgURL;
    private int x;
    private int y;
    private int enemyID;

    public Enemy(int id) {
        random = new Random();
        if (id == 1)
            imgURL = "/resources/images/unmovedenemy.png";
        else if (id == 2 || id == 3)
            imgURL = "/resources/images/movingEnemy.png";
        enemyID = id;
        LordImage(imgURL);
        ResetEnemy(id);
    }

    public int getID() {
        return this.enemyID;
    }

    public int getImgWidth() {
        return image.getWidth();
    }

    public int getImgHeight() {
        return image.getHeight();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean collision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    public int generateRandNum(int range) {
        return random.nextInt(range) + 1;
    }

    public void ResetEnemy(int id) {
        x = generateRandNum(Framework.frameWidth - getImgWidth());
        switch (id) {
            case 1:
                y = 200;
                break;
            case 2:
            case 3:
                y = Framework.frameHeight - 10;
                break;
        }
        bounds = updateBounds();
    }

    public Rectangle updateBounds() {
        return new Rectangle(x, y, getImgWidth(), getImgHeight());
    }

    public void LordImage(String imgURL) {
        try {
            URL enemyImgUrl = this.getClass().getResource(imgURL);
            image = ImageIO.read(enemyImgUrl);
        } catch (Exception e) {
            Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, null);

    }
}