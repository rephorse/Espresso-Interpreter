/**
 * 
 */
package EspInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author tasso
 *
 */
public class interpreter {
		private Variable [] variable_table; //array of all letters of the alphabet without a value
		private int size; //size of the variable array
		private static char a; //placeholder for a variable
		private Character[] placeholder; //placeholder for character array containing expressions(infix)
		private infixEvaluator algebra; //evaluator to change infix to postfix and to evaluate postfix
		private String var; //string containing the line
		private int linecounter; //keeps track of the position inside the text file.
		private int pl; //placeholder for an integer.
		
		/**
		 * Constructor for the interpreter. Initiates the variables
		 */
interpreter(){
		a = 'A'; //to subtract from the array to access the right location 
		size = 80; //large enough array to store all letters of the alphabet
		variable_table = new Variable[size]; //creates a table for all letters to store variables
						//initiates each part of the array to a new variable.
		for(int i = 0;i<size;i++) {
				variable_table[i] = new Variable();
		}
		var=""; //holds the line with commands
		algebra = new infixEvaluator(); //creates a new evaluator
		linecounter=0; //initiates the linecounter to keep track of where the interpreter is.
}

public void run(String args) throws FileNotFoundException {
		//new scanner to read the file line by line
		Scanner sc = new Scanner(System.in);
		Scanner scanner = new Scanner(new File(args));
		
		//reads the file till there is no new line
		while(scanner.hasNextLine()) {		
				//stores the line in the variable and increases linecounter by one
		var = scanner.next() + scanner.nextLine();
		linecounter++;
		
		var = var.trim();
		char c;
		//if line contains the word read, the line is evaluated by this part
		if(var.contains("read")) {
				//read is delted and line is set to character. Error check is done to see if everything is valid.
				String temp = var.replace("read", "");
				temp = temp.replace(" ", "");
				temp.trim();
				c=temp.charAt(0);
				//Variable can only be one letter
				if(temp.length()>1) {
					System.out.println("Error: Variable can only consist of one letter."); //If the variable is not a single letter an exception is thrown
					System.out.println("Line " + linecounter +": " + var);
					System.exit(3);
				}
				//trys to create variable and assign number. If not possible, it catches the error, prints line, type of error and exits the program.
				System.out.println("Enter an integer number for variable " + c + ":");
				try{createVariable(c,sc.nextInt());}
				catch(Exception e) {
						System.out.println("Undefined variable " + c);
						System.out.println("Line " + linecounter +": " + var);
						System.exit(2);
				}
		}
		//evaluates the line if it contains an equal sign.
		if(var.contains("=")) {

				String temp1 = var.split("=")[0];
				String temp2 = var.split("=")[1];
				
				temp1 = temp1.replace(" ", "");
				temp1.trim();
				c=temp1.charAt(0);
			
				if(temp1.length()>1) {
						System.out.println("Error: Variable can only consist of one letter."); //If the variable is not a single letter an exception is thrown
						System.out.println("Line " + linecounter+": " + var);
						System.exit(3);
				}
				//evaluates the line after the equal sign.
				temp2 = temp2.replace(" ", "");
				temp2.trim();
				algebra = new infixEvaluator();
				replaceLetters(temp2);
				try{algebra.topostfix(this.placeholder,this.placeholder.length);}
				catch(Exception e) {
						System.out.println("Syntax error");
						System.out.println("Line " + linecounter+": " + var);
						System.exit(2);
				}
				//trys to evaluate the expression and assigns it to a placeholder.
				try{pl = algebra.postfixInterpreter();}
				catch(Exception e) {
						System.out.println("Syntax error");
						System.out.println("Line " + linecounter +": "+ var);
						System.exit(1);
				}
				//trys to assign the corresponding value to the variable.
				try{variable_table[c-a].setValue(pl);}
				catch(Exception e) {
						System.out.println("Undefined variable " + c);
						System.out.println("Line " + linecounter +": "+ var);
						System.exit(1);
				}
				
		}
		
		//evaluates the line if it contains a print
		if(var.contains("print")) {
				
				String temp = var.replace("print", "");
				temp = temp.replace(" ", "");
				temp.trim();
			
				//evaluates the line after the print. If its not possible to be evaluated, it prints out an error
				algebra = new infixEvaluator();
				replaceLetters(temp);
				try{algebra.topostfix(this.placeholder,this.placeholder.length);
				System.out.println(algebra.postfixInterpreter());			}
				catch(Exception e) {
						System.out.println("Syntax error");
						System.out.println("Line " + linecounter +": "+ var);
						System.exit(1);
				}
						

		}
}
		scanner.close();
		sc.close();
}

public void createVariable(char c, int value) {
		variable_table[c-a].setValue(value);
}

/**
 * replaces the Letters in an expression with the corresponding values and prints an error if there is a letter without a value assigned.
 * @param s accepts the expression as a line
 */
public void replaceLetters(String s){
		Stack<Character> replace = new Stack<Character>();
	//pushes all the digits in the stack
		for(int i=0;i<s.length();i++) {
				if(Character.isDigit(s.charAt(i))){
						replace.push(s.charAt(i));
				}
				//searches for the values of the letters and pushes them into the stack
				else if(Character.isLetter(s.charAt(i))) {
						try {
								String temp = Integer.toString(variable_table[s.charAt(i)-a].getValue());
								for(int z=0;z<temp.length();z++) {
								replace.push(temp.charAt(z));
								}
						} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("Error: Variable "+ s.charAt(i) + " is not defined." );
								System.out.println("Line " + linecounter + ": "+ var);
								System.exit(1);
						}
				}
				//if its not a variable or a letter it just pushes it into the stack
				else{
						replace.push(s.charAt(i));
				}
		}
//creates a new character array that holds the expression, now only with numbers instead of letters. Assigns the array to placeholder.
		this.size=replace.size();
		this.placeholder =new Character[size]; 
		
		for(int i = size-1;i>=0;i--) {
				this.placeholder[i]=replace.pop();
		}

}
}
