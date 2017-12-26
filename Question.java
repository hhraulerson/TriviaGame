/**
Program that creates a Question object which stores a question's
attributes (i.e., answer, etc.) for playing a trivia game.  

@author Hiram Raulerson
@version 1.0
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 20, 2015.
 
COP5007	Project #: 4
File Name: Question.java
*/

public class Question
{
   /**
   Stores the question
   */
   private String question;
   
   /**
   Stores the answer to the question
   */
   private String answer;
   
   /**
   Stores the point value for the question
   */
   private int points;
   
   /**
   Stores the ID for the question
   */
   private int questionID;
   
   /**
   Stores the next ID for a question
   */
   private static int nextID = 1;
   
   /**
   Constructs a Question object with the all instance variables set to default
   values
   */
   public Question()
   {
      setQuestion(null);
      setAnswer(null);
      setPoints(1);
      setQuestionID(-1);
   }
   
   /**
   Constructs a Question object with the question set to the first parameter,
   the answer set to the second parameter, and question's point value set to 
   the third parameter
   @param theQuestion the question
   @param theAnswer the answer to the question
   @param pts the points for answering the question correctly
   */
   public Question(String theQuestion, String theAnswer, int pts)
   {
      setQuestion(theQuestion);
      setAnswer(theAnswer);
      setPoints(pts);
      setQuestionID(-1);
   }
   
   /**
   Constructs a Question object with the question set to the first parameter,
   the answer set to the second parameter, the question's point value set to 
   the third parameter, and the question's ID set to the fourth parameter value. 
   @param theQuestion the question
   @param theAnswer the answer to the question
   @param pts the points for answering the question correctly
   @param ID the question's ID
   */
   public Question(String theQuestion, String theAnswer, int pts, int ID)
   {
      setQuestion(theQuestion);
      setAnswer(theAnswer);
      setPoints(pts);
      setQuestionID(ID);
   }
   
   /**
	Sets the question to the first parameter
   @param theQuestion the question
   */
   public void setQuestion(String theQuestion)
   {
      if (theQuestion == null || theQuestion.equals(""))
      {
         question = "Who was the second U.S. President?";
         
         setAnswer("John Adams");
      }
      else
      {
         question = theQuestion;
      }
   }
   
   /**
	Sets the answer to the first parameter
   @param theAnswer the question's answer
   */
   public void setAnswer(String theAnswer)
   {
      if (theAnswer == null || theAnswer.equals(""))
      {
         answer = "John Adams";
      }
      else
      {
         answer = theAnswer;
      }
   }
   
   /**
	Sets the points for a question to the first parameter (if 
   the parameter is between 1-5)
   @param pts the point value for the question
   */
   public void setPoints(int pts)
   {
      try
      {
         if (pts < 1 || pts > 5)
         {
            points = 3;
         
            throw new QuestionIncorrectDataException("Question's point value out of range. Points set to 3 by default.\n");
         }
         else
         {
            points = pts;
         }
      }
      catch (QuestionIncorrectDataException e)
      {
         System.out.println(e.getMessage());
      }
      catch (Exception e)
      {
         System.out.println("\nProblems with setting the Question's points. Points set to 3 by default.\n");
      }
   }
   
   /**
	Sets the ID for a question to the using the instance variable nextID
   @param i the question's ID (if -1 - use nextID to set the ID)
   */
   public void setQuestionID(int i)
   {
      if (i == -1)
      {
         questionID = getNextID();
         updateNextID();
      }
      else
      {
         questionID = i;
      }
   }
   
   /**
	Increments the next ID for a question ID
   */
   private void updateNextID()
   {
      ++nextID;
   }

   /**
   Returns the question
   @return the question
   */
   public String getQuestion()
   {
      return question;
   }
   
   /**
   Returns the answer to the question
   @return the question's answer
   */
   public String getAnswer()
   {
      return answer;
   }
   
   /**
   Returns the points for the question
   @return the question's points
   */
   public int getPoints()
   {
      return points;
   }
   
   /**
   Returns the ID for the question
   @return the question's ID
   */
   public int getQuestionID()
   {
      return questionID;
   }
   
   /**
   Returns the next ID for a question
   @return the next ID for a question
   */
   private static int getNextID()
   {
      return nextID;
   }
   
   /**
	Returns a nicely formatted String composed of all of the 
   question object's details
	@return the details of a question object
   */
   public String toString()
   {
      return ("Question:\t\t" + getQuestion() + "\nQuestion's Answer:\t" +
             getAnswer() + "\nQuestions Point Value:\t" + getPoints() + 
             "\nQuestion ID:\t\t" + getQuestionID() + "\n");
   }
}