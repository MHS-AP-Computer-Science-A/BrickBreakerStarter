import java.awt.Color;
import java.awt.Graphics2D;

public class PaddleSprite extends Sprite {

	public PaddleSprite(int x, int y, int width, int height) {
		super(x, y, width, height);
		setColor(Color.LIGHT_GRAY);
	}

	@Override
	/*
	 * @see Sprite#intersects(Sprite) This method overrides the default behavior of
	 * intersects. It divides the paddle into 5 zones and changes the bounce angle
	 * of the ball based on where it hits the paddle
	 */
	public boolean intersects(Sprite ball) {
		if (super.intersects(ball)) {
			ball.invertDy();

			// Define midpoint of ball
			double ball_mid = ball.getX() + ball.getWidth() / 2;

			// Define paddle zones boundaries
			// ___|___|___|___|___
			//    z0  z1  z2  z3
			double z0 = this.getX() + this.getWidth() / 5;
			double z1 = this.getX() + 2 * this.getWidth() / 5;
			double z2 = this.getX() + 3 * this.getWidth() / 5;
			double z3 = this.getX() + 4 * this.getWidth() / 5;

			// Determine which zone is hit by the middle of the ball and
			// set ball velocity
			if (ball_mid <= z0) {
				// Far Left
				ball.setVelocity(-4, -4);
			} else if (ball_mid <= z1) {
				// Middle Left
				ball.setVelocity(-2, -4);
			} else if (ball_mid <= z2) {
				// Middle
				ball.setVelocity(0, -4);
			} else if (ball_mid <= z3) {
				// Middle Right
				ball.setVelocity(2, -4);
			} else {
				// Far Right
				ball.setVelocity(4, -4);
			}

			return true; // It does intersect
		}
		return false; // It does not intersect
	}

	public void update(Graphics2D g2, int width) {
		// Move
		if (getDx() > 0 && getX() + getWidth() + getDx() < width || getDx() < 0 && getX() + getDx() > 0) {
			super.update(g2);
		} else {
			// Redraw the sprite
			draw(g2);
		}
	}

}
