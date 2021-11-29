package moon_lander;

import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MovingEnemyWithBullet extends Enemy {

    private int velY; // y축 이동 속도
    private int interval;
    private int maxInterval = 150;

    private LinkedList<Bullet> bulletList = new LinkedList<Bullet>();

    public MovingEnemyWithBullet(int id) {
        super(id);
        velY = generateRandNum(10);
        interval = 0;
    }

    private void move() {
        int currentY = this.getY();
        setY(currentY += velY);
        // for reset y coordinate
        if (currentY >= Framework.frameHeight) {
            setY(0);
        }
    }

    private void fireBulletCheck() {
        this.interval += generateRandNum(20);
        if (interval > maxInterval) {
            this.interval = maxInterval;
        }
    }

    public void fireBullet() {
        if (interval < maxInterval)
            return;
        this.interval = 0;
        bulletList.add(new Bullet(getX(), getY()));
    }

    public void Update() {
        fireBulletCheck();
        fireBullet();
        move();
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            bullet.tick();
            if (bullet.getY() >= Framework.frameHeight)
                bulletList.remove(bullet);
        }
    }

    public void bulletDraw(Graphics2D g2d) {
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet b = bulletList.get(i);
            b.Draw(g2d);
        }
    }

    public boolean bulletCollision(Rectangle a) {
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet b = bulletList.get(i);
            if (b.collision(a, b.updateBounds())) {
                return true;
            }
        }
        return false;
    }
}