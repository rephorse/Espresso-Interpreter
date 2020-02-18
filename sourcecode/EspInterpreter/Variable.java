package EspInterpreter;

public class Variable
{
private int value; //holds value of variable
private boolean initialized; //true if variable has value, else false

public Variable() {
		this.initialized=false; //sets new variable to false(no value)
}
//value is set and intialized is set to true
public void setValue(int value) {
		this.value=value;
		this.initialized=true;
}
//gets the value of the variable.
public int getValue() throws Exception {
		if(this.initialized==false) throw new Exception();
		else return this.value;
}

}
