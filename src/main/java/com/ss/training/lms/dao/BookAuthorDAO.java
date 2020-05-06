package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.BookAuthor;

public class BookAuthorDAO extends BaseDAO<BookAuthor> {
    public BookAuthorDAO(Connection conn) {
        super(conn);
    }

    public void addBookAuthorEntry(BookAuthor bookAuthor) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_authors (bookId, authorId) VALUES (?, ?)", new Object[] {bookAuthor.getBookId(), bookAuthor.getAuthorId()});
	}

	public void updateBookAuthorByAuthor(Integer oldId, Integer newId)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_authors SET authorId = ? WHERE authorId = ?", new Object[] {oldId, newId});
    }
    
    public void updateBookAuthorByBook(Integer oldId, Integer newId)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_authors SET bookId = ? WHERE bookId = ?", new Object[] {oldId, newId});
	}

	public void deleteAuthorsReferenceByAuthor(Integer Author_id)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_authors WHERE Author_id = ?", new Object[]{Author_id});
	}

	public void deleteAuthorsReferenceByBook(Integer bookId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[] { bookId } );
	}
	
	public List<BookAuthor> readAllAuthorReferences() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_authors", null);
	}
	

    @Override
    public List<BookAuthor> extractData(ResultSet rs) throws SQLException {
        List<BookAuthor> bookAuthors = new ArrayList<>();
		while(rs.next()){
			BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setAuthorId(rs.getInt("authorId"));
            bookAuthor.setBookId(rs.getInt("bookId"));
			bookAuthors.add(bookAuthor);
		}
		return bookAuthors;
    }
}