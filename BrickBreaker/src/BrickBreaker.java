import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BrickBreaker implements ActionListener, KeyListener {
	// Variables used to render the graphics
	private final int WIDTH = 500, HEIGHT = 600;
	private final int PADDLE_SPEED = 5;
	private JFrame frame;
	private DrawingPanel panel;
	private Timer gameTimer;
	private Image offScreenImage = null;
	private Graphics2D offScreenGraphics = null;
	private int BALL_START_X = WIDTH / 2;
	private int BALL_START_Y = HEIGHT - HEIGHT / 3;

	// When this variable reaches 0, the player is a winner
	// Change this to equal the number of brick you want the
	// player to break
	int bricksLeftToWin = 8;

	// Player's score
	int score = 0;

	// Player's lives
	int lives = 3;

	// All the bricks go in this ArrayList
	ArrayList<Brick> bricks = new ArrayList<Brick>();
	// Ball Sprite
	Sprite ball = new BallSprite(BALL_START_X, BALL_START_Y);
	// Paddle Sprite
	PaddleSprite paddle = new PaddleSprite(WIDTH / 2 - WIDTH / 10, HEIGHT - HEIGHT / 10, WIDTH / 5, HEIGHT / 40);

	public BrickBreaker() {
		frame = new JFrame("BrickBreaker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DrawingPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);

		// Create the game Sprites
		initializeSprites();

		// Set up and start the game timer
		gameTimer = new Timer(10, this);// (speed, listener)
		gameTimer.start();
	}

	// POST: All the needed game Sprites are initialized
	public void initializeSprites() {
		// Initialize paddle and ball
		ball = new BallSprite(BALL_START_X, BALL_START_Y);
		ball.setDx(0);
		ball.setDy(3);

		paddle = new PaddleSprite(WIDTH / 2 - WIDTH / 10, HEIGHT - HEIGHT / 10, WIDTH / 5, HEIGHT / 40);

		// The initial row of bricks
		int x = 50, y = 100;

		for (int i = 0; i < 8; i++) {
			Brick b = new Brick(x, y, 50, 10);
			bricks.add(b);
			x += b.getWidth() + 1;
		}

	}

	// Customized JPanel for drawing
	private class DrawingPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			final Dimension d = getSize();
			if (offScreenImage == null) {
				offScreenImage = createImage(d.width, d.height);
				offScreenGraphics = (Graphics2D) offScreenImage.getGraphics();
			}

			offScreenGraphics.setColor(new Color(30, 30, 30));
			offScreenGraphics.fillRect(0, 0, d.width, d.height);
			renderOffScreen(offScreenGraphics);
			g2.drawImage(offScreenImage, 0, 0, null);

		}
	}

	public void renderOffScreen(Graphics2D g) {
		// Convert to a Graphics2D object bc it gives us way more tools

		// Turn on anti-aliasing (smoothing)
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Check winner
		if (bricksLeftToWin == 0) {
			gameTimer.stop();
			g.setColor(new Color(220, 220, 255));
			Font theFont = new Font("Consolas", Font.BOLD, 36);
			g.setFont(theFont);
			int start = (WIDTH - g.getFontMetrics().stringWidth("WINNER")) / 2;
			g.drawString("WINNER", start, HEIGHT / 2);
		}

		// Update and redraw the bricks
		ArrayList<Brick> toRemove = new ArrayList<Brick>();
		for (Brick b : bricks) {
			b.update(g);
			int result = b.isHit(ball);
			if (result > 0) {
				score += result;
				bricksLeftToWin--;
				toRemove.add(b);
			}
			if (b.intersects(ball))
				ball.update(g);
		}
		bricks.removeAll(toRemove);

		// Update and redraw the ball
		ball.update(g);

		// Check ball against the walls
		if (ball.getX() + ball.getWidth() + ball.getDx() > WIDTH || ball.getX() + ball.getDx() < 0) {
			ball.invertDx();
		}
		if (ball.getY() + ball.getDy() < 0) {
			ball.invertDy();
		}

		// Update the paddle
		paddle.update(g, WIDTH); // Move
		paddle.intersects(ball); // Check collision with ball

		// Draw lives
		g.setColor(new Color(170, 170, 170));
		Font theFont = new Font("Consolas", Font.BOLD, 30);
		g.setFont(theFont);
		g.drawString("Lives: " + lives, 5, 25);

		// Draw score
		g.setColor(new Color(170, 170, 170));
		int start = WIDTH - g.getFontMetrics().stringWidth("Score: " + score) - 10;
		g.drawString("Score: " + score, start, 25);

		// Draw remaining bricks
		g.setColor(new Color(130, 130, 130));
		theFont = new Font("Consolas", Font.BOLD, 20);
		g.setFont(theFont);
		g.drawString("Bricks Remaining: " + bricksLeftToWin, 5, HEIGHT - 5);

		// Life Lost
		if (ball.getY() + ball.getHeight() + ball.getDy() > HEIGHT) {
			lives--;
			score /= 2; // Score penalty for missed ball
			if (lives > 0) {
				ball = new BallSprite(BALL_START_X, BALL_START_Y);
				ball.setDx(0);
				ball.setDy(3);
			} else {
				// Lose
				gameTimer.stop();
				g.setColor(new Color(255, 107, 147));
				theFont = new Font("Consolas", Font.BOLD, 36);
				g.setFont(theFont);
				start = (WIDTH - g.getFontMetrics().stringWidth("GAME OVER")) / 2;
				g.drawString("GAME OVER", start, HEIGHT / 2);
			}
		}
	}

	// Implement ActionPerformed
	public void actionPerformed(ActionEvent e) {
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 39) {
			paddle.setDx(PADDLE_SPEED);
		}
		if (e.getKeyCode() == 37) {
			paddle.setDx(-PADDLE_SPEED);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		paddle.setDx(0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		// TODO Auto-generated method stub
	}

}