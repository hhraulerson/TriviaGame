/**
Program that creates a TriviaGameDriver class to run that creates a 
TriviaGame object and lets a user play a game of trivia.

@author Hiram Raulerson
@version 1.1
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 25, 2015.
 
COP5007	Project #: 4
File Name: TriviaGameDriver.java
*/

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;
import java.util.InputMismatchException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class TriviaGameDriver
{
   public static void main(String[] args)
   {
      TriviaGame trivia;
      Player player = null;
      Scanner keyboard = new Scanner(System.in);
      Question q = null;
      String realName = "";
      String nickName = "";
      String fileName = "";
      String playerType = "";
      boolean anotherGame = true;
      boolean quit = false;
      boolean correctInput = false;
      boolean newPlayer = false;
      int gameScore = 0;
      final int NUMBER_QUESTIONS = 5;
      
      while (anotherGame)
      {
         anotherGame = true;
         quit = false;
         correctInput = false;
         newPlayer = false;
      
         System.out.println("\n***************************\n");
         System.out.println("Hello and welcome to The Trivia Game.");
               
         while (!correctInput)
         {
            System.out.println("Are you a New or Existing player (enter N or E)?");
         
            try
            {
               playerType = keyboard.nextLine();
            
               if (playerType.charAt(0) == 'e' || playerType.charAt(0) == 'E')
               {  
                  System.out.println("\nPlease enter your user name in lower case" +
                        " with no spaces (i.e., John Smith = \"johnsmith\"):");
               
                  fileName = keyboard.nextLine() + ".dat";
               
                  ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
               
                  player = (Player)input.readObject();
               
                  correctInput = true;
               }
               else if (playerType.charAt(0) == 'n' || playerType.charAt(0) == 'N')
               {
                  System.out.println("\nPlease enter your real name:");
               
                  realName = keyboard.nextLine();
               
                  System.out.println("\nPlease enter your nick name:");
               
                  nickName = keyboard.nextLine();
               
                  correctInput = true;
               
                  newPlayer = true;
               }
            }
            catch (InputMismatchException e)
            {
               System.out.println("\nError: a String was not entered.  Try again.\n");
            }
            catch(FileNotFoundException e) 
            {
               System.out.println("Error: could not find " + fileName + ".  Try again.\n"); 
            }
            catch(ClassNotFoundException e) 
            {
               System.out.println("Error: problems with file I/O.  Try again.\n"); 
            }
            catch(IOException e) 
            {
               System.out.println("Error: problems with file I/O.  Try again.\n"); 
            }
            catch (Exception e)
            {
               System.out.println("\nError: problems with user input.  Try again.\n");
            }
         }
      
         if (newPlayer)
         {
            player = new Player(realName, nickName);
         }
      
         trivia = new TriviaGame(player);
      
         while (!quit)
         {
            trivia.addQuestions();
         
            for (int i = 0; i < NUMBER_QUESTIONS; ++i)
            {
               q = trivia.askQuestion();
            
               System.out.println("\n" + q.getQuestion());
            
               boolean correct = trivia.evaluateAnswer(q, keyboard.nextLine());
            
               if (correct)
               {
                  System.out.println("\nThat is correct! Nice one!\n");
                  gameScore += q.getPoints();
                  System.out.println("Your score is " + gameScore + ".");
               }
               else
               {
                  System.out.println("\nYour answer was incorrect.  The correct answer" +
                     " is " + q.getAnswer() + ".");
                  System.out.println("\nYour score is " + gameScore + "\n");
               }
            }
         
            System.out.println("\nWould you like to play again? Enter yes or no.");
            
            if (!keyboard.nextLine().equalsIgnoreCase("yes"))
            {
               quit = true;
            }
            else
            {
               trivia.clear();
               gameScore = 0;
            }
         }
      
         player.updateTotalScore(gameScore);
      
         System.out.println("\nThank you for playing, " + player.getNickName() + "!");
         
         if (gameScore > 5)
         {
            System.out.println("\nYou did great!");
         }
         else
         {
            System.out.println("\nYou should probably do some studying before playing again.");
         }
         
         System.out.println("\nYour total score is " + player.getTotalScore() + ".\n");
      
         System.out.println("\nWould someone else like to play? Enter yes or no.");
            
         if (!keyboard.nextLine().equalsIgnoreCase("yes"))
         {
            anotherGame = false;
         }
         else
         {
            trivia.clear();
            gameScore = 0;
         }
      }
   }
   
}

