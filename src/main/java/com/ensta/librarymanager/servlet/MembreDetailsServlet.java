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

import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Emprunt;

import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

public class MembreDetailsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputId = request.getParameter("id");
		int id = -1;
		
		Membre membre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		MembreServiceImpl membreService = MembreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		
		try{
			id = Integer.parseInt(inputId);
			membre = membreService.getById(id);
			listEmprunts = empruntService.getListCurrentByMembre(id);
			
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();
			
		} catch(NumberFormatException ebis){
			membre = new Membre();
			throw new ServletException(("Erreur lors du parsing : id="+inputId),ebis);
		}
		
		request.setAttribute("emprunts",listEmprunts);
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_details.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputId = request.getParameter("id");
		String inputNom = request.getParameter("nom");
		String inputPrenom = request.getParameter("prenom");
		String inputAdresse = request.getParameter("adresse");
		String inputEmail = request.getParameter("email");
		String inputTelephone = request.getParameter("telephone");
		String inputAbonnement = request.getParameter("abonnement");
		
		
		Abonnement abonnement = Abonnement.valueOf("BASIC");
		int id = -1;
		
		Membre membre = new Membre();
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();
		
		MembreServiceImpl membreService = MembreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		try {
			id = Integer.parseInt(inputId);
			try{
				abonnement = Abonnement.valueOf(inputAbonnement);
			} catch (Exception e){
				throw new ServletException("Type d'abonnement inexistant",e);
			}

			membre = membreService.getById(id);
			membre.setNom(inputNom);
			membre.setPrenom(inputPrenom);
			membre.setAdresse(inputAdresse);
			membre.setEmail(inputEmail);
			membre.setTelephone(inputTelephone);
			membre.setAbonnement(abonnement);
			
			membreService.update(membre);
			listEmprunts = empruntService.getListCurrentByMembre(id);
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();
		} catch(NumberFormatException ebis){
			membre = new Membre();
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		}
		
		request.setAttribute("emprunts",listEmprunts);
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_details.jsp");
		dispatcher.forward(request, response);
	}
}
