package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookLoanDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.LibraryBranch;

@Component
public class AdminBranchService {
    public ConnectionUtil connUtil = new ConnectionUtil();
    
    public Integer addABranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO branchDAO = new LibraryBranchDAO(conn);
            Integer primaryKey = branchDAO.addBranch(branch);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that branch.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void deleteABranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO branchDAO = new LibraryBranchDAO(conn);
            BookLoanDAO loanDAO = new BookLoanDAO(conn);
            loanDAO.deleteBookLoansByBranch(branch.getBranchId());
            BookCopiesDAO copiesDAO = new BookCopiesDAO(conn);
            copiesDAO.deleteBookLoansByBranch(branch.getBranchId());
            branchDAO.deleteBranch(branch);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that branch.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateABranch(LibraryBranch branch) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO branchDAO = new LibraryBranchDAO(conn);
            branchDAO.updateBranch(branch);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that branch.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public LibraryBranch readABranch(Integer branchId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO branchDAO = new LibraryBranchDAO(conn);
            List<LibraryBranch> branches = branchDAO.readABranch(branchId);
            if(branches.size() == 0) {
                return null;
            }
            return branches.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the branch.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<LibraryBranch> readAllBranches() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            LibraryBranchDAO branchDAO = new LibraryBranchDAO(conn);
            List<LibraryBranch> branches = branchDAO.readAllBranches();
            if(branches.size() == 0) {
                return null;
            }
            return branches;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the branch.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}