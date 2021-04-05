package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {
	
	String SELECT_ALL =  "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom prenom;";
	String SELECT_ID = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
	String CREATE = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	String UPDATE = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
	String DELETE = "DELETE FROM membre WHERE id = ?;";
	String COUNT = "SELECT COUNT(id) AS count FROM membre;";
	
	private static MembreDaoImpl instance;
	private MembreDaoImpl(){}
	
	// Singleton
	public static MembreDaoImpl getInstance() {
		if(instance==null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	
	public List<Membre> getList() throws DaoException{
        List<Membre> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                       Membre membre =  new Membre();
                       membre.setId(rs.getInt("id"));
                       membre.setNom(rs.getString("nom"));
                       membre.setPrenom(rs.getString("prenom"));
                       membre.setAdresse(rs.getString("adresse"));
                       membre.setEmail(rs.getString("email"));
                       membre.setTelephone(rs.getString("telephone"));
                       membre.setAbonnement(Abonnement.valueOf(rs.getString("abonnement")));
                       result.add(membre);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
    }
    
	public Membre getById(int id) throws DaoException{
		Membre membre = new Membre();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ID)){
                   ResultSet rs = preparedStatement.executeQuery();
                   membre.setId(rs.getInt("id"));
                   membre.setNom(rs.getString("nom"));
                   membre.setPrenom(rs.getString("prenom"));
                   membre.setAdresse(rs.getString("adresse"));
                   membre.setEmail(rs.getString("email"));
                   membre.setTelephone(rs.getString("telephone"));
                   membre.setAbonnement(Abonnement.valueOf(rs.getString("abonnement")));
                   return membre;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
		int id = 0;
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(CREATE)){
        		   conn.setAutoCommit(false);
                   preparedStatement.setString(1, nom);
                   preparedStatement.setString(2, prenom);
                   preparedStatement.setString(3, adresse);
                   preparedStatement.setString(4, email);
                   preparedStatement.setString(5, telephone);
                   preparedStatement.setString(6,"BASIC");		//BASIC par defaut.
       			
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
	
	public void update(Membre membre) throws DaoException{
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(UPDATE)){
        		   conn.setAutoCommit(false);
                   preparedStatement.setString(1, membre.getNom());
                   preparedStatement.setString(2, membre.getPrenom());
                   preparedStatement.setString(3, membre.getAdresse());
                   preparedStatement.setString(4, membre.getEmail());
                   preparedStatement.setString(5, membre.getTelephone());
                   preparedStatement.setString(6, membre.getAbonnement().name());
                   preparedStatement.setString(7, String.valueOf(membre.getId()));
                   
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
