package moon_lander;

import java.util.LinkedList;
import java.awt.Graphics2D;

public class EnemyController {

    private LinkedList<Enemy> enemyList = new LinkedList<Enemy>();
    // public static LinkedList<Bullet> bulletList = new LinkedList<Bullet>();
    Enemy tempEnemy;
    private int unMovedEnemyID = 1;
    private int movingEnemyID = 2;
    private int bulletEnemyID = 3;

    public EnemyController(int count, int id) {
        if (id == bulletEnemyID) {
            specialInitEnemy(count, id);
            return;
        }
        initEnemy(count, id);
    }

    public void ResetController(int count, int id) {
        enemyList.clear();
        // bulletList.clear();
        if (id == bulletEnemyID) {
            specialInitEnemy(count, id);
            return;
        }
        initEnemy(count, id);
    }

    private void initEnemy(int count, int id) {
        for (int i = 0; i < count; i++) {
            int generatorIdx = i + 1;
            if (generatorIdx <= unMovedEnemyID) {
                id = unMovedEnemyID;
                addEnemy(new Enemy(id));
            } else if (generatorIdx >= movingEnemyID) {
                id = movingEnemyID;
                addEnemy(new Moving_Enemy(id));
            }
        }
    }

    private void specialInitEnemy(int count, int id) {
        for (int i = 0; i < count; i++) {
            id = bulletEnemyID;
            enemyList.add(new MovingEnemyWithBullet(id));
        }
    }

    public void Draw(Graphics2D g2d) {
        for (int i = 0; i < enemyList.size(); i++) {
            tempEnemy = enemyList.get(i);
            tempEnemy.Draw(g2d);
            if (tempEnemy.getID() == bulletEnemyID) {
                MovingEnemyWithBullet shootingEnemy = (MovingEnemyWithBullet) tempEnemy;
                shootingEnemy.bulletDraw(g2d);
            }
        }
    }

    public void Update() {
        for (int i = 0; i < enemyList.size(); i++) {
            tempEnemy = enemyList.get(i);
            if (tempEnemy.getID() == movingEnemyID) {
                Moving_Enemy temp = (Moving_Enemy) tempEnemy; // 다운 캐스팅
                temp.tick();
            } else if (tempEnemy.getID() == bulletEnemyID) {
                MovingEnemyWithBullet shootingEnemy = (MovingEnemyWithBullet) tempEnemy;
                shootingEnemy.Update();
            }
        }
    }

    public LinkedList<Enemy> getEnemyList() {
        return enemyList;
    }

    public void addEnemy(Enemy Enemy) {
        enemyList.add(Enemy);
    }

    public void removeEnemy(Enemy Enemy) {
        enemyList.remove(Enemy);
    }
}