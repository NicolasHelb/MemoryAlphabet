package View;

import java.util.ArrayList;

import Controller.Controller;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainView{

	/**
	 * Width of the game board
	 */
	public final static int WIDTH = 600;
	/**
	 * Height of the game board
	 */
	public final static int HEIGHT = 600;
	/**
	 * Color of the scene
	 */
	public static String SCENE_COLOR = "white";
	/**
	 * Pawn object
	 */
	private Pawn pawn;
	/**
	 * Object to hold the actual scene
	 */
	private Scene scene;
	/**
	 * The stage
	 */
	private Stage stage;
	/**
	 * Actual state of the game
	 */
	private GameState state;

	
	boolean visible;
	boolean cheating;
	private String theme;
	/**
	 * Array of obstacles passed by board class
	 */
	
	private ArrayList<Obstacle> obstacles;
	private ArrayList<User> users;
	private ArrayList<Save> saves;
	/**
	 * Board object
	 */
	private Board board;
	private Group g;
	private Pane canvas;
	private GridPane grid;
	private StackPane stack;
	private ObservableList<String> names = FXCollections.observableArrayList();
	private User user;
	/**
	 * Default constructor initializes the basic variables and objects
	 */
	public MainView() {
		
		board = new Board();
		pawn = board.getPawn();
		theme = "Dark Theme";
		visible = board.isVisible();
		cheating = board.isCheating();
		obstacles = board.getObstacles();
		users = board.getUsers();
		saves = board.getSaves();
		
		stage = new Stage();
		stage.setTitle("Memory Alphabet");
		
		canvas = new Pane();
		canvas.setStyle("-fx-background-color: "+SCENE_COLOR);
        canvas.setPrefSize(WIDTH,HEIGHT);
        
		stack = new StackPane();
		grid = new GridPane();
		
	    g = new Group();
		scene = new Scene(g, WIDTH, HEIGHT + ScoreView.SCORE_HEIGHT);
		scene.setFill(Color.web(SCENE_COLOR));
		
		render();
	}
	
	/**
	 * The render method, that displays the graphics
	 */
	public void render() {
			
		this.state = Controller.getState(); // get the actual game state
		switch(state) { // checks for actual game state
		
			case Started:	// if game state is Started display the starting screen
				whenStarted();
				break;
			case Running:	
				whenRunning(); // if Running show the board, pawn, objects, etc.
				break;
			case Leveling:	
				whenRunning(); // if Running show the board, pawn, objects, etc.
				break;
			case Paused: // if Paused show the pause screen
				whenPaused();
				break;
			case Finished: // if Finished show the ending game screen and display the score
				whenFinished();
				break;
			case Home: // if Finished show the ending game screen and display the score
				whenHome();
				break;
			default:
				break;
		}
	} 
	
	private void whenHome() {
		g = new Group();
		Button buttonScore = new Button();
		buttonScore.setText("Best Scores");
		buttonScore.setPrefSize(150, 75);
		buttonScore.setMaxSize(300, 300);
		buttonScore.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		buttonScore.setLayoutX(WIDTH/2-75);
		buttonScore.setLayoutY(HEIGHT-HEIGHT/5);
		buttonScore.setOnAction(event -> whenCheckingScores());
		
		Button buttonSettings = new Button();
		buttonSettings.setText("Settings");
		buttonSettings.setPrefSize(150, 75);
		buttonSettings.setMaxSize(300, 300);
		buttonSettings.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		buttonSettings.setLayoutX(WIDTH/2-75);
		buttonSettings.setLayoutY(HEIGHT/2);
		buttonSettings.setOnAction(event -> whenSettings());
		
		Button buttonPlay = new Button();
		buttonPlay.setText("Play");
		buttonPlay.setPrefSize(150, 75);
		buttonPlay.setMaxSize(300, 300);
		buttonPlay.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;"); //https://docs.oracle.com/javafx/2/ui_controls/button.htm style donné en exemple
		buttonPlay.setLayoutX(WIDTH/2-75);
		buttonPlay.setLayoutY(HEIGHT/6);
		buttonPlay.setOnAction(event -> whenConnecting());
		
		Text largeText = new Text(WIDTH/2 - 250, HEIGHT/10 , "Memory Alphabet");

		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		largeText.setFill(Color.RED);
		g.getChildren().addAll(largeText,buttonSettings, buttonPlay, buttonScore);
		scene.setRoot(g);
		stage.setScene(scene);
		
		
	}

	private void whenConnecting() {
		g = new Group();
		
		TextField tfLogin = new TextField();
		tfLogin.setLayoutX(WIDTH/2-75);
		tfLogin.setLayoutY(HEIGHT/2.5);
		PasswordField tfPassword = new PasswordField();
		tfPassword.setLayoutX(WIDTH/2-75);
		tfPassword.setLayoutY(HEIGHT/2);
		
		
		Button buttonPlay = new Button();
		buttonPlay.setText("Play");
		buttonPlay.setPrefSize(150, 75);
		buttonPlay.setMaxSize(300, 300);
		buttonPlay.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		buttonPlay.setLayoutX(WIDTH - WIDTH/4-75);
		buttonPlay.setLayoutY(HEIGHT-HEIGHT/5);
		Text smallText2 = new Text(WIDTH/3 - 150, HEIGHT/2 + 90, "");
		smallText2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		smallText2.setFill(Color.RED);
		Text smallText = new Text(WIDTH/3 -180, HEIGHT/3 , "Don't enter anything for quickplay with no save and no best score");
		smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		smallText.setFill(Color.RED);
		
		
		buttonPlay.setOnAction((event) ->{
			boolean userCorrect = false, userExist=false;
			for(User u : users) {
				if(u.getName().equals(tfLogin.getText())) {
					userExist=true;
					if (u.getPassword().equals(tfPassword.getText())) {
						user=u;
						userCorrect = true;
						board.setUser(user);
						Controller.handleClickOnPlay(event);
						whenStarted();
					}	
					
				}
			}if (!userExist) {
				user= new User(tfLogin.getText(), tfPassword.getText(), 0);	
				Save save = new Save(user.getName(),1,0);
				user.setSave(save);
				saves.add(save);
				board.setUser(user);
				whenStarted();
				Controller.handleClickOnPlay(event);
			}
			else if(!userCorrect){
				smallText2.setText("Wrong Password");
				user =null;}	
		});	
		//buttonPlay.setOnAction(Controller::handleClickOnPlay);
		
		Button buttonBack = new Button();
		buttonBack.setText("Back");
		buttonBack.setPrefSize(150, 75);
		buttonBack.setMaxSize(300, 300);
		buttonBack.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;"); //https://docs.oracle.com/javafx/2/ui_controls/button.htm style donné en exemple
		buttonBack.setLayoutX(WIDTH/4-75);
		buttonBack.setLayoutY(HEIGHT-HEIGHT/5);
		buttonBack.setOnAction(event -> whenHome());
		Text largeText = new Text(WIDTH/2 - 250, HEIGHT/10 , "Memory Alphabet");
		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		largeText.setFill(Color.RED);
		Text textLogin = new Text(WIDTH/2 - 250, HEIGHT/10 , "Login: ");
		textLogin.setLayoutX(WIDTH/4.5);
		textLogin.setLayoutY(HEIGHT/3);
		textLogin.setFill(Color.DARKGREY);
		Text textPassword = new Text(WIDTH/2 - 250, HEIGHT/10 , "Password: ");
		textPassword.setLayoutX(WIDTH/5);
		textPassword.setLayoutY(HEIGHT/2.35);
		textPassword.setFill(Color.DARKGREY);
		g.getChildren().addAll(smallText, largeText, textLogin,smallText2, textPassword, buttonPlay, buttonBack, tfPassword, tfLogin);
		scene.setRoot(g);
		scene.setFill(Color.web(SCENE_COLOR));
		stage.setScene(scene);
		
		
	}

	private void whenSettings() {
		g = new Group();
		Button buttonTheme = new Button();
		buttonTheme.setText(theme);
		buttonTheme.setPrefSize(300, 75);
		buttonTheme.setMaxSize(300, 300);
		buttonTheme.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		buttonTheme.setLayoutX(WIDTH/2-150);
		buttonTheme.setLayoutY(HEIGHT/6);
		buttonTheme.setOnAction((event) -> {
			if (SCENE_COLOR=="white") {
				theme="Light Theme";
				SCENE_COLOR="black";
				}
			else {
				theme="Dark Theme";
				SCENE_COLOR="white";
				}
			whenSettings();
		});
		
		Button buttonCheating = new Button();
		buttonCheating.setText("Activate Cheating");
		buttonCheating.setPrefSize(300, 75);
		buttonCheating.setMaxSize(300, 300);
		buttonCheating.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		buttonCheating.setLayoutX(WIDTH/2-150);
		buttonCheating.setLayoutY(HEIGHT/2);
		buttonCheating.setOnAction((event) ->{
			cheating = !cheating;
			board.setIsCheating(cheating);
			if (cheating) {buttonCheating.setText("Desactivate Cheating");}
			else {buttonCheating.setText("Activate Cheating");}
		});	
		
		Button buttonBack = new Button();
		buttonBack.setText("Back");
		buttonBack.setPrefSize(150, 75);
		buttonBack.setMaxSize(300, 300);
		buttonBack.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;"); //https://docs.oracle.com/javafx/2/ui_controls/button.htm style donné en exemple
		buttonBack.setLayoutX(WIDTH/2-75);
		buttonBack.setLayoutY(HEIGHT-HEIGHT/5);
		buttonBack.setOnAction(event -> whenHome());
		Text largeText = new Text(WIDTH/2 - 250, HEIGHT/10 , "Memory Alphabet");
		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		largeText.setFill(Color.RED);
		g.getChildren().addAll(largeText,buttonCheating, buttonBack, buttonTheme);
		scene.setRoot(g);
		scene.setFill(Color.web(SCENE_COLOR));
		stage.setScene(scene);
		
	}

	private void whenCheckingScores() {
		g = new Group();

		if(names.isEmpty()) {
			for (User u : users) {
				names.add(u.getHighScore()+"  "+(u.getName()));
			}
		}
		ListView<String> listView = new ListView <>(names);
		//listView.setItems(names);
		listView.setPrefSize(150, 400);
		listView.setLayoutX(WIDTH/2-75);
		listView.setLayoutY(HEIGHT/7);
		listView.setFocusTraversable( false );
		Button button = new Button();
		button.setText("Back");
		button.setPrefSize(150, 75);
		button.setMaxSize(300, 300);
		button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		button.setLayoutX(WIDTH/2-75);
		button.setLayoutY(HEIGHT-HEIGHT/10);
		button.setOnAction(event -> whenHome());
		listView.setCellFactory(ComboBoxListCell.forListView(names));	
		Text largeText = new Text(WIDTH/2 - 170, HEIGHT/10 , "Best Scores");

		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		largeText.setFill(Color.RED);
		g.getChildren().addAll(largeText, listView,button);
		scene.setRoot(g);
		stage.setScene(scene);
		
	}

	/**
	 * Method to get the scene
	 * @return Returns the actual scene
	 */
	public Scene getScene() {
		return stage.getScene();
	}
	/**
	 * Method to get the stage
	 * @return Returns the game stage
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Method for displaying starting screen
	 */
	private void whenStarted() {
		
		g = new Group();
		Text largeText = new Text(WIDTH/3 - 170, HEIGHT/2 - 30, "Memory Alphabet");
		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		Text smallText = new Text(WIDTH/2 - 130, HEIGHT/2 + 20 , "Press ENTER to play");
		smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
		smallText.setFill(Color.DARKGREEN);
		g.getChildren().addAll(smallText, largeText);
		scene.setRoot(g);
		stage.setScene(scene);
	}
	
	/**
	 * Main render method to display the actual running game  
	 */
	private void whenRunning() {
		
		grid.getChildren().clear(); // clear grid	
		canvas.getChildren().clear(); // clear canvas  
		stack = board.getScoreView().getStack(); // get the graphic panel created for score
		visible = Controller.getIsAlphabetVisible();
		int helpX, helpY; // variables for loops
		Text largeText;
		Circle c;

		for(Obstacle o : obstacles) {//(int i = 0; i < obstacles.size(); ++i) {
			helpX = o.getX();
			helpY = o.getY();
			Rectangle r = new Rectangle(helpX - (GameObject.SIZE/2) , helpY - (GameObject.SIZE/2) , GameObject.SIZE, GameObject.SIZE); 
			r.setFill(Obstacle.OBSTACLE_COLOR);
			canvas.getChildren().add(r);
		}
		
		
		if(visible) {
			for (Obstacle o : obstacles) {
				largeText = new Text(o.getX()- (GameObject.SIZE/2), o.getY()+ (GameObject.SIZE/2), o.getLettre());
				largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, GameObject.SIZE+5));
				canvas.getChildren().add(largeText);
			}
		}
		c = new Circle(pawn.getX() , pawn.getY(), GameObject.SIZE/2); 
		c.setFill(pawn.HEAD_COLOR);
		canvas.getChildren().add(c);
		// adding canvas that holds the game objects, and stack that holds the score display, together
	
		grid.add(stack, 0, 1);
		grid.add(canvas, 0, 0);
		
		scene.setRoot(grid);		  
	    stage.setScene(scene);
	}

	/**
	 * Method invoked when game is paused
	 */
	private void whenPaused() {
		
		g = new Group();
		Circle c2;
		Rectangle r;
		Text largeText, smallText, smallText2, smallText3;
		
		largeText = new Text(WIDTH/2 - 190, HEIGHT/2 - 30, "Game Paused");
		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		smallText = new Text(WIDTH/2 - 190, HEIGHT/2 + 30, "Press SPACE to resume");
		smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		smallText.setFill(Color.DARKGREEN);
		smallText2 = new Text(WIDTH/3 - 150, HEIGHT/2 + 90, "Press ESCAPE to Quit & Save");
		smallText2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		smallText2.setFill(Color.RED);
		smallText3 = new Text(WIDTH/3 - 200, HEIGHT/2 + 150, "Press T to show letters in CheatMode");
		smallText3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 28));
		smallText3.setFill(Color.YELLOW);
		
		g.getChildren().addAll(smallText,smallText2,smallText3,  largeText);
		scene.setRoot(g);
		stage.setScene(scene);
	}
	
	/**
	 * Shows the finish screen when game is ended
	 */
	private void whenFinished() {
		
		g = new Group();
		Text largeText = new Text(WIDTH/2 - 220, HEIGHT/2 - 60, "Game Over");
		largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 70));
		largeText.setFill(Color.RED);
		Text largeText2 = new Text(WIDTH/2 - 170, HEIGHT/2 + 20, "FINAL SCORE: " + board.getHighScore());
		largeText2.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 37));
		Text smallText = new Text(WIDTH/2 - 160, HEIGHT/2 +100, "Press ENTER to replay");
		smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
		smallText.setFill(Color.DARKGREEN);
		Text smallText2 = new Text(WIDTH/2 - 130, HEIGHT/2 + 130 , " or ESCAPE to exit");
		smallText2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
		smallText2.setFill(Color.DARKGREEN);
		g.getChildren().addAll(smallText, largeText2, smallText2, largeText);
		scene.setRoot(g);
		stage.setScene(scene);
	}
	
	/**
	 * Method to get, or rather pass the Pawn object
	 * @return The pawn object
	 */
	public Pawn getPawn() {
		return pawn;
	}

	/**
	 * Method to get, or rather pass the Board object
	 * @return Board object
	 */
	public Board getBoard() {
		return board;
	}
	public boolean getCheating() {
		return cheating;
	}

	public User getUser() {
		return user;
		
	}

}
