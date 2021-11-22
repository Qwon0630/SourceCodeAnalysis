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
import javax.swing.JButton;
import javax.swing.JPanel;



import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.*;
import java.awt.*;

/**
 * Framework that controls the game (Game.java) that created it, update it and
 * draw it on the screen.
 * 
 * @author www.gametutorial.net
 */

public class Framework extends Canvas{

	
	ArrayList<PlayerBullet> bulletData = new ArrayList<PlayerBullet>();
	long prevtime = 0;
	
	boolean isPress = false;
	
	int SPEED = 15;
	
    /**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds. 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;

    /**
     * Time of one millisecond in nanoseconds. 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;

    /**
     * FPS - Frames per second How many times per second the game should update?
     */
    private final int GAME_FPS = 16;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;

    /**
     * Possible states of the game
     */
    public static enum GameState {
        STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, LEVEL_MENU, OPTIONS, PLAYING, PLAYING2P, GAMEOVER,
        GAMEOVER2P, BLACKSCREEN, DESTROYED, PLAYING_SHOOTING
    }

    /**
     * Current state of the game
     */
    public static GameState gameState;

    PlayerRocket pr = new PlayerRocket(1,1);
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;

    // The actual game
    private Game game;
    private Game2p game2p;
    
    public static int gameTimeTaken1 = 0;
    
    public static int gameTimeTaken2 = 0;

    public static int blackscreenTime = 0;
    /**
     * Image for menu.
     */
    private BufferedImage moonLanderMenuImg;
    
    
    
    

    /* 스코어 */
    public static Score score = new Score();

    
    JButton btn1p = new JButton("기본모드");
    JButton btn2p = new JButton("2인 경쟁모드");
    JButton btnLevelMode = new JButton("난이도 선택모드");
    JButton btnMain = new JButton("메인");

    JButton btnLevel1 = new JButton("Level 1");
    JButton btnLevel2 = new JButton("Level 2");
    JButton btnLevel3 = new JButton("Level 3");
    JButton btnLevel4 = new JButton("Level 4");
    JButton btnLevel5 = new JButton("Level 5");

