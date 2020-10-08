package Model;

public class User {
	private String name;
	private String password;
	private boolean hasSave;
	private int highScore;
	private Save save = null;
	public User(String name, String password, int highScore) {
		super();
		this.name = name;
		this.password = password;
		this.highScore = highScore;
	}

	public String getName() {
		if (name.length()>0){
		return name;
		}
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getHighScore() {
		return highScore;
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	public boolean isHasSave() {
		return hasSave;
	}
	public void setHasSave(boolean hasSave) {
		this.hasSave = hasSave;
	}
	public Save getSave() {
		return save;
	}
	public void setSave(Save save) {
		this.save = save;
	}
	

}
