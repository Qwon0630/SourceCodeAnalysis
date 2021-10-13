package moon_lander;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Item {
    private Random random;

    public int x;

    public int y;

    private BufferedImage itemImg;

    public int itemImgWidth;

    public int itemImgHeight;

    public boolean isTouched;

    private BufferedImage blackscreenImg;

    public Item() {
        Initialize();
        LoadContent();
    }

    private void Initialize() {
        random = new Random();

        x = random.nextInt(Framework.frameWidth - itemImgWidth);
        y = random.nextInt(Framework.frameHeight - itemImgHeight - 100);
    }

    private void LoadContent() {
        try {
            URL itemImgUrl = this.getClass().getResource("/resources/images/item.png");
            itemImg = ImageIO.read(itemImgUrl);
            itemImgWidth = itemImg.getWidth();
            itemImgHeight = itemImg.getHeight();

            URL blackscreenImgUrl = this.getClass().getResource("/resources/images/blackscreen.png");
            blackscreenImg = ImageIO.read(blackscreenImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ResetItem() {
        random = new Random();

        x = random.nextInt(Framework.frameWidth - itemImgWidth);
        y = random.nextInt(Framework.frameHeight - itemImgHeight);

    }

    public Rectangle drawRect() {
        return new Rectangle(x, y, itemImgWidth, itemImgHeight);
    }

    public void DeleteLandingAreaItem(boolean hide) {
        // hide landing area

    }

    public void MovingEnemyItem() {
        // make moving enemy
    }

    public void Draw(Graphics2D g2d) {
        g2d.drawImage(itemImg, x, y, null);

    }

}