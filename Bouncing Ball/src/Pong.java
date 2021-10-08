// filler code for pong provided by Mr. David

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong extends JPanel implements KeyListener {
	
	// constants that are predefined and won't change as the program runs
	private final int WIDTH = 600, HEIGHT = 600, WINDOW_HEIGHT = 650;
	private final int PADDLE_WIDTH = 20, DIAM = 20, PADDLE_HEIGHT = 100;
	private final int PADDLE_SPEED = 3;

	
	// your instance variables here, I've given you a few 
	private boolean up1, down1, up2, down2; 		// booleans to keep track of paddle movement
	private boolean solo = false;
	
	private int bvx=2;
	private int bvy=1;
	private int bx=WIDTH/2;
	private int by=HEIGHT/2;
	private int r1x=0;
	private int r1y=HEIGHT/2;
	private int r2x=WIDTH-PADDLE_WIDTH;
	private int r2y=HEIGHT/2;
	private int p1s=0;
	private int p2s=0;
	
	
	// move the ball according to its current velocity
	public void move_ball() {
        if (by + bvy < 0 || by + DIAM + bvy > HEIGHT) {
            bvy=bvy*-1;
        }
        bx=bx+bvx;
        by=by+bvy;
	}
	
	
	// this method moves the paddles at each timestep
	public void move_paddles() {
		
		if (up1==true) {
			r1y=r1y-PADDLE_SPEED;
			if (r1y<0){
				r1y=r1y+PADDLE_SPEED;
			}
		}
		if (down1==true) {
			r1y=r1y+PADDLE_SPEED;
			if (r1y>HEIGHT-PADDLE_HEIGHT){
				r1y=r1y-PADDLE_SPEED;
			}
		}
		
		if (up2==true) {
			r2y=r2y-PADDLE_SPEED;
			if (r2y<0){
				r2y=r2y+PADDLE_SPEED;
			}
		}
		
		if (down2==true) {
			r2y=r2y+PADDLE_SPEED;
			if (r2y>HEIGHT-PADDLE_HEIGHT){
				r2y=r2y-PADDLE_SPEED;
			}
		}
		
	}
	
	// this method checks if there are any bounces to take care of,
	// and if the ball has reached a left/right wall it adds to 
	// the corresponding player's score
	public void check_collisions() {
		
		if (bx<r1x+PADDLE_WIDTH&&by<r1y+PADDLE_HEIGHT&&by>r1y) {
			bvx=-(int) ((Math.random()*10)%2+1);
			bvy=-(int) ((Math.random()*10)%2+1);
			bvx=(int) (bvx*-1);
			bvy=(int) (bvy*-1);
		}
		
		if (bx>r2x-PADDLE_WIDTH&&by<r2y+PADDLE_HEIGHT&&by>r2y) {
			bvx=(int) ((Math.random()*10)%2+1);
			bvy=(int) ((Math.random()*10)%2+1);
			bvx=(int) (bvx*-1);
			bvy=(int) (bvy*-1);
		}

        if (bx + bvx < 0) {
        	p2s=p2s+1;
        	bx=WIDTH/2;
            by=HEIGHT/2;
            r1y=HEIGHT/2;
        	r2y=HEIGHT/2;
        }
        
        if (bx + DIAM + bvx > WIDTH) {
        	p1s=p1s+1;
        	bx=WIDTH/2;
            by=HEIGHT/2;
        	r1y=HEIGHT/2;
        	r2y=HEIGHT/2;
        }
	}
	
	public void soloPlay() {
		while (solo=true){
			r1y=by;
			up1=false;
			down1=false;
		}
	}

	// defines what we want to happen anytime we draw the game
	// you simply need to fill in a few parameters here
	public void paint(Graphics g) {

		// background color is gray
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// draw your rectangles and circles here
		
		g.setColor(new Color(176,38,255));
		g.fillOval(bx, by, DIAM, DIAM);
		g.drawOval(bx, by, DIAM, DIAM);
		g.setColor(new Color(0,255,0));
		g.fillRect(r1x, r1y, PADDLE_WIDTH, PADDLE_HEIGHT);
		g.drawRect(r1x, r1y, PADDLE_WIDTH, PADDLE_HEIGHT);
		g.fillRect(r2x, r2y, PADDLE_WIDTH, PADDLE_HEIGHT);
		g.drawRect(r2x, r2y, PADDLE_WIDTH, PADDLE_HEIGHT);
		
		// writes the score of the game - you just need to fill the scores in
		Font f = new Font("Arial", Font.BOLD, 14);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("P1 Score: ", WIDTH/5, 20);
		g.drawString("P2 Score: ", WIDTH*3/5, 20);
		g.drawString(String.valueOf(p1s), WIDTH/5+100, 20);
		g.drawString(String.valueOf(p2s), WIDTH*3/5+100, 20);
		
	}
	

	// defines what we want to happen if a keyboard button has 
	// been pressed - you need to complete this
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// changes paddle direction based on what button is pressed
		if (keyCode == KeyEvent.VK_DOWN) 
			down2=true;
		
		if (keyCode == KeyEvent.VK_UP) 
			up2=true;

		if (e.getKeyChar() == 'w')
			up1=true;
		
		if (e.getKeyChar() =='s')
			down1=true;
			
		// turn 1-player mode on
		if (e.getKeyChar() == '1')
			solo=true;
			
		// turn 2-player mode on
		if (e.getKeyChar() == '2') {
			solo=false;
		}
			// fill this in
	}

	// defines what we want to happen if a keyboard button
	// has been released - you need to complete this
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		// stops paddle motion based on which button is released
		if (keyCode == KeyEvent.VK_UP) 
			up2=false;

		if (keyCode == KeyEvent.VK_DOWN) 
			down2=false;
  
		if(e.getKeyChar() == 'w')
			up1=false;
		
		if (e.getKeyChar() == 's') {
			down1=false;
		}
	}
	
	// restarts the game, including scores
	public void restart() {

		p1s=0;
		p2s=0;
		
	}

	//////////////////////////////////////
	//////////////////////////////////////
	
	// DON'T TOUCH THE BELOW CODE
	
	
	// this method runs the actual game.
	public void run() {

		// while(true) should rarely be used to avoid infinite loops, but here it is ok because
		// closing the graphics window will end the program
		while (true) {
	
			// draws the game
			repaint();
			
			// we move the ball, paddle, and check for collisions
			// every hundredth of a second
			move_ball();
			move_paddles();
			check_collisions();
			
			//rests for a hundredth of a second
			try {
				Thread.sleep(10);
			} catch (Exception ex) {}
		}
	}
	
	// very simple main method to get the game going
	public static void main(String[] args) {
		new Pong();
	}
 
	// does complicated stuff to initialize the graphics and key listeners
	// DO NOT CHANGE THIS CODE UNLESS YOU ARE EXPERIENCED WITH JAVA
	// GRAPHICS!
	public Pong() {
		JFrame frame = new JFrame();
		JButton button = new JButton("restart");
		frame.setSize(WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.add(button, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart();
				Pong.this.requestFocus();
			}
		});
		this.addKeyListener(this);
		this.setFocusable(true);
		
		run();
	}
	
	// method does nothing
	public void keyTyped(KeyEvent e) {}
}