package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {
	
	String SELECT_ALL =  "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,"
			+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
			+ "FROM emprunt AS e"
			+ "INNER JOIN membre ON membre.id = e.idMembre"
			+ "INNER JOIN livre ON livre.id = e.idLivre"
			+ "ORDER BY dateRetour DESC;";
	String SELECT_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
			+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
			+ "FROM emprunt AS e"
			+ "INNER JOIN membre ON membre.id = e.idMembre"
			+ "INNER JOIN livre ON livre.id = e.idLivre"
			+ "WHERE dateRetour IS NULL;";
	String SELECT_CURRENT_MEMBRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
			+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
			+ "FROM emprunt AS e"
			+ "INNER JOIN membre ON membre.id = e.idMembre"
			+ "INNER JOIN livre ON livre.id = e.idLivre"
			+ "WHERE dateRetour IS NULL AND membre.id = ?;";
	String SELECT_CURRENT_LIVRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
			+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
			+ "FROM emprunt AS e"
			+ "INNER JOIN membre ON membre.id = e.idMembre"
			+ "INNER JOIN livre ON livre.id = e.idLivre"
			+ "WHERE dateRetour IS NULL AND livre.id = ?;";
	String SELECT_ID = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
			+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
			+ "FROM emprunt AS e"
			+ "INNER JOIN membre ON membre.id = e.idMembre"
			+ "INNER JOIN livre ON livre.id = e.idLivre"
			+ "WHERE e.id = ?;";
	String CREATE = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	String UPDATE = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
	String COUNT = "SELECT COUNT(id) AS count FROM emprunt;";
	
	private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl() {}
    
    // Singleton
    public static EmpruntDaoImpl getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }
	
	public List<Emprunt> getList() throws DaoException{
		List<Emprunt> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                	   Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                       Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
       				   Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
       				   result.add(emprunt);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CURRENT)){
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                	   Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                       Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
       				   Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
       				   result.add(emprunt);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CURRENT_MEMBRE)){
        		   preparedStatement.setInt(1, idMembre);
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                	   Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                       Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
       				   Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
       				   result.add(emprunt);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> result = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CURRENT_LIVRE)){
        		   preparedStatement.setInt(1, idLivre);
                   ResultSet rs = preparedStatement.executeQuery();
                   while (rs.next()){
                	   Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                       Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
       				   Emprunt emprunt = new Emprunt(rs.getInt("id"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
       				   result.add(emprunt);
                   }
                return result;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public Emprunt getById(int id) throws DaoException{
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ID)){
        		   preparedStatement.setInt(1, id);
                   ResultSet rs = preparedStatement.executeQuery();
                   Livre livre = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                   Membre membre = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
                   Emprunt emprunt = new Emprunt(rs.getInt("idEmprunt"), membre, livre, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                   return emprunt;
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
        try (Connection conn = ConnectionManager.getConnection();
               PreparedStatement preparedStatement = conn.prepareStatement(CREATE)){
        		   conn.setAutoCommit(false);
                   preparedStatement.setInt(1, idMembre);
                   preparedStatement.setInt(2, idLivre);
                   preparedStatement.setDate(3, java.sql.Date.valueOf(dateEmprunt));
                   preparedStatement.setDate(4, java.sql.Date.valueOf("NULL"));  //NULL par defaut.
       			
                   preparedStatement.executeUpdate();
       			   preparedStatement.close();
       			   conn.commit();
        } catch (SQLException e){
            throw new DaoException("Erreur",e); 
        }
	}
	
	public void update(Emprunt emprunt) throws DaoException{
		try (Connection conn = ConnectionManager.getConnection();
	               PreparedStatement preparedStatement = conn.prepareStatement(UPDATE)){
	        		   conn.setAutoCommit(false);
	                   preparedStatement.setInt(1, emprunt.getMembre().getId());
	                   preparedStatement.setInt(2, emprunt.getLivre().getId());
	                   preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
	                   preparedStatement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
	                   preparedStatement.setInt(5, emprunt.getId());
	                   
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
