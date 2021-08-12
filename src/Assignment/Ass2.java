package Assignment;


//(A%3)*(2-BC^(13));
import java.util.HashMap;
import java.util.Map;




public class Ass2 {

    //A Hashmap which stores the ids and the values assigned to them. Static as it will be the only one which exists throughout the program.
    public static HashMap< String,Integer> assignments = new HashMap<String, Integer>();

    public static void main(String[] args) {
        Parser p = new Parser();

        System.out.println("Registration Number : 1804159");
        System.out.println("Welcome to Callum's Expression evaluation program.");


        boolean run = true;
        boolean runQuestion = true;


        do{
            runQuestion =true;
            System.out.println("Please enter an expression:");
            ExpTree expression = null;
            boolean expressionAccepted = true;

            try {
                expression = p.parseLine();
            } catch (ParseException e){
                System.out.println("Wrong expression format. Please make sure you have terminated the expression with a semicolon");
                expressionAccepted = false;
            }



            if (expressionAccepted){
                //Post order (Part 2)
                System.out.println("Post order: ");
                expression.postOrder();

                //In Order
                System.out.println("\nTo String");
                System.out.print(expression.toString());

                //Evaluation of expression
                System.out.println("\nEvaluate: ");
                System.out.println(expression.evaluate());

            }
            //Clears the assignment map which is used in the expression
            assignments.clear();
            while (runQuestion){
                System.out.println("\nAnother expression? [Y/N] ");
                String question = p.getLine().toLowerCase();
                switch (question){
                    case "Y":
                        runQuestion = false;
                        run = true;
                        break;
                    case "y":
                        runQuestion = false;
                        run = true;
                        break;
                    case "N":
                        runQuestion = false;
                        run = false;
                        System.out.println("Exiting...");
                        break;
                    case "n":
                        runQuestion = false;
                        run = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Please enter Y/N");
                }
            }

        }while (run);
    }
}
