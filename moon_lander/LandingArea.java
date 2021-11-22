package moon_lander;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * Landing area where rocket will land.
 * 
 * @author www.gametutorial.net
 */

public class LandingArea {
    private Random random;

    /**
     * X coordinate of the landing area.
     */
    public int landingArea1_X;
    public int landingArea2_X;
    /**
     * Y coordinate of the landing area.
     */
    public int landingArea1_Y;
    public int landingArea2_Y;

    /**
     * Image of landing area.
     */
    private BufferedImage landingArea1Img;

    private BufferedImage landingArea2Img;
    /**
     * Width of landing area.
     */
    public int landingArea1ImgWidth;

    public int landingArea2ImgWidth;
    

    
    public LandingArea(int mode) {
    	if(mode==1) {
    		Initialize(1);
            LoadContent(1);
            
    	}
    	else if(mode==2) {
    		Initialize(2);
            LoadContent(2);
            
    	}
        
    }

    public void ResetLandingArea() {
        random = new Random();
       
        landingArea1_X = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
        landingArea1_Y = (int) (Framework.frameHeight * 0.88);
        
        landingArea2_X = random.nextInt(Framework.frameWidth - landingArea2ImgWidth-50);
        landingArea2_Y = (int) (Framework.frameHeight * 0.88);
    }

    private void Initialize(int mode) {
        random = new Random();
        if(mode==1) {
        	landingArea1_X = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
            landingArea1_Y = (int) (Framework.frameHeight * 0.88);
       
    }
        else if(mode==2) {
        	landingArea1_X = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
            landingArea1_Y = (int) (Framework.frameHeight * 0.88);
            
            landingArea2_X = random.nextInt(Framework.frameWidth - landingArea1ImgWidth-50);
            landingArea2_Y = (int) (Framework.frameHeight * 0.88);
            
        }
    }

    private void LoadContent(int mode) {
    	if(mode==1) {
    		try {
                URL landingArea1ImgUrl = this.getClass().getResource("/resources/images/landing_area.png");
                landingArea1Img = ImageIO.read(landingArea1ImgUrl);
                landingArea1ImgWidth = landingArea1Img.getWidth();
            } catch (IOException ex) {
                Logger.getLogger(LandingArea.class.getName()).log(Level.SEVERE, null, ex);
            }
    		
    	}
        
    	else if(mode==2) {
    		try {
                URL landingArea1ImgUrl = this.getClass().getResource("/resources/images/landing_area.png");
                landingArea1Img = ImageIO.read(landingArea1ImgUrl);
                landingArea1ImgWidth = landingArea1Img.getWidth();
                URL landingArea2ImgUrl = this.getClass().getResource("/resources/images/landing_area2p.png");
                landingArea2Img = ImageIO.read(landingArea2ImgUrl);
                landingArea2ImgWidth = landingArea2Img.getWidth();
            } catch (IOException ex) {
                Logger.getLogger(LandingArea.class.getName()).log(Level.SEVERE, null, ex);
            }
    		
    	}
    		
    }

    public void DrawlandingArea1p(Graphics2D g2d) {
    	
        g2d.drawImage(landingArea1Img, landingArea1_X, landingArea1_Y, null);
        
    }
    public void DrawlandingArea2p(Graphics2D g2d) {
    	g2d.drawImage(landingArea2Img, landingArea2_X, landingArea2_Y, null);
    }

	

}