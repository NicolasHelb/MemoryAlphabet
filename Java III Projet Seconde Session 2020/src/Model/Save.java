package Model;

public class Save {
	
	private String user;
	private int level;
	private int scoreAtStartLevel;
	
	public Save(String user, int level, int scoreAtStartLevel) {
		super();
		this.user = user;
		this.level = level;
		this.scoreAtStartLevel = scoreAtStartLevel;
	}
	
	public String getUser() {
		//if (user.length()>0){
		return user;
		//}
		//return null;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getScoreAtStartLevel() {
		return scoreAtStartLevel;
	}
	public void setScoreAtStartLevel(int scoreAtStartLevel) {
		this.scoreAtStartLevel = scoreAtStartLevel;
	}
	

}
