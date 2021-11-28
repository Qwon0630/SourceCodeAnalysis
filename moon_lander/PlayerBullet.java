package moon_lander;

import java.awt.Rectangle;

public class PlayerBullet {

    public double x;
    public double y;
    public double vx;
    public double vy;

    public PlayerBullet(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public boolean moveBullet() {
        x += vx;
        y += vy;
        if (x < 0 || x > Framework.frameWidth || y < 0 || y > Framework.frameHeight)// 화면을 벗어나면
        {
            return false;
        }
        return true;
    }

    public Rectangle drawRect() {
        return new Rectangle((int) x, (int) y, 5, 5);
    }

}