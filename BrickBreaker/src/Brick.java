import java.awt.Color;
import java.awt.Point;


public class Brick extends Sprite {
	private int hitLimit; // How many hits a brick can take before being broken
	private int pointsEarned; // How many points a brick is worth once broken

	public Brick(int x, int y, int width, int height) {
		super(x, y, width, height);
		hitLimit = 1;
		pointsEarned = 100;
		setColor(Color.red);
	}
	
	// Updates ball direction based on hit location
	// Returns points > 0 if the brick is broken, 0 if it is not hit or not
	// broken
	public int isHit(Sprite ball) {
		// Check to see in the ball is hitting the brick
		if (intersects(ball)) {
			// Decrement the hit limit
			hitLimit--;

			// Determine hit direction and make ball bounce
			updateBall(ball);

			if (hitLimit == 0) {
				return getPointsEarned(); // Broken, send back points
			}
		}
		return 0; // Not broken, no points
	}

	// Update ball directions
	private void updateBall(Sprite ball) {
		
		Point left = new Point(ball.getX(), ball.getY() + ball.getHeight()/2);		
		
		//Left/Right Collision
		if(left.y > getY() && left.y < getY() + getHeight()) {
			ball.invertDx();
		}
		//Top/Bottom Collision
		else {
			ball.invertDy();
		}
	}

	public int getHitLimit() {
		return hitLimit;
	}

	public void setHitLimit(int hitLimit) {
		this.hitLimit = hitLimit;
	}

	public int getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
}

