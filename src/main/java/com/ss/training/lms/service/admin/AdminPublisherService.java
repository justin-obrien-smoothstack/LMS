package com.ss.training.lms.service.admin;

import com.ss.training.lms.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.PublisherDAO;
import com.ss.training.lms.entity.Publisher;

public class AdminPublisherService {
	ConnectionUtil connUtil;
    
    public Integer addAPublisher(Publisher publisher) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PublisherDAO borDAO = new PublisherDAO(conn);
            Integer primaryKey = borDAO.addPublisher(publisher);
            conn.commit();
            return primaryKey;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not add that publisher.");
            e.printStackTrace();
            conn.rollback();
            return 0;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void deleteAPublisher(Publisher publisher) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PublisherDAO pubDAO = new PublisherDAO(conn);
            BookDAO bookDAO = new BookDAO(conn);
            bookDAO.deleteBooksByPublisher(publisher.getPublisherID());
            pubDAO.deletePublisher(publisher);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not delete that publisher.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public void updateAPublisher(Publisher publisher) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PublisherDAO borDAO = new PublisherDAO(conn);
            borDAO.updatePublisher(publisher);
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not update that publisher.");
            conn.rollback();
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public Publisher readAPublisher(Integer pubId) throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PublisherDAO borDAO = new PublisherDAO(conn);
            List<Publisher> publishers = borDAO.readAPublisher(pubId);
            if(publishers.size() == 0) {
                return null;
            }
            return publishers.get(0);
        } catch ( SQLException e) {
            System.out.println("We could not read the publisher.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }

    public List<Publisher> readAllPublishers() throws SQLException {
        Connection conn = null;
        try {
            conn = connUtil.getConnection();
            PublisherDAO borDAO = new PublisherDAO(conn);
            List<Publisher> publishers = borDAO.readAllPublishers();
            if(publishers.size() == 0) {
                return null;
            }
            return publishers;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("We could not read the publishers.");
            conn.rollback();
            return null;
        } finally {
			if(conn!=null){
				conn.close();
			}
		}
    }
}