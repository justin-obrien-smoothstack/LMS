package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.entity.Book;

@Component
public class BookDAO extends BaseDAO<Book> {
    public BookDAO(Connection conn) {
        super(conn);
    }

    public Integer addBook(Book book) throws ClassNotFoundException, SQLException {
        return saveWithPK("INSERT INTO tbl_book (title, pubId) VALUES (?, ?)", new Object[] { book.getTitle(), book.getPublisherId() });
    }

    public void updateBook(Book book) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_book SET title = ? WHERE pubId = ?",
                new Object[] { book.getTitle(), book.getPublisherId() });
    }

    public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
    }

    public void deleteBooksByPublisher(Integer pubId) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_book WHERE pubId = ?", new Object[] { pubId });
    }

    public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
        return read("SELECT * FROM tbl_book", null);
    }

    public List<Book> readAllBooksWithSearch(String search) throws SQLException {
        return read("SELECT * FROM tbl_book WHERE title LIKE ?;", new Object[] {true, search });
    }

    public List<Book> readABookById(Integer bookId) throws SQLException {
        return read("SELECT * FROM tbl_book WHERE bookId = ?;", new Object[] { bookId });
    }

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setBookId(rs.getInt("bookId"));
            book.setTitle(rs.getString("title"));
            book.setPublisherId(rs.getInt("pubId"));
            books.add(book);
        }
        return books;
    }
}