package com.ensta.librarymanager.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.dao.LivreDaoImpl;

public class LivreServiceImpl {
	
	private static LivreServiceImpl instance;
	private LivreServiceImpl(){}
	
	// Singleton
	public static LivreServiceImpl getInstance() {
		if(instance==null) {
			instance = new LivreServiceImpl();
		}
		return instance;
	}
	
	public List<Livre> getList() throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
	    List<Livre> livres = new ArrayList<Livre>();
	        
	    try{
			livres = livreDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
			
		return livres;
	}
	
	public List<Livre> getListDispo() throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
		try{
			livres = livreDao.getList();
			Iterator<Livre> iter = livres.listIterator();

			while (iter.hasNext()) {
				Livre livre = iter.next();
				if (!empruntService.isLivreDispo(livre.getId())) {
					iter.remove();
				}
			}
				

		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livres;
	}
	
	public Livre getById(int id) throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        
		try{
			livre = livreDao.getById(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livre;
	}
	
	public int create(String titre, String auteur, String isbn) throws ServiceException{
		 LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
	     int id = 0;
		 if (titre == null) {throw new ServiceException("Le titre ne peut pas etre vide");}
			
		 try{
			 id = livreDao.create(titre, auteur, isbn);
		 } catch (DaoException e){
			 System.out.println(e.getMessage());
		 }
			
		 return id;
	}
	
	public void update(Livre livre) throws ServiceException{
		 LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		 if (livre.getTitre() == null) {throw new ServiceException("Le titre ne peut pas etre vide");}
			
		 try{
			 livreDao.update(livre);
		 } catch (DaoException e){
			 System.out.println(e.getMessage());
		 }
	}
	
	public void delete(int id) throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

		try{
			livreDao.delete(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
	}
	
	public int count() throws ServiceException{
		 LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
	     int count = 0;
	        
		 try{
			 count = livreDao.count();
		 } catch (DaoException e){
			 System.out.println(e.getMessage());
		 }
			
		 return count;
	}
}
