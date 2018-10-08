/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import DTOs.Movie;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author sitsh
 */
public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface
{

    public List<Movie> findAllMovies() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<Movie>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM MOVIES LIMIT 10";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int movie_id = rs.getInt("MOVIE_ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                String copies = rs.getString("COPIES");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");

                Movie m = new Movie(movie_id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                movies.add(m);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllMovies()" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllMovies()" + e.getMessage());
            }
        }
        return movies;     // may be empty
    }

    public Movie findMovieByTitleYear(String mtitle, String myear) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM MOVIES WHERE TITLE = ? AND YEAR = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, mtitle);
            ps.setString(2, myear);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int movie_id = rs.getInt("MOVIE_ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                String copies = rs.getString("COPIES");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                m = new Movie(movie_id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);

            }
        } catch (SQLException e)
        {
            throw new DaoException("findMovieByTitleYear" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findMovieByTitleYear" + e.getMessage());
            }
        }
        return m;     // m may be null 
    }

    public List<Movie> findMovieByTitle(String mtitle) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<Movie>();
        Movie m = null;
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM MOVIES WHERE TITLE = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, mtitle);

            rs = ps.executeQuery();
            if (rs.next())
            {
                int movie_id = rs.getInt("MOVIE_ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                String copies = rs.getString("COPIES");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");
                m = new Movie(movie_id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                movies.add(m);
//                System.out.println("moovie: " + m);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findMovieByTitle" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findMovieByTitle" + e.getMessage());
            }
        }
        return movies;     // m may be null 
    }

    public List<Movie> findMoviesByDirector(String mdirector) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> moviesDirector = new ArrayList<Movie>();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM MOVIES WHERE DIRECTOR = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, mdirector);

            rs = ps.executeQuery();

            while (rs.next())
            {
                int movie_id = rs.getInt("MOVIE_ID");
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                String director = rs.getString("DIRECTOR");
                String runtime = rs.getString("RUNTIME");
                String plot = rs.getString("PLOT");
                String location = rs.getString("LOCATION");
                String poster = rs.getString("POSTER");
                String rating = rs.getString("RATING");
                String format = rs.getString("FORMAT");
                String year = rs.getString("YEAR");
                String starring = rs.getString("STARRING");
                String copies = rs.getString("COPIES");
                String barcode = rs.getString("BARCODE");
                String user_rating = rs.getString("USER_RATING");

                Movie dm = new Movie(movie_id, title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, user_rating);
                moviesDirector.add(dm);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findMovieByDirector" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findMovieByDirector" + e.getMessage());
            }
        }
        return moviesDirector;
    }

    public int addNewMovie(String mtitle, String mgenre, String mdirector) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        Movie m = null;

        int rowCount = 0;

        try
        {
            con = this.getConnection();

            String query = "INSERT INTO MOVIES (TITLE, GENRE, DIRECTOR)\n"
                    + "VALUES (?, ?, ?);";
            ps = con.prepareStatement(query);
            ps.setString(1, mtitle);
            ps.setString(2, mgenre);
            ps.setString(3, mdirector);

            rowCount = ps.executeUpdate();
//            System.out.println(rowCount);

        } catch (SQLException e)
        {
            throw new DaoException("addNewMovie" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("addNewMovie" + e.getMessage());
            }
        }
        return rowCount;     // return number of rows affected: 0=none, 1=new row added
    }
    public int updateMovie(Movie movie) throws DaoException
 //   public void updateMovie(String mtitle, String mgenre, String mdirector, int movieId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
//        Movie m = null;
        
        int rowCount = 0;

        try
        {
            con = this.getConnection();

//            UPDATE MOVIE SET TITLE = ?, GENRE = ?, DIRECTOR = ? WHERE condition;
            String query = "UPDATE MOVIES SET TITLE = ?, GENRE = ?, DIRECTOR = ? WHERE MOVIE_ID = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, movie.getTitle()); // movie.getTitle();
            ps.setString(2, movie.getGenre());
            ps.setString(3, movie.getDirector());
            ps.setInt(4, movie.getId());

            rowCount = ps.executeUpdate();
            

        } catch (SQLException e)
        {
            throw new DaoException("updateMovie" + e.getMessage());
        } finally
        {
            try
            {
                
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("updateMovie" + e.getMessage());
            }
        }
        return rowCount;     // number of rows affected: 0=none, 1=new row updated

    }

    public boolean removeMovie(int movieId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();

            String query = "DELETE FROM MOVIES WHERE MOVIE_ID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, movieId);

            ps.executeUpdate();

        } catch (SQLException e)
        {
            throw new DaoException("removeMovie" + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("removeMovie" + e.getMessage());
            }
        }
        return true;
    }

   
}
