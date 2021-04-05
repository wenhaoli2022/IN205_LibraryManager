package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;

public class EmpruntServiceImpl {
	
	private static EmpruntServiceImpl instance;
	private EmpruntServiceImpl(){}
	
	// Singleton
	public static EmpruntServiceImpl getInstance() {
		if(instance==null) {
			instance = new EmpruntServiceImpl();
		}
		return instance;
    }
	
	public List<Emprunt> getList() throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunts;
	}
	
	public List<Emprunt> getListCurrent() throws ServiceException{
		 EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
	     List<Emprunt> emprunts = new ArrayList<Emprunt>();
	        
		 try{
			 emprunts = empruntDao.getListCurrent();
		 } catch (DaoException e){
			 System.out.println(e.getMessage());
		 }
			
		 return emprunts;
	}
	
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
		 EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
	     List<Emprunt> emprunts = new ArrayList<Emprunt>();
	        
		 try{
			 emprunts = empruntDao.getListCurrentByMembre(idMembre);
		 } catch (DaoException e){
			 System.out.println(e.getMessage());
		 }
			
		 return emprunts;
	}
	
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        
		try{
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunts;
	}
	
	public Emprunt getById(int id) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        
		try{
			emprunt = empruntDao.getById(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return emprunt;
	}
	
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        
		try{
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void returnBook(int id) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

		try{
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
	}
	
	public int count() throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        int count = 0;
        
		try{
			count = empruntDao.count();
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return count;
	}
	
	public boolean isLivreDispo(int idLivre) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        boolean result = false;
        
		try{
            List<Emprunt> listLivre = empruntDao.getListCurrentByLivre(idLivre);
            if(listLivre.size() == 0) {
                result = true;
            }
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return result;
	}
	
	public boolean isEmpruntPossible(Membre membre) throws ServiceException{
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        boolean result = false;
        
		try{
            List<Emprunt> listemprunt = empruntDao.getListCurrentByMembre(membre.getId());
            Abonnement abonnement = membre.getAbonnement();
            int n_emprunts = listemprunt.size();
            switch(abonnement) {
                case BASIC :
                    if (n_emprunts < 2) {result = true;} break;
                case PREMIUM :
                    if (n_emprunts < 5) {result = true;} break;
                case VIP :
                    if (n_emprunts < 20) {result = true;} break;
            }
		} catch (DaoException e){
			System.out.println(e.getMessage());
        }
        return result;
	}
}
