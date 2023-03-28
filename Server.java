import java.io.*;
import java.net.*;
import java.time.*;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.time.temporal.ChronoUnit;

class Server
{
    /*****************************************************************************
     * main method that initializes server socket, logger, and file handler to keep track of all users
     * NOTE: In line of code 20, the TA must change the path to store the log file in their computers
     */
    public static void main(String args[]) throws Exception
    {
        ServerSocket serSocket = new ServerSocket(5555);
        Logger logger = Logger.getLogger(ClientHandler.class.getName());
        FileHandler fh = new FileHandler("C:/Users/madm0/Downloads/projlog.txt"); //Path where log file has to be stored. It has to be changed per localhost
        SimpleFormatter formatter = new SimpleFormatter();
        try
        {
            serSocket.setReuseAddress(true);
            logger.addHandler(fh);
            fh.setFormatter(formatter);
            //accept multiple clients and create threads for each
            while (true)
            {
                Socket cl = serSocket.accept();                          //accept individual client
                System.out.println("Connection established!");
                ClientHandler cl_Thread = new ClientHandler(cl, logger); //create a thread for that client
                new Thread(cl_Thread).start();                           //start running current thread
            }
        }
        catch(IOException e)                                             //Catch exception if no client can be accepted
        {
            e.printStackTrace();
        }
        //read the status of the socket. If it is not null, then close it. Otherwise, keep running it
        finally
        {
            if(serSocket != null)
            {
                try
                {
                    serSocket.close();
                }
                catch(IOException e)                                      //Print exception if server cannnot be closed
                {
                    e.printStackTrace();
                }
            }
        }
    }
    //Clienthandler class that simulates the server running and logging the information of the users
    private static class ClientHandler implements Runnable
    {
        private final Socket cl_Socket;                       //client socket
        Logger logger;                                        //logger to keep the information
        Date d = new Date();                                  //date of the connection established
        public ClientHandler(Socket s, Logger log)            //ClientHandler constructor
        {
            this.cl_Socket = s;
            logger = log;
        }
        public void run()
        {
            BufferedReader content = null;                    //Buffer to read from clients
            String sentence;                                  //response and sentence are used to receive and send data to the user
            String response;
            String[] client_names = new String[5];            //store client names in array
            PrintWriter out;                                  //print to the client
            try
            {
                Instant start = Instant.now();                //start timer when connection is established
                logger.info("Connection Established on " + cl_Socket.getRemoteSocketAddress().toString() + " at time: " 
                        + d.toString());                                                           //log the info of the connection 
                out = new PrintWriter(cl_Socket.getOutputStream(), true);                          //buffer to print to the user
                content = new BufferedReader(new InputStreamReader(cl_Socket.getInputStream()));   //buffer to read from the user
                client_names[0] = content.readLine();                                              //read the name of the client
                System.out.println(client_names[0]);        
                while((sentence = content.readLine()) != null)                                     //keep reading from the client until requested otherwise
                {
                    System.out.println("Message received from " + client_names[0] + ": ");
                    if(sentence.equalsIgnoreCase("stop")){                                         //if message is "stop", then connection must be terminated
                        Instant end = Instant.now();                                               //get the time when connection is terminated
                        Duration timeElapsed = Duration.between(start, end);                       //duration of the whole session for that current client

                        Date dd = new Date();                                                      //get the new date when connection is terminated
                        logger.info(cl_Socket.getRemoteSocketAddress().toString() + " terminated connection at " + dd.toString() + " for a duration of " + timeElapsed.getSeconds() + " seconds " + " with " + client_names[0]);
                    }
                    response = Calc.evaluate(sentence);                                            //in case of no stopping, send the math calculation to the correspondent method
                    out.println("SERVER RESPONSE: " + response);                                   //print results to the client
                }
            }
            catch (IOException e)                                                                  //print exception if no client can be connected
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if(content != null)                                                            //if the buffer is not null, then
                    {
                        content.close();                                                           //close the buffer
                        cl_Socket.close();                                                         //close the client socket
                    }
                }
                catch(IOException e)                                                               //print exception if client buffer cannot be read
                {
                    e.printStackTrace();
                }
            }
        }
    }
}