package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ss.training.lms.entity.BookLoan;

public class BookLoanDAO extends BaseDAO<BookLoan> {
    public BookLoanDAO(Connection conn) {
        super(conn);
    }

    public void addBookLoan(BookLoan loan) throws ClassNotFoundException, SQLException{
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?, ?, ?, ?, ?, ?)", new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate(), loan.getDateIn()});
	}

	public void updateBookLoan(BookLoan loan)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_book_loans SET dueDate = ?, dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[] {loan.getDueDate(), loan.getDateIn(), loan.getBookId(), loan.getBranchId(), loan.getCardNo()});
	}

	public void deleteBookLoan(BookLoan loan)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?", new Object[]{loan.getBookId(), loan.getBranchId(), loan.getBranchId()});
	}

	public void deleteBookLoansByBorrower(Integer borrowerCard)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_loans WHERE cardNo = ?", new Object[]{borrowerCard});
	}

	public void deleteBookLoansByBranch(Integer branchId)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_book_loans WHERE branchId = ?", new Object[]{branchId});
	}
	
	public List<BookLoan> readAllLoans() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_loans", null);
	}
	
	public List<BookLoan> readAllLoansFromABorrower(Integer cardNo) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND dateIn IS NULL", new Object[] {cardNo});
    }

    @Override
    public List<BookLoan> extractData(ResultSet rs) throws SQLException {
        List<BookLoan> loans = new ArrayList<>();
		while(rs.next()){
			BookLoan loan = new BookLoan();
            loan.setBookId(rs.getInt("bookId"));
            loan.setBranchId(rs.getInt("branchId"));
            loan.setCardNo(rs.getInt("cardNo"));
            loan.setDateOut(rs.getTimestamp("dateOut"));
            loan.setDueDate(rs.getTimestamp("dueDate"));
			loan.setDateIn(rs.getTimestamp("dateIn"));
			loans.add(loan);
		}
		return loans;
    }

}