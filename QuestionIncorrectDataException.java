/**
Program that creates a QuestionIncorrectDataException class to handle
exceptions such as creating too large of a Question object or incorrect
number of points assigned to a Question object.

@author Hiram Raulerson
@version 1.0
 
E-mail Address: hhr3@students.uwf.edu.
 
Last Changed: October 25, 2015.
 
COP5007	Project #: 4
File Name: QuestionIncorrectDataException.java
*/

public class QuestionIncorrectDataException extends Exception
{
   /**
   Default constructor creates a QuestionIncorrectDataException
   exception with the exception message set to a default value
   */
   public QuestionIncorrectDataException()
   {
      super("Question object exceeds the max bytes allowed.");
   }
   
   /**
   Constructor creates a QuestionIncorrectDataException
   exception with the exception message set to the parameter
   */
   public QuestionIncorrectDataException(String msg)
   {
      super(msg);
   }
}