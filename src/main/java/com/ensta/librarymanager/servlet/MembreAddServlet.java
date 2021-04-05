package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Membre;

import com.ensta.librarymanager.service.MembreServiceImpl;

public class MembreAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_add.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreServiceImpl membreService = MembreServiceImpl.getInstance();
		String inputNom = request.getParameter("nom");
		String inputPrenom = request.getParameter("prenom");
		String inputAdresse = request.getParameter("adresse");
		String inputEmail = request.getParameter("email");
		String inputTelephone = request.getParameter("telephone");
		
		String inputId ="-1";
		int id = -1;
		Membre newMembre;
		try {
			id = membreService.create(inputNom, inputPrenom, inputAdresse, inputEmail, inputTelephone);
			inputId = String.valueOf(id);
			newMembre = membreService.getById(id);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			newMembre = new Membre();
		}
		
		request.setAttribute("id",inputId);
		request.setAttribute("membre", newMembre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_details.jsp");
		dispatcher.forward(request, response);
	}
}
