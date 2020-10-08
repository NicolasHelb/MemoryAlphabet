package Model;

import java.util.ArrayList;

import View.MainView;
import javafx.scene.paint.Color;

public class Pawn {

	private int x;
	private int y;
	private int xStart = ((MainView.WIDTH/GameObject.SIZE)/2)*GameObject.SIZE + (GameObject.SIZE/2);
	private int yStart = ((MainView.HEIGHT/GameObject.SIZE)/2)*GameObject.SIZE + (GameObject.SIZE/2);
	public static final Color HEAD_COLOR = Color.ROYALBLUE;	



	public Pawn() {
		
		this.x=xStart;
		this.y=yStart;
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
	

	public void setStart() {
		
		this.x=xStart;
		this.y=yStart;
	}	
	
}	
	