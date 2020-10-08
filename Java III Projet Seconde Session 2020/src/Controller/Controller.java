package Controller;

import Model.*;
import View.MainView;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller{

	/**
	 * Actual state of the game
	 */
	protected static GameState state;
	protected static boolean isAlphabetVisible;
	protected static boolean isCheating;
	protected boolean visible;
	private static boolean once= true;
	private boolean pause, resume, start ;
	
	private Pawn pawn;
	private MainView view; 
	private Board board;
	
	public Controller() {
		state = GameState.Home;
		isAlphabetVisible = false;
		pause = resume = start = false;
		view = new MainView();
		pawn = view.getPawn();
		board = view.getBoard();
		isCheating = view.getCheating();
		view.getUser();	
		resume();
	}
	
	/**
	 * Method to handle pressed keys on scene given as argument
	 * @param scene on which events are performed
	 */
	
	private void movement(Scene scene) {
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			public void	handle(KeyEvent e){
		                 
				switch(e.getCode()) {
					case UP:
						if (state == GameState.Running && (pawn.getY()-GameObject.SIZE > 0)) {
							pawn.setY(pawn.getY()-(GameObject.SIZE));
						}
						break;
					case DOWN:
						if( state == GameState.Running && (pawn.getY()+GameObject.SIZE < MainView.HEIGHT)) {
							pawn.setY(pawn.getY()+(GameObject.SIZE));

						}
						break;
					case LEFT:
						if(state == GameState.Running && (pawn.getX()-GameObject.SIZE > 0)) {
							pawn.setX(pawn.getX()-(GameObject.SIZE));
						}
						break;
					case RIGHT:
						if(state == GameState.Running && (pawn.getX()+GameObject.SIZE < MainView.WIDTH)) {	
							pawn.setX(pawn.getX()+(GameObject.SIZE));
							
						}
						break;
					case SPACE: // pause or resume game
						if(state == GameState.Running || state == GameState.Paused) {
							if(pause == false) {
								pause = true;
								resume = false;
							}
							else {
								resume = true;
								pause = false;
								resume();
							}
						}
						break;
					case ENTER:{ // start or restart the game
				
							if(state == GameState.Started) {
								start = true;
								board.setUser(view.getUser());
								resume();
								//isAlphabetVisible=true;
								
							}
							if(state == GameState.Finished) {
								start = true;
								resume();
							}
						}
						break;
						
					case T:
						if(state == GameState.Running ){
							//if(isCheating) {
							isAlphabetVisible = !isAlphabetVisible;
							//}
						}
					
						break;
					case ESCAPE: // exit program
						board.updateFiles();
						System.exit(0);
						break;
					default:
						break;
				}	
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			 @Override
	         public void handle(KeyEvent event) {
			 }
		});
		

		
	}
	public static boolean handleClickOnPlay(Event event) {
			state = GameState.Started;
			return isAlphabetVisible;
		
	}

	/**
	 * The gameloop, handles user input, updates and renders the game
	 */
	private void resume(){
		
		 new AnimationTimer(){
			 
				int i=0;
				@Override
				public void handle(long now) {
					
					
					if(pause && !resume) {
						state = GameState.Paused;
						view.render();
						stop();
					}
					// when game resumed
					if(resume && !pause) {
						state = GameState.Running;
						resume = false;
					}
					// when game started or restarted
					if(start && (state == GameState.Finished || state == GameState.Started)) {
						restart();
						start = false;
					}
					// when game finished
					if(state == GameState.Finished) {
						stop();
					}	
					if(state == GameState.Home) {
						stop();
					}	
					if(state == GameState.Leveling) {
						
						restart();
					}	
					// when game is running, make movement

					update(); // updating the game parameters, positions, etc.
					view.render(); // rendering the scene
					movement(view.getScene()); // handling user key input on actual scene
				}
			}.start(); // starting the timer
		
	}

	/**
	 * The update method
	 */
	private void update() {
		
		board.updateObstacles(); // updating the obstacles on board
		board.updateScore(); // updating score(passing it to scoreView class to show it on screen)
		board.updateHScore();
		board.updateLevel();
		view.getUser();
		GameState tempState;
		tempState = board.checkCollision();
		if(tempState == GameState.Leveling) { // check if a collision occurred
			state = GameState.Leveling; 
		}
		if(tempState == GameState.Finished) {
			state = GameState.Finished;
			
		}
		

	}
		
	/**
	 * Restarting the game by setting basic parameters to their primary values
	 */
	private void restart() {
		state = GameState.Running;
		isAlphabetVisible=true;
		Timeline timeVisible = new Timeline(new KeyFrame(Duration.millis(5000), lambda->isAlphabetVisible=false));
		timeVisible.play();
	}
	
	/**
	 * Static method for returning the actual state of game for the Model and View classes
	 * @return Actual state of the game
	 */
	public static GameState getState() {
		return state;
	}	
	public static boolean getIsAlphabetVisible() {
		return isAlphabetVisible;
	}
	
	/**
	 * Returns the stage, pass it to Main class
	 * @return The game stage
	 */
	public Stage getStage() {
		return view.getStage();
	}

	public static boolean getIsCheating() {
		// TODO Auto-generated method stub
		return isCheating;
	}
}