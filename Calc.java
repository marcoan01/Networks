/********************************************************************************
 * This class only performs math calculations that are sent from clients to the server. 
 */
class Calc
{
    //Evaluate which mathematical operation is needed. 
    public static String evaluate(String expression) {

        expression = removeSpaces(expression);           //remove the spaces from the message sent

        for(char operator : expression.toCharArray()){   //read all the characters within the string
            if(operator == '+'){                         //if "+", summation will be done
                return add(expression.split("\\+", 2));
            }
            else if(operator == '-'){                    //if "-", substraction will be done
                return sub(expression.split("-",2));
            }
            else if(operator == '/'){                    //if "/", division will be done
                return div(expression.split("/",2));
            }
            else if(operator == '*'){                    //if "*" multiplication will be done
                return mul(expression.split("\\*",2));
            }
        }

        return "";                                       //return nothing as terms are being split
    }

    //summation is performed between the two number provided. This methods return the final result
    public static String add(String[] operands){
        int res = Integer.parseInt(operands[0]) + Integer.parseInt(operands[1]);
        return Integer.toString(res);
    }
    //substraction is performed between the two number provided. This method returns the final result
    public static String sub(String[] operands){
        int res = Integer.parseInt(operands[0]) - Integer.parseInt(operands[1]);
        return Integer.toString(res);
    }
    //division is performed between the two number provided. This method returns the final result
    public static String div(String[] operands){
        int res = Integer.parseInt(operands[0]) / Integer.parseInt(operands[1]);
        return Integer.toString(res);
    }
    //multiplication is performed between the two number provided. This method returns the final result
    public static String mul(String[] operands){
        int res = Integer.parseInt(operands[0]) * Integer.parseInt(operands[1]);
        return Integer.toString(res);
    }
    //method to remove the spaces given in the string from the user 
    public static String removeSpaces(String expression){
        StringBuilder sb = new StringBuilder();
        for(char operator : expression.toCharArray()){
            if(operator != ' '){
                sb.append(operator);
            }
        }
        System.out.println(sb.toString());        //print the message with no spaces to the screen
        return sb.toString();                     //return message with no spaces to provide math calculation
    }

}