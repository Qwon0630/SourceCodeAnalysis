package moon_lander;

import java.util.Collections;
import java.util.Vector;

public class Score {
    /* 스코어 */
    public Vector<Integer> score = new Vector<Integer>();

    public Score() {
        addScore(0);
    }

    public void resetScore() {
        score.clear();
    }

    public int getBestScore() {
        Collections.sort(score, Collections.reverseOrder());
        int bestScore = score.firstElement();
        return bestScore;
    }

    public void addScore(int _score) {
        score.add(_score);
    }

    public void removeScore(int index) {
        score.remove(index);
    }

    public Vector<Integer> getScoreVector() {
        return score;
    }
}
