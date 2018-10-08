/**
 * This is the Model that maintains the STATE of the interaction.
 * The PROTOCOL is implemented using logic and state transitions.
 *
 *
 * */
package BusinessObjects;

import static BusinessObjects.MainApp.keyboard;
import DTOs.Movie;
import Daos.MovieDaoInterface;
import Daos.MySqlMovieDao;
import Exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Protocol
{

    private MovieDaoInterface movieDao;
    private static final Gson GSON = new Gson();

    public Protocol()
    {
        movieDao = new MySqlMovieDao();
    }

    public String processInput(String theInput) throws DaoException
    {
        System.out.println("protocol: " + theInput);
        String json = null;
        List<Movie> list = null;
        int added = 0;
        //String theOutput = null;
//        MovieDaoInterface movieDao = new MySqlMovieDao();
        // creat a new dao
        //create arraylist
        ArrayList<String> userInput = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(theInput, ",");
        while (st.hasMoreTokens())
        {    //add each token to array list                
            userInput.add(st.nextToken());
        }
//        System.out.println("user command: " + userInput.get(0));
        //check arraylist(0) to see what comand needs to be actioned
        if (userInput.get(0).equalsIgnoreCase("ShowAll"))
        {

            try
            {
                list = movieDao.findAllMovies();
            } catch (DaoException ex)
            {
                //Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
            }

//            Gson gson = new Gson();
            // next we create a Type object that represents the type of the
            // object that we want to serialize (here, type is List<Movie>  )
            Type type = new TypeToken<List<Movie>>()
            {
            }.getType();

            // we pass the list and its type into the Gson serializer method
            json = GSON.toJson(list, type); // serializes target to Json
        } else if (userInput.get(0).equalsIgnoreCase("SearchByTitle"))
        {
            System.out.println("search by title method");
            System.out.println("title to check for: " + userInput.get(1));
            try
            {
                list = movieDao.findMovieByTitle(userInput.get(1));

            } catch (DaoException ex)
            {
                //Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            Type type = new TypeToken<List<Movie>>()
            {
            }.getType();

            // we pass the movie object and its type into the Gson serializer method
            json = GSON.toJson(list, type); // serializes target to Json   
//            System.out.println("json string : " + json);
        } else if (userInput.get(0).equalsIgnoreCase("SearchByDirector"))
        {
            System.out.println("search by Director method");
            System.out.println("Director to check for: " + userInput.get(1));
            try
            {
                list = movieDao.findMoviesByDirector(userInput.get(1));
                if (list.isEmpty())
                {
                    System.out.println("There are no Movies directed by " + userInput.get(1));
                }
                for (Movie dmovie : list)
                {
                    if (dmovie != null)
                    {
                        System.out.println("Movie Directed by " + userInput.get(1));
                        System.out.println(dmovie);
                    } else
                    {
                        System.out.println("Movie Directed by " + userInput.get(1) + " not found");
                    }
                }

                List<Movie> moviesList = movieDao.findAllMovies();

                if (moviesList.isEmpty())
                {
                    System.out.println("There are no Movies");
                }

            } catch (DaoException ex)
            {
                //Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            Type type = new TypeToken<List<Movie>>()
            {
            }.getType();

            // we pass the movie object and its type into the Gson serializer method
            json = GSON.toJson(list, type); // serializes target to Json   
//            System.out.println("json string : " + json);
        } else if (userInput.get(0).equalsIgnoreCase("AddMovie"))
        {
            try
            {
                added = movieDao.addNewMovie(userInput.get(1), userInput.get(2), userInput.get(3));
                if (added >= 1)
                {
                    json = "true";
                } else
                {
                    json = "false";
                }
            } catch (DaoException ex)
            {
                //Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (userInput.get(0).equalsIgnoreCase("Remove"))
        {
            boolean isRemoved;
            int movieId = 0;
            try
            {
                list = movieDao.findMovieByTitle(userInput.get(1));
                if (list.size() > 1)
                {
                    //iterate thru the arraylist 
                    //ask the user if its the right movie
                    //get its id and pass onto the movieid to movieDao.removeMovie
                    for (int i = 0; i < list.size(); i++)
                    {
                        System.out.println("---\nIs this the right Movie: " + list.get(i).getTitle() + "?\n1. Yes\n2. No");
                        int choice = keyboard.nextInt();

                        if (choice == 1)
                        {
                            try
                            {
                                movieId = list.get(i).getId();
                                isRemoved = movieDao.removeMovie(movieId);
                                if (isRemoved)
                                {
                                    json = "true";
                                } else
                                {
                                    json = "false";
                                }
                                System.out.println("Deleted " + userInput.get(1) + " Successfully");
                            } catch (DaoException e)
                            {
                                e.printStackTrace();
                            }

                        } else
                        {
                            System.out.println("Did not delete " + userInput.get(1));
                        }
                    }
//                    System.out.println("Movie Found: " + list.get);
//                    movieId = list.getId();
                } else if (list.size() == 1)
                {
                    movieId = list.get(0).getId();

                    System.out.println("---\nDelete Movie " + userInput.get(1) + "?\n1. Yes\n2. No");
                    int choice = keyboard.nextInt();

                    if (choice == 1)
                    {
                        try
                        {
                            isRemoved = movieDao.removeMovie(movieId);
                            if (isRemoved)
                            {
                                json = "true";
                            } else
                            {
                                json = "false";
                            }
                            System.out.println("Deleted " + userInput.get(1) + " Successfully");
                        } catch (DaoException e)
                        {
                            e.printStackTrace();
                        }

                    } else
                    {
                        System.out.println("Did not delete " + userInput.get(1));
                    }

                } else
                {
                    System.out.println("Not Found");
                    return null;
                }
            } catch (DaoException e)
            {
                e.printStackTrace();
            }
        } 
        
        else if (userInput.get(0).equalsIgnoreCase("Update"))
        {            
            try
            {
                list = movieDao.findMovieByTitle(userInput.get(1));
                json = list.size()+""; //convert to a String
            } catch (DaoException e)
            {
                e.printStackTrace();
            }            
            
//        }
//        else if (userInput.get(0).equalsIgnoreCase("UpdateNext")){        
//            
//            added = movieDao.updateMovie(userInput.get(1), userInput.get(2), userInput.get(3))
//            
//                ///if blank is entered that means user is skipping which means we use existing title/genre/director
//                newTitle = (newTitle.length() == 0) ? movie.get(0).getTitle() : newTitle;
//                newGenre = (newGenre.length() == 0) ? movie.get(0).getGenre() : newGenre;
//                newDirector = (newDirector.length() == 0) ? movie.get(0).getDirector() : newDirector;
//
//                movie.get(0).setTitle(newTitle);
//                movie.get(0).setGenre(newGenre);
//                movie.get(0).setDirector(newDirector);
//                movie.get(0).setId(movie.get(0).getId());               
                
        
        }

        return json;
    }
}
