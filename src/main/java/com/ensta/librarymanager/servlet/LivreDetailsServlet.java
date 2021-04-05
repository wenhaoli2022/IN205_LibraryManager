package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Emprunt;

import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

public class LivreDetailsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String inputId = request.getParameter("id");
		int id = -1;
		
		Livre livre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		
		try{
			id = Integer.parseInt(inputId);
			livre = livreService.getById(id);
			listEmprunts = empruntService.getListCurrentByLivre(id);
		} catch(ServiceException e) {
			livre = new Livre();
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch(NumberFormatException ebis){
			livre = new Livre();
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("livre",livre);
		request.setAttribute("emprunts",listEmprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputId = request.getParameter("id");
		String inputTitre = request.getParameter("titre");
		String inputAuteur = request.getParameter("auteur");
		String inputIsbn = request.getParameter("isbn");
		
		int id = -1;
		
		Livre livre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		try {
			id = Integer.parseInt(inputId);
						
			livre = livreService.getById(id);
			livre.setTitre(inputTitre);
			livre.setAuteur(inputAuteur);
			livre.setIsbn(inputIsbn);
			
			livreService.update(livre);
			listEmprunts = empruntService.getListCurrentByLivre(id);
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			livre = new Livre();
		} catch(NumberFormatException ebis){
			livre = new Livre();
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("emprunts",listEmprunts);
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}
}
