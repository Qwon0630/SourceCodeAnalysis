package moon_lander;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.Rectangle;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game2p {

    /**
     * The space rocket with which player will have to land.
     */
    private PlayerRocket playerRocket = new PlayerRocket(1,2);


    /**
     * Landing area on which rocket will have to land.
     */
    private LandingArea landingArea = new LandingArea(2);

    

    public static int itemTime = 0;

    private Item item1 = new Item();
    private Item item2 = new Item();
    private Item item3 = new Item();



    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;

    /**
     * Red border of the frame. It is used when player crash the rocket.
     */
    private BufferedImage redBorderImg;

    private BufferedImage blackscreenImg;

    public Game2p() {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;

        Thread threadForInitGame = new Thread() {
            @Override
            public void run() {
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();

                Framework.gameState = Framework.GameState.PLAYING2P;
            }
        };
        threadForInitGame.start();
    }

    /**
     * Set variables and objects for the game.
     */
    private void Initialize() {
        playerRocket = new PlayerRocket(1,2);

        landingArea = new LandingArea(2);
        

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

            URL blackscreenImgUrl = this.getClass().getResource("/resources/images/blackscreen.png");
            blackscreenImg = ImageIO.read(blackscreenImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DrawBlackScreen(Graphics2D g2d) {
        // hide all screen
        g2d.drawImage(blackscreenImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

    }

    /**
     * Restart game - reset some variables.
     */
    public void RestartGame2() {

        playerRocket.ResetPlayer();

        landingArea.ResetLandingArea();
        

        item1.ResetItem();
        item2.ResetItem();
        item3.ResetItem();

    }

    /**
     * Update game logic.
     * 
     * @param gameTime      gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition) {
        // Move the rocket

        playerRocket.Update();
        

        // Item Collision
        Rectangle rocket1 = playerRocket.makeRect1p();
        Rectangle rocket2 = playerRocket.makeRect2p();
        Rectangle item11 = item1.drawRect();
        Rectangle item22 = item2.drawRect();
        Rectangle item33 = item3.drawRect();

        if (rocket1.intersects(item11) || rocket2.intersects(item11)) {
        
        	item1.blackScreenItem();
        }
        else if(rocket1.intersects(item22)||rocket2.intersects(item22)) {
        	if(rocket1.intersects(item22)) {
        		playerRocket.crashed_1p = true;
        	}
        	else
        		playerRocket.crashed_2p = true;
        	
        	Framework.gameState = Framework.gameState.GAMEOVER2P;
        }
        else if(rocket1.intersects(item33)||rocket2.intersects(item33)) {
        	int player = 0;
        	if (rocket1.intersects(item33)) {
        		player = 1;
                item3.hideLandingAreaItem(player);

            } else if (rocket2.intersects(item33)) {
                player = 2;
                item3.hideLandingAreaItem(player);
            }
        	player = 0;
        }
        

        // Checks where the player rocket is. Is it still in the space or is it landed
        // or crashed?
        // First we check bottom y coordinate of the rocket if is it near the landing
        // area.
        if (playerRocket.rocket1_Y + playerRocket.rocketImgHeight - 10 > landingArea.landingArea1_Y) {
            // Here we check if the rocket is over landing area.
            if ((playerRocket.rocket1_X > landingArea.landingArea1_X) && (playerRocket.rocket1_X < landingArea.landingArea1_X
                    + landingArea.landingArea1ImgWidth - playerRocket.rocketImgWidth)) {
                // Here we check if the rocket speed isn't too high.
                if (playerRocket.speed1p_Y <= playerRocket.topLandingSpeed)
                    playerRocket.landed_1p = true;
                else
                    playerRocket.crashed_1p = true;
            } else
                playerRocket.crashed_1p = true;

            Framework.gameState = Framework.GameState.GAMEOVER2P;
        }
        if (playerRocket.rocket2_Y + playerRocket.rocket2pImgHeight - 10 > landingArea.landingArea2_Y) {
            // Here we check if the rocket is over landing area.
            if ((playerRocket.rocket2_X > landingArea.landingArea2_X) && (playerRocket.rocket2_X < landingArea.landingArea2_X
                    + landingArea.landingArea2ImgWidth - playerRocket.rocket2pImgWidth)) {
                // Here we check if the rocket speed isn't too high.
                if (playerRocket.speed2p_Y <= playerRocket.topLandingSpeed)
                    playerRocket.landed_2p = true;
                else
                    playerRocket.crashed_2p = true;
            } else
                playerRocket.crashed_2p = true;

            Framework.gameState = Framework.GameState.GAMEOVER2P;
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

        playerRocket.Draw(g2d);
        
        item1.Draw(g2d);
        item2.Draw(g2d);
        item3.Draw(g2d);
    }

    public void DrawLandingArea1(Graphics2D g2d, Point mousePosition) {
        landingArea.DrawlandingArea1p(g2d);
        
    }
    public void DrawLandingArea2(Graphics2D g2d, Point mousePosition) {
        landingArea.DrawlandingArea2p(g2d);
        
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
        DrawLandingArea1(g2d, mousePosition);
        DrawLandingArea2(g2d, mousePosition);
        

        g2d.drawString("Press space or enter to restart.", Framework.frameWidth / 2 - 100,
                Framework.frameHeight / 3 + 70);

        if (playerRocket.landed_1p) {
            g2d.drawString("1p have successfully landed!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);

        } else if (playerRocket.landed_2p) {
            g2d.drawString("2p have successfully landed!", Framework.frameWidth / 2 - 100, Framework.frameHeight / 3);

        } else {
            if (playerRocket.crashed_1p) {
                g2d.setColor(Color.red);
                g2d.drawString("2p win!", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
                g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
            } else if (playerRocket.crashed_2p) {
                g2d.setColor(Color.red);
                g2d.drawString("1p win!", Framework.frameWidth / 2 - 95, Framework.frameHeight / 3);
                g2d.drawImage(redBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);

            }
        }

    }
}