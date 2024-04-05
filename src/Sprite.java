/*
 * In video games, a "Sprite" is a term used to describe a single graphical element
 * on the screen. Sprites can move and interact with each other. This class
 * represents the attributes and behaviors common to all Sprites in our game. This
 * class can be extended to create different types of Sprites that have specialized
 * attributes and behaviors.
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Sprite {

	//Location
	private int x;
	private int y;
	
	//Size
	private int width;
	private int height;
	
	//x and y velocity
	private int dx;
	private int dy;
	
	//Color
	private Color color;	
	
	//Default constructor
	public Sprite() {
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		dx = 0;
		dy = 0;
	}
	
	//Constructor with parameters
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = 0;
		this.dy = 0;
		color = Color.BLUE;
	}
	
	//This method moves the Sprite according to dX and dY
	public void update(Graphics2D g2) {
		//Move
		x += dx;
		y += dy;
		//Redraw the sprite
		draw(g2);
	}
	
    //Returns true if this Sprite intersects s, false otherwise
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
	
    //Renders the Sprite on screen
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(x, y, width, height);
	}
	
	//Returns a rectangle representing the Sprite
    public Rectangle getBoundary()
    {
        return new Rectangle(x,y,width,height);
    }
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void invertDx() {
		dx = -dx;
	}
	
	public void invertDy() {
		dy = -dy;
	}
	
	public void setVelocity(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
}
