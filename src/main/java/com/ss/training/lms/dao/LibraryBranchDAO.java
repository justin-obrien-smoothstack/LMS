package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{
    public LibraryBranchDAO(Connection conn) {
        super(conn);
    }

    public Integer addBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)", new Object[] {branch.getBranchName(), branch.getBranchAddress()});
	}

	public void updateBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?", new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}

	public void deleteBranch(LibraryBranch branch)  throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[]{branch.getBranchId()});
	}
	
	public List<LibraryBranch> readAllBranches() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_library_branch", null);
	}

	public List<LibraryBranch> readABranch(Integer branchId) throws SQLException{
		return read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[]{ branchId });
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
            branch.setBranchName(rs.getString("branchName"));
            branch.setBranchAddress(rs.getString("branchAddress"));
			branches.add(branch);
		}
		return branches;
	}
}
