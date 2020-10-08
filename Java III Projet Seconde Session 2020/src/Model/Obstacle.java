package Model;

import javafx.scene.paint.Color;

public class Obstacle extends GameObject{
	private boolean isFlagged;
	private String lettre;
	
	

	public static final Color OBSTACLE_COLOR = Color.BROWN;
	
	
	public Obstacle(int x, int y, String lettre) {
		super(x, y);
		this.isFlagged=false;
		this.lettre=lettre;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public String getLettre() {
		return lettre;
	}

	public void setLettre(String lettre) {
		this.lettre = lettre;
	}
	
	

}
