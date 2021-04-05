package com.ensta.librarymanager.service;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.dao.MembreDaoImpl;

public class MembreServiceImpl {
	
	private static MembreServiceImpl instance;
	private MembreServiceImpl(){}
	
	// Singleton
	public static MembreServiceImpl getInstance() {
		if(instance==null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}
	
	public List<Membre> getList() throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		List<Membre> membres = new ArrayList<Membre>();
		try{
			membres = membreDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(membres);
	}
	
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		
		List<Membre> membres = new ArrayList<Membre>();
		List<Membre> membresPossible = new ArrayList<Membre>();
		try{
			membres = membreDao.getList();
			for(Membre m : membres){
				if(empruntService.isEmpruntPossible(m)){
					membresPossible.add(m);
				}
			}
		}
		catch(DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(membresPossible);
	}
	
	public Membre getById(int id) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return(membre);
	}
	
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		int id = 0;
		
		if(nom.isEmpty()||prenom.isEmpty()){
			throw new ServiceException("Les prénoms et noms ne peuvent pas etre vide");
		}
		else{
			MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
			try{
				id = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
			} catch(DaoException e){
				System.out.println(e.getMessage());
			}
		}
		
		return(id);
	}
	
	public void update(Membre membre) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		String nom = membre.getNom();
		if(nom.isEmpty()||(membre.getPrenom()).isEmpty()){
			throw new ServiceException("Les prénoms et noms ne peuvent pas etre vide");
		}
		else{
			try{
				membre.setNom(nom.toUpperCase());
				membreDao.update(membre);
			} catch(DaoException e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void delete(int id) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		try{
			membreDao.delete(id);
		} catch(DaoException e){
			System.out.println(e.getMessage());
		}
	}
	
	public int count() throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		int count = 0;
		try{
			count = membreDao.count();
		} catch(DaoException e){
			System.out.println(e.getMessage());
		}
		
		return(count);
	}
}
