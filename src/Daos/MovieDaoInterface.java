/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;
import DTOs.Movie;
import Exceptions.DaoException;
import java.util.List;

/**
 *
 * @author sitsh
 */
public interface MovieDaoInterface 
{
    public List<Movie> findAllMovies() throws DaoException;
    public Movie findMovieByTitleYear(String mtitle, String myear) throws DaoException;
    public List<Movie> findMovieByTitle(String mtitle) throws DaoException;
    public List<Movie> findMoviesByDirector(String mdirector) throws DaoException;
    public int addNewMovie(String mtitle, String mgenre, String mdirector) throws DaoException;
    public boolean removeMovie(int movieId) throws DaoException;
    public int updateMovie(Movie movie) throws DaoException;
    //public int updateMovie(String mtitle, String mgenre, String mdirector) throws DaoException;
}
