package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	
	static String fileNameSaves = "res/Saves.txt";
	static String fileNameUsers = "res/Users.txt";
	
	/*
	#ToDO 	public FileManager() {
	}


	public FileManager(ArrayList<User> users,ArrayList<Save> saves) {
		usersToFile(users);
		savesToFile(saves);
	}
	*/

		
	
    public static User getUserDepuisLigne(String ligne){
    	  
        User user ; 
        String[] split = ligne.split(";");
        String name = split[0];
        String password = split[1];
        int hScore = Integer.parseInt(split[2]);
        user = new User(name, password, hScore);  
        return user;
    }
    public static Save getSavesDepuisLigne(String ligne){
  	  
        Save save ; 
        String[] split = ligne.split(";");
        String name = split[0];
        int lvl =  Integer.parseInt(split[1]);
        int score = Integer.parseInt(split[2]);
        save = new Save(name, lvl, score);  
        return save;
    }

    public static void usersToFile(ArrayList<User> users) {
    	 
        try {
          FileWriter myWriter = new FileWriter(fileNameUsers);
  
          for(User u : users){
        		  myWriter.write(u.getName()+";"+u.getPassword()+";"+u.getHighScore() + "\n");
          }
          myWriter.close();
          
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }	
    }
    public static void savesToFile(ArrayList<Save> saves) {
   	 
        try {
          FileWriter myWriter = new FileWriter(fileNameSaves);
  
          for(Save s : saves){
        		  myWriter.write(s.getUser()+";"+s.getLevel()+";"+s.getScoreAtStartLevel() + "\n");
          }
          myWriter.close();
          
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    	
    	
    }
    
	


    public static  ArrayList<User> usersFromFile(String filename) 
    { 
    	ArrayList<User>listeUsers = new ArrayList<User>();
        try {
        	
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                User newUser = getUserDepuisLigne(data);
                listeUsers.add(newUser);
            }
            myReader.close();
         } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
         }
		return listeUsers;

    } 
    public static  ArrayList<Save> savesFromFile(String filename) 
    { 
    	ArrayList<Save> listeSaves = new ArrayList<Save>();
        try {
        	
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Save newSave = getSavesDepuisLigne(data);
                listeSaves.add(newSave);
            }
            myReader.close();
         } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
         }
		return listeSaves;

    }

}
