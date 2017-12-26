/**
Program that creates a TriviaGame class read 5 random questions
from the question bank.  This class also creates a trivia game that ask
the user those 5 questions.

@author Hiram Raulerson
@version 1.4
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 25, 2015.
 
COP5007	Project #: 4
File Name: TriviaGame.java
*/

import java.util.Random;
import java.util.ArrayList;

public class TriviaGame
{
   /**
   Creates a constant for the max number of a questions to be stored in the container 
   for the trivia game
   */
   private static final int LIST_SIZE = 5;
   
   /**
   Creates a counter to store the number of questions added to the container
   */
   private int counter;
   
   /**
   Creates a question bank to pull 5 random questions from
   */
   private QuestionBank bank;
   
   /**
   Creates a container to store 5 random questions
   */
   private ArrayList<Question> gameQuestions;
   
   /**
   Creates a Random object used to generate random numbers
   */
   private Random generator;
   
   /**
   Creates a Player object used to update the player's stats
   */
   private Player thePlayer;
   
   /**
   Default constructor creates a TriviaGame object with 
   the container set to a size of 5.  Also creates a Random
   object to generate random numbers
   */
   public TriviaGame()
   {
      bank = new QuestionBank();
      gameQuestions = new ArrayList<Question>(5);   
      generator = new Random();
      setCounter(0);
      thePlayer = new Player();
   }
   
   /**
   Creates a TriviaGame object with the Player object set equal to the first 
   parameter.  This class also creates TriviaGame object with 
   the container set to a size of 5 and a Random
   object to generate random numbers
   @param p the player used to create the Player object
   */
   public TriviaGame(Player p)
   {
      bank = new QuestionBank();
      gameQuestions = new ArrayList<Question>(5);   
      generator = new Random();
      setCounter(0);
      thePlayer = new Player(p);
   }
   
   /**
   Returns the next question in the question bank based on a randomly
   generated number (note: if the question returned is null, use a new,
   random number to search for a valid question)
   @return a valid question from the question bank
   */
   public Question getNextQuestion()
   {
      Question q = bank.getQuestion(generator.nextInt(bank.fileSize()));  
      
      while (q == null)
      {
         q = bank.getQuestion(generator.nextInt(bank.fileSize())); 
      }
      
      return q;
   }
   
   /**
   Evaluates a user's answer to a question (ignores case)
   @param q the Question object that the user is trying to answer
   @param userAnswer the answer given by the user
   @return if the answer is correct
   */
   public boolean evaluateAnswer(Question q, String userAnswer)
   {
      String theAnswer = q.getAnswer();
      
      if (userAnswer.equalsIgnoreCase(theAnswer))
      {
         thePlayer.updateTotalScore(q.getPoints());
         
         setCounter(1);
         
         return true;
      }
      else
      {
         setCounter(1);
         
         return false;
      }
   }
   
   /**
   Adds the 5 questions to the container for use in the 
   trivia game.  The questions selected are based on random number
   generation.
   */
   public void addQuestions()
   {
      for (int i = 0; i < LIST_SIZE; ++i)
      {
         Question q = getNextQuestion();
         
         gameQuestions.add(q);
      }
   }
   
   /**
   Clears the container of previously used questions and resets
   the question counter to zero
   */
   public void clear()
   {
      gameQuestions.clear();
      
      setCounter(0);
   }
  
   /**
   Sets the counter based on the parameter passed in
   @param count indicates whether to increment counter or reset to zero
   */
   public void setCounter(int count)
   {
      if (count == 0)
      {
         counter = 0;
      }
      else
      {
         ++counter;
      }
   }
   
   /**
   Returns the question number that is being asked 
   @returns the question counter 
   */
   public int getCounter()
   {
      return counter;
   }
   
   /**
   Returns a Question object based on the current value of the question 
   counter 
   @return a Question object
   */
   public Question askQuestion()
   {  
      return gameQuestions.get(counter);
   }
   
}
