/** This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */
package BusinessObjects;

import DTOs.Movie;
import Daos.MovieDaoInterface;
import Daos.MySqlMovieDao;
import com.google.gson.Gson;
import DTOs.User;
//import Daos.MySqlUserDao;
//import Daos.UserDaoInterface;
import Exceptions.DaoException;
import java.util.List;
import java.util.Scanner;

public class MainApp
{

    static Scanner keyboard = new Scanner(System.in);
    private static final Gson GSON = new Gson();

    public static void main(String[] args)
    {
        //UserDaoInterface userDao = new MySqlUserDao();
        MovieDaoInterface movieDao = new MySqlMovieDao();

        // Notice that the userDao reference is an Interface type.
        // This allows for the use of different concrete implementations.
        // e.g. we could replace the MySqlUserDao with an OracleUserDao
        // without changing anything in the Interface. 
        // If the Interface doesn't change, then none of the
        // code below that uses the interface needs to change.
        // The 'contract' defined by the interface holds true.
        // The Business Objects requires that all User DAOs implement
        // the interfcae called "UserDaoInterface"
        // 
        try
        {
            List<Movie> movies = movieDao.findAllMovies();
                       
                        if( movies.isEmpty() )
                            System.out.println("There are no Movies");
                        
                        for( Movie movie : movies )
                            System.out.println("Movie: " + movie.toString());
//                        
            

//                        System.out.println("------Search By Title--------");
//                        System.out.println("Enter movie title to search for: ");
//                        String searchTitle = keyboard.nextLine();
//                        Movie movie = movieDao.findMovieByTitle(searchTitle);
//                        if (movie != null)
//                        {
//                            System.out.println("Movie found: " + movie);
//                        } else
//                        {
//                            System.out.println("Movie with Title " + searchTitle + " not found");
//                        }


//                        System.out.println("------Search By Director--------");
//                        System.out.println("Enter Director to search for: ");
//            
//                        String searchDirector = keyboard.nextLine();
//                        List<Movie> moviesDirector = movieDao.findMoviesByDirector(searchDirector);
//                        if (moviesDirector.isEmpty())
//                        {
//                            System.out.println("There are no Movies directed by " + searchDirector);
//                        }
//            
//                        for (Movie dmovie : moviesDirector)
//                        {
//                            if (dmovie != null)
//                            {
//                                System.out.println("Movie Directed by " + searchDirector);
//                                System.out.println(dmovie);
//                            } else
//                            {
//                                System.out.println("Movie Directed by " + searchDirector + " not found");
//                            }
//                        }
//                        List<Movie> moviesList = movieDao.findAllMovies();
//            
//                        if (moviesList.isEmpty())
//                        {
//                            System.out.println("There are no Movies");
//                        }
            
//
//
//            System.out.println("------Add A Movie--------");
//            int mAdded;         //number of rows affected: 0=none, 1=new row added
//
//            System.out.println("Please enter Title: ");
//            String title = keyboard.nextLine();
//            System.out.println("Please enter Genre: ");
//            String genre = keyboard.nextLine();
//            System.out.println("Please enter Director: ");
//            String director = keyboard.nextLine();
//
//            String output = title +"," + genre + "," + director;
//            
//            mAdded = movieDao.addNewMovie(title, genre, director);
//
//            if (mAdded > 0)      //number of rows affected: 0=none, 1=new row added
//            {
//                System.out.println("Movie " + title + " added successfully...");
//            } else
//            {
//                System.out.println("Movie NOT added, Try again.\n");
//            }

//
//            System.out.println("------Delete A Movie--------");
//            remove();
//
//
//            System.out.println("------Update A Movie--------");
//            update();

        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove()
    {
        MovieDaoInterface movieDao = new MySqlMovieDao();

        System.out.println("Please enter Movie Title to DELETE ");
        String title = keyboard.nextLine();

        int movieId = 0;
        try
        {
            Movie movie = (Movie) movieDao.findMovieByTitle(title);
            if (movie != null)
            {
                System.out.println("Movie Found: " + movie);
                movieId = movie.getId();
            }else
            {
                System.out.println("Not Found");
                return;
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

        System.out.println("---\nDelete Movie " + title + "?\n1. Yes\n2. No");
        int choice = keyboard.nextInt();

        if (choice == 1)
        {
            try
            {
                movieDao.removeMovie(movieId);
                System.out.println("Deleted " + title + " Successfully");
            } catch (DaoException e)
            {
                e.printStackTrace();
            }

        } else
        {
            System.out.println("Did not delete " + title);
        }

    }

    public static void update()
    {
        MovieDaoInterface movieDao = new MySqlMovieDao();

        System.out.println("Please enter Movie Title to UPDATE");
        String title = keyboard.nextLine();

        int movieId = 0;
        try
        {
            List<Movie> movie = movieDao.findMovieByTitle(title);
            if (movie != null)
            {
                System.out.println("Movie Found: " + movie);
                movieId = movie.getId();
            }else
            {
                System.out.println("Not Found");
                return;
            }
        } catch (DaoException e)
        {
            e.printStackTrace();
        }

        System.out.println("\nUpdate Movie " + title + "?\n1. Yes\n2. No");
        int choice = Integer.parseInt(keyboard.nextLine()); // Throws an EXCEPTION!!!

        if (choice == 1)
        {
            try
            {
                List<Movie> movie = movieDao.findMovieByTitle(title);

                System.out.println("Please enter New Title (or leave blank to skip): ");
                String newTitle = keyboard.nextLine();
                System.out.println("Please enter New Genre (or leave blank to skip): ");
                String newGenre = keyboard.nextLine();
                System.out.println("Please enter New Director (or leave blank to skip): ");
                String newDirector = keyboard.nextLine();

                //if blank is entered that means user is skipping which means we use existing title/genre/director
                newTitle = (newTitle.length() == 0) ? movie.get(0).getTitle() : newTitle;
                newGenre = (newGenre.length() == 0) ? movie.get(0).getGenre() : newGenre;
                newDirector = (newDirector.length() == 0) ? movie.get(0).getDirector() : newDirector;
                
                movie.get(0).setTitle(newTitle);
                movie.get(0).setGenre(newGenre);
                movie.get(0).setDirector(newDirector);
                movie.get(0).setId(movie.get(0).getId());

                movieDao.updateMovie((Movie) movie);

                System.out.println("Updated " + title + " Successfully");

            } catch (DaoException e)
            {
                e.printStackTrace();
            }

        } else
        {
            System.out.println("Did not update " + title);
        }

    }

//        try
//        {
//            List<User> users = userDao.findAllUsers();
//            
//            if( users.isEmpty() )
//                System.out.println("There are no Users");
//            
//            for( User user : users )
//                System.out.println("User: " + user.toString());
//            
//            // test dao - with good username and password
//            User user = userDao.findUserByUsernamePassword("smithj", "password");
//            if(user != null)
//                System.out.println("User found: " + user);
//            else
//                System.out.println("Username with that password not found");
//            
//            // test dao - with bad username
//            user = userDao.findUserByUsernamePassword("madmax", "thunderdome");
//            if(user != null)
//                System.out.println("User found: " + user);
//            else
//                System.out.println("Username with that password not found");
//            
//        }
//        catch( DaoException e )
//        {
//          e.printStackTrace();         
//        }       
//    }
}
