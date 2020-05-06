package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.BookAuthorDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BookGenreDAO;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookAuthor;
import com.ss.training.lms.entity.BookGenre;
import com.ss.training.lms.entity.Genre;

public class AdminBookService {
    public ConnectionUtil connUtil = new ConnectionUtil();
    
    public Integer addBook(Book book) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            Integer primaryKey = bookDAO.addBook(book);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that book.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void addBookReferences(Book book, Author author, Genre genre) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();

            // Add the book genre reference
            BookGenreDAO bookGenreDAO = new BookGenreDAO(conn);
            BookGenre bookGenre = new BookGenre(genre.getGenreID(), book.getBookId());
            bookGenreDAO.addBookGenreEntry(bookGenre);

            // Add the author book reference
            BookAuthorDAO bookAuthorDAO = new BookAuthorDAO(conn);
            BookAuthor bookAuthor = new BookAuthor(book.getBookId(), author.getAuthorId());
            bookAuthorDAO.addBookAuthorEntry(bookAuthor);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that book.");
            e.printStackTrace();
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}


    }

    public void deleteBook(Book book) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            bookDAO.deleteBook(book);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that book.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateABook(Book book) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            bookDAO.updateBook(book);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that book.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public Book readABook(Integer bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            List<Book> books = bookDAO.readABookById(bookId);
            if(books.size() == 0) {
                return null;
            }
            return books.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the book.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Book> readAllBooks() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            List<Book> books = bookDAO.readAllBooks();
            if(books.size() == 0) {
                return null;
            }
            return books;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the books.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}