package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.BookGenreDAO;
import com.ss.training.lms.dao.GenreDAO;
import com.ss.training.lms.entity.Genre;

public class AdminGenreService {
    public ConnectionUtil connUtil = new ConnectionUtil();
    
    public Integer addAGenre(Genre genre) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            GenreDAO genreDAO = new GenreDAO(conn);
            Integer primaryKey = genreDAO.addGenre(genre);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that genre.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void deleteAGenre(Genre genre) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            GenreDAO pubDAO = new GenreDAO(conn);
            BookGenreDAO bookGenreDAO = new BookGenreDAO(conn);
            bookGenreDAO.deleteGenresReferenceByGenre(genre.getGenreID());
            pubDAO.deleteGenre(genre);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that genre.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateAGenre(Genre genre) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            GenreDAO genreDAO = new GenreDAO(conn);
            genreDAO.updateGenre(genre);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that genre.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public Genre readAGenre(Integer genreId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            GenreDAO genreDAO = new GenreDAO(conn);
            List<Genre> genres = genreDAO.readAGenre(genreId);
            if(genres.size() == 0) {
                return null;
            }
            return genres.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the genre.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Genre> readAllGenres() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            GenreDAO genreDAO = new GenreDAO(conn);
            List<Genre> genres = genreDAO.readAllGenres();
            if(genres.size() == 0) {
                return null;
            }
            return genres;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the genres.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}