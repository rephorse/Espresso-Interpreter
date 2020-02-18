package EspInterpreter;

import java.util.Stack;

public class infixEvaluator
{
private Stack<Character> postfix; //stack of characters holding the expression
private String result; //string holding the postfix expression
private int parenthesis; //counts amount of parenthesis
private Stack<Integer> postfixEval; //stack of integers to evaluate the postfix expression
private int operator; //counts the amount of operators. No unary operators are allowed, so if there have to be one operator less than total of integers.
/**
 * constructor for the infix evaluator
 */
public infixEvaluator(){
		// TODO Auto-generated constructor stub
		result = new String();
		postfix = new Stack<Character>();
		parenthesis =0;
		operator=0;
}
/**
 * converts from infix to postfix
 * @param x character array with the infix expression
 * @param size size of the character array
 * @throws Exception
 */
public void topostfix(Character x[], int size) throws Exception {
		// TODO Auto-generated constructor stub
	
		for(int i=0;i<size;i++) {
			//If the character is a digit, add it to the string
			
				//adds to parenthesis if a parenthesis is encountered.
			if(x[i].equals('(')) {
					parenthesis++;
			}
			//subtracts if a parenthesis is encountered so that at the end parenthesis should be 0.
			if(x[i].equals(')')) {
					parenthesis--;
			}
			//throws exception if invalid parenthesis is encountered
			if(x[i].equals('[')||x[i].equals('{')||x[i].equals(']')||x[i].equals('}')) {
					throw new Exception();
			}
			//if character is a letter or digit its added to result, with whitespaces between the numbers(if a number has more than one digit)
			if(Character.isLetterOrDigit(x[i])) {
					//adds to resultstring till encounters a whitespace
					while(Character.isLetterOrDigit(x[i])) {
							result += x[i];
							i++;
							if(i>=x.length) break;
					}
						i--;
						result += " ";
			}
			
		
			else{
			//if the stack is empty, or if the precedence of operator in stack is greater, or if there is an opening parenthesis in the stack
					if(postfix.isEmpty()||precedencecheck(postfix.peek())>precedencecheck(x[i])||postfix.peek().equals('(')) {
							//and if the current character is not a parenthesis, it is pushed to stack
							if(!x[i].equals(')')&&!x[i].equals('(')){
									postfix.push(x[i]);	
							}
					}
					
					//otherwise it is checked that the stack is not empty and precedence is not smaller, whatever is int the stack is poped.
					else {
								while(!postfix.isEmpty()&&precedencecheck(postfix.peek())<=precedencecheck(x[i])) {
										//opening parenthesis are discarded				
										if(postfix.peek().equals('(')) {
																postfix.pop();
																break;
														}
														result += postfix.pop() + " ";
											}
								postfix.push(x[i]);
							
						}
					//opening parenthesis, if encountered, is pushed to stack
					if(x[i].equals('(')) {
							postfix.push(x[i]);
							continue;
					}	
					//closing parenthesis is discarded, and all operators are poped till a closing parenthesis is encountered.
					if(x[i].equals(')')) {
							while(!postfix.empty()) {	
									if(postfix.peek().equals('(')) {
											postfix.pop();
											break;
									}
								if(postfix.peek().equals(')')) postfix.pop();
									
								 result+=postfix.pop()+ " ";

							}
					}
							//if there is a closing parenthesis, add the operators to the result
					
			}
	}

	//averything in the postfix is put in the result string at the end. Parenthesis is checked.
	while(!postfix.isEmpty()) {
			if(postfix.peek().equals('(')) {
					 break;
			}
			result += postfix.pop();
	}
	if(!(parenthesis==0)) {
			throw new Exception();
	}

}

//checks the precedence and returns an appropriate integer.
public int precedencecheck(Character x) {
		if(x=='+'||x=='-') return 3;
		if(x=='*'||x=='/'||x=='%') return 2;
		return 1;
}
//returns result as string
public String getResult() {
		return result;
}
//evaluates the postfix expression
public int postfixInterpreter() throws Exception {
		//sets amount of operators to 0
		operator=0;
		postfixEval = new Stack<Integer>(); //new integer stack

		for(int i=0;i<result.length();i++) {
				//checks for white space
				if(result.charAt(i)==' ') {continue;}
				
				else {
						//adds the integers to the stack
						if(Character.isDigit(result.charAt(i))) {
								int n = 0;
								while(!Character.isWhitespace((result.charAt(i)))) {
										n = n*10 + (int)(result.charAt(i)- '0');
										i++;
								}
								//pushes the operands to the stack
							postfixEval.push(n);
							operator--;
						}
						
						else {
								int b = postfixEval.pop();
								int a = postfixEval.pop();
								int c = 0;
								if(result.charAt(i)=='+') {c=a+b;}
								else if(result.charAt(i)=='-') {c=a-b;}
								else if(result.charAt(i)=='*') {c=a*b;}
								else if(result.charAt(i)=='/') {c=a/b;}
								else if(result.charAt(i)=='%') {c=a%b;}
								else {
											throw new Exception();
								}
								postfixEval.push(c);
								
								operator++;
								
						}
				}
		}

		if(operator!=-1) {
			throw new Exception();
		}
		return postfixEval.pop();
}
}
