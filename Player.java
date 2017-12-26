/**
Program that creates a Player class which to track a player's
attributes when playing a trivia game.  This class also has the
ability to write the user's info to a binary file.

@author Hiram Raulerson
@version 1.3
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 25, 2015.
 
COP5007	Project #: 4
File Name: Player.java
*/

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.StringTokenizer;

public class Player implements Serializable
{
   /**
   Stores a player's real name
   */
   private String realName;
   
   /**
   Stores a player's nick name
   */
   private String nickName;
   
   /**
   Stores a player's total score for all games that have been played
   */
   private int totalScore = 0;
   
   /**
   Default constructor creates a Player object with default values for 
   all instance variables
   */
   public Player()
   {
      setRealName(null);
      setNickName(null);
      updateTotalScore(0);
   }
   
   /**
   Constructs a Player object with the player's real name equal to the 
   first parameter value and the player's nick name set equal to the 
   second parameter
   @param usersRealName the player's real name
   @param usersNickName the player's nick name
   */
   public Player(String usersRealName, String usersNickName)
   {
      setRealName(usersRealName);
      setNickName(usersNickName);
      updateTotalScore(0);
   }
   
   /**
   Copy constructor that creates a Player object with the player's details
   set equal to the Player object parameter's details 
   @param p the player whose details will be copied
   */
   public Player(Player p)
   {
      this.setRealName(p.getRealName());
      this.setNickName(p.getNickName());
      this.updateTotalScore(p.getTotalScore());
   }
   
   /**
	Sets the player's real name
   @param name the player's real name
   */
   public void setRealName(String name)
   {
      if (name == null || name.equals(""))
      {
         realName = "John Smith";
      }
      else
      {
         realName = name;
      }
   }
   
   /**
	Sets the player's nick name
   @param name the player's nick name
   */
   public void setNickName(String name)
   {
      if (name == null || name.equals(""))
      {
         nickName = "Johnny Boy";
      }
      else
      {
         nickName = name;
      }
   }
   
   /**
	Sets the player's total score by adding the parameter value
   to the player's total score
   @param score the player's score earned in a game of trivia
   */
   public void updateTotalScore(int score)
   {
      if (score >= 0)
      {
         totalScore += score;
      }
      else
      {
         System.out.println("\nA negative score is not valid.  Try again.\n");
      }
   }
   
   /**
	Stores the current state of a the player's object in a serialized file
   @return if the write to file was successful
   */
   public boolean writeUserObject()
   {
      String fileName = getFileName();
      
      try
      {
         ObjectOutputStream output = new ObjectOutputStream
               (new FileOutputStream(fileName));
               
         output.writeObject(this);
         
         output.close();
      }
      catch (IOException e)
      {
         System.out.println("\nProblems opening the file " + fileName + ".\n");
         
         return false;
      }
      
      return true;
   }

   /**
   Returns the player's real name
   @return the player's real name
   */
   public String getRealName()
   {
      return realName;
   }
   
   /**
   Returns the player's nick name
   @return the player's nick name
   */
   public String getNickName()
   {
      return nickName;
   }
   
   /**
   Returns the player's total score
   @return the player's total score
   */
   public int getTotalScore()
   {
      return totalScore;
   }
   
   /**
   Returns the file name for writing a player object to file
   @return the file name to store a player object
   */
   private String getFileName()
   {
      String s1 = "";
      String s2 = "";
      String fileName = "";
      
      StringTokenizer words = new StringTokenizer(getRealName());
      
      while (words.hasMoreTokens())
      {
         s1 = words.nextToken();
         s2 = s1.toLowerCase();
         
         fileName += s2;
      }
      
      fileName += ".dat";
      
      return fileName;
   }
   
   /**
	Returns a nicely formatted String composed of all of the 
   player's details
	@return the details of a player
   */
   public String toString()
   {
      return ("Player's Name:\t\t" + getRealName() + "\nPlayer's Nick Name:\t" +
             getNickName() + "\nPlayer's Total Score:\t" + getTotalScore() + "\n");
   }
}