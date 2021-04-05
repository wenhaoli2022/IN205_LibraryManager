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

import com.ensta.librarymanager.model.Emprunt;

import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

public class DashboardServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreServiceImpl membreService = MembreServiceImpl.getInstance();
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		
		int nbMembres = 0;
		int nbLivres = 0;
		int nbEmprunts = 0;
		
		List<Emprunt> empruntsEnCours = new ArrayList<Emprunt>();
		try {
			nbMembres = membreService.count();
			nbLivres = livreService.count();
			nbEmprunts = empruntService.count();
			empruntsEnCours = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nbMembres", nbMembres);
		request.setAttribute("nbLivres",nbLivres);
		request.setAttribute("nbEmprunts",nbEmprunts);
		request.setAttribute("empruntsEnCours",empruntsEnCours);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
		dispatcher.forward(request, response);
	}
}
