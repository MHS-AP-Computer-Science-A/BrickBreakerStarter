import java.awt.Color;
import java.awt.Graphics2D;

public class BallSprite extends Sprite {

	public BallSprite(int x, int y) {
		super(x, y, 10, 10);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		g2.fillOval(getX(), getY(), getWidth(), getHeight());
	}
}