    public void Levelbutton(boolean add) {
        if (add) {
            this.add(btnLevel1);
            this.add(btnLevel2);
            this.add(btnLevel3);
            this.add(btnLevel4);
            this.add(btnLevel5);

           
            btnLevel1.setVisible(add);
            btnLevel1.setBounds(100, 400, 100, 50);
            /* Level 1 */
            

            btnLevel2.setVisible(add);
            btnLevel2.setBounds(210, 400, 100, 50);
            /* Level 2 */
            
            btnLevel3.setVisible(add);
            btnLevel3.setBounds(330, 400, 100, 50);
            /* Level 3 */
            

            btnLevel4.setVisible(add);
            btnLevel4.setBounds(450, 400, 100, 50);
            /* Level 4 */
            
            btnLevel5.setVisible(add);
            btnLevel5.setBounds(570, 400, 100, 50);
            /* Level 5 */
            
            ActionListener listener = new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(btnLevel1.equals(e.getSource())) {
            			newGame(1);
            		}
            		if(btnLevel2.equals(e.getSource())) {
            			newGame(2);
            		}
            		if(btnLevel3.equals(e.getSource())) {
            			newGame(3);
            		}
            		if(btnLevel4.equals(e.getSource())) {
            			newGame(4);
            		}
            		if(btnLevel5.equals(e.getSource())) {
            			newGame(5);
            		}
            		
            	}
            };
            btnLevel1.addActionListener(listener);
            btnLevel2.addActionListener(listener);
            btnLevel3.addActionListener(listener);
            btnLevel4.addActionListener(listener);
            btnLevel5.addActionListener(listener);

        } else {
            this.remove(btnLevel1);
            this.remove(btnLevel2);
            this.remove(btnLevel3);
            this.remove(btnLevel4);
            this.remove(btnLevel5);

        }
    }

    public void Mainbutton(boolean add) {
        if (add) {
            this.add(btn1p);
            this.add(btn2p);
            this.add(btnLevelMode);
            this.add(btnMain);

            btn1p.setVisible(add);
            btn1p.setBounds(100, 400, 125, 50);

            btn2p.setVisible(add);
            btn2p.setBounds(250, 400, 125, 50);
            
            btnLevelMode.setVisible(add);
            btnLevelMode.setBounds(400, 400, 125, 50);
            
            btnMain.setVisible(add);
            btnMain.setBounds(550, 400, 125, 50);
            
            ActionListener listener = new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(btn1p.equals(e.getSource())) {
            			newGame(1);
            		}
            		if(btn2p.equals(e.getSource())) {
            			newGame2p();
            		}
            		if(btnLevelMode.equals(e.getSource())) {
            			Framework.gameState = Framework.GameState.LEVEL_MENU;
            		}
            		if(btnMain.equals(e.getSource())) {
            			Framework.gameState = Framework.GameState.MAIN_MENU;
            		}
            		
            		
            	}
            };
            btn1p.addActionListener(listener);
            btn2p.addActionListener(listener);
            btnLevelMode.addActionListener(listener);
            btnMain.addActionListener(listener);
            
        } else {
            this.remove(btn1p);
            this.remove(btn2p);
            this.remove(btnLevelMode);
            this.remove(btnMain);

        }
    }

    public Framework() {
    	
        super();

        gameState = GameState.VISUALIZING;

        // We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                GameLoop();
            }
        };
        gameThread.start();
    }

    /**
     * Set variables and objects. This method is intended to set the variables and
     * objects for this class, variables and objects for the actual game can be set
     * in Game.java.
     */
    private void Initialize() {

    }

    /**
     * Load files - images, sounds, ... This method is intended to load files for
     * this class, files for the actual game can be loaded in Game.java.
     */
    private void LoadContent() {
        try {
            URL moonLanderMenuImgUrl = this.getClass().getResource("/resources/images/menu.jpg");
            moonLanderMenuImg = ImageIO.read(moonLanderMenuImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated
     * and then the game is drawn on the screen.
     */
    private void GameLoop() {
        // This two variables are used in VISUALIZING state of the game. We used them to
        // wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();

        // This variables are used for calculating the time that defines for how long we
        // should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;

        while (true) {
            beginTime = System.nanoTime();

            switch (gameState) {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;

                    game.UpdateGame(gameTime, mousePosition());

                    
                    if(isPress) {
                    	makeBullet();
                    }
                    moveBullet();
                    
                    lastTime = System.nanoTime();
                    break;
                case PLAYING2P:
                    gameTime += System.nanoTime() - lastTime;
                    gameTimeTaken1 += 1;
                    gameTimeTaken2 += 1;
                    game2p.UpdateGame(gameTime, mousePosition());

                    lastTime = System.nanoTime();
                    break;

                case BLACKSCREEN:
                    gameTime += System.nanoTime() - lastTime;
                    gameTimeTaken1 += 1;
                    gameTimeTaken2 += 1;

                    game2p.UpdateGame(gameTime, mousePosition());

                    lastTime = System.nanoTime();
                    break;
                case GAMEOVER:
                    // ...
                    break;
                case LEVEL_MENU:
                    break;
                case GAMEOVER2P:
                    gameTimeTaken1 = 0;
                    gameTimeTaken2 = 0;
                    break;
                case MAIN_MENU:
                    // ...
                    break;
                case OPTIONS:
                    // ...
                    break;
                case GAME_CONTENT_LOADING:
                    // ...
                    break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();

                    // When all things that are called above finished, we change game status to main
                    // menu.
                    gameState = GameState.MAIN_MENU;
                    break;
                case VISUALIZING:
                    // On Ubuntu OS (when I tested on my old computer) this.getWidth() method
                    // doesn't return the correct value immediately (eg. for frame that should be
                    // 800px width, returns 0 than 790 and at last 798px).
                    // So we wait one second for the window/frame to be set to its correct size.
                    // Just in case we
                    // also insert 'this.getWidth() > 1' condition in case when the window/frame
                    // size wasn't set in time,
                    // so that we although get approximately size.
                    if (this.getWidth() > 1 && visualizingTime > secInNanosec) {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();

                        // When we get size of frame we change status.
                        gameState = GameState.STARTING;
                    } else {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                    break;
            }

            // Repaint the screen.
            repaint();

            // Here we calculate the time that defines for how long we should put threat to
            // sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep
            // for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10)
                timeLeft = 10; // set a minimum
            try {
                // Provides the necessary delay and also yields control so that other thread can
                // do work.
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) {
            }
        }
    }

    /**
     * Draw the game to the screen. It is called through repaint() method in
     * GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d) {
        switch (gameState) {
            case PLAYING:
                game.Draw(g2d, mousePosition());
                g2d.drawString("Mouse coordinates: " + mouse.x + " : " + mouse.y, 10, 30);
                for (PlayerBullet b : bulletData)//총알 그리기 
                {
                      g2d.drawOval((int) (b.x - 1), (int) (b.y - 1), 5, 5); 
               }
                Mainbutton(false);
                Levelbutton(false);
                break;
            case PLAYING2P:
                game2p.Draw(g2d, mousePosition());
                if(gameTimeTaken1>160 && gameTimeTaken2>160) {
                	game2p.DrawLandingArea1(g2d, getMousePosition());
                	game2p.DrawLandingArea2(g2d, getMousePosition());
                }
                else if(gameTimeTaken1<160 && gameTimeTaken2>160) {
                	game2p.DrawLandingArea2(g2d, getMousePosition());
                }
                else if(gameTimeTaken1>160&&gameTimeTaken2<160) {
                	game2p.DrawLandingArea1(g2d, getMousePosition());
                }
                else {
                	
                }
                Mainbutton(false);
                Levelbutton(false);
                break;

            case BLACKSCREEN:
            	
                game2p.DrawBlackScreen(g2d);
                blackscreenTime += 1;
                if (blackscreenTime > 50) {
                    Framework.gameState = Framework.GameState.PLAYING2P;
                }
                Mainbutton(false);
                Levelbutton(false);
                break;
            case GAMEOVER:
                game.DrawGameOver(g2d, mousePosition(), gameTime);
                
                Mainbutton(true);
                Levelbutton(false);
                break;
            case GAMEOVER2P:
                game2p.DrawGameOver(g2d, mousePosition(), gameTime);
                Mainbutton(true);
                Levelbutton(false);
                break;
            case MAIN_MENU:
                g2d.drawImage(moonLanderMenuImg, 0, 0, frameWidth, frameHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("최고 점수 :" + score.getBestScore(), frameWidth / 2 - 40, frameHeight / 2);
                g2d.setColor(Color.white);
                g2d.drawString("Use w a d keys to controle the rocket1.", frameWidth / 2 - 117, frameHeight / 2 + 50);
                g2d.drawString("Use up left right keys to controle the rocket2.", frameWidth / 2 - 117,
                        frameHeight / 2 + 80);
                g2d.drawString("WWW.GAMETUTORIAL.NET", 7, frameHeight - 5);
                
                Mainbutton(true);
                Levelbutton(false);
                break;
            case LEVEL_MENU:
                g2d.drawImage(moonLanderMenuImg, 0, 0, frameWidth, frameHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("Select your level!", frameWidth / 2 - 117, frameHeight / 2);
                Mainbutton(false);
                Levelbutton(true);

                break;
            case OPTIONS:
                Mainbutton(false);
                Levelbutton(false);
                break;
            case GAME_CONTENT_LOADING:
                g2d.setColor(Color.white);
                g2d.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);
                Mainbutton(false);
                Levelbutton(false);
                break;
                default:
                	break;
            
        }
    }

    /**
     * Starts new game.
     */
    private void newGame(int level) {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        game = new Game(level);

        requestFocus();
    }

    private void newGame2p() {
        gameTime = 0;
        lastTime = System.nanoTime();

        game2p = new Game2p();

        requestFocus();
    }

    /**
     * Restart game - reset game time and call RestartGame() method of game object
     * so that reset some variables.
     */
    private void restartGame() {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        game.RestartGame();

        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }

    private void restartGame2() {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();

        game2p.RestartGame2();

        // We change game status so that the game can start.
        gameState = GameState.PLAYING2P;
    }

    /**
     * Returns the position of the mouse pointer in game frame/window. If mouse
     * position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition() {
        try {
            Point mp = this.getMousePosition();

            if (mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e) {
        switch (gameState) {
            case MAIN_MENU:
                newGame(1);
                break;
            case GAMEOVER:
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame();
                break;
            case GAMEOVER2P:
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
                    restartGame2();
                break;
        }
    }
    public void makeBullet()
    { 
          if (System.currentTimeMillis() - prevtime > 300)//약 0.3초(300/1000) 마다 총알 생성 
          { 
        	    double x1 = PlayerRocket.rocket1_X+pr.rocketImgWidth/2; 
        	    double y1 = PlayerRocket.rocket1_Y+pr.rocketImgHeight; 
        	    double x2 = mouse.x; 
        	    double y2 = mouse.y; 
                double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));//네모와 마우스 사이의 거리 측정 
                double vx = (x2 - x1) / d * SPEED; 
                double vy = (y2 - y1) / d * SPEED;//총알 속도 계산 
                PlayerBullet bullet = new PlayerBullet(x1, y1, vx, vy);//총알 객체 생성 
                bulletData.add(bullet);//배열에 저장 
                prevtime = System.currentTimeMillis();//생성된 시간 저장 
         } 
    } 
    
    public void moveBullet() 
    {
          for (int i = 0; i < bulletData.size(); i++) 
          { 
                if (bulletData.get(i).moveBullet() == false)//화면을 벗어나면 삭제 하기 
                { 
                      bulletData.remove(i); 
                      break; 
               } 
        } 
    }

    /**
     * This method is called when mouse button is clicked.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    public void mousePressed(MouseEvent e) {
        
	    isPress = true; 
        makeBullet();
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	
        isPress = false;
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
