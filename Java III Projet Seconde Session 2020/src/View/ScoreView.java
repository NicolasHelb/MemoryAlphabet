package View;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ScoreView {
	
	
	//reprise de https://github.com/Rotgar/Snake
	
	private StackPane stack; 
	private Label l1, l2, l3, l4, l5, l6, l7, l8;
	/**
	 * Height of score panel
	 */
	public static final int SCORE_HEIGHT = 50;
	/**
	 * Color of score label
	 */
	private final Color scoreColor = Color.YELLOW;
	/**
	 * Color of the score panel
	 */
	private final Color scoreFieldColor = Color.rgb(100, 60, 100);
	
	public ScoreView() {
		
		stack = new StackPane();
		stack.setStyle("-fx-background-color: "+MainView.SCENE_COLOR);
		Rectangle r = new Rectangle(MainView.WIDTH - (2*SCORE_HEIGHT), SCORE_HEIGHT);
	    r.setFill(scoreFieldColor);
	    
	    double x = (double)MainView.WIDTH;
	    double y = (double)SCORE_HEIGHT;
	    double z = (double)MainView.HEIGHT;
	    
	    Polygon t1 = new Polygon();
        t1.getPoints().addAll(new Double[]{
            0.0, y+z,
            y, y+z,
            y, z });
        
        Polygon t2 = new Polygon();
        t2.getPoints().addAll(new Double[]{
            x, y+z,
            x-y, y+z,
            x-y, z });
	    
	    t1.setFill(scoreFieldColor);
	    t2.setFill(scoreFieldColor);
        
	    // setting the labels
		l1 = new Label("SCORE: ");
		l1.setFont(new Font(25));
		l1.setTextFill(scoreColor);
	
		l2 = new Label("0");
		l2.setFont(new Font(25));
		l2.setTextFill(scoreColor);
		
		l3 = new Label("HIGHSCORE: ");
		l3.setFont(new Font(20));
		l3.setTextFill(scoreColor);
	
		l4 = new Label("0");
		l4.setFont(new Font(20));
		l4.setTextFill(scoreColor);
		
		l5 = new Label("LEVEL: ");
		l5.setFont(new Font(20));
		l5.setTextFill(scoreColor);
	
		l6 = new Label("0");
		l6.setFont(new Font(25));
		l6.setTextFill(scoreColor);
		
		l7 = new Label("Press SPACE to Pause");
		l7.setFont(new Font(13));
		l7.setTextFill(scoreColor);
		
		l8 = new Label("Press ESC to Exit & Save");
		l8.setFont(new Font(13));
		l8.setTextFill(scoreColor);
		
		stack.getChildren().addAll(r, t1, t2, l1, l2, l3, l4, l5, l6, l7, l8);		
		stack.getChildren().get(1).setTranslateX(-(r.getWidth()/2+SCORE_HEIGHT/2));
		stack.getChildren().get(2).setTranslateX(r.getWidth()/2+SCORE_HEIGHT/2);
		stack.getChildren().get(3).setTranslateX(-30);
		stack.getChildren().get(4).setTranslateX(40);
		stack.getChildren().get(5).setTranslateX(-200);
		stack.getChildren().get(5).setTranslateY(-10);
		stack.getChildren().get(6).setTranslateX(-130);
		stack.getChildren().get(6).setTranslateY(-10);
		stack.getChildren().get(7).setTranslateX(150);
		stack.getChildren().get(7).setTranslateY(-10);
		stack.getChildren().get(8).setTranslateX(200);
		stack.getChildren().get(8).setTranslateY(-10);
		stack.getChildren().get(9).setTranslateX(150);		
		stack.getChildren().get(9).setTranslateY(15);		
		stack.getChildren().get(10).setTranslateX(-170);		
		stack.getChildren().get(10).setTranslateY(15);
	}
	
	/**
	 * Gets the actual score and displays it on the scene
	 * @param score in game
	 */
	public void addScore(int score) {
		l2.setText(Integer.toString(score));
	}
	public void addHighScore(int hScore) {
		l4.setText(Integer.toString(hScore));
	}
	public void addLevel(int lvl) {
		l6.setText(Integer.toString(lvl));
	}
	
	/**
	 * Returns the stack that holds all elements to be displayed on the score panel
	 * @return the stack
	 */
	public StackPane getStack() {
		return stack;
	}	
}