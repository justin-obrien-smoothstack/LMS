package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Author;

public class AuthorDAO extends BaseDAO<Author>{
    public AuthorDAO(Connection conn) {
        super(conn);
    }

    public Integer addAuthor(Author author) throws ClassNotFoundException, SQLException{
		return saveWithPK("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] {author.getAuthorName()});
	}

	public void updateAuthor(Author author)  throws ClassNotFoundException, SQLException{
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}

	public void deleteAuthor(Author author)  throws ClassNotFoundException, SQLException{
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[]{author.getAuthorId()});
	}
	
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_author", null);
	}

	public List<Author> readAnAuthor(Integer authorId) throws ClassNotFoundException, SQLException{
		return read("SELECT * FROM tbl_author WHERE authorId = ?", new Object[]{ authorId });
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
}