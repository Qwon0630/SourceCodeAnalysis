package moon_lander;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.*;


/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game extends MouseAdapter {
	
	
    ArrayList<Bullet> bulletData = new ArrayList<Bullet>();//만든 총알들을 저장
    
    private int stageLevel;
    /**
     * The space rocket with which player will have to land.
     */
    private PlayerRocket playerRocket1 = new PlayerRocket();

    /**
     * Landing area on which rocket will have to land.
     */
    private LandingArea landingArea1 = new LandingArea();

    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;

    /**
     * Red border of the frame. It is used when player crash the rocket.
     */
    private BufferedImage redBorderImg;
    /* Enemy */
    private Unmoved_Enemy UnmoveEnemy = new Unmoved_Enemy();

    private EnemyController moving_Enemy = new EnemyController(1);
    
    private MovingEnemyWithBullet movingBulletEnemy = new MovingEnemyWithBullet();

    

    private int baseScore = 1000;

    
    
//    public void mousePressed(MouseEvent e)//마우스를 버튼을 누루면 
//    { 
//          isPress = true; 
//          makeBullet();//총알 생성 
//   } 
//   
//   public void mouseReleased(MouseEvent e)//마우스 버튼을 놓으면 
//   { 
//         isPress = false; 
//  }
    public Game(int level) {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        stageLevel = level;
        Thread threadForInitGame = new Thread() {
            @Override
            public void run() {
            	
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }

    /**
     * Set variables and objects for the game.
     */
    private void Initialize() {
        
        switch (stageLevel) {
            case 1:
            	playerRocket1 = new PlayerRocket();
                landingArea1 = new LandingArea();
                break;
            case 2:
            	playerRocket1 = new PlayerRocket();
                landingArea1 = new LandingArea();
                UnmoveEnemy = new Unmoved_Enemy();
                break;
            case 3:
            	playerRocket1 = new PlayerRocket();
                landingArea1 = new LandingArea();
                UnmoveEnemy = new Unmoved_Enemy();
                moving_Enemy = new EnemyController(1);
                break;
            case 4:
            	playerRocket1 = new PlayerRocket(2);
                landingArea1 = new LandingArea();
                UnmoveEnemy = new Unmoved_Enemy();
                moving_Enemy = new EnemyController(2);
                break;
            default:
            	playerRocket1 = new PlayerRocket(3);
                landingArea1 = new LandingArea();
                UnmoveEnemy = new Unmoved_Enemy();
                movingBulletEnemy = new MovingEnemyWithBullet();
                moving_Enemy = new EnemyController(3);
                break;
        }
    }

    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent() {
        try {
            URL backgroundImgUrl = this.getClass().getResource("/resources/images/background.jpg");
            backgroundImg = ImageIO.read(backgroundImgUrl);

            URL redBorderImgUrl = this.getClass().getResource("/resources/images/red_border.png");
            redBorderImg = ImageIO.read(redBorderImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Restart game - reset some variables.
     */
    public void RestartGame() {
        playerRocket1.ResetPlayer();
        landingArea1.ResetLandingArea();
        UnmoveEnemy.ResetUnmovedEnemy();
        moving_Enemy.ResetController(stageLevel);
        baseScore = 1000;
       
        movingBulletEnemy.Reset();
    }

    /**
     * Update game logic.
     * 
     * @param gameTime      gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition) {
        // Move the rocket
        playerRocket1.Update();
        moving_Enemy.Update();
        movingBulletEnemy.Update();
        // Checks where the player rocket is. Is it still in the space or is it landed
        // or crashed?
        // First we check bottom y coordinate of the rocket if is it near the landing
        // area.
        
        if (playerRocket1.y + playerRocket1.rocketImgHeight - 10 > landingArea1.y) {
            // Here we check if the rocket is over landing area.
            if ((playerRocket1.x > landingArea1.x) && (playerRocket1.x < landingArea1.x
                    + landingArea1.landingArea1ImgWidth - playerRocket1.rocketImgWidth)) {
                // Here we check if the rocket speed isn't too high.
                if (playerRocket1.speedY <= playerRocket1.topLandingSpeed)
                    playerRocket1.landed = true;
                else
                    playerRocket1.crashed = true;
            } else
                playerRocket1.crashed = true;

            Framework.gameState = Framework.GameState.GAMEOVER;
        }

        /* Enemy Collision */
        Rectangle rocket = playerRocket1.makeRect();
        Rectangle enemy = UnmoveEnemy.drawRect();
        Rectangle border = MovingEnemyWithBullet.bullet.drawRect();
        if(rocket.intersects(enemy)||rocket.intersects(border)) {
            playerRocket1.crashed = true;
            Framework.gameState = Framework.gameState.GAMEOVER;
        }
        /* Moving Enemy Collision */
        LinkedList<Moving_Enemy> e = moving_Enemy.getEnemyList();
        

        for (int i = 0; i < e.size(); i++) {
        	if (rocket.intersects(e.get(i).drawRect())) {
                playerRocket1.crashed = true;
                Framework.gameState = Framework.gameState.GAMEOVER;
                break;
            }
        }
        
        

    }

    /**
     * Draw the game to the screen.
     * 
     * @param g2d           Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition) {
        g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

        landingArea1.Draw(g2d);

        playerRocket1.Draw(g2d);

        UnmoveEnemy.Draw(g2d);

        moving_Enemy.Draw(g2d);
        
        movingBulletEnemy.Draw(g2d);
        
        
        
    }

    /**
     * Draw the game over screen.
     * 
     * @param g2d           Graphics2D
     * @param mousePosition Current mouse position.
     * @param gameTime      Game time in nanoseconds.
     */
    public void DrawGameOver(Graphics2D g2d, Point mousePosition, long gameTime) {
        Draw(g2d, mousePosition);

        g2d.drawString("Press space or enter to restart.", Framework.frameWidth / 2 - 100,
                Framework.frameHeight / 3 + 70);

        if (playerRocket1.landed) {
            g2d.drawString("You have successfully landed!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);
            g2d.drawString("You have landed in " + gameTime / Framework.secInNanosec + " seconds.",
                    Framework.frameWidth / 2 - 100, Framework.frameHeight / 3 + 20);
            baseScore -= (gameTime / Framework.secInNanosec) * 15;
            Framework.score.addScore(baseScore);
        } else {
            g2d.setColor(Color.red);
            g2d.drawString("You have crashed the rocket!", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
            g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
        }
    }
}