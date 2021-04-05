package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Livre;

import com.ensta.librarymanager.service.LivreServiceImpl;

public class LivreAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_add.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		String inputTitre = request.getParameter("titre");
		String inputAuteur = request.getParameter("auteur");
		String inputIsbn = request.getParameter("isbn");
		
		String inputId ="-1";
		int id = -1;
		Livre newLivre;
		try {
			id = livreService.create(inputTitre, inputAuteur, inputIsbn);
			inputId = String.valueOf(id);
			newLivre = livreService.getById(id);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			newLivre = new Livre();
		}
		
		request.setAttribute("id",inputId);
		request.setAttribute("livre", newLivre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
	}
}
