package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {
    public BookCopiesDAO(Connection conn) {
        super(conn);
    }

    public void addBookCopiesEntry(BookCopies entry) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_copies (bookId, branchId, NoOfCopies) VALUES (?, ?, ?)", new Object[] {entry.getBookId(), entry.getBranchId(), entry.getNoOfCopies()});
	}

	public void updateBookCopiesEntry(BookCopies entry)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", new Object[] {entry.getNoOfCopies(), entry.getBookId(), entry.getBranchId()});
	}

	public void deleteBookCopiesEntry(BookCopies entry)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[]{entry.getBookId(), entry.getBranchId()});
	}

	public void deleteBookLoansByBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies WHERE branchId = ?", new Object[] { branchId } );
	}
	
	public List<BookCopies> readAllEntries() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_copies", null);
	}
	
	public List<BookCopies> readAnEntry(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_copies WHERE branchId = ? AND bookId = ?", new Object[] {branchId, bookId});
	}
	
	public List<BookCopies> readBooksFromABranch(Integer branchId) throws SQLException {
		return read("SELECT * FROM tbl_book_copies WHERE branchId = ? AND noOfCopies > 0", new Object[] {branchId});
	}

    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException {
        List<BookCopies> entries = new ArrayList<>();
		while(rs.next()){
			BookCopies entry = new BookCopies();
            entry.setBookId(rs.getInt("bookId"));
            entry.setBranchId(rs.getInt("branchId"));
			entry.setNoOfCopies(rs.getInt("noOfCopies"));
			entries.add(entry);
		}
		return entries;
    }
}