package Model;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import View.MainView;
import View.ScoreView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Board {

	private static final int BWIDTH = MainView.WIDTH/GameObject.SIZE;
	private static final int BHEIGHT = MainView.HEIGHT/GameObject.SIZE;

	private ArrayList<Obstacle> obstacles; // List of obstacles
	private ArrayList<User> users;
	private ArrayList<Save> saves;
	int score, highScore, level;
	/**
	 * Pawn object
	 */
	private Pawn pawn; 

	/**
	 * Random number for generating points to place objects on them
	 */
	Random rand; 
	/**
	 * State of the game
	 */
	private GameState state;
	
	private Alphabet alphabet;
	private Alphabet currentAlphabet;
	
	private User user;
		/**
	 *  Object of ScoreView to exchange informations about actual score
	 */
	private ScoreView scoreView; 
	
	boolean letterIsUsed = false,cheating,visible;

	/**
	 * Default constructor of board class to initialize starting variables
	 */
	public Board() { 
		
		scoreView = new ScoreView();
		obstacles = new ArrayList<>();
		users = new ArrayList<>();
		saves = new ArrayList<>();
		readFiles();		
		score = 0;
		pawn = new Pawn();
		rand = new Random();
		state = GameState.Started;
		alphabet = Alphabet.A;
		currentAlphabet = Alphabet.A;
		level = 1;
		
	}
	
	/**
	 * Method to check if an collision occurred with an obstacle on the board
	 * @return Returns the finished state of game
	 */
	public GameState checkCollision() {
		
		
		Position<Integer> posX = new Position<Integer>(pawn.getX());
		Position<Integer> posY = new Position<Integer>(pawn.getY());
		
		visible=Controller.getIsAlphabetVisible();
		cheating=Controller.getIsCheating();

		if (!visible || cheating) {
		for(Obstacle o : obstacles) {
			if (!o.isFlagged()) {
				
				if(o.getX() == posX.getValue() && o.getY() == posY.getValue()) {
						
						if(currentAlphabet.name().equals(o.getLettre())) {
							
							o.setFlagged(true);
							score++;
							
							this.currentAlphabet = currentAlphabet.getNext();
							if(!verifyObstacleLeft()) {
								user.getSave().setScoreAtStartLevel(score);
								user.getSave().setLevel(user.getSave().getLevel()+1);
								level ++;
								updateFiles();
								reset();
								return GameState.Leveling;
							}
							
	
						}else if(!cheating) {
							if (user.getHighScore()<score) {
								user.setHighScore(score);
							}
							//highScore=score;
							
							user.getSave().setScoreAtStartLevel(0);
							score = 0;
							user.getSave().setLevel(1);
							updateFiles();
							reset();
							return GameState.Finished;
						}
					}
				}
			}
		}
		
		
		return Controller.getState();
	}
	
	public  boolean verifyObstacleLeft() {
				
		for (Obstacle o : obstacles) {
			if (!o.isFlagged()) {return true;}	
		}
		return false;
		
		
	}	

	/**
	 * Method called in controller to update the state of obstacles in the game
	 */
	public void updateObstacles() {
		//
		if (user==null) {
			while(obstacles.size() <= (level+1)*2) {	//placing possible amount of obstacles
				placeObstacles();
			}
		}else {
			while(obstacles.size() <= (user.getSave().getLevel()+1)*2) {	//placing possible amount of obstacles
				placeObstacles();
				score=user.getSave().getScoreAtStartLevel();
				level=user.getSave().getLevel();
				highScore=user.getHighScore();
			}
		}
	}
	
	/**
	 * Looks for a point on the board to add new obstacle. Checks it to not collide with pawn or themselves and make sure they are accessible  
	 */
	private void placeObstacles() {

			
			int obstacleX = 0 , obstacleY = 0;	
			Position<Integer> posX =new Position<Integer>(pawn.getX());
			boolean collision = true;		
	
			while(collision) {
				
				collision = false;
				obstacleX = (rand.nextInt(BWIDTH)*GameObject.SIZE)+GameObject.SIZE/2; // random point on board
				obstacleY = (rand.nextInt(BHEIGHT)*GameObject.SIZE)+GameObject.SIZE/2;
				
				for(Obstacle o : obstacles) {
					if(o.getX()==obstacleX && o.getY()==obstacleY) {collision=true;}
					else if(o.getX()==obstacleX && ((o.getY()==obstacleY+GameObject.SIZE)|| o.getY()==obstacleY-GameObject.SIZE)) {collision=true;}
				}
				if(obstacleX == posX.getValue()) {collision=true;}
			}
			
		addObstacle(obstacleX, obstacleY, alphabet);	
		alphabet=alphabet.getNext();
		letterIsUsed = !letterIsUsed;
	}
	
	
	public void readFiles() {
		users = FileManager.usersFromFile("res/Users.txt");
		saves = FileManager.savesFromFile("res/Saves.txt");
		for (User u : users) {
			
			for (Save s : saves) {
				
				if(u.getName().equals(s.getUser())) {
					u.setSave(s);
				}
			}
			
		}
		
	}
	
	
	public void updateFiles() {
		if(!(user.getName()==null)){                                                    //Ajout apres remise, on vérifie que l'user n'est pas temporaire ( pas de nom rempli) plutot que dans l'écriture fichier
			updateUsers();
			FileManager.usersToFile(users);
			FileManager.savesToFile(saves);
		}
	}

	
	private void updateUsers() {
		boolean userIsIn=false;
		for (User u : users) {
			if (u.getName()==user.getName()){
				userIsIn=true;
				if (u.getHighScore()<user.getHighScore()) {
					u.setHighScore(user.getHighScore());
				}
			}
		}
		if (!userIsIn){
			users.add(user);
		}		
	}
	

	
	/**
	 * Add new obstacle to array
	 * @param X coordinate
	 * @param Y coordinate
	 */
	private void addObstacle(int X, int Y, Alphabet lettre) {
		obstacles.add(new Obstacle(X, Y, lettre.name()));
	}
	
	public void updateScore() {
		scoreView.addScore(score);
	}
	public void updateHScore() {
		scoreView.addHighScore(highScore);
	}
	
	public void updateLevel() {
		scoreView.addLevel(level);
	}
	
	/**
	 * Resets basic values of the game after a loss
	 */
	private void reset() {
		pawn.setStart();
		obstacles.clear();
		alphabet=Alphabet.A;
		currentAlphabet= Alphabet.A;
		visible = true;
		
	}
	
	/**
	 * Returns obstacles
	 * @return Array with obstacles
	 */
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}
	
	/**
	 * Returns the pawn
	 * @return Pawn object
	 */
	public Pawn getPawn() {
		return pawn;
	}
	
	/**
	 * Returns the object representing ScoreView in Board class
	 * @return The ScoreView object
	 */
	public ScoreView getScoreView() {
		return scoreView;
	}
	
	/**
	 * Returns the actual score
	 * @return Value of score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the highscore when game finished
	 * @return Value of final score
	 */
	public int getHighScore() {
		return highScore;
	}
	
	public int getLevel() {
		return level;
	}
	
	/**
	 * Returns the actual state of the game
	 * @return Value of GameState
	 */
	public GameState getState() {
		return state;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	public ArrayList<Save> getSaves() {
		return saves;
	}

	public boolean isCheating() {
		return cheating;
	}

	public void setUser(User user) {
		if(user !=null) {
			this.user=user;
		}
		
	}

	public void setIsCheating(boolean cheating) {
		this.cheating=cheating;
	}

	
}