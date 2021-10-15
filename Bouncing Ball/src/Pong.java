// filler code for pong provided by Mr. David
// Name: Gregory Li
// Controls:
// W - left paddle up
// S - left paddle down
// up - right paddle up
// down - right paddle down
// 1 - single player
// 2 - two player
// p - pause
// space - resume
// powerups - 50x50 yellow squares, when touched by the ball, freezes the opponent's paddle

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
	private final int POW_DIAM=50;

	
	// your instance variables here, I've given you a few 
	private boolean up1, down1, up2, down2; 		// booleans to keep track of paddle movement
	private boolean solo = false; // solo mode boolean
	private int bvx=(int) ((Math.random()*10)%2+2);  // ball x velocity
	private int bvy=(int) (((Math.random()*10)/2)-3); // ball y velocity
	private int bx=WIDTH/2-DIAM/2; // ball x coordinate
	private int by=HEIGHT/2-DIAM/2; // ball y coordinate
	private int r1x=0; //player 1 paddle/rectangle x coordinate
	private int r1y=HEIGHT/2-PADDLE_HEIGHT/2; //player 1 paddle/rectangle y coordinate
	private int r2x=WIDTH-PADDLE_WIDTH; //player 2 paddle/rectangle x coordinate
	private int r2y=HEIGHT/2-PADDLE_HEIGHT/2; //player 2 paddle/rectangle y coordinate
	private int p1s=0; // player 1 score
	private int p2s=0; // player 2 score
	private boolean pause=true; // pause boolean
	private boolean pow=false; // powerup boolean
	private int powx=(int) ((Math.random()*500)); // powerup x coordinate
	private int powy=(int) ((Math.random()*500)+20); // powerup y coordinate
	private boolean pow1=false; // powerup player 1 effect boolean
	private boolean pow2=false; // powerup player 2 effect boolean
	private int minpowx=50;
	private int t=(int) -(Math.random()*10); //timer for powerup to appear
	
	
	// move the ball according to its current velocity
	public void move_ball() {
		if (pause==false){
			if (by + bvy < 0 || by + DIAM + bvy > HEIGHT) {
	            bvy=bvy*-1;
	        }
	        bx=bx+bvx;
	        by=by+bvy;
		}
		// if statement to show the powerup
		if (pow1==false&&pow2==false&&t>3) {
			pow=true;
		}
		else {
			pow=false;
		}
	}
	
	
	// this method moves the paddles at each timestep
	public void move_paddles() {
		if (pause==false) {
			if (solo==true) {
				// solo mode
				
				while (r1y+PADDLE_HEIGHT/2<by&&r1y<HEIGHT-PADDLE_HEIGHT&&pow1==false) {
					r1y+=PADDLE_SPEED;
				}
				while (r1y+PADDLE_HEIGHT/2>by&&r1y>0&&pow1==false) {
					r1y-=PADDLE_SPEED;
				}
				// with while loops and checking if powerup effect (pow1) is true
			}
			// in 2 player mode, player 1 movement
			if (up1==true&&r1y>0&&solo==false&&pow1==false) {
				r1y=r1y-PADDLE_SPEED;
				if (r1y<0){
					r1y=r1y+PADDLE_SPEED;
				}
			}
			if (down1==true&&r1y<HEIGHT-PADDLE_HEIGHT&&solo==false&&pow1==false) {
				r1y=r1y+PADDLE_SPEED;
				if (r1y>HEIGHT-PADDLE_HEIGHT){
					r1y=r1y-PADDLE_SPEED;
				}
			}
			// player 2 movement 
			if (up2==true&&r2y>0&&pow2==false) {
				r2y=r2y-PADDLE_SPEED;
			}
			
			if (down2==true&&r2y<HEIGHT-PADDLE_HEIGHT&&pow2==false) {
				r2y=r2y+PADDLE_SPEED;
			}
		}
	}
	
	// this method checks if there are any bounces to take care of,
	// and if the ball has reached a left/right wall it adds to 
	// the corresponding player's score
	public void check_collisions() {
		
		if (bx<r1x+PADDLE_WIDTH&&by<r1y+PADDLE_HEIGHT&&by>r1y) {
			bvx=-(int) ((Math.random()*10)%2+2); // makes ball's x velocity 2 or 3
			bvx=(int) (bvx*-1); // bounces the ball
			// make ball bouncing more realistic rather than just bouncing from 180 degrees to 1 degree
			if (bvy>0) {
				bvy=(int)((Math.random()*10)/2);
			}
			if (bvy<0) {
				bvy=(int)-((Math.random()*10)/2);
			}
			if (bvy==0) {
				bvy=(int)(((Math.random()*10)%4)-2);
			}
			pow1=false; // disable powerup effects
			pow2=false;
			t+=1; // add to counter for every hit
		}
		
		if (bx>r2x-PADDLE_WIDTH&&by<r2y+PADDLE_HEIGHT&&by>r2y) {
			bvx=(int) ((Math.random()*10)%2+2);
			bvx=(int) (bvx*-1);
			if (bvy>0) {
				bvy=(int)((Math.random()*10)/2);
			}
			if (bvy<0) {
				bvy=(int)-((Math.random()*10)/2);
			}
			if (bvy==0) {
				bvy=(int)(((Math.random()*10)%4)-2);
			}
			pow1=false;
			pow2=false;
			t+=1;
			// same as above except for player 2
		}

        if (bx + bvx < 0) {
        	p2s=p2s+1; // adds to score
        	bx=WIDTH/2-DIAM/2; // resets ball location
    		by=HEIGHT/2-DIAM/2;
    		r1y=HEIGHT/2-PADDLE_HEIGHT/2; //resets padddle locations
        	r2y=HEIGHT/2-PADDLE_HEIGHT/2;
        	bvx=(int) ((Math.random()*10)%2+2);
        	bvy=(int) (((Math.random()*10)/2)-3);
			pow1=false; // resets powerup effects
			pow2=false;
        	pause=true; // pauses the game
        	t=(int) -(Math.random()*10); // resets counter for powerup
        }
        
        if (bx + DIAM + bvx > WIDTH) {
        	p1s=p1s+1;
        	bx=WIDTH/2-DIAM/2;
    		by=HEIGHT/2-DIAM/2;
    		r1y=HEIGHT/2-PADDLE_HEIGHT/2;
        	r2y=HEIGHT/2-PADDLE_HEIGHT/2;
        	bvx=(int) ((Math.random()*2)+2);
        	bvy=(int) (((Math.random()*10)/2)-3);
			pow1=false;
			pow2=false;
        	pause=true;
        	t=(int) -(Math.random()*10); //same as above but for the other side
        }
        
        if (powx+POW_DIAM>bx&&powy+POW_DIAM>by&&powx-DIAM<bx&&powy-DIAM<by&&pow==true) { //checks for contact of powerup
        	if(bvx>0) {
        		pow2=true; // makes powerup effect true
        	}
        	if(bvx<0) {
        		pow1=true; // makes powerup effect true
        	}
        	pow=false;
        }
        if (pow==false) {
        	powx=(int) ((Math.random()*WIDTH*5/6));
        	powy=(int) ((Math.random()*WIDTH*5/6)+DIAM); //randomizes powerup location but keeps it in the middle-ish of the screen
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
		if (pow==true) {
			if (powx<minpowx+1) {
				powx+=minpowx;
			}
			g.setColor(new Color(255,255,0));
			g.fillRect(powx, powy, POW_DIAM, POW_DIAM);
			g.drawRect(powx, powy, POW_DIAM, POW_DIAM);
		} // makes powerup appear when boolean is true
		
		// writes the score of the game - you just need to fill the scores in
		Font f = new Font("Arial", Font.BOLD, 14);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("P1 Score: ", WIDTH/5, 20);
		g.drawString("P2 Score: ", WIDTH*3/5, 20);
		g.drawString(String.valueOf(p1s), WIDTH/5+100, 20); // player 1 score
		g.drawString(String.valueOf(p2s), WIDTH*3/5+100, 20); // player 2 score
		
		if (pause==true) {
			Font font = new Font("Arial", Font.BOLD, 20);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("game paused, click space to resume", WIDTH/5, HEIGHT/5);
		} // text on pause screen
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
		
		if (e.getKeyChar()==' ') {
			pause=false;
		} // resume button
		if (e.getKeyChar()=='p') {
			pause=true;
		} // pause button
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
		bx=WIDTH/2-DIAM/2;
		by=HEIGHT/2-DIAM/2;
		bvx=(int) ((Math.random()*10)%2+2);
		bvy=(int) (((Math.random()*10)/2)-3);
		r1y=HEIGHT/2-PADDLE_HEIGHT/2;
    	r2y=HEIGHT/2-PADDLE_HEIGHT/2;
    	powx=(int) ((Math.random()*500));
    	powy=(int) ((Math.random()*500)+20);
    	t=(int) -(Math.random()*10);
    	pow1=false;
    	pow2=false;
    	pow=true;
    	solo=false;
		pause=true;
		// resets all variables 
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