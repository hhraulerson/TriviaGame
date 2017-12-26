/**
Program that creates a QuestionBank class to write and store
all created questions in a random access file.  This
class also displays a menu for a trivia game, evaluates the 
user's answer to a question, lists all questions in the question 
bank, deletes a question from the bank, and performs a number of other
actions.

@author Hiram Raulerson
@version 1.3
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 24, 2015.
 
COP5007	Project #: 4
File Name: QuestionBank.java
*/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.EOFException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QuestionBank
{
   /**
   Creates a RandomAccessFile object
   */
   private RandomAccessFile file;
   
   /**
   Creates a constant for the size of a delete flag to be stored in the file
   */
   private static final int FLAG_SIZE = 1;
   
   /**
   Creates a constant for the max size of a question to be stored in the file
   */
   private static final int QUESTION_SIZE = 50;  
   
   /**
   Creates a constant for the max size of an answer to be stored in the file
   */
   private static final int ANSWER_SIZE = 20; 
   
   /**
   Creates a constant for the max size of a question's point value to be stored 
   in the file
   */ 
   private static final int POINTS_SIZE = 4;
   
   /**
   Creates a constant for the max size of a question's ID to be stored in the file
   */
   private static final int QUESTION_ID_SIZE = 4;
   
   /**
   Creates a constant for the max size of a question's record to be stored 
   in the file
   */
   private static final int RECORD_SIZE = FLAG_SIZE + QUESTION_SIZE + ANSWER_SIZE + POINTS_SIZE + QUESTION_ID_SIZE;
   
   /**
   Stores a flag to indicate whether a record has been deleted or not
   */
   private boolean deleteFlag = false;
   
   /**
   Constructs a QuestionBank object, sets the file to null, and
   opens the file
   */
   public QuestionBank()
   {
      file = null;
      
      open("questionbank.dat");
   }
   
   /**
	Displays the question bank administration menu and calls 
   the method that parses the user's menu selection 
   */
   public void displayMenu()
   {
      System.out.println("\n***************************\n");
      System.out.println("Trivia Game Administration");
      System.out.println("\t1. List all questions.");
      System.out.println("\t2. Delete a question.");
      System.out.println("\t3. Add a question.");
      System.out.println("\t4. Quit.\n");
      System.out.println("Enter selection: ");
   }
   
   /**
   Opens the file specified by the parameter value
   @param fileName the name of the file to open
   */
   private void open(String fileName)
   { 
      try
      {
         if (file != null) 
         {
            file.close();
         }
         
         file = new RandomAccessFile(fileName, "rw");
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Problem opening " + fileName + ".");
      }
      catch (IOException e)
      {
         System.out.println("Problems with file I/O.");
      }
   }
   
   /**
   Adds a question to the question bank file
   @param question the question
   @param answer the answer to the question
   @param pts the point value of the question
   */
   public void addQuestion(String question, String answer, int pts)
   { 
      boolean maxBytes = false;
      
      while (!maxBytes)
      {
         maxBytes = false;
         
         try
         {
            /*
            These variables allow the program to test if the max bytes have 
            exceeded for the question and answer instance variables
            */
            byte[] questionBytes = question.getBytes();
            byte[] answerBytes = answer.getBytes();
         
            if (questionBytes.length > 50)
            {
               throw new QuestionIncorrectDataException("Question exceeds max bytes allowed. Try again.\n");
            }
            else if (answerBytes.length > 20)
            {
               throw new QuestionIncorrectDataException("Question's answer exceeds max bytes allowed. Try again.\n");
            }
         
            Question q = new Question(question, answer, pts);
            
            setFlag(false);
         
            write(fileSize(), q);
         
            maxBytes = true;
         
         }
         catch (QuestionIncorrectDataException e)
         {
            System.out.println(e.getMessage());
         }
         catch (Exception e)
         {
            System.out.println("\nProblems with variable sizes.\n");
         }
      
      }
   }
   
   /**
	Lists all questions that have their flag set to false
   */
   public void listQuestions()
   {
      String question = "";
      String answer = "";
      int pts = 0;
      int questionID = -1;
      boolean flag = false;
      Question q = null;
      
      if (size() == 0)
      {
         System.out.println("\nThere are no entries in the question bank.\n");
      }
      
      try
      {
         for (int i = 0; i < fileSize(); ++i)
         {
            file.seek(i * RECORD_SIZE);
            
            flag = file.readBoolean();
            
            if (flag)
            {
               continue;
            }
            
            question = file.readUTF();
            
            file.seek((i * RECORD_SIZE) + 51);
            answer = file.readUTF();
            
            file.seek((i * RECORD_SIZE) + 71);
            pts = file.readInt();
            
            questionID = file.readInt();
            
            q = new Question(question, answer, pts, questionID);
            
            System.out.println("\n" + q);
         }
      }
      catch (EOFException e)
      {
         System.out.println("\nReached the end of the file.\n");
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.\n");
      }
   }
   
   /**
   Writes a question to the question bank file 
   @param question the question
   @param answer the answer to the question
   @param pts the point value of the question
   */
   public void write(int recordNumber, Question q)
   {
      try
      {
         file.seek(recordNumber * RECORD_SIZE);
         
         file.writeBoolean(getFlag());
         
         file.writeUTF(q.getQuestion());
         
         file.seek((recordNumber * RECORD_SIZE) + 51);
         file.writeUTF(q.getAnswer());
         
         file.seek((recordNumber * RECORD_SIZE) + 71);
         file.writeInt(q.getPoints());
        
         file.writeInt(q.getQuestionID());
      }
      catch (IOException e)
      {
         System.out.println("\nProblems printing output to file.");
      }
   }
   
   /**
   Runs appropriate methods based on user's menu selection
   @param selection the user's menu selection
   */
   public void parseUserSelection(int selection)
   {
      Scanner keyboard = new Scanner(System.in);
      
      switch (selection)
      {
         case 1: 
            listQuestions();
            break;
         case 2: 
            {
               System.out.println("\nPlease enter the ID of the question that will be removed: ");
                 
               int ID = 0;
               boolean correctInput = false;
                  
               while (!correctInput)
               {
                  try
                  {
                     ID = keyboard.nextInt();
                        
                     delete(ID);
                     
                     correctInput = true;
                  }
                  catch (InputMismatchException e)
                  {
                     System.out.println("\nError: integer was not entered.  Try again.");
                  }
                  catch (Exception e)
                  {
                     System.out.println("\nError: problems user input.  Try again.");
                  }
               }
               break;
            }
         case 3: 
            {
               String question = "";
               String answer = "";
               int pts = 0;
               boolean correctInput = false;
                  
               while (!correctInput)
               {
                  try
                  {
                     System.out.println("\nPlease enter the question to store (max size = 50 bytes): ");
                     
                     question = keyboard.nextLine();
                     
                     System.out.println("\nPlease enter the question's answer to store (max size = 20 bytes): ");
                     
                     answer = keyboard.nextLine();
                     
                     System.out.println("\nPlease enter the question's point value to store (1 to 5 only please): ");
                        
                     pts = keyboard.nextInt();
                     
                     addQuestion(question, answer, pts);
                     
                     correctInput = true;
                  }
                  catch (InputMismatchException e)
                  {
                     System.out.println("\nError: integer was not entered.  Try again.");
                  }
                  catch (Exception e)
                  {
                     System.out.println("\nError: problems user input.  Try again.");
                  }
               }
               break;
            }
         default:
            System.out.println("\nInvalid selection.  Try again");
            break;
      }
   }
   
   /**
   Closes the file
   */
   public void close()
   {
      try
      {
         if (file != null) 
         { 
            file.close();
         }
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.\n");
      }
      
      file = null;
      
   }
   
   /**
	Returns the total number of records stored in the file
   @return the number of records stored in the file
   */
   public int fileSize()
   {
      try
      {
         return (int)(file.length() / RECORD_SIZE);
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.\n");
         
         return -1;
      }
   }
   
   /**
	Returns the number of questions stored in the file that have their
   delete flags set to false
   @return the number of questions stored in the file
   */
   private int size()
   {
      int questionCount = 0;
      boolean flag = false;
      
      try
      {
         for (int i = 0; i < fileSize(); ++i)
         {
            file.seek(i * RECORD_SIZE);
            
            flag = file.readBoolean();
            
            if (flag)
            {
               continue;
            }
            
            ++questionCount;
         }
      }
      catch (EOFException e)
      {
         System.out.println("\nReached the end of the file.\n");
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.\n");
      }
      
      return questionCount;
   }
   
   /**
   Finds the position of a question based on a question's ID 
   @param ID the question's ID
   @return the position of the question with that ID
   */
   private int find(int ID)
   {
      try
      {
         for (int i = 0; i < fileSize(); ++i)
         {
            file.seek((i * RECORD_SIZE) + 75);
            
            int questionID = file.readInt(); 
                    
            if (questionID == ID) 
            {
               return i;
            }
         } 
         
         return -1;
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.\n");
         
         return -1;
      }
      
   }
   
   public Question getQuestion(int index)
   {
      String question = "";
      String answer = "";
      int pts = 0;
      int questionID = -1;
      boolean flag = false;
      
      Question q = null;
      
      try
      {
         file.seek(index * RECORD_SIZE);
            
         flag = file.readBoolean();
            
         if (flag)
         {
            return q;
         }
            
         question = file.readUTF();
            
         file.seek((index * RECORD_SIZE) + 51);
         answer = file.readUTF();
            
         file.seek((index * RECORD_SIZE) + 71);
         pts = file.readInt();
            
         questionID = file.readInt();
            
         q = new Question(question, answer, pts, questionID);
            
         return q;
         
      }
      catch (IOException e)
      {
         System.out.println("\nProblems with file I/O.");
         return null;
      }
   }
   
   /**
   Deletes the question's record identified by the parameter value
   @param questionID the ID used to identify the question to delete
   @return whether the question was deleted successfully
   */
   public boolean delete(int questionID)
   {
      int index = find(questionID);
      String question = "";
      setFlag(true);
      
      try
      {
         
         file.seek(index * RECORD_SIZE);
         file.writeBoolean(getFlag());
         
         file.seek((index * RECORD_SIZE) + 1);
         question = file.readUTF();
         
         System.out.println("\nRemoving the question: " + question + ".\n");
      }
      catch (IOException e)
      {
         System.out.println("\nProblems printing output to file.\n");
         
         return false;
      }
      
      return true;
   }
   
   /**
   Sets the question's flag to the parameter value
   @param keepOrDelete the value of a question's flag
   */
   private void setFlag(boolean keepOrDelete)
   {
      deleteFlag = keepOrDelete;
   }
   
   /**
   Returns the question's flag
   @return the question's flag
   */
   private boolean getFlag()
   {
      return deleteFlag;
   }
   
}





