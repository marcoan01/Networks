import java.io.*;
import java.net.*;
import java.util.*;

/************************************************************************************************
 * Client class the only connects with the server running in the local host and sends math calculations
 * to ultimately get a response back from the server with a solution. After that, connection stops
 */
class Client
{
    public static void main(String args[]) throws Exception
    {
        Socket s = new Socket("localhost", 5555);                                              //create a socket and connect to the local host
        try
        {
            String id = "Client_1";                                                            //name of this client
            String sentence = null;                                                            //message to be sent to the server
            PrintWriter p = new PrintWriter(s.getOutputStream(), true);                        //printer to the server
            Scanner sc = new Scanner(System.in);                                               //read input from the user
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //buffer reader to get a response from the server
            p.println(id);                                                                     //send the name of the client to the server
            p.flush();
            while(!"stop".equalsIgnoreCase(sentence))                                          //keep reading from the user, until "stops" is entered
            {
                sentence = sc.nextLine();
                p.println(sentence);                                                           //send the math calculation to the server
                p.flush();
                System.out.println(in.readLine());                                             //read the results from the server
            }
            s.close();                                                                         //close the socket connected to the server
        }
        catch(IOException e)                                                                   //print exception if no connection can be established
        {
            e.printStackTrace();
        }
    }
}