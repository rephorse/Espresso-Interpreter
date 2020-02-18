Interpreter class
Interpreter(): constructor for the interpreter class. Creates a new instance and assigns necessary values.
run(String args): is the instance of the class that runs the program
createVariable(char c, int value): assigns a value to a variable
replaceLetters(String): replaces the letters in an expression.

infixEvaluator Class
infixEvaluator(): constructor, that creates a new infix evaluator.
topostfix(Character x[], int size): converts the infix expression to postfix(the interpreter can take variables or integers)
int precedencecheck(Character x): Checks if an operator has greates precedence or not.
String getResult: Returns the postfix expression as a string.
int postfixInterpreter(): it calculates the result for the postfix expression. If it can't calculate, it outputs an error.

Variable Class
Variable() Constructor: Creates an empty variable with initiated to false.
setValue(int value): Assigns a value to a variable and then sets the variable to initiated.
int getValue(): returns the value of the variable if it has one assigned, otherwise it throws an error.



Available program statements are:
read (one letter variable)
print (expression OR one letter variable)
(one letter variable) = (expression)

(one letter variable) would be 'a b c d e ....', any letter of the alphabet
(expression) would be sum, substraction, multiplication, division or modulus with brackets.
