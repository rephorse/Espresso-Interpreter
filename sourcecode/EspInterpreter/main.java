package EspInterpreter;

public class main
{

		public static void main(String[] args){

				//new interpreter
				interpreter c = new interpreter();
				
				try {//runs the interpreter with the file as variable.
						c.run(args[0]);
				}catch(Exception e){
						System.out.println("File was not found!");;
				}
			
		}

}
