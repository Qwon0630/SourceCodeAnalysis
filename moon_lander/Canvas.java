package moon_lander;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 * Create a JPanel on which we will draw and listen for keyboard and mouse
 * events.
 * 
 * @author www.gametutorial.net
 */

public abstract class Canvas extends JPanel implements KeyListener, MouseListener, FocusListener, MouseMotionListener, ActionListener{

    // Keyboard states - Here are stored states for keyboard keys - is it down or
    // not.
	Point mouse = new Point(0, 0);
    private static boolean[] keyboardState = new boolean[525];

    // Mouse states - Here are stored states for mouse keys - is it down or not.
    private static boolean[] mouseState = new boolean[3];

 // 연료가 0일때 누르지 못하게 하는 변수
    private static boolean isBlocked = false;
    
    
    public Canvas() {
        // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);

        // If you will draw your own mouse cursor or if you just want that mouse cursor
        // disapear,
        // insert "true" into if condition and mouse cursor will be removed.
        if (false) {
            BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
            this.setCursor(blankCursor);
        }

        // Adds the keyboard listener to JPanel to receive key events from this
        // component.
        this.addKeyListener(this);
        // Adds the mouse listener to JPanel to receive mouse events from this
        // component.
        this.addMouseListener(this);

        this.addFocusListener(this);
        
        this.addMouseMotionListener(this);
        
        
        

    }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
    }

    // This method is overridden in Framework.java and is used for drawing to the
    // screen.
    public abstract void Draw(Graphics2D g2d);

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        Draw(g2d);
        g2d.drawLine(mouse.x, mouse.y-10,mouse.x,mouse.y+10);
        g2d.drawLine(mouse.x-10,mouse.y,mouse.x+10,mouse.y);
        
    }

    public static void changeBlockState(boolean flag) {
        isBlocked = flag;
    }

    public static boolean getBlock() {
        return isBlocked;
    }
    // Keyboard
    /**
     * Is keyboard key "key" down?
     * 
     * @param key Number of key for which you want to check the state.
     * @return true if the key is down, false if the key is not down.
     */
    public static boolean keyboardKeyState(int key) {
        return keyboardState[key];
    }

    // Methods of the keyboard listener.
    @Override
    public void keyPressed(KeyEvent e) {
        keyboardState[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyboardState[e.getKeyCode()] = false;
        keyReleasedFramework(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public abstract void keyReleasedFramework(KeyEvent e);

    // Mouse
    /**
     * Is mouse button "button" down? Parameter "button" can be "MouseEvent.BUTTON1"
     * - Indicates mouse button #1 or "MouseEvent.BUTTON2" - Indicates mouse button
     * #2 ...
     * 
     * @param button Number of mouse button for which you want to check the state.
     * @return true if the button is down, false if the button is not down.
     */
    public static boolean mouseButtonState(int button) {
        return mouseState[button - 1];
    }

    // Sets mouse key status.
    private void mouseKeyStatus(MouseEvent e, boolean status) {
        if (e.getButton() == MouseEvent.BUTTON1)
            mouseState[0] = status;
        else if (e.getButton() == MouseEvent.BUTTON2)
            mouseState[1] = status;
        else if (e.getButton() == MouseEvent.BUTTON3)
            mouseState[2] = status;
    }

    // Methods of the mouse listener.
    @Override
    public void mousePressed(MouseEvent e) {
        
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	
              
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override 
    public void mouseMoved(MouseEvent e)//마우스를 움직이면 
    { 
          mouse = e.getPoint(); 
   } 
   @Override 
   public void mouseDragged(MouseEvent e)//마우스 버튼을 누루고 움직이면 
   { 
         mouse = e.getPoint(); 
  } 
    

}