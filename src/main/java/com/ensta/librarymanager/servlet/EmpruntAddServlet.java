package com.ensta.librarymanager.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Emprunt;

import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

public class EmpruntAddServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		
		List<Membre> membres = new ArrayList<Membre>();
		List<Livre> livres = new ArrayList<Livre>();
		try{
            livres = livreService.getListDispo();
            membres = membreService.getList();
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("livres", livres);
		request.setAttribute("membres", membres);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_add.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		String inputIdLivre = request.getParameter("idDuLivre");
		String inputIdMembre = request.getParameter("idDuMembre");
		
		try {
			int idLivre = Integer.parseInt(inputIdLivre);
			int idMembre = Integer.parseInt(inputIdMembre);
			empruntService.create(idMembre, idLivre, LocalDate.now());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(NumberFormatException ebis){
			throw new ServletException("Erreur lors du parsing : idMembre="+inputIdMembre+" idLivre="+inputIdLivre,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try{
			emprunts = empruntService.getListCurrent();
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
		dispatcher.forward(request, response);
	}
}
