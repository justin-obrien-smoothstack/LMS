package com.ss.training.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.jdbc.ConnectionUtil;

public class LibrarianService {
    public ConnectionUtil connUtil = new ConnectionUtil();

    public boolean updateCopies(BookCopies entry) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookCopiesDAO entriesDAO = new BookCopiesDAO(conn);
            List<BookCopies> entries = entriesDAO.readAnEntry(entry.getBranchId(), entry.getBookId());
            if (entries.size() == 0) {
                entriesDAO.addBookCopiesEntry(entry);
            } else {
                System.out.println("No Entry found!!!");
                entriesDAO.updateBookCopiesEntry(entry); 
            }
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public boolean updateBranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO libDAO = new LibraryBranchDAO(conn);
            libDAO.updateBranch(branch);
            conn.commit();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn.rollback();
            return false;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<LibraryBranch> getBranches() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO libDAO = new LibraryBranchDAO(conn);
            return libDAO.readAllBranches();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Book> getBooksWithSearch(String search) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookDAO bookDAO = new BookDAO(conn);
            return bookDAO.readAllBooksWithSearch(search);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public BookCopies getAnEntryOfBookCopies(Integer branchId, Integer bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookCopiesDAO entriesDAO = new BookCopiesDAO(conn);
            List<BookCopies> entries = entriesDAO.readAnEntry(branchId, bookId);
            if(entries.size() == 0) {
                return null;
            }
            return entriesDAO.readAnEntry(branchId, bookId).get(0);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Book> getBooksAtABranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BookCopiesDAO entriesDAO = new BookCopiesDAO(conn);
            List<Book> books = new ArrayList<>();
            BookDAO bookDAO = new BookDAO(conn);
            List<BookCopies> entries = entriesDAO.readBooksFromABranch(branch.getBranchId());
            if(entries.size() == 0) {
                return null;
            }
            for(BookCopies entry: entries) {
                books.add(bookDAO.readABookById(entry.getBookId()).get(0));
            }
            return books;
        } catch ( SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}