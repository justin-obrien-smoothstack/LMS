package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.entity.Borrower;

public class AdminBorrowerService {
    public ConnectionUtil connUtil = new ConnectionUtil();
    
    public Integer addABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BorrowerDAO borDAO = new BorrowerDAO(conn);
            Integer primaryKey = borDAO.addBorrower(borrower);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that borrower.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void deleteABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BorrowerDAO borDAO = new BorrowerDAO(conn);
            BookLoanDAO loanDAO = new BookLoanDAO(conn);
            loanDAO.deleteBookLoansByBorrower(borrower.getCardNo());
            borDAO.deleteBorrower(borrower);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that borrower.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateABorrower(Borrower borrower) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BorrowerDAO borDAO = new BorrowerDAO(conn);
            borDAO.updateBorrower(borrower);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that borrower.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public Borrower readABorrower(Integer cardNo) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BorrowerDAO borDAO = new BorrowerDAO(conn);
            List<Borrower> borrowers = borDAO.readABorrower(cardNo);
            if(borrowers.size() == 0) {
                return null;
            }
            return borrowers.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the borrower.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Borrower> readAllBorrowers() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            BorrowerDAO borDAO = new BorrowerDAO(conn);
            List<Borrower> borrowers = borDAO.readAllBorrowers();
            if(borrowers.size() == 0) {
                return null;
            }
            return borrowers;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the borrowers.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}