package moon_lander;

public class Moving_Enemy extends Enemy {

    public Moving_Enemy(int id) {
        super(id);
    }

    // 움직임을 위한 메소드
    public void tick() {
        int movingY = super.getY();
        movingY += 5;
        setY(movingY);
        if (movingY >= Framework.frameHeight) {
            setY(0);
        }
    }
}