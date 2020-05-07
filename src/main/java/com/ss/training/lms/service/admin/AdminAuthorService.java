package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.AuthorDAO;
import com.ss.training.lms.entity.Author;

public class AdminAuthorService {
    ConnectionUtil connUtil;
    
    public Integer addAuthor(Author author) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            Integer primaryKey = authorDAO.addAuthor(author);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that author.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void deleteAuthor(Author author) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            authorDAO.deleteAuthor(author);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that author.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateAAuthor(Author author) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            authorDAO.updateAuthor(author);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that author.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public Author readAnAuthor(Integer authorId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            List<Author> authors = authorDAO.readAnAuthor(authorId);
            if(authors.size() == 0) {
                return null;
            }
            return authors.get(0);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the author.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Author> readAllAuthors() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            List<Author> authors = authorDAO.readAllAuthors();
            if(authors.size() == 0) {
                return null;
            }
            return authors;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the authors.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}