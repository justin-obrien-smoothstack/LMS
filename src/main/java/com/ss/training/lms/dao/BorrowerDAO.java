package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ss.training.lms.entity.Borrower;

@Component
public class BorrowerDAO extends BaseDAO<Borrower> {
    public BorrowerDAO(Connection conn) {
        super(conn);
    }

    public Integer addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        return saveWithPK("INSERT INTO tbl_borrower (name, address, phone) VALUES (?, ?, ?)", new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
    }

    public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
                new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() } );
    }

    public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getCardNo() });
    }

    public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {
        return read("SELECT * FROM tbl_borrower", null);
    }

    public List<Borrower> readABorrower(Integer cardNo) throws SQLException {
        return read("SELECT * FROM tbl_borrower WHERE cardNo = ?;", new Object[] { cardNo });
    }

    @Override
    public List<Borrower> extractData(ResultSet rs) throws SQLException {
        List<Borrower> borrowers = new ArrayList<>();
        while (rs.next()) {
            Borrower borrower = new Borrower();
            borrower.setCardNo(rs.getInt("cardNo"));
            borrower.setName(rs.getString("name"));
            borrower.setAddress(rs.getString("name"));
            borrower.setPhone(rs.getString("name"));
            borrowers.add(borrower);
        }
        return borrowers;
    }
}