/** CLIENT
 * KnockKnockClient - uses Socket to connect this client to a server
 *
 * https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 */
package BusinessObjects;

import static BusinessObjects.MainApp.keyboard;
import DTOs.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;

public class Client
{

    private static final Gson GSON = new Gson();

    public static void main(String[] args) throws IOException
    {
        String hostName = "localhost";  // the server (running on local machine)
        int portNumber = 4444;          // port number the server is listening on

        menuPrint();

        try ( /// try-with-resources - will autoclose resources including sockets
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));)
        {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput = null;

            System.out.println("Enter Command: ");
            userInput = stdIn.readLine();
            while (!userInput.equalsIgnoreCase("exit"))
            {
                String words[] = userInput.split(" ");
                if (words[0].equalsIgnoreCase("showAll"))
                {
                    showAllMoviesMethod(userInput, out, in);

                } else if (words[0].equalsIgnoreCase("SearchByTitle"))
                {   //method search
                    searchByTitleMethod(userInput, out, in);

                } else if (words[0].equalsIgnoreCase("SearchByDirector"))
                {   //method search
                    searchByDirectorMethod(userInput, out, in);

                } else if (words[0].equalsIgnoreCase("AddMovie"))
                {   //addMethod
                    addMovieMethod(userInput, out, in);

                } else if (words[0].equalsIgnoreCase("Remove"))
                {
                    //remove method
                    removeMethod(userInput, out, in);
                } else if (words[0].equalsIgnoreCase("Update"))
                {
                    //Update method
                    updateMethod(userInput, out, in);
                }

                menuPrint();
                System.out.println("Enter Command: ");  // ask user for input
                userInput = stdIn.readLine();
            }

        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }

    public static void menuPrint()
    {
        System.out.println("This is the Movie Client program.");
        System.out.println("Connecting to server ... ");
        System.out.println("+++++++++++++++++++++++MENU+++++++++++++++++++++++++");
        System.out.println("ShowAll");
        System.out.println("SearchByTitle");
        System.out.println("SearchByDirector");
        System.out.println("AddMovie");
        System.out.println("Remove");
        System.out.println("Exit");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static void showAllMoviesMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {
//        userInput.substring("ShowAll".length() + 1);

        System.out.println("Sending (Client -> Server): " + userInput);
        out.println(userInput);  // send message to server

        List<Movie> list;

        String fromServer = in.readLine();
        System.out.println("Received JSON String (Server -> Client): " + fromServer);
        // Now to Deserialize the JSON String
        Type type = new TypeToken<List<Movie>>()
        {
        }.getType();
        System.out.println(fromServer);
//                Gson gson = new Gson();
        // pass the JSON String and the type of the object into the deserializer
        // and it will deserialize the JSON and create a List of Movies.

        list = GSON.fromJson(fromServer, type);

        for (Movie m : list)
        {
//            System.out.println(m.getTitle());
            System.out.println(m);
        }
    }

    public static void searchByTitleMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {
        String title = userInput.substring("SearchByTitle".length() + 1);
        System.out.println("title:" + title);
        userInput = "SearchByTitle," + title; // "searchbytitle,iron man"

        System.out.println("Sending (Client -> Server): " + userInput);
        out.println(userInput);  // send message to server

        List<Movie> list;

        String fromServer = in.readLine();
        System.out.println("Received JSON String (Server -> Client): " + fromServer);
        // Now to Deserialize the JSON String
        Type type = new TypeToken<List<Movie>>()
        {
        }.getType();
        System.out.println(fromServer);
//                Gson gson = new Gson();
        // pass the JSON String and the type of the object into the deserializer
        // and it will deserialize the JSON and create a List of Movies.

        list = GSON.fromJson(fromServer, type);

        for (Movie m : list)
        {
//            System.out.println(m.getTitle());
            System.out.println(m);
        }
    }

    public static void searchByDirectorMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {
        String searchDirector = userInput.substring("SearchByDirector".length() + 1);
        System.out.println("Director:" + searchDirector);
        userInput = "SearchByDirector," + searchDirector; // "searchbytitle,George Lucas"

        System.out.println("Sending (Client -> Server): " + userInput);
        out.println(userInput);  // send message to server

        List<Movie> list;

        String fromServer = in.readLine();
        System.out.println("Received JSON String (Server -> Client): " + fromServer);
        // Now to Deserialize the JSON String
        Type type = new TypeToken<List<Movie>>()
        {
        }.getType();
        System.out.println(fromServer);
//                Gson gson = new Gson();
        // pass the JSON String and the type of the object into the deserializer
        // and it will deserialize the JSON and create a List of Movies.

        list = GSON.fromJson(fromServer, type);

        for (Movie m : list)
        {
//            System.out.println(m.getTitle());
            System.out.println(m);
        }

    }

    public static void addMovieMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {
        System.out.println("Please enter Title: ");
        String title = keyboard.nextLine();
        System.out.println("Please enter Genre: ");
        String genre = keyboard.nextLine();
        System.out.println("Please enter Director: ");
        String director = keyboard.nextLine();

        String output = title + "," + genre + "," + director;
        userInput = "AddMovie," + output;

        System.out.println("Sending (Client -> Server): " + userInput);
        out.println(userInput);  // send message to server

        String fromServer = in.readLine();
        // keep reading from stream
        System.out.println("Received JSON String (Server -> Client): " + fromServer);

        if (fromServer.equals("true"))
        {
            System.out.println("Movie added successfully...");
        } else
        {
            System.out.println("Movie NOT added, Try again.\n");
        }
    }

    public static void removeMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {
        System.out.println("Please enter Movie Title to DELETE ");
        String title = keyboard.nextLine();
        userInput = "Remove," + title;

        System.out.println("Sending (Client -> Server): " + userInput);
        out.println(userInput);  // send message to server

        String fromServer = in.readLine();
        // keep reading from stream
        System.out.println("Received JSON String (Server -> Client): " + fromServer);

        if (fromServer.equals("true"))
        {
            System.out.println("Movie successfully DELETED...");
        } else
        {
            System.out.println("Movie NOT DELETE, Try again.\n");
        }
    }

    public static void updateMethod(String userInput, PrintWriter out, BufferedReader in) throws IOException
    {

//        System.out.println("Please enter Movie Title to UPDATE");
//        String title = keyboard.nextLine();
//        userInput = "Update," + title; //update, iron man
//
//        System.out.println("Sending (Client -> Server): " + userInput);
//        out.println(userInput);  // send message to server
        String title = userInput.substring("Update".length() + 1);
        userInput = "Update," + title; // "Update, iron man"

        System.out.println("\nUpdate Movie " + title + "?\n1. Yes\n2. No");
        int choice = Integer.parseInt(keyboard.nextLine()); // Throws an EXCEPTION!!!

        if (choice == 1)
        {
            //List<Movie> movie = movieDao.findMovieByTitle(title);

            out.println("Update," + title);
            int reply = Integer.parseInt(in.readLine());

            if (reply > 0)
            {
                System.out.println("Please enter New Title (or leave blank to skip): ");
                String newTitle = keyboard.nextLine();
                System.out.println("Please enter New Genre (or leave blank to skip): ");
                String newGenre = keyboard.nextLine();
                System.out.println("Please enter New Director (or leave blank to skip): ");
                String newDirector = keyboard.nextLine();

                String output = newTitle + "," + newGenre + "," + newDirector;
                userInput = "UpdateNext," + output;
                
            }
//            movieDao.updateMovie((Movie) movie);
//            System.out.println("Updated " + title + " Successfully");            

            System.out.println("Sending (Client -> Server): " + userInput);
            out.println(userInput);  // send message to server

            String fromServer = in.readLine();
            // keep reading from stream
            System.out.println("Received JSON String (Server -> Client): " + fromServer);

            if (fromServer.equals("true"))
            {
                System.out.println("Movie Updated successfully...");
            } else
            {
                System.out.println("Movie NOT Updated, Try again.\n");
            }

        } else
        {
            System.out.println("Did not update " + title);
        }

    }

}
