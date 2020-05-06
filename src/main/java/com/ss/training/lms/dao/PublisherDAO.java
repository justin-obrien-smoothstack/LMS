package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {
    public PublisherDAO(Connection conn) {
        super(conn);
    }

    public Integer addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		return saveWithPK("INSERT INTO tbl_publisher (publisherName, publisherAddress) VALUES (?, ?)", new Object[] {publisher.getPublisherName(), publisher.getAddress()});
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ? WHERE publisherId = ?", new Object[] {publisher.getPublisherName(), publisher.getAddress(), publisher.getPublisherID()});
	}

	public void deletePublisher(Publisher publisher)  throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[]{publisher.getPublisherID()});
	}
	
	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_publisher", null);
	}

	public List<Publisher> readAPublisher(Integer publisherId) throws SQLException{
		return read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[]{ publisherId });
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publisheres = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherID(rs.getInt("publisherId"));
            publisher.setPublisherName(rs.getString("publisherName"));
            publisher.setAddress(rs.getString("publisherAddress"));
			publisheres.add(publisher);
		}
		return publisheres;
	}
}
