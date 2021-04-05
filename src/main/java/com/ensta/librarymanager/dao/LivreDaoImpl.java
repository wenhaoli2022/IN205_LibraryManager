package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {
	
	String SELECT_ALL =  "SELECT id, titre, auteur, isbn FROM livre;";
	String SELECT_ID = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
	String CREATE = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	String UPDATE = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	String DELETE = "DELETE FROM livre WHERE id = ?;";
	String COUNT = "SELECT COUNT(id) AS count FROM livre;";
	
	private static LivreDaoImpl instance;
    private LivreDaoImpl() {}
    
    // Singleton
    public static LivreDaoImpl getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }
	
	public List<Livre> getList() throws DaoException{
		List<Livre> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                       Livre livre =  new Livre();
                       livre.setId(rs.getInt("id"));
                       livre.setTitre(rs.getString("titre"));
                       livre.setAuteur(rs.getString("auteur"));
                       livre.setIsbn(rs.getString("isbn"));
                       result.add(livre);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public Livre getById(int id) throws DaoException{
		Livre livre =  new Livre();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL)){
                   ResultSet rs = preparedStatement.executeQuery();
                   livre.setId(rs.getInt("id"));
                   livre.setTitre(rs.getString("titre"));
                   livre.setAuteur(rs.getString("auteur"));
                   livre.setIsbn(rs.getString("isbn"));
                   return livre;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public int create(String titre, String auteur, String isbn) throws DaoException{
		int id = 0;
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(CREATE)){
        		   conn.setAutoCommit(false);
                   preparedStatement.setString(1, titre);
                   preparedStatement.setString(2, auteur);
                   preparedStatement.setString(3, isbn);
       			
                   preparedStatement.executeUpdate();
       			
       			   ResultSet rs = preparedStatement.getGeneratedKeys();
       			   if(rs.next()){
       				   id = rs.getInt(1);
       			   }
       			
       			   preparedStatement.close();
       			
       			   conn.commit();
       			   return id;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public void update(Livre livre) throws DaoException{
		 try (Connection conn = ConnectionManager.getConnection();
	               PreparedStatement preparedStatement = conn.prepareStatement(UPDATE)){
	        		   conn.setAutoCommit(false);
	                   preparedStatement.setString(1, livre.getTitre());
	                   preparedStatement.setString(2, livre.getAuteur());
	                   preparedStatement.setString(3, livre.getIsbn());
	                   preparedStatement.setString(4, String.valueOf(livre.getId()));
	                   
	                   preparedStatement.executeUpdate();
	       			   preparedStatement.close();
	       			
	       			   conn.commit();
	        } catch (SQLException e){
	            throw new DaoException("Erreur",e); 
	        }
	}
	
	public void delete(int id) throws DaoException{
		try (Connection conn = ConnectionManager.getConnection();
	               PreparedStatement preparedStatement = conn.prepareStatement(DELETE)){
	        		   conn.setAutoCommit(false);
	                   preparedStatement.setString(1, String.valueOf(id));
	                   
	                   preparedStatement.executeUpdate();
	       			   preparedStatement.close();
	       			
	       			   conn.commit();
	        } catch (SQLException e){
	            throw new DaoException("Erreur",e); 
	        }
	}
	
	public int count() throws DaoException{
		int count=0;
		try (Connection conn = ConnectionManager.getConnection();
	               PreparedStatement preparedStatement = conn.prepareStatement(COUNT)){
	        		   conn.setAutoCommit(false);
	                   preparedStatement.executeUpdate();
	                   ResultSet rs = preparedStatement.executeQuery();
	                   
	       			   count = rs.getInt(count);
	                   
	       			   preparedStatement.close();
	       			   conn.commit();
	       			   return count;
	        } catch (SQLException e){
	            throw new DaoException("Erreur",e); 
	        }
	}
}
